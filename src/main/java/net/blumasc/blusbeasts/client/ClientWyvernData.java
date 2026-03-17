package net.blumasc.blusbeasts.client;

import java.util.Map;
import java.util.UUID;

public class ClientWyvernData {

    public static Map<UUID, Boolean> hasDragon;

    public static void clear() {
        hasDragon = null;
    }
}
