package net.blumasc.blusbeasts.sound;

import net.blumasc.blusbeasts.BlusBeastsMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENT =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, BlusBeastsMod.MODID);

    public static final Supplier<SoundEvent> PACKWING = registerSoundEvent("packwing");
    public static final Supplier<SoundEvent> PACKWING_DEATH = registerSoundEvent("packwing_death");
    public static final Supplier<SoundEvent> PACKWING_HURT = registerSoundEvent("packwing_hurt");
    public static final Supplier<SoundEvent> SALAMANDER = registerSoundEvent("salamander");
    public static final Supplier<SoundEvent> SALAMANDER_DEATH = registerSoundEvent("salamander_death");
    public static final Supplier<SoundEvent> SALAMANDER_HURT = registerSoundEvent("salamander_hurt");
    public static final Supplier<SoundEvent> SALAMANDER_BURN = registerSoundEvent("salamander_burn");
    public static final Supplier<SoundEvent> SALAMANDER_BURN_START = registerSoundEvent("salamander_burn_start");
    public static final Supplier<SoundEvent> SALAMANDER_BURN_END = registerSoundEvent("salamander_burn_end");
    public static final Supplier<SoundEvent> THE_AXOLOTL_SONG = registerSoundEvent("the_axolotl_song");
    public static final Supplier<SoundEvent> MOON_SQUID = registerSoundEvent("moon_squid");
    public static final Supplier<SoundEvent> MOON_SQUID_HURT = registerSoundEvent("moon_squid_hurt");
    public static final Supplier<SoundEvent> MOON_SQUID_DEATH = registerSoundEvent("moon_squid_death");
    public static final Supplier<SoundEvent> TOAD_CROAK = registerSoundEvent("toad_croak");
    public static final Supplier<SoundEvent> TOAD_DEATH = registerSoundEvent("toad_death");
    public static final Supplier<SoundEvent> TOAD_FART = registerSoundEvent("toad_fart");
    public static final Supplier<SoundEvent> TOAD_HURT = registerSoundEvent("toad_hurt");
    public static final Supplier<SoundEvent> TOAD_SLEEP = registerSoundEvent("toad_sleep");
    public static final Supplier<SoundEvent> TOAD_SWALLOW = registerSoundEvent("toad_swallow");
    public static final Supplier<SoundEvent> PRAYFINDER_CALL = registerSoundEvent("prayfinder_call");
    public static final Supplier<SoundEvent> PRAYFINDER_CHIRP = registerSoundEvent("prayfinder_chirp");
    public static final Supplier<SoundEvent> PRAYFINDER_DEATH = registerSoundEvent("prayfinder_death");
    public static final Supplier<SoundEvent> PRAYFINDER_HURT = registerSoundEvent("prayfinder_hurt");
    public static final Supplier<SoundEvent> MIMICART_SNAP = registerSoundEvent("mimicart_snap");
    public static final Supplier<SoundEvent> MIMICART_LICK = registerSoundEvent("mimicart_lick");
    public static final Supplier<SoundEvent> LEACH = registerSoundEvent("leach");
    public static final Supplier<SoundEvent> BURRY_AMBIENT = registerSoundEvent("burry_ambient");
    public static final Supplier<SoundEvent> BURRY_DEATH = registerSoundEvent("burry_death");
    public static final Supplier<SoundEvent> BURRY_HURT = registerSoundEvent("burry_hurt");
    public static final Supplier<SoundEvent> GRAVE_AMBIENT = registerSoundEvent("grave_ambient");
    public static final Supplier<SoundEvent> GRAVE_DEATH = registerSoundEvent("grave_death");
    public static final Supplier<SoundEvent> GRAVE_HURT = registerSoundEvent("grave_hurt");
    public static final Supplier<SoundEvent> ROOTLING_AMBIENT = registerSoundEvent("rootling_ambient");
    public static final Supplier<SoundEvent> ROOTLING_DEATH = registerSoundEvent("rootling_death");
    public static final Supplier<SoundEvent> HEART_EXPLOSION = registerSoundEvent("heart_explosion");
    public static final Supplier<SoundEvent> MAGIC_WAND_HEAL = registerSoundEvent("magic_wand_heal");
    public static final Supplier<SoundEvent> PARTY_POPPER = registerSoundEvent("party_popper");
    public static final Supplier<SoundEvent> PIXIE = registerSoundEvent("pixie");
    public static final Supplier<SoundEvent> SPARKLE_ALL = registerSoundEvent("sparkle_all");
    public static final Supplier<SoundEvent> WAND_HEAL_EFFECT = registerSoundEvent("wand_heal_effect");
    public static final Supplier<SoundEvent> ENDERDRAKE_AMBIENT = registerSoundEvent("enderdrake_ambient");
    public static final Supplier<SoundEvent> ENDERDRAKE_DEATH = registerSoundEvent("enderdrake_death");
    public static final Supplier<SoundEvent> ENDERDRAKE_HURT = registerSoundEvent("enderdrake_hurt");
    public static final Supplier<SoundEvent> ENDERDRAKE_WINGBEAT = registerSoundEvent("enderdrake_wingbeat");
    public static final Supplier<SoundEvent> DEEPSHOVELER_SHOOT = registerSoundEvent("deepshoveler_shoot");
    public static final Supplier<SoundEvent> MINERSNACK_DEATH = registerSoundEvent("minersnack_death");
    public static final Supplier<SoundEvent> MINERSNACK_FLOP = registerSoundEvent("minersnack_flop");
    public static final Supplier<SoundEvent> MINERSNACK_HURT = registerSoundEvent("minersnack_hurt");

    private static Supplier<SoundEvent> registerSoundEvent(String name){
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, name);
        return SOUND_EVENT.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENT.register(eventBus);
    }
}
