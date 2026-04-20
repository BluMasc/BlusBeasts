package net.blumasc.blusbeasts.entity.custom;

import net.blumasc.blusbeasts.item.ModItems;
import net.blumasc.blusbeasts.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;

public class MinersSnackEntity extends AbstractSchoolingFish {
    private static final EntityDataAccessor<Integer> HUNGER =
            SynchedEntityData.defineId(MinersSnackEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SATURATION =
            SynchedEntityData.defineId(MinersSnackEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> COLOR_TOP =
            SynchedEntityData.defineId(MinersSnackEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> COLOR_BOTTOM =
            SynchedEntityData.defineId(MinersSnackEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DURATION_TOP =
            SynchedEntityData.defineId(MinersSnackEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DURATION_BOTTOM =
            SynchedEntityData.defineId(MinersSnackEntity.class, EntityDataSerializers.INT);
    MobEffect topEffect;
    MobEffect bottomEffect;

    public int getHunger() {
        return this.entityData.get(HUNGER);
    }

    public void setHunger(int value) {
        this.entityData.set(HUNGER, value);
    }
    public int getSaturation() {
        return this.entityData.get(SATURATION);
    }

    public void setSaturation(int value) {
        this.entityData.set(SATURATION, value);
    }

    public int getColorTop() {
        return this.entityData.get(COLOR_TOP);
    }

    public void setColorTop(int value) {
        this.entityData.set(COLOR_TOP, value);
    }

    public int getColorBottom() {
        return this.entityData.get(COLOR_BOTTOM);
    }

    public void setColorBottom(int value) {
        this.entityData.set(COLOR_BOTTOM, value);
    }

    public int getDurationTop() {
        return this.entityData.get(DURATION_TOP);
    }

    public void setDurationTop(int value) {
        this.entityData.set(DURATION_TOP, value);
    }

    public int getDurationBottom() {
        return this.entityData.get(DURATION_BOTTOM);
    }

    public void setDurationBottom(int value) {
        this.entityData.set(DURATION_BOTTOM, value);
    }
    public MinersSnackEntity(EntityType<? extends AbstractSchoolingFish> entityType, Level level) {
        super(entityType, level);
    }

    private String StringFromMobEffect(MobEffect e){
        ResourceLocation id = BuiltInRegistries.MOB_EFFECT.getKey(e);
        return id.toString();
    }

    private MobEffect MobEffectFromString(String s){
        ResourceLocation id = ResourceLocation.tryParse(s);
        return id == null ? null : BuiltInRegistries.MOB_EFFECT.get(id);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(HUNGER, 0);
        builder.define(SATURATION, 0);
        builder.define(COLOR_TOP, Color.GREEN.getRGB());
        builder.define(COLOR_BOTTOM, Color.GREEN.getRGB());
        builder.define(DURATION_TOP, 0);
        builder.define(DURATION_BOTTOM, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        saveCustomDataToTag(compound);
    }
    public void saveCustomDataToTag(CompoundTag compound){
        compound.putInt("hunger", getHunger());
        compound.putInt("saturation", getSaturation());
        compound.putInt("durationTop", getDurationTop());
        compound.putInt("durationBottom", getDurationBottom());
        compound.putString("effectTop", StringFromMobEffect(topEffect));
        compound.putString("effectBottom", StringFromMobEffect(bottomEffect));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        readCustomDataFromTag(compound);
    }
    public void readCustomDataFromTag(CompoundTag compound){
        if(compound.contains("hunger")){
            setHunger(compound.getInt("hunger"));
        }else{
            setHunger(this.random.nextInt(3));
        }
        if(compound.contains("saturation")){
            setSaturation(compound.getInt("saturation"));
        }else{
            setSaturation(this.random.nextInt(5));
        }
        if(compound.contains("durationTop")){
            setDurationTop(compound.getInt("durationTop"));
        }else{
            setDurationTop(this.random.nextInt(5));
        }
        if(compound.contains("durationBottom")){
            setDurationBottom(compound.getInt("durationBottom"));
        }else{
            setDurationBottom(this.random.nextInt(3));
        }
        List<MobEffect> possibleEffects=null;
        if(compound.contains("effectTop")){
            MobEffect e = MobEffectFromString(compound.getString("effectTop"));
            if(e!=null){
                topEffect = e;
                setColorTop(topEffect.getColor());
            }else{
                possibleEffects = getEffectsWithRegisteredPotion();
                topEffect = getRandomFromList(possibleEffects);
                setColorTop(topEffect.getColor());
            }
        }else{
            possibleEffects = getEffectsWithRegisteredPotion();
            topEffect = getRandomFromList(possibleEffects);
            setColorTop(topEffect.getColor());
        }
        if(compound.contains("effectBottom")){
            MobEffect e = MobEffectFromString(compound.getString("effectBottom"));
            if(e!=null){
                bottomEffect = e;
                setColorBottom(bottomEffect.getColor());
            }else{
                if(possibleEffects==null) possibleEffects = getEffectsWithRegisteredPotion();
                bottomEffect = getRandomFromList(possibleEffects);
                setColorBottom(bottomEffect.getColor());
            }
        }else{
            if(possibleEffects==null) possibleEffects = getEffectsWithRegisteredPotion();
            bottomEffect = getRandomFromList(possibleEffects);
            setColorBottom(bottomEffect.getColor());
        }
    }

    public MobEffect getRandomFromList(List<MobEffect> list){
        if (list.isEmpty()) {
            return MobEffects.HUNGER.value();
        }
        return list.get(random.nextInt(list.size()));
    }

    public void saveToBucketTag(ItemStack stack) {
        Bucketable.saveDefaultDataToBucketTag(this, stack);
        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, stack, this::saveCustomDataToTag);
    }

    public void loadFromBucketTag(CompoundTag tag) {
        Bucketable.loadDefaultDataFromBucketTag(this, tag);
        readCustomDataFromTag(tag);

    }

    @Override
    protected SoundEvent getFlopSound() {
        return ModSounds.MINERSNACK_FLOP.get();
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.MINERSNACK_HURT.get();
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return ModSounds.MINERSNACK_DEATH.get();
    }

    @Override
    public ItemStack getBucketItemStack() {
        return ModItems.MINERS_SNACK_BUCKET.toStack();
    }

    public static List<MobEffect> getEffectsWithRegisteredPotion() {
        return BuiltInRegistries.POTION.stream()
                .flatMap(potion -> potion.getEffects().stream())
                .map(effectInstance -> effectInstance.getEffect().value())
                .distinct()
                .toList();
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {

        if (!player.getItemInHand(hand).isEmpty()) {
            return super.mobInteract(player, hand);
        }

        if (!this.level().isClientSide) {

            ItemStack stack = new ItemStack(ModItems.MINERS_SNACK.get());

            CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> {
                saveCustomDataToTag(tag);
            });

            if (!player.getInventory().add(stack)) {
                player.drop(stack, false);
            }

            this.discard();
        }

        return InteractionResult.sidedSuccess(this.level().isClientSide);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level,
                                        DifficultyInstance difficulty,
                                        MobSpawnType spawnType,
                                        @Nullable SpawnGroupData spawnGroupData) {

        spawnRandomEffectsIfNeeded();

        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    private void spawnRandomEffectsIfNeeded() {

        List<MobEffect> effects = getEffectsWithRegisteredPotion();

        if (effects.isEmpty()) {
            return;
        }

        setHunger(this.random.nextInt(3));
        setSaturation(this.random.nextInt(5));

        topEffect = getRandomFromList(effects);
        bottomEffect = getRandomFromList(effects);

        setColorTop(topEffect.getColor());
        setColorBottom(bottomEffect.getColor());

        setDurationTop(this.random.nextInt(5));
        setDurationBottom(this.random.nextInt(3));
    }

    public static boolean checkMinersSnackSpawnRules(EntityType<? extends LivingEntity> minersSnack, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return pos.getY() <= level.getSeaLevel() - 33 && level.getBlockState(pos).is(Blocks.WATER);
    }

}
