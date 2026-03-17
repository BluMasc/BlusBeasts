package net.blumasc.blusbeasts.entity.variants;

import java.util.Arrays;
import java.util.Comparator;

public enum BurryVariant {
    SAND(0),
    RED_SAND(1);

    private static final BurryVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(BurryVariant::getId)).toArray(BurryVariant[]::new);
    private final int id;

    BurryVariant(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static BurryVariant byId(int id){
        return BY_ID[id % BY_ID.length];
    }
}
