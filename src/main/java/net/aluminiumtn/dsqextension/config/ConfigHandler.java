package net.aluminiumtn.dsqextension.config;

import net.aluminiumtn.dsqextension.enums.RuleTags;
import net.aluminiumtn.dsqextension.util.Rule;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigHandler {

    private static final Path CONFIG_FILE = Paths.get("config", "dsqextension.conf");

    @Rule(
            desc = "Items go directly into the inventory while sneaking",
            tags = {
                    RuleTags.QOL,
                    RuleTags.NOT_VANILLA,
                    RuleTags.SURVIVAL
            }
    )
    private static boolean sneakingItemsEnabled = false;

    @Rule(
            desc = "Reintroduces 1.14 raid mechanics",
            tags = {
                    RuleTags.REINTRODUCE,
                    RuleTags.NOT_VANILLA,
                    RuleTags.SURVIVAL
            }
    )
    private static boolean reIntroduceOldRaidsEnabled = false;

    @Rule(
            desc = "Reintroduces Void trade",
            tags = {
                    RuleTags.REINTRODUCE,
                    RuleTags.NOT_VANILLA,
                    RuleTags.SURVIVAL,
                    RuleTags.EXPERIMENTAL
            }
    )
    private static boolean returnVoidTradeEnabled = false;

    @Rule(
            desc = "Reintroduces experience from zombie pigmen (death not caused by player)",
            tags = {
                    RuleTags.REINTRODUCE,
                    RuleTags.NOT_VANILLA,
                    RuleTags.SURVIVAL
            }
    )
    private static boolean returnExpFromPigmansEnabled = false;

    @Rule(
            desc = "Disables mob AI when hit with a shovel",
            tags = {
                    RuleTags.NOT_VANILLA,
                    RuleTags.QOL,
                    RuleTags.EXPERIMENTAL
            }
    )
    private static boolean AIDisableShovelEnabled = false;

    @Rule(
            desc = "Disables chunk light recalculation",
            tags = {
                    RuleTags.RISKY,
                    RuleTags.EXPERIMENTAL,
                    RuleTags.NOT_VANILLA
            }
    )
    private static boolean disableDeleteLightDataFixerEnabled = false;

    @Rule(
            desc = "Disables block update queue",
            tags = {
                    RuleTags.RISKY,
                    RuleTags.EXPERIMENTAL,
                    RuleTags.NOT_VANILLA
            }
    )
    private static boolean reIntroduceInstantBlockUpdatesEnabled = false;

    @Rule(
            desc = "Simulates the light engine queue",
            tags = {
                    RuleTags.RISKY,
                    RuleTags.EXPERIMENTAL,
                    RuleTags.NOT_VANILLA
            }
    )
    private static boolean reIntroduceLightSuppressionEnabled = false;

    @Rule(
            desc = "Hand refill from shulker in your inventory",
            tags = {
                    RuleTags.QOL,
                    RuleTags.NOT_VANILLA,
                    RuleTags.SURVIVAL
            }
    )
    private static boolean shulkerRefillEnabled = false;



    // AntiShadowPatch options
    // Тут описания написаны нейронкой



    @Rule(desc = "Reintroduces StackOverflow Suppression")
    private static boolean bringBackSOSuppression = true;

    @Rule(desc = "Reintroduces CCE Suppression")
    private static boolean bringBackCCESuppression = true;

    @Rule(desc = "Reintroduces trapdoor update skipping")
    private static boolean bringBackTrapdoorUpdateSkipping = true;

    @Rule(desc = "Reintroduces floating redstone components on trapdoors")
    private static boolean bringBackFloatingRedstoneComponentsOnTopOfTrapdoor = true;

    @Rule(desc = "Reintroduces furnace XP duplication")
    private static boolean bringBackFurnaceXPDupe = true;

    @Rule(desc = "Reintroduces inner collisions for full blocks")
    private static boolean bringBackFullBlockInnerCollisions = false;

    @Rule(desc = "Reintroduces Block Entity Swap")
    private static boolean bringBackBlockEntitySwap = true;

    @Rule(desc = "Keeps blocks with swapped Block Entities")
    private static boolean keepBlocksWithSwappedBlockEntities = true;

    @Rule(desc = "Reintroduces item shadowing from 1.17")
    private static boolean bringBackItemShadowing_1_17 = true;

    @Rule(desc = "Reintroduces item shadowing from 1.18")
    private static boolean bringBackItemShadowing_1_18 = true;

    @Rule(desc = "Reintroduces curse book overstacking")
    private static boolean bringBackCurseBookOverstacking = false;

    @Rule(desc = "Reintroduces overstacked item movement from 1.20")
    private static boolean bringBackOverstackedItemMovement_1_20 = false;

    @Rule(desc = "Reintroduces chunk save state from 1.14")
    private static boolean bringBackChunkSaveState_1_14 = false;

    @Rule(desc = "Reintroduces chunk save state from 1.21")
    private static boolean bringBackChunkSaveState_1_21 = false;

    @Rule(desc = "Reintroduces old dragon freezing")
    private static boolean bringBackOldDragonFreezing = true;

    @Rule(desc = "Reintroduces armor stand immunity to wither damage")
    private static boolean bringBackArmorStandInvulnerableToWitherDamage = true;

    @Rule(desc = "Reintroduces shadow items in mob inventories")
    private static boolean bringBackShadowItemsInMobInventory = true;

    @Rule(desc = "Reintroduces voidless void trading")
    private static boolean bringBackVoidlessVoidTrading = true;

    @Rule(desc = "Reintroduces graceful StackOverflow handling")
    private static boolean bringBackGracefulSOHandling = true;

    @Rule(desc = "Reintroduces graceful Out Of Memory handling")
    private static boolean bringBackGracefulOOMHandling = true;

    @Rule(desc = "Blocked message")
    private static boolean cceSuppressorChatMessageEnabled = true;

    static {
        loadConfig();
    }

    public static void loadConfig() {
        try {
            if (!Files.exists(CONFIG_FILE.getParent())) {
                Files.createDirectories(CONFIG_FILE.getParent());
            }

            if (!Files.exists(CONFIG_FILE)) {
                saveConfig();
                return;
            }

            Properties properties = new Properties();
            try (Reader reader = Files.newBufferedReader(CONFIG_FILE)) {
                properties.load(reader);
            }

            sneakingItemsEnabled = Boolean.parseBoolean(properties.getProperty("sneakingItems", "false"));
            reIntroduceOldRaidsEnabled = Boolean.parseBoolean(properties.getProperty("reIntroduceOldRaids", "false"));
            returnVoidTradeEnabled = Boolean.parseBoolean(properties.getProperty("returnVoidTradeEnabled", "false"));
            returnExpFromPigmansEnabled = Boolean.parseBoolean(properties.getProperty("returnExpFromPigmansEnabled", "false"));
            AIDisableShovelEnabled = Boolean.parseBoolean(properties.getProperty("AIDisableShovel", "false"));
            reIntroduceInstantBlockUpdatesEnabled = Boolean.parseBoolean(properties.getProperty("reIntroduceInstantBlockUpdates", "false"));
            cceSuppressorChatMessageEnabled = Boolean.parseBoolean(properties.getProperty("cceSuppressorChatMessageEnabled", "false"));
            disableDeleteLightDataFixerEnabled = Boolean.parseBoolean(properties.getProperty("disableDeleteLightDataFixer", "false"));
            reIntroduceLightSuppressionEnabled = Boolean.parseBoolean(properties.getProperty("reIntroduceLightSuppression", "false"));
            shulkerRefillEnabled = Boolean.parseBoolean(properties.getProperty("shulkerRefill", "false"));

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

        } catch (Exception e) {
            System.err.println("[DSQExtension] Failed to load config file!");
            e.printStackTrace();
        }
    }

    public static void saveConfig() {
        try {
            if (!Files.exists(CONFIG_FILE.getParent())) {
                Files.createDirectories(CONFIG_FILE.getParent());
            }

            Properties properties = new Properties();
            properties.setProperty("sneakingItems", Boolean.toString(sneakingItemsEnabled));
            properties.setProperty("reIntroduceOldRaids", Boolean.toString(reIntroduceOldRaidsEnabled));
            properties.setProperty("returnVoidTradeEnabled", Boolean.toString(returnVoidTradeEnabled));
            properties.setProperty("returnExpFromPigmansEnabled", Boolean.toString(returnExpFromPigmansEnabled));
            properties.setProperty("AIDisableShovel", Boolean.toString(AIDisableShovelEnabled));
            properties.setProperty("reIntroduceInstantBlockUpdates", Boolean.toString(reIntroduceInstantBlockUpdatesEnabled));
            properties.setProperty("cceSuppressorChatMessageEnabled", Boolean.toString(cceSuppressorChatMessageEnabled));
            properties.setProperty("disableDeleteLightDataFixer", Boolean.toString(disableDeleteLightDataFixerEnabled));
            properties.setProperty("shulkerRefill", Boolean.toString(shulkerRefillEnabled));

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
            properties.setProperty("reIntroduceLightSuppression", Boolean.toString(reIntroduceLightSuppressionEnabled));

            try (Writer writer = Files.newBufferedWriter(CONFIG_FILE)) {
                properties.store(writer, "DSQ Extension Config");
            }
        } catch (Exception e) {
            System.err.println("[DSQExtension] Failed to save config file!");
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

    public static boolean isDisableDeleteLightDataFixerEnabled() {
        return disableDeleteLightDataFixerEnabled;
    }

    public static void setDisableDeleteLightDataFixerEnabled(boolean enabled) {
        disableDeleteLightDataFixerEnabled = enabled;
        saveConfig();
    }

    public static boolean isReIntroduceLightSuppressionEnabled() {
        return reIntroduceLightSuppressionEnabled;
    }

    public static void setReIntroduceLightSuppressionEnabled(boolean enabled) {
        reIntroduceLightSuppressionEnabled = enabled;
        saveConfig();
    }

    public static boolean isShulkerRefillEnabled() {
        return shulkerRefillEnabled;
    }

    public static void setShulkerRefillEnabled(boolean enabled) {
        shulkerRefillEnabled = enabled;
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
}