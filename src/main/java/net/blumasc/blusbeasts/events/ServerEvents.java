package net.blumasc.blusbeasts.events;

import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.Config;
import net.blumasc.blusbeasts.damage.ModDamageTypes;
import net.blumasc.blusbeasts.effect.ModEffects;
import net.blumasc.blusbeasts.entity.ModEntities;
import net.blumasc.blusbeasts.entity.custom.EndWyvernEntity;
import net.blumasc.blusbeasts.entity.custom.PersonalMinecart;
import net.blumasc.blusbeasts.entity.custom.PrayfinderEntity;
import net.blumasc.blusbeasts.entity.custom.RootlingEntity;
import net.blumasc.blusbeasts.item.ModItems;
import net.blumasc.blusbeasts.network.DragonWingSingleSyncPacket;
import net.blumasc.blusbeasts.network.DragonWingSyncPacket;
import net.blumasc.blusbeasts.playerstate.PlayerDragonState;
import net.blumasc.blusbeasts.playerstate.PlayerDragonStateHandler;
import net.blumasc.blusbeasts.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.entity.item.ItemExpireEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockDropsEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.*;

@EventBusSubscriber(modid = BlusBeastsMod.MODID)
public class ServerEvents {
    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        LivingEntity dead = event.getEntity();

        double range = 48.0D;
        AABB box = dead.getBoundingBox().inflate(range);

        List<Mob> mobs = dead.level().getEntitiesOfClass(
                Mob.class,
                box,
                mob -> mob.getTarget() == dead
        );

        for (Mob mob : mobs) {
            mob.setTarget(null);
        }
    }
    @SubscribeEvent
    public static void onPraypartnerSpawn(FinalizeSpawnEvent event){
        if(event.isSpawnCancelled()) return;
        if(event.getSpawnType() != MobSpawnType.NATURAL) return;
        LivingEntity spawned = event.getEntity();
        Level level = event.getLevel().getLevel();
        if (level.isClientSide) return;
        if (!spawned.getType().is(ModTags.EntityTypes.PRAYFINDER_FRIENDS)) return;
        if(level.isDay()) return;
        if(level.dimension() != Level.OVERWORLD) return;
        BlockPos pos = spawned.blockPosition();
        if (level.getBrightness(LightLayer.SKY, pos) <= 5) return;
        long totalDays = level.getDayTime() / 24000L;
        float chance = 0.0f;
        if(totalDays > Config.PRAYFINDER_DAY_REQUIREMENT.get()) {
            chance = 0.01f * (totalDays - 10);
            if(chance > 0.22f) chance = 0.22f;
        }
        if (level.random.nextFloat() > chance) return;
        PrayfinderEntity prayfinder = ModEntities.PRAYFINDER.get().create(level);
        if (prayfinder != null) {
            prayfinder.moveTo(spawned.getX(), spawned.getY(), spawned.getZ(), spawned.getYRot(), spawned.getXRot());
            level.addFreshEntity(prayfinder);
        }
    }

    @SubscribeEvent
    public static void onRailClicked(PlayerInteractEvent.RightClickBlock event){
        Level l = event.getLevel();
        if(l.isClientSide())return;
        if (event.isCanceled()) return;
        if(l.getBlockState(event.getPos()).is(BlockTags.RAILS))
        {
            Player p = event.getEntity();
            if(isWearingCurio(p, ModItems.PERSONAL_MINECART.get(), "body")){
                PersonalMinecart pm = new PersonalMinecart(l, event.getPos().getX(), event.getPos().getY(), event.getPos().getZ());
                l.addFreshEntity(pm);
                p.startRiding(pm);
                event.setCanceled(true);
            }

        }
    }

    private static boolean isWearingCurio(Player player, Item l, String slotName) {
        Optional<ICuriosItemHandler> curiosInventoryOpt = CuriosApi.getCuriosInventory(player);
        if (curiosInventoryOpt.isPresent()) {
            Map<String, ICurioStacksHandler> curios = curiosInventoryOpt.get().getCurios();
            for (ICurioStacksHandler slotInventory : curios.values()) {
                for (int slot = 0; slot < slotInventory.getSlots(); slot++) {
                    ItemStack stack = slotInventory.getStacks().getStackInSlot(slot);
                    if (!stack.isEmpty() && stack.is(l)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @SubscribeEvent
    public static void playerPlacedPlantEvent(BlockEvent.EntityPlaceEvent e){
        if(e.getLevel().isClientSide()) return;
        Level level = (Level) e.getLevel();
        BlockPos pos = e.getPos();
        if(!(e.getEntity() instanceof Player p)) return;
        BlockState b = e.getPlacedBlock();
        if(!(b.is(ModTags.Blocks.ROOTLING_PLANTS))) return;
        List<RootlingEntity> rootlings = p.level().getEntitiesOfClass(
                RootlingEntity.class,
                p.getBoundingBox().inflate(8),
                player -> true
        );
        for(RootlingEntity re : rootlings){
            re.plantedCloseBy(b);
        }
    }

    @SubscribeEvent
    public static void plantDespawnEvent(ItemExpireEvent e){
        if(!Config.SPAWN_ROOTLING.get()) return;
        if(e.getEntity().level().isClientSide()) return;
        ItemEntity itemEntity = e.getEntity();
        if(itemEntity.getItem().getItem() instanceof BlockItem bi){
            BlockState bs = bi.getBlock().defaultBlockState();
            if(bs.is(ModTags.Blocks.ROOTLING_PLANTS)){
                RootlingEntity re = new RootlingEntity(ModEntities.ROOTLING.get(), itemEntity.level());
                re.setPos(itemEntity.position());
                if(re.setBlock(bs)){
                    itemEntity.level().addFreshEntity(re);
                }
            }
        }
    }

    public static final ResourceLocation WYVERN_FLIGHT_MODIFIER_RESOURCE = ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "wyvern_flight_modifier");
    public static final AttributeModifier WYVERN_FLIGHT_MODIFIER = new AttributeModifier(
            WYVERN_FLIGHT_MODIFIER_RESOURCE,
            1.0,
            AttributeModifier.Operation.ADD_VALUE
    );

    @SubscribeEvent
    public static void onPlayerFlightTick(PlayerTickEvent.Pre e) {
        Player p = e.getEntity();
        if (p.level() instanceof ServerLevel sl) {
            PlayerDragonState pds = PlayerDragonStateHandler.loadState(p);
            if (!pds.emptyBack()) {
                var attribute = p.getAttribute(NeoForgeMod.CREATIVE_FLIGHT);
                if (attribute != null && attribute.getModifier(WYVERN_FLIGHT_MODIFIER_RESOURCE) == null) {
                    attribute.addTransientModifier(WYVERN_FLIGHT_MODIFIER);
                    p.getAbilities().flying = true;
                    p.fallDistance = 0f;
                    p.onUpdateAbilities();
                    PacketDistributor.sendToAllPlayers(new DragonWingSingleSyncPacket(p.getUUID(), true));
                }
                return;
            }

            var attribute = p.getAttribute(NeoForgeMod.CREATIVE_FLIGHT);
            if (attribute != null) {
                attribute.removeModifier(WYVERN_FLIGHT_MODIFIER_RESOURCE);
                PacketDistributor.sendToAllPlayers(new DragonWingSingleSyncPacket(p.getUUID(), false));
            }
        }
    }

    @SubscribeEvent
    public static void wyvernDetatchment(PlayerTickEvent.Post e){
        Player p = e.getEntity();
        if(e.getEntity().level().isClientSide())return;
        PlayerDragonState pds = PlayerDragonStateHandler.loadState(p);
        if(pds.emptyBack()) return;
        if(p.onGround() || pds.toFarFromHome(p.getOnPos())){
            EndWyvernEntity wyvern = pds.detatchWyvern();
            wyvern.setPos(p.position());
            p.level().addFreshEntity(wyvern);
            PlayerDragonStateHandler.saveState(p, pds);
        }
    }

    @SubscribeEvent
    public static void wyvenrInfoOnJoin(PlayerEvent.PlayerLoggedInEvent e){
        Player player = e.getEntity();
        if(player.level().isClientSide()) return;
        if(!(player instanceof ServerPlayer p)) return;
        Level l = p.level();
        Map<UUID, Boolean> wingsData = new HashMap<UUID, Boolean>();
        PlayerDragonState mainPds = PlayerDragonStateHandler.loadState(p);
        for(ServerPlayer pl: l.getServer().getPlayerList().getPlayers()){
            PlayerDragonState pds = PlayerDragonStateHandler.loadState(pl);
            wingsData.put(pl.getUUID(), !pds.emptyBack());
        }
        PacketDistributor.sendToPlayer(
                p,
                new DragonWingSyncPacket(wingsData)
        );
        PacketDistributor.sendToAllPlayers(new DragonWingSingleSyncPacket(p.getUUID(), !mainPds.emptyBack()));
    }

    @SubscribeEvent
    public static void onGeodeBreakBreak(BlockDropsEvent event) {
        Entity e = event.getBreaker();
        if (e == null) return;
        if(!(e instanceof Player p)) return;
        if(!isWearingCurio(p, ModItems.EMBEDDED_CRYSTALS.get(), "body")) return;
        if (e.level().isClientSide) return;
        BlockState s = event.getState();
        if(!(s.is(ModTags.Blocks.BREAKABLE_GEODES))) return;
        for (ItemEntity dropEntity : event.getDrops()) {
            ItemStack drop = dropEntity.getItem();
            if (drop.is(s.getBlock().asItem())){
                return;
            }
        }
        event.getDrops().clear();
        event.getDrops().add(new ItemEntity(e.level(), event.getPos().getX()+0.5, event.getPos().getY()+0.5, event.getPos().getZ()+0.5, new ItemStack(s.getBlock().asItem())));
    }
}
