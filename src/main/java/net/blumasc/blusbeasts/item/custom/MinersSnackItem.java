package net.blumasc.blusbeasts.item.custom;

import net.blumasc.blusbeasts.entity.ModEntities;
import net.blumasc.blusbeasts.entity.custom.MinersSnackEntity;
import net.blumasc.blusbeasts.item.ModItems;
import net.blumasc.blusbeasts.item.custom.helper.EffectIngredientMapper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.CRC32;

public class MinersSnackItem extends FishBoneSkewerFoodItem{
    public MinersSnackItem(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable FoodProperties getFoodProperties(ItemStack stack, @Nullable LivingEntity entity) {
        if (!(entity instanceof Player player)) {
            return super.getFoodProperties(stack, entity);
        }

        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        if (data == null) return super.getFoodProperties(stack, entity);

        CompoundTag tag = data.copyTag();

        int hunger = tag.getInt("hunger");
        int saturation = tag.getInt("saturation");
        return new FoodProperties.Builder().nutrition(hunger+1).saturationModifier(saturation/10.0f).build();
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {

        if (!(entity instanceof Player player)) {
            return stack;
        }

        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        if (data == null) return stack;

        CompoundTag tag = data.copyTag();

        int hunger = tag.getInt("hunger");
        int saturation = tag.getInt("saturation");

        player.getFoodData().eat(hunger+1, saturation/10.0f);

        if (tag.contains("effectTop")) {
            MobEffect effect = BuiltInRegistries.MOB_EFFECT.get(
                    ResourceLocation.parse(tag.getString("effectTop"))
            );
            Holder<MobEffect> holder = BuiltInRegistries.MOB_EFFECT.wrapAsHolder(effect);
            int duration = (tag.getInt("durationTop")+1)*5*20;

            if (effect != null) {
                player.addEffect(new MobEffectInstance(holder, duration));
            }
        }

        if (tag.contains("effectBottom")) {
            MobEffect effect = BuiltInRegistries.MOB_EFFECT.get(
                    ResourceLocation.parse(tag.getString("effectBottom"))
            );
            Holder<MobEffect> holder = BuiltInRegistries.MOB_EFFECT.wrapAsHolder(effect);
            int duration = (tag.getInt("durationBottom")+1)*5*20;

            if (effect != null) {
                player.addEffect(new MobEffectInstance(holder, duration));
            }
        }

        return new ItemStack(ModItems.FISH_BONE_SKEWER.get());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {

        ItemStack stack = player.getItemInHand(hand);

        BlockHitResult hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.WATER);

        if (hitResult.getType() != HitResult.Type.BLOCK) {
            return super.use(level, player, hand);
        }

        BlockPos pos = hitResult.getBlockPos();

        if (!level.isClientSide) {

            CustomData data = stack.get(DataComponents.CUSTOM_DATA);

            MinersSnackEntity fish = ModEntities.MINER_SNACK.get().create(level);

            if (fish != null && data != null) {
                fish.readCustomDataFromTag(data.copyTag());
                fish.moveTo(
                        pos.getX() + 0.5,
                        pos.getY() + 0.5,
                        pos.getZ() + 0.5,
                        player.getYRot(),
                        0
                );

                level.addFreshEntity(fish);

                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
            }
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        if (data == null){
            super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
            return;
        }

        CompoundTag tag = data.copyTag();
        MobEffect first = null;
        int duration = 0;
        if (tag.contains("effectTop")) {
            first = BuiltInRegistries.MOB_EFFECT.get(
                    ResourceLocation.parse(tag.getString("effectTop"))
            );
            duration += tag.getInt("durationTop");
        }
        MobEffect second = null;
        if (tag.contains("effectBottom")) {
            second = BuiltInRegistries.MOB_EFFECT.get(
                    ResourceLocation.parse(tag.getString("effectBottom"))
            );
            duration += tag.getInt("durationBottom");
        }
        if(second==null || first==null){
            super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
            return;
        }
        EffectIngredientMapper.IngredientPair pair = EffectIngredientMapper.getIngredientPair(hashToLong("Fuck it, we ball"),first, second);
        tooltipComponents.add(Component.translatable("item.blusbeasts.donut.tooltip_"+duration, Component.translatable("item.blusbeasts.donut.type_"+pair.second(), Component.translatable("item.blusbeasts.donut.flavour_"+pair.first()))).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    private static long hashToLong(String input) {
        CRC32 crc = new CRC32();
        crc.update(input.getBytes(StandardCharsets.UTF_8));
        return crc.getValue();
    }
}
