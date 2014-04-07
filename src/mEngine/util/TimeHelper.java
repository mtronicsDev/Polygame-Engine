package mEngine.util;

public class TimeHelper {

    public static boolean oneSecondPassed = false;
    public static float deltaTime;
    private static float lastTime = System.nanoTime() / 1000000;

    public static int FPS;
    public static int highestFPS;
    private static float lastFPS;
    private static long currentFPS;

    public static int TPS;
    public static int highestTPS;
    private static float lastTPS;
    private static long currentTPS;

    public static boolean isInSlowMotion = false;

    public static void setupTiming() {

        lastTime = getTime();
        lastFPS = getTime();
        lastTPS = getTime();

    }

    public static float getTime() {
        return System.nanoTime() / 1000000;
    }

    public static void updateDeltaTime() {

        float thisTime = getTime();
        deltaTime = thisTime - lastTime;
        if(isInSlowMotion) deltaTime /= 8;
        lastTime = thisTime;

    }

    public static void updateFPS() {

        oneSecondPassed = false;
        if (getTime() - lastFPS > 1000) {

            oneSecondPassed = true;
            FPS = (int) currentFPS;
            if (currentFPS > highestFPS) highestFPS = FPS;
            currentFPS = 0;
            lastFPS += 1000;

        }

        currentFPS++;

    }

    public static void updateTPS() {

        if (getTime() - lastTPS > 1000) {

            TPS = (int) currentTPS;
            if (currentTPS > highestTPS) highestTPS = TPS;
            currentTPS = 0;
            lastTPS += 1000;

        }

        currentTPS++;

    }

}
