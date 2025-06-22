package net.aluminiumtn.dsqextension.config;

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
    private static boolean AIDisableShovelEnabled = false;

    // AntiShadowPatch options
    private static boolean bringBackSOSuppression = true;
    private static boolean bringBackCCESuppression = true;
    private static boolean bringBackTrapdoorUpdateSkipping = true;
    private static boolean bringBackFloatingRedstoneComponentsOnTopOfTrapdoor = true;
    private static boolean bringBackFurnaceXPDupe = true;
    private static boolean bringBackFullBlockInnerCollisions = false;
    private static boolean bringBackBlockEntitySwap = true;
    private static boolean keepBlocksWithSwappedBlockEntities = true;
    private static boolean bringBackItemShadowing_1_17 = true;
    private static boolean bringBackItemShadowing_1_18 = true;
    private static boolean bringBackCurseBookOverstacking = false;
    private static boolean bringBackOverstackedItemMovement_1_20 = false;
    private static boolean bringBackChunkSaveState_1_14 = false;
    private static boolean bringBackChunkSaveState_1_21 = false;
    private static boolean bringBackOldDragonFreezing = true;
    private static boolean bringBackArmorStandInvulnerableToWitherDamage = true;
    private static boolean bringBackShadowItemsInMobInventory = true;
    private static boolean bringBackVoidlessVoidTrading = true;
    private static boolean bringBackGracefulSOHandling = true;
    private static boolean bringBackGracefulOOMHandling = true;
    private static boolean cceSuppressorChatMessageEnabled = false;
    private static boolean reIntroduceInstantBlockUpdatesEnabled = false;

    static {
        Properties properties = new Properties();
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            properties.load(reader);
        } catch (IOException e) {
            // It's okay if the file doesn't exist, we'll create it on first save.
        }
        sneakingItemsEnabled = Boolean.parseBoolean(properties.getProperty("sneakingItems", "false"));
        reIntroduceOldRaidsEnabled = Boolean.parseBoolean(properties.getProperty("reIntroduceOldRaids", "false"));
        returnVoidTradeEnabled = Boolean.parseBoolean(properties.getProperty("returnVoidTradeEnabled", "false"));
        returnExpFromPigmansEnabled = Boolean.parseBoolean(properties.getProperty("returnExpFromPigmansEnabled", "false"));
        AIDisableShovelEnabled = Boolean.parseBoolean(properties.getProperty("AIDisableShovelEnabled", "false"));
        reIntroduceInstantBlockUpdatesEnabled = Boolean.parseBoolean(properties.getProperty("reIntroduceInstantBlockUpdates", "false"));
        cceSuppressorChatMessageEnabled = Boolean.parseBoolean(properties.getProperty("cceSuppressorChatMessageEnabled", "false"));

        // AntiShadowPatch options
        bringBackSOSuppression = Boolean.parseBoolean(properties.getProperty("bringBackSOSuppression", "true"));
        bringBackCCESuppression = Boolean.parseBoolean(properties.getProperty("bringBackCCESuppression", "true"));
        bringBackTrapdoorUpdateSkipping = Boolean.parseBoolean(properties.getProperty("bringBackTrapdoorUpdateSkipping", "true"));
        bringBackFloatingRedstoneComponentsOnTopOfTrapdoor = Boolean.parseBoolean(properties.getProperty("bringBackFloatingRedstoneComponentsOnTopOfTrapdoor", "true"));
        bringBackFurnaceXPDupe = Boolean.parseBoolean(properties.getProperty("bringBackFurnaceXPDupe", "true"));
        bringBackFullBlockInnerCollisions = Boolean.parseBoolean(properties.getProperty("bringBackFullBlockInnerCollisions", "false"));
        bringBackBlockEntitySwap = Boolean.parseBoolean(properties.getProperty("bringBackBlockEntitySwap", "true"));
        keepBlocksWithSwappedBlockEntities = Boolean.parseBoolean(properties.getProperty("keepBlocksWithSwappedBlockEntities", "true"));
        bringBackItemShadowing_1_17 = Boolean.parseBoolean(properties.getProperty("bringBackItemShadowing_1_17", "true"));
        bringBackItemShadowing_1_18 = Boolean.parseBoolean(properties.getProperty("bringBackItemShadowing_1_18", "true"));
        bringBackCurseBookOverstacking = Boolean.parseBoolean(properties.getProperty("bringBackCurseBookOverstacking", "false"));
        bringBackOverstackedItemMovement_1_20 = Boolean.parseBoolean(properties.getProperty("bringBackOverstackedItemMovement_1_20", "false"));
        bringBackChunkSaveState_1_14 = Boolean.parseBoolean(properties.getProperty("bringBackChunkSaveState_1_14", "false"));
        bringBackChunkSaveState_1_21 = Boolean.parseBoolean(properties.getProperty("bringBackChunkSaveState_1_21", "false"));
        bringBackOldDragonFreezing = Boolean.parseBoolean(properties.getProperty("bringBackOldDragonFreezing", "true"));
        bringBackArmorStandInvulnerableToWitherDamage = Boolean.parseBoolean(properties.getProperty("bringBackArmorStandInvulnerableToWitherDamage", "true"));
        bringBackShadowItemsInMobInventory = Boolean.parseBoolean(properties.getProperty("bringBackShadowItemsInMobInventory", "true"));
        bringBackVoidlessVoidTrading = Boolean.parseBoolean(properties.getProperty("bringBackVoidlessVoidTrading", "true"));
        bringBackGracefulSOHandling = Boolean.parseBoolean(properties.getProperty("bringBackGracefulSOHandling", "true"));
        bringBackGracefulOOMHandling = Boolean.parseBoolean(properties.getProperty("bringBackGracefulOOMHandling", "true"));
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
        saveConfig();
    }

    public static boolean isAIDisableShovel() {
        return AIDisableShovelEnabled;
    }

    public static void setAIDisableShovel(boolean enabled) {
        AIDisableShovelEnabled = enabled;
        saveConfig();
    }

    public static boolean isReIntroduceInstantBlockUpdatesEnabled() {
        return reIntroduceInstantBlockUpdatesEnabled;
    }

    public static void setReIntroduceInstantBlockUpdatesEnabled(boolean enabled) {
        reIntroduceInstantBlockUpdatesEnabled = enabled;
        saveConfig();
    }

    public static boolean isCceSuppressorChatMessageEnabled() {
        return cceSuppressorChatMessageEnabled;
    }

    public static void setCceSuppressorChatMessageEnabled(boolean enabled) {
        cceSuppressorChatMessageEnabled = enabled;
        saveConfig();
    }

    // AntiShadowPatch Getters
    public static boolean isBringBackSOSuppressionEnabled() {
        return bringBackSOSuppression;
    }

    public static void setBringBackSOSuppressionEnabled(boolean enabled) {
        bringBackSOSuppression = enabled;
        saveConfig();
    }

    public static boolean isBringBackCCESuppressionEnabled() {
        return bringBackCCESuppression;
    }

    public static boolean isBringBackTrapdoorUpdateSkippingEnabled() {
        return bringBackTrapdoorUpdateSkipping;
    }

    public static boolean isBringBackFloatingRedstoneComponentsOnTopOfTrapdoorEnabled() {
        return bringBackFloatingRedstoneComponentsOnTopOfTrapdoor;
    }

    public static boolean isBringBackFurnaceXPDupeEnabled() {
        return bringBackFurnaceXPDupe;
    }

    public static boolean isBringBackFullBlockInnerCollisionsEnabled() {
        return bringBackFullBlockInnerCollisions;
    }

    public static boolean isBringBackBlockEntitySwapEnabled() {
        return bringBackBlockEntitySwap;
    }

    public static boolean isKeepBlocksWithSwappedBlockEntitiesEnabled() {
        return keepBlocksWithSwappedBlockEntities;
    }

    public static boolean isBringBackItemShadowing_1_17Enabled() {
        return bringBackItemShadowing_1_17;
    }

    public static boolean isBringBackItemShadowing_1_18Enabled() {
        return bringBackItemShadowing_1_18;
    }

    public static boolean isBringBackCurseBookOverstackingEnabled() {
        return bringBackCurseBookOverstacking;
    }

    public static boolean isBringBackOverstackedItemMovement_1_20Enabled() {
        return bringBackOverstackedItemMovement_1_20;
    }

    public static boolean isBringBackChunkSaveState_1_14Enabled() {
        return bringBackChunkSaveState_1_14;
    }

    public static boolean isBringBackChunkSaveState_1_21Enabled() {
        return bringBackChunkSaveState_1_21;
    }

    public static boolean isBringBackOldDragonFreezingEnabled() {
        return bringBackOldDragonFreezing;
    }

    public static boolean isBringBackArmorStandInvulnerableToWitherDamageEnabled() {
        return bringBackArmorStandInvulnerableToWitherDamage;
    }

    public static boolean isBringBackShadowItemsInMobInventoryEnabled() {
        return bringBackShadowItemsInMobInventory;
    }

    public static boolean isBringBackVoidlessVoidTradingEnabled() {
        return bringBackVoidlessVoidTrading;
    }

    public static boolean isBringBackGracefulSOHandlingEnabled() {
        return bringBackGracefulSOHandling;
    }

    public static boolean isBringBackGracefulOOMHandlingEnabled() {
        return bringBackGracefulOOMHandling;
    }

    private static void saveConfig() {
        Properties properties = new Properties();
        properties.setProperty("sneakingItems", Boolean.toString(sneakingItemsEnabled));
        properties.setProperty("reIntroduceOldRaids", Boolean.toString(reIntroduceOldRaidsEnabled));
        properties.setProperty("returnVoidTradeEnabled", Boolean.toString(returnVoidTradeEnabled));
        properties.setProperty("returnExpFromPigmansEnabled", Boolean.toString(returnExpFromPigmansEnabled));
        properties.setProperty("AIDisableShovel", Boolean.toString(AIDisableShovelEnabled));
        properties.setProperty("reIntroduceInstantBlockUpdates", Boolean.toString(reIntroduceInstantBlockUpdatesEnabled));
        properties.setProperty("cceSuppressorChatMessageEnabled", Boolean.toString(cceSuppressorChatMessageEnabled));

        // AntiShadowPatch options
        properties.setProperty("bringBackSOSuppression", Boolean.toString(bringBackSOSuppression));
        properties.setProperty("bringBackCCESuppression", Boolean.toString(bringBackCCESuppression));
        properties.setProperty("bringBackTrapdoorUpdateSkipping", Boolean.toString(bringBackTrapdoorUpdateSkipping));
        properties.setProperty("bringBackFloatingRedstoneComponentsOnTopOfTrapdoor", Boolean.toString(bringBackFloatingRedstoneComponentsOnTopOfTrapdoor));
        properties.setProperty("bringBackFurnaceXPDupe", Boolean.toString(bringBackFurnaceXPDupe));
        properties.setProperty("bringBackFullBlockInnerCollisions", Boolean.toString(bringBackFullBlockInnerCollisions));
        properties.setProperty("bringBackBlockEntitySwap", Boolean.toString(bringBackBlockEntitySwap));
        properties.setProperty("keepBlocksWithSwappedBlockEntities", Boolean.toString(keepBlocksWithSwappedBlockEntities));
        properties.setProperty("bringBackItemShadowing_1_17", Boolean.toString(bringBackItemShadowing_1_17));
        properties.setProperty("bringBackItemShadowing_1_18", Boolean.toString(bringBackItemShadowing_1_18));
        properties.setProperty("bringBackCurseBookOverstacking", Boolean.toString(bringBackCurseBookOverstacking));
        properties.setProperty("bringBackOverstackedItemMovement_1_20", Boolean.toString(bringBackOverstackedItemMovement_1_20));
        properties.setProperty("bringBackChunkSaveState_1_14", Boolean.toString(bringBackChunkSaveState_1_14));
        properties.setProperty("bringBackChunkSaveState_1_21", Boolean.toString(bringBackChunkSaveState_1_21));
        properties.setProperty("bringBackOldDragonFreezing", Boolean.toString(bringBackOldDragonFreezing));
        properties.setProperty("bringBackArmorStandInvulnerableToWitherDamage", Boolean.toString(bringBackArmorStandInvulnerableToWitherDamage));
        properties.setProperty("bringBackShadowItemsInMobInventory", Boolean.toString(bringBackShadowItemsInMobInventory));
        properties.setProperty("bringBackVoidlessVoidTrading", Boolean.toString(bringBackVoidlessVoidTrading));
        properties.setProperty("bringBackGracefulSOHandling", Boolean.toString(bringBackGracefulSOHandling));
        properties.setProperty("bringBackGracefulOOMHandling", Boolean.toString(bringBackGracefulOOMHandling));

        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            properties.store(writer, "DSQ Extension Config");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}