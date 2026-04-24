package net.blumasc.blusbeasts;

import java.util.List;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue REGENERATE_ECHO_CRABS = BUILDER
            .comment("Should Echo Crabs be able to regrow their crystals?")
            .define("echoCrabGemGrowth", true);

    public static final ModConfigSpec.BooleanValue SALAMANDER_STACK_SMELT = BUILDER
            .comment("Should Salamanders be able to smelt entire stacks?")
            .define("salamanderStackSmelt", true);

    public static final ModConfigSpec.BooleanValue SPAWN_AMETHYST_CRAB = BUILDER
            .comment("Should Amethyst Crabs spawn from growing amethyst buds?")
            .define("spawnamethystcrab", true);

    public static final ModConfigSpec.BooleanValue SPAWN_ROOTLING = BUILDER
            .comment("Should Rootlings spawn from despawning plant items?")
            .define("spawnrootling", true);

    public static final ModConfigSpec.ConfigValue<Integer> PRAYFINDER_DAY_REQUIREMENT = BUILDER
            .comment("After what number of days should prayfinders spawn?")
            .define("prayfinderdayrequirement", 10);

    public static final ModConfigSpec.ConfigValue<List<? extends String>> EFFECT_STRINGS_MINER_BLACKLIST = BUILDER
            .comment("A list of effects blacklisted from miners snacks.")
            .defineListAllowEmpty("effects_miner_blacklist",
                    List.of("minecraft:instant_health", "minecraft:instant_damage"),
                    () -> "",
                    Config::validateEffect);

    static final ModConfigSpec SPEC = BUILDER.build();

    private static boolean validateEffect(Object obj) {
        if (!(obj instanceof String s)) return false;


        return BuiltInRegistries.MOB_EFFECT.containsKey(ResourceLocation.parse(s));
    }

}
