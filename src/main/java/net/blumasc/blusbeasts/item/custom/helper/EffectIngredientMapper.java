package net.blumasc.blusbeasts.item.custom.helper;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;

import java.util.List;

public class EffectIngredientMapper {

    public record IngredientPair(int first, int second) {}

    private static final int A_COUNT = 210;
    private static final int B_COUNT = 12;
    private static final int TOTAL = A_COUNT * B_COUNT;
    private static List<MobEffect> effectList;

    public static IngredientPair getIngredientPair(
            long worldSeed,
            MobEffect effectA,
            MobEffect effectB
    ) {

        if(effectList==null){
            effectList = getEffectsWithRegisteredPotion();
        }

        int indexA = effectList.indexOf(effectA);
        int indexB = effectList.indexOf(effectB);

        int totalEffects = effectList.size();
        int pairId = indexA * totalEffects + indexB;

        long mixed = mix(worldSeed ^ pairId);

        int mapped = (int) (Math.floorMod(mixed, TOTAL));

        int first = mapped / B_COUNT;
        int second = mapped % B_COUNT;

        return new IngredientPair(first, second);
    }

    private static long mix(long x) {
        x ^= (x >>> 33);
        x *= 0xff51afd7ed558ccdL;
        x ^= (x >>> 33);
        x *= 0xc4ceb9fe1a85ec53L;
        x ^= (x >>> 33);
        return x;
    }

    public static List<MobEffect> getEffectsWithRegisteredPotion() {
        return BuiltInRegistries.POTION.stream()
                .flatMap(potion -> potion.getEffects().stream())
                .map(effectInstance -> effectInstance.getEffect().value())
                .distinct()
                .toList();
    }
}