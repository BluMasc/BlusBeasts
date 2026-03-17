package net.blumasc.blusbeasts.entity.variants;

import java.util.Arrays;
import java.util.Comparator;

public enum DeepshovelerVariant {
    STONE(0),
    IRON(1),
    GOLD(2),
    DIAMOND(3);

    private static final DeepshovelerVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(DeepshovelerVariant::getId)).toArray(DeepshovelerVariant[]::new);
    private final int id;

    DeepshovelerVariant(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static DeepshovelerVariant byId(int id){
        return BY_ID[id % BY_ID.length];
    }
}
