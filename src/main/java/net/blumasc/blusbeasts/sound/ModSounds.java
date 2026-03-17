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

    private static Supplier<SoundEvent> registerSoundEvent(String name){
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, name);
        return SOUND_EVENT.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENT.register(eventBus);
    }
}
