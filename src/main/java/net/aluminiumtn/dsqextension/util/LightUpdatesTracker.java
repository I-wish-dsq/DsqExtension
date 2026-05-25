package net.aluminiumtn.dsqextension.util;

public class LightUpdatesTracker {

    private static int updatesPerTick = 0;

    public static final int MAX_LIGHT_UPDATES_PER_TICK = 50000;
    private static final int LIGHT_ENGINE_CALCULATIONS_PER_TICK = 10000;

    public static void simulateLightEngineWork117() {
        updatesPerTick = (updatesPerTick < LIGHT_ENGINE_CALCULATIONS_PER_TICK) ? 0 :  updatesPerTick - LIGHT_ENGINE_CALCULATIONS_PER_TICK;
    }

    public static boolean shouldSupress() {
        updatesPerTick++;

        return updatesPerTick > MAX_LIGHT_UPDATES_PER_TICK;
    }

    public static int getLightEngineQueue() {
        return updatesPerTick;
    }
}
