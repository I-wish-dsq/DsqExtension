package net.aluminiumtn.dsqextension;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class ConfigHandler {

    private static final String CONFIG_FILE = "config/dsqextension.conf";
    private static boolean sneakingItemsEnabled = false;
    private static boolean reIntroduceOldRaidsEnabled = false;
    private static boolean returnVoidTradeEnabled = false;
    private static boolean returnExpFromPigmansEnabled = false;

    static {
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            Properties properties = new Properties();
            properties.load(reader);
            sneakingItemsEnabled = Boolean.parseBoolean(properties.getProperty("sneakingItems", "false"));
            reIntroduceOldRaidsEnabled = Boolean.parseBoolean(properties.getProperty("reIntroduceOldRaids", "false"));
            returnVoidTradeEnabled = Boolean.parseBoolean(properties.getProperty("returnVoidTradeEnabled", "false"));
            returnExpFromPigmansEnabled = Boolean.parseBoolean(properties.getProperty("returnExpFromPigmansEnabled", "false"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isSneakingItemsEnabled() {
        return sneakingItemsEnabled;
    }

    public static void setSneakingItemsEnabled(boolean enabled) {
        sneakingItemsEnabled = enabled;
        saveConfig();
    }

    public static boolean isReIntroduceOldRaidsEnabled() {
        return reIntroduceOldRaidsEnabled;
    }

    public static void setReIntroduceOldRaidsEnabled(boolean enabled) {
        reIntroduceOldRaidsEnabled = enabled;
        saveConfig();
    }

    public static boolean isReturnVoidTradeEnabled() {
        return returnVoidTradeEnabled;
    }

    public static void setReturnVoidTradeEnabled(boolean enabled) {
        returnVoidTradeEnabled = enabled;
        saveConfig();
    }

    public static boolean isReturnExpFromPigmansEnabled() {
        return returnExpFromPigmansEnabled;
    }

    public static void setReturnExpFromPigmansEnabled(boolean enabled) {
        returnExpFromPigmansEnabled = enabled;
    }

    private static void saveConfig() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            Properties properties = new Properties();
            properties.setProperty("sneakingItems", Boolean.toString(sneakingItemsEnabled));
            properties.setProperty("reIntroduceOldRaids", Boolean.toString(reIntroduceOldRaidsEnabled));
            properties.setProperty("returnVoidTradeEnabled", Boolean.toString(returnVoidTradeEnabled));
            properties.setProperty("returnExpFromPigmansEnabled", Boolean.toString(returnExpFromPigmansEnabled));
            properties.store(writer, "DSQ Extension Config");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}