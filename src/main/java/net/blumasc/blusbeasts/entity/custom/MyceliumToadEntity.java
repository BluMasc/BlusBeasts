package net.blumasc.blusbeasts.entity.custom;

import net.blumasc.blubasics.block.BaseModBlocks;
import net.blumasc.blubasics.effect.BaseModEffects;
import net.blumasc.blusbeasts.block.ModBlocks;
import net.blumasc.blusbeasts.datagen.ModLootTables;
import net.blumasc.blusbeasts.effect.ModEffects;
import net.blumasc.blusbeasts.sound.ModSounds;
import net.blumasc.blusbeasts.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.*;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.minecraft.world.level.block.Block.popResource;

public class MyceliumToadEntity extends Animal {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState sleepAnimationState = new AnimationState();
    public final AnimationState eatAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    private static List<Block> CACHED_MUSHROOMS;
    private boolean initiated = false;
    private int sleep_timer = 0;
    private int stomach = 0;
    private int fleeTimer = 0;
    private static int MAX_STOMACH = 30;
    private static final EntityDataAccessor<BlockState> BACK_BLOCK_0 =
            SynchedEntityData.defineId(MyceliumToadEntity.class, EntityDataSerializers.BLOCK_STATE);
    private static final EntityDataAccessor<BlockState> BACK_BLOCK_1 =
            SynchedEntityData.defineId(MyceliumToadEntity.class, EntityDataSerializers.BLOCK_STATE);
    private static final EntityDataAccessor<BlockState> BACK_BLOCK_2 =
            SynchedEntityData.defineId(MyceliumToadEntity.class, EntityDataSerializers.BLOCK_STATE);
    private static final EntityDataAccessor<Integer> SQUID_LEG =
            SynchedEntityData.defineId(MyceliumToadEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean>  SHELF_RIGHT=
            SynchedEntityData.defineId(MyceliumToadEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean>  SHELF_LEFT=
            SynchedEntityData.defineId(MyceliumToadEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer>  SHELF_RIGHT_HEIGHT=
            SynchedEntityData.defineId(MyceliumToadEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer>  SHELF_RIGHT_DEPTH=
            SynchedEntityData.defineId(MyceliumToadEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer>  SHELF_LEFT_HEIGHT=
            SynchedEntityData.defineId(MyceliumToadEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer>  SHELF_LEFT_DEPTH=
            SynchedEntityData.defineId(MyceliumToadEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean>  SLEEPING=
            SynchedEntityData.defineId(MyceliumToadEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer>  ATTACK_TIMER=
            SynchedEntityData.defineId(MyceliumToadEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer>  EAT_TIMER=
            SynchedEntityData.defineId(MyceliumToadEntity.class, EntityDataSerializers.INT);

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 30d)
                .add(Attributes.MOVEMENT_SPEED, 0.1f)
                .add(Attributes.FOLLOW_RANGE, 12D);
    }

    public static void cacheMushrooms(RegistryAccess access) {
        //if (CACHED_MUSHROOMS != null) return;

        var tag = access.registryOrThrow(Registries.BLOCK).getTag(ModTags.Blocks.MUSHROOM);
        CACHED_MUSHROOMS = tag.orElseThrow().stream().map(h -> h.value()).toList();
    }

    private static String blockStateToString(BlockState state) {
        return BuiltInRegistries.BLOCK.getKey(state.getBlock()).toString();
    }

    private static BlockState stringToBlockState(String id) {
        Block block = BuiltInRegistries.BLOCK.get(ResourceLocation.parse(id));
        return block.defaultBlockState();
    }
    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);

        tag.putBoolean("Randomized", initiated);
        tag.putInt("SleepTimer", sleep_timer);
        tag.putInt("Stomach", stomach);
        tag.putInt("Fleeing", fleeTimer);

        tag.putString("BackBlock0", blockStateToString(getBackBlock0()));
        tag.putString("BackBlock1", blockStateToString(getBackBlock1()));
        tag.putString("BackBlock2", blockStateToString(getBackBlock2()));

        tag.putInt("SquidLeg", getSquidLeg());

        tag.putBoolean("ShelfRight", hasShelfRight());
        tag.putInt("ShelfRightHeight", getShelfRightHeight());
        tag.putInt("ShelfRightDepth", getShelfRightDepth());

        tag.putBoolean("ShelfLeft", hasShelfLeft());
        tag.putInt("ShelfLeftHeight", getShelfLeftHeight());
        tag.putInt("ShelfLeftDepth", getShelfLeftDepth());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        initiated = tag.getBoolean("Randomized");
        sleep_timer = tag.getInt("SleepTimer");
        stomach = tag.getInt("Stomach");
        fleeTimer = tag.getInt("Fleeing");

        this.entityData.set(BACK_BLOCK_0, stringToBlockState(tag.getString("BackBlock0")));
        this.entityData.set(BACK_BLOCK_1, stringToBlockState(tag.getString("BackBlock1")));
        this.entityData.set(BACK_BLOCK_2, stringToBlockState(tag.getString("BackBlock2")));

        this.entityData.set(SQUID_LEG, tag.getInt("SquidLeg"));

        this.entityData.set(SHELF_RIGHT, tag.getBoolean("ShelfRight"));
        this.entityData.set(SHELF_RIGHT_HEIGHT, tag.getInt("ShelfRightHeight"));
        this.entityData.set(SHELF_RIGHT_DEPTH, tag.getInt("ShelfRightDepth"));

        this.entityData.set(SHELF_LEFT, tag.getBoolean("ShelfLeft"));
        this.entityData.set(SHELF_LEFT_HEIGHT, tag.getInt("ShelfLeftHeight"));
        this.entityData.set(SHELF_LEFT_DEPTH, tag.getInt("ShelfLeftDepth"));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(BACK_BLOCK_0, Blocks.RED_MUSHROOM.defaultBlockState());
        builder.define(BACK_BLOCK_1, BaseModBlocks.SPORE_MUSHROOM_BLOCK.get().defaultBlockState());
        builder.define(BACK_BLOCK_2, Blocks.WARPED_FUNGUS.defaultBlockState());
        builder.define(SQUID_LEG, 3);
        builder.define(SHELF_RIGHT, true);
        builder.define(SHELF_LEFT, true);
        builder.define(SHELF_LEFT_DEPTH, 0);
        builder.define(SHELF_RIGHT_DEPTH, 8);
        builder.define(SHELF_LEFT_HEIGHT, 0);
        builder.define(SHELF_RIGHT_HEIGHT, -4);
        builder.define(SLEEPING, false);
        builder.define(ATTACK_TIMER, 0);
        builder.define(EAT_TIMER, 0);
    }

    public void setSleepin(boolean val){
        this.entityData.set(SLEEPING, val);
    }

    public boolean getSleepin(){
        return this.entityData.get(SLEEPING);
    }

    public void setAttackTimer(int val){
        this.entityData.set(ATTACK_TIMER, val);
    }

    public int getAttackTimer(){
        return this.entityData.get(ATTACK_TIMER);
    }

    public void setEatTimer(int val){
        this.entityData.set(EAT_TIMER, val);
    }

    public int getEatTimer(){
        return this.entityData.get(EAT_TIMER);
    }

    public BlockState getBackBlock0() {
        return this.entityData.get(BACK_BLOCK_0);
    }

    public BlockState getBackBlock1() {
        return this.entityData.get(BACK_BLOCK_1);
    }

    public BlockState getBackBlock2() {
        return this.entityData.get(BACK_BLOCK_2);
    }

    public int getSquidLeg() {
        return this.entityData.get(SQUID_LEG);
    }

    public boolean hasShelfRight() {
        return this.entityData.get(SHELF_RIGHT);
    }

    public boolean hasShelfLeft() {
        return this.entityData.get(SHELF_LEFT);
    }

    public int getShelfRightHeight() {
        return this.entityData.get(SHELF_RIGHT_HEIGHT);
    }

    public int getShelfRightDepth() {
        return this.entityData.get(SHELF_RIGHT_DEPTH);
    }

    public int getShelfLeftHeight() {
        return this.entityData.get(SHELF_LEFT_HEIGHT);
    }

    public int getShelfLeftDepth() {
        return this.entityData.get(SHELF_LEFT_DEPTH);
    }

    public int getFleeTimer() { return fleeTimer; }
    public void setFleeTimer(int t) { fleeTimer = t; }

    public MyceliumToadEntity(EntityType<? extends Animal> entityType, Level level) {

        super(entityType, level);
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.6F, 1.0F, true);
        this.setPathfindingMalus(PathType.WATER, 0.0F);
        this.setPathfindingMalus(PathType.WATER_BORDER, 0.0F);
    }

    @Override
    public boolean isSleeping() {
        return getSleepin();
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (this.isControlledByLocalInstance() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
        } else {
            super.travel(travelVector);
        }
    }


    @Override
    public boolean canSwimInFluidType(FluidType type) {
        return true;
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new ToadPathNavigation(this, level);
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return ModSounds.TOAD_DEATH.get();
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.TOAD_HURT.get();
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        if(getSleepin()) return ModSounds.TOAD_SLEEP.get();
        return ModSounds.TOAD_CROAK.get();
    }

    @Override
    public void tick() {
        super.tick();
        if(!this.level().isClientSide) {
            if (!initiated) {
                this.entityData.set(BACK_BLOCK_0, getRandomBlockFromTag(ModTags.Blocks.MUSHROOM));
                this.entityData.set(BACK_BLOCK_1, getRandomBlockFromTag(ModTags.Blocks.MUSHROOM));
                this.entityData.set(BACK_BLOCK_2, getRandomBlockFromTag(ModTags.Blocks.MUSHROOM));
                this.entityData.set(SQUID_LEG, random.nextInt(5));
                boolean rightShelf = random.nextBoolean();
                this.entityData.set(SHELF_RIGHT, rightShelf);
                if (rightShelf) {
                    int shelfdepth = random.nextInt(15) - 7;
                    this.entityData.set(SHELF_RIGHT_DEPTH, shelfdepth);
                    if (shelfdepth > -3 && shelfdepth < 3)
                        this.entityData.set(SHELF_RIGHT_HEIGHT, -random.nextInt(5));
                    else
                        this.entityData.set(SHELF_RIGHT_HEIGHT, (-3 - random.nextInt(2)));
                }
                boolean leftShelf = random.nextBoolean();
                this.entityData.set(SHELF_LEFT, leftShelf);
                if (leftShelf) {
                    int shelfdepth = random.nextInt(15) - 7;
                    this.entityData.set(SHELF_LEFT_DEPTH, shelfdepth);
                    if (shelfdepth > -3 && shelfdepth < 3)
                        this.entityData.set(SHELF_LEFT_HEIGHT, -random.nextInt(5));
                    else
                        this.entityData.set(SHELF_LEFT_HEIGHT, (-3 - random.nextInt(2)));
                }
                initiated = true;
            }
            if(this.getAttackTimer()>0)
            {
                this.setAttackTimer(this.getAttackTimer()-1);
                if(this.getAttackTimer()==15)
                {
                    spawnCloud();
                    level().playSound(null, this.getX(), this.getY(), this.getZ(), ModSounds.TOAD_FART.get(), this.getSoundSource(), 1.0f, random.nextFloat()+0.5f);
                }
            }else if(stomach>=MAX_STOMACH && sleep_timer<=0 && fleeTimer<=0){
                sleep_timer = 20 * 60;
            }
            if(sleep_timer>0)
            {
                setSleepin(true);
                sleep_timer--;
                if(sleep_timer<=0)
                {
                    setSleepin(false);
                    stomach = 0;

                    Item item = getRandomBlockFromLootTable();
                    BlockPos startPos = this.blockPosition().offset(random.nextInt(3) - 1, 0, random.nextInt(3) - 1);
                    BlockPos targetPos = null;
                    if(item instanceof BlockItem bi){
                        BlockState state = bi.getBlock().defaultBlockState();
                        for (int dy = -2; dy <= 2; dy++) {
                            BlockPos pos = startPos.offset(0, dy, 0);
                            if(level().getBlockState(pos).canBeReplaced()) {
                                if (state.canSurvive(level(), pos)) {
                                    targetPos = pos;
                                    break;
                                }
                            }
                        }
                        if (targetPos != null) {
                            level().setBlock(targetPos, state, 3);
                        } else {
                            popResource(this.level(), startPos, new ItemStack(state.getBlock()));
                        }
                    }else {
                        popResource(this.level(), startPos, new ItemStack(item));
                    }
                    level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ITEM_PICKUP, this.getSoundSource(), 1.0f, random.nextFloat()+0.5f);
                }
            }else{
                setSleepin(false);
            }
            if(getEatTimer()>0){
                setEatTimer(getEatTimer()-1);
                if(getEatTimer()==5){
                    level().playSound(null, this.getX(), this.getY(), this.getZ(), ModSounds.TOAD_SWALLOW.get(), this.getSoundSource(), 1.0f, random.nextFloat()+0.5f);
                }
            }
            if(getFleeTimer()>0){
                setFleeTimer(getFleeTimer()-1);
            }
            if (this.isInWater() && !this.getSleepin() && !hasLandExitNearby()) {

                double surface = getWaterSurfaceY();
                double targetY = surface - 1.2;

                double diff = targetY - this.getY();

                Vec3 motion = this.getDeltaMovement();

                double adjust = Mth.clamp(diff * 0.08, -0.04, 0.04);

                this.setDeltaMovement(motion.x, motion.y + adjust, motion.z);
            }
            if (this.isInWater() && this.getSleepin()) {
                Vec3 motion = this.getDeltaMovement();

                this.setDeltaMovement(motion.x, Math.max(motion.y - 0.08, -0.3), motion.z);

                this.getNavigation().stop();
            }
            if (shouldJumpOutOfWater()) {
                Vec3 motion = this.getDeltaMovement();

                this.setDeltaMovement(
                        motion.x * 0.6,
                        0.32,
                        motion.z * 0.6
                );

                this.hasImpulse = true;
            }
        }else{
            setupAnimationState();
        }
    }

    private void spawnCloud() {
        AreaEffectCloud c = new AreaEffectCloud(level(), getX(), getY(), getZ());
        c.setRadius(5);
        c.addEffect(new MobEffectInstance(MobEffects.POISON, 600, 1));
        c.addEffect(new MobEffectInstance(BaseModEffects.HALLUCINATION, 600, 2));
        level().addFreshEntity(c);
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        if (effect.getEffect() == MobEffects.POISON) return false;
        if (effect.getEffect() == BaseModEffects.HALLUCINATION) return false;
        return super.canBeAffected(effect);
    }

    @Override
    protected int decreaseAirSupply(int air) {
        return air;
    }

    @Override
    public boolean canDrownInFluidType(FluidType type) {
        return false;
    }

    private void setupAnimationState() {
        if(getAttackTimer()>0 && getAttackTimer()<31){
            this.idleAnimationState.stop();
            this.sleepAnimationState.stop();
            this.eatAnimationState.stop();
            this.attackAnimationState.startIfStopped(this.tickCount);
            return;
        }else if(getEatTimer()>0){
            this.idleAnimationState.stop();
            this.sleepAnimationState.stop();
            this.attackAnimationState.stop();
            this.eatAnimationState.startIfStopped(this.tickCount);
            return;
        }
        if(getSleepin()){
            this.idleAnimationState.stop();
            this.attackAnimationState.stop();
            this.eatAnimationState.stop();
            this.sleepAnimationState.startIfStopped(this.tickCount);
        }else {
            this.sleepAnimationState.stop();
            this.attackAnimationState.stop();
            this.eatAnimationState.stop();
            this.idleAnimationState.startIfStopped(this.tickCount);
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (!this.level().isClientSide && source.getEntity() instanceof LivingEntity attacker) {
            this.setLastHurtByMob(attacker);
            this.setAttackTimer(50);
            fleeTimer = 60*20;
        }
        return super.hurt(source, amount);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.is(ModTags.Items.TOAD_FOOD)) {

            if (!level().isClientSide) {
                if(sleep_timer>0) return InteractionResult.PASS;
                if(fleeTimer>0) return InteractionResult.PASS;
                FoodProperties food = stack.getFoodProperties(this);
                if (food != null) {
                    stomach = Math.min(MAX_STOMACH, stomach + food.nutrition());

                    if(getEatTimer()<=0) {
                        setEatTimer(15);
                    }

                    if (!player.getAbilities().instabuild)
                        stack.shrink(1);
                }
            }

            return InteractionResult.sidedSuccess(level().isClientSide);
        }

        return super.mobInteract(player, hand);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new ToadFleeGoal(this));

        this.goalSelector.addGoal(2,
                new SleepBlockingGoal(this,
                        new TemptGoal(this, 1.0, (i) -> i.is(ModTags.Items.TOAD_FOOD), false)
                ));

        this.goalSelector.addGoal(3,
                new SleepBlockingGoal(this,
                        new RandomStrollGoal(this, 0.8) {
                            @Override
                            protected Vec3 getPosition() {
                                return DefaultRandomPos.getPos(this.mob, 10, 7);
                            }
                        }
                ));
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return false;
    }

    public static boolean checkMushroomSpawnRules(EntityType<MyceliumToadEntity> myceliumToad, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource randomSource) {
        return level.getBlockState(pos.below()).is(ModTags.Blocks.MYCELIUM_TOAD_SPAWNABLE) && isBrightEnoughToSpawn(level, pos);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }
    public BlockState getRandomBlockFromTag(TagKey<Block> tag) {
        cacheMushrooms(level().registryAccess());
        return CACHED_MUSHROOMS.get(random.nextInt(CACHED_MUSHROOMS.size())).defaultBlockState();
    }

    public Item getRandomBlockFromLootTable() {
        LootTable table = level().getServer()
                .reloadableRegistries()
                .getLootTable(ModLootTables.MYCELIUM_TOAD_LOOT_TABLE);

        LootParams params = new LootParams.Builder((ServerLevel) level())
                .withParameter(LootContextParams.ORIGIN, this.position())
                .create(LootContextParamSets.CHEST);

        List<ItemStack> items = table.getRandomItems(params);

        if (items.isEmpty()){
            return random.nextBoolean()? BaseModBlocks.SPORE_MUSHROOM_BLOCK.asItem() : BaseModBlocks.JUMP_MUSHROOM.asItem();
        }
        return items.get(0).getItem();
    }

    private double getWaterSurfaceY() {
        BlockPos pos = this.blockPosition();

        while (level().getBlockState(pos).getFluidState().isSource()) {
            pos = pos.above();
        }
        return pos.getY();
    }

    private boolean hasLandExitNearby() {
        BlockPos base = this.blockPosition();

        BlockPos.MutableBlockPos pos = base.mutable();
        while (level().getBlockState(pos).getFluidState().isSource()) {
            pos.move(0, 1, 0);
            if (pos.getY() > level().getMaxBuildHeight()) return false;
        }

        BlockPos surface = pos.below();

        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {

                if (dx == 0 && dz == 0) continue;

                BlockPos ground = surface.offset(dx, 0, dz);
                BlockPos feet   = ground.above();

                BlockState groundState = level().getBlockState(ground);
                BlockState feetState   = level().getBlockState(feet);

                if (groundState.isFaceSturdy(level(), ground, Direction.UP) &&
                        feetState.canBeReplaced()) {
                    return true; // climbable shore found
                }
            }
        }

        return false;
    }

    private boolean shouldJumpOutOfWater() {

        if (!this.isInWater()) return false;

        var nav = this.getNavigation();
        if (nav.getPath() == null) return false;

        var path = nav.getPath();

        if (path.isDone() || path.getNextNodeIndex() >= path.getNodeCount())
            return false;

        Vec3 next = Vec3.atCenterOf(path.getNextNodePos());

        if (next.y <= this.getY() + 0.2)
            return false;

        Vec3 look = this.getLookAngle().scale(0.8);
        BlockPos front = BlockPos.containing(this.getX() + look.x, this.getY(), this.getZ() + look.z);

        BlockState ground = level().getBlockState(front);
        BlockState space  = level().getBlockState(front.above());

        return ground.isFaceSturdy(level(), front, Direction.UP)
                && space.canBeReplaced();
    }


    public class ToadFleeGoal extends Goal {

        private final MyceliumToadEntity toad;
        private LivingEntity attacker;
        private Vec3 fleePos;

        public ToadFleeGoal(MyceliumToadEntity toad) {
            this.toad = toad;
        }

        @Override
        public boolean canUse() {
            if (toad.getAttackTimer() > 0) return false;
            if (toad.getFleeTimer() <= 0) return false;

            attacker = toad.getLastHurtByMob();
            if (attacker == null || !attacker.isAlive()) return false;

            fleePos = DefaultRandomPos.getPosAway(toad, 12, 7, attacker.position());
            return fleePos != null;
        }

        @Override
        public boolean canContinueToUse() {
            return toad.getFleeTimer() > 0 ;
        }

        @Override
        public void start() {
            toad.getNavigation().moveTo(fleePos.x, fleePos.y, fleePos.z, 1.4);
        }

        @Override
        public void tick() {
            if(!toad.getNavigation().isInProgress()){
                toad.getNavigation().moveTo(fleePos.x, fleePos.y, fleePos.z, 1.4);
            }
        }

        @Override
        public void stop() {
            toad.setFleeTimer(0);
            super.stop();
        }
    }
    public class SleepBlockingGoal extends Goal {

        private final Goal inner;
        private final MyceliumToadEntity toad;

        public SleepBlockingGoal(MyceliumToadEntity toad, Goal inner) {
            this.toad = toad;
            this.inner = inner;
        }

        @Override
        public boolean canUse() {
            return !toad.getSleepin() && inner.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return !toad.getSleepin() && inner.canContinueToUse();
        }

        @Override
        public void start() { inner.start(); }

        @Override
        public void stop() { inner.stop(); }

        @Override
        public void tick() { inner.tick(); }
    }

    static class ToadNodeEvaluator extends AmphibiousNodeEvaluator {
        private final BlockPos.MutableBlockPos belowPos = new BlockPos.MutableBlockPos();

        public ToadNodeEvaluator(boolean p_218548_) {
            super(p_218548_);
        }

        public Node getStart() {
            return !this.mob.isInWater() ? super.getStart() : this.getStartNode(new BlockPos(Mth.floor(this.mob.getBoundingBox().minX), Mth.floor(this.mob.getBoundingBox().minY), Mth.floor(this.mob.getBoundingBox().minZ)));
        }

        public PathType getPathType(PathfindingContext p_331446_, int p_326799_, int p_326899_, int p_326891_) {
            this.belowPos.set(p_326799_, p_326899_ - 1, p_326891_);
            return super.getPathType(p_331446_, p_326799_, p_326899_, p_326891_);
        }
    }

    static class ToadPathNavigation extends AmphibiousPathNavigation {
        ToadPathNavigation(MyceliumToadEntity mob, Level level) {
            super(mob, level);
        }

        public boolean canCutCorner(PathType pathType) {
            return pathType != PathType.WATER_BORDER && super.canCutCorner(pathType);
        }

        protected PathFinder createPathFinder(int maxVisitedNodes) {
            this.nodeEvaluator = new ToadNodeEvaluator(true);
            this.nodeEvaluator.setCanPassDoors(true);
            return new PathFinder(this.nodeEvaluator, maxVisitedNodes);
        }
    }


}
