package net.aluminiumtn.dsqextension.config;

import net.aluminiumtn.dsqextension.enums.RuleTags;
import net.aluminiumtn.dsqextension.util.Rule;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelResource;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class ConfigHandler {

    private static Path CONFIG_FILE;

    public static void init(MinecraftServer server) {
        Path worldPath = server.getWorldPath(LevelResource.ROOT);

        CONFIG_FILE = worldPath.resolve("data").resolve("dsqextension.conf");

        loadConfig();
    }

    @Rule(
            key = "removeRaidOmenFromAdvancements",
            desc = "Removes raid omen from advancement. RESTART SERVER TO APPLY",
            tags = {
                    RuleTags.REINTRODUCE,
                    RuleTags.NOT_VANILLA
            }
    )
    private static boolean removeRaidOmenFromAdvancementsEnabled = true;

    @Rule(
            key = "sneakingItems",
            desc = "Items go directly into the inventory while sneaking",
            tags = {
                    RuleTags.QOL,
                    RuleTags.NOT_VANILLA,
                    RuleTags.SURVIVAL
            }
    )
    private static boolean sneakingItemsEnabled = false;

    @Rule(
            key = "reIntroduceOldRaids",
            desc = "Reintroduces 1.14 raid mechanics",
            tags = {
                    RuleTags.REINTRODUCE,
                    RuleTags.NOT_VANILLA,
                    RuleTags.SURVIVAL
            }
    )
    private static boolean reIntroduceOldRaidsEnabled = false;

    @Rule(
            key = "reIntroduceVoidTrading",
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
            key = "returnExpFromPigmans",
            desc = "Reintroduces experience from zombie pigmen (death not caused by player)",
            tags = {
                    RuleTags.REINTRODUCE,
                    RuleTags.NOT_VANILLA,
                    RuleTags.SURVIVAL
            }
    )
    private static boolean returnExpFromPigmansEnabled = false;

    @Rule(
            key = "aiDisableShovel",
            desc = "Disables mob AI when hit with a shovel",
            tags = {
                    RuleTags.NOT_VANILLA,
                    RuleTags.QOL,
                    RuleTags.EXPERIMENTAL
            }
    )
    private static boolean aiDisableShovelEnabled = false;

    @Rule(
            key = "disableDeleteLightDataFixer",
            desc = "Disables chunk light recalculation",
            tags = {
                    RuleTags.RISKY,
                    RuleTags.EXPERIMENTAL,
                    RuleTags.NOT_VANILLA
            }
    )
    private static boolean disableDeleteLightDataFixerEnabled = false;

    @Rule(
            key = "reIntroduceInstantBlockUpdates",
            desc = "Disables block update queue",
            tags = {
                    RuleTags.RISKY,
                    RuleTags.EXPERIMENTAL,
                    RuleTags.NOT_VANILLA
            }
    )
    private static boolean reIntroduceInstantBlockUpdatesEnabled = false;

    @Rule(
            key = "simulateLightEngineQueue",
            desc = "Simulates the light engine queue",
            tags = {
                    RuleTags.RISKY,
                    RuleTags.EXPERIMENTAL,
                    RuleTags.NOT_VANILLA
            }
    )
    private static boolean reIntroduceLightSuppressionEnabled = false;

    @Rule(
            key = "handRefillFromShulker",
            desc = "Hand refill from shulker in your inventory",
            tags = {
                    RuleTags.QOL,
                    RuleTags.NOT_VANILLA,
                    RuleTags.SURVIVAL
            }
    )
    private static boolean shulkerRefillEnabled = false;

    // я хуй знает че за правила ниже, оно было и пусть будет

    @Rule(
            key = "bringBackSOSuppression",
            desc = "Reintroduces StackOverflow suppression",
            tags = {
                    RuleTags.REINTRODUCE,
                    RuleTags.NOT_VANILLA,
                    RuleTags.EXPERIMENTAL
            }
    )
    private static boolean bringBackSOSuppressionEnabled = true;

    @Rule(
            key = "bringBackCCESuppression",
            desc = "Reintroduces ClassCastException suppression",
            tags = {
                    RuleTags.REINTRODUCE,
                    RuleTags.NOT_VANILLA,
                    RuleTags.EXPERIMENTAL
            }
    )
    private static boolean bringBackCCESuppressionEnabled = true;

    @Rule(
            key = "bringBackTrapdoorUpdateSkipping",
            desc = "Reintroduces trapdoor update skipping",
            tags = {
                    RuleTags.REINTRODUCE,
                    RuleTags.NOT_VANILLA,
                    RuleTags.EXPERIMENTAL
            }
    )
    private static boolean bringBackTrapdoorUpdateSkippingEnabled = true;

    @Rule(
            key = "bringBackFloatingRedstoneComponentsOnTopOfTrapdoor",
            desc = "Reintroduces floating redstone components on top of trapdoors",
            tags = {
                    RuleTags.REINTRODUCE,
                    RuleTags.NOT_VANILLA,
                    RuleTags.EXPERIMENTAL
            }
    )
    private static boolean bringBackFloatingRedstoneComponentsOnTopOfTrapdoorEnabled = true;

    @Rule(
            key = "bringBackFurnaceXPDupe",
            desc = "Reintroduces furnace experience duplication",
            tags = {
                    RuleTags.REINTRODUCE,
                    RuleTags.NOT_VANILLA,
                    RuleTags.RISKY
            }
    )
    private static boolean bringBackFurnaceXPDupeEnabled = true;

    @Rule(
            key = "bringBackFullBlockInnerCollisions",
            desc = "Reintroduces inner collisions for full blocks",
            tags = {
                    RuleTags.REINTRODUCE,
                    RuleTags.NOT_VANILLA,
                    RuleTags.EXPERIMENTAL
            }
    )
    private static boolean bringBackFullBlockInnerCollisionsEnabled = false;

    @Rule(
            key = "bringBackBlockEntitySwap",
            desc = "Reintroduces block entity swapping",
            tags = {
                    RuleTags.REINTRODUCE,
                    RuleTags.NOT_VANILLA,
                    RuleTags.RISKY
            }
    )
    private static boolean bringBackBlockEntitySwapEnabled = true;

    @Rule(
            key = "keepBlocksWithSwappedBlockEntities",
            desc = "Keeps blocks with swapped block entities",
            tags = {
                    RuleTags.NOT_VANILLA,
                    RuleTags.RISKY,
                    RuleTags.EXPERIMENTAL
            }
    )
    private static boolean keepBlocksWithSwappedBlockEntitiesEnabled = true;

    @Rule(
            key = "bringBackItemShadowing_1_17",
            desc = "Reintroduces item shadowing from 1.17",
            tags = {
                    RuleTags.REINTRODUCE,
                    RuleTags.NOT_VANILLA,
                    RuleTags.RISKY
            }
    )
    private static boolean bringBackItemShadowing_1_17Enabled = true;

    @Rule(
            key = "bringBackItemShadowing_1_18",
            desc = "Reintroduces item shadowing from 1.18",
            tags = {
                    RuleTags.REINTRODUCE,
                    RuleTags.NOT_VANILLA,
                    RuleTags.RISKY
            }
    )
    private static boolean bringBackItemShadowing_1_18Enabled = true;

    @Rule(
            key = "bringBackCurseBookOverstacking",
            desc = "Reintroduces curse book overstacking",
            tags = {
                    RuleTags.REINTRODUCE,
                    RuleTags.NOT_VANILLA,
                    RuleTags.RISKY
            }
    )
    private static boolean bringBackCurseBookOverstackingEnabled = false;

    @Rule(
            key = "bringBackOverstackedItemMovement_1_20",
            desc = "Reintroduces overstacked item movement from 1.20",
            tags = {
                    RuleTags.REINTRODUCE,
                    RuleTags.NOT_VANILLA,
                    RuleTags.RISKY
            }
    )
    private static boolean bringBackOverstackedItemMovement_1_20Enabled = false;

    @Rule(
            key = "bringBackChunkSaveState_1_14",
            desc = "Reintroduces chunk save state behavior from 1.14",
            tags = {
                    RuleTags.REINTRODUCE,
                    RuleTags.NOT_VANILLA,
                    RuleTags.EXPERIMENTAL
            }
    )
    private static boolean bringBackChunkSaveState_1_14Enabled = false;

    @Rule(
            key = "bringBackChunkSaveState_1_21",
            desc = "Reintroduces chunk save state behavior from 1.21",
            tags = {
                    RuleTags.REINTRODUCE,
                    RuleTags.NOT_VANILLA,
                    RuleTags.EXPERIMENTAL
            }
    )
    private static boolean bringBackChunkSaveState_1_21Enabled = false;

    @Rule(
            key = "bringBackOldDragonFreezing",
            desc = "Reintroduces old Ender Dragon freezing behavior",
            tags = {
                    RuleTags.REINTRODUCE,
                    RuleTags.NOT_VANILLA,
                    RuleTags.EXPERIMENTAL
            }
    )
    private static boolean bringBackOldDragonFreezingEnabled = true;

    @Rule(
            key = "bringBackArmorStandInvulnerableToWitherDamage",
            desc = "Reintroduces armor stand immunity to wither damage",
            tags = {
                    RuleTags.REINTRODUCE,
                    RuleTags.NOT_VANILLA,
                    RuleTags.SURVIVAL
            }
    )
    private static boolean bringBackArmorStandInvulnerableToWitherDamageEnabled = true;

    @Rule(
            key = "bringBackShadowItemsInMobInventory",
            desc = "Reintroduces shadow items in mob inventories",
            tags = {
                    RuleTags.REINTRODUCE,
                    RuleTags.NOT_VANILLA,
                    RuleTags.RISKY
            }
    )
    private static boolean bringBackShadowItemsInMobInventoryEnabled = true;

    @Rule(
            key = "bringBackVoidlessVoidTrading",
            desc = "Reintroduces voidless void trading",
            tags = {
                    RuleTags.REINTRODUCE,
                    RuleTags.NOT_VANILLA,
                    RuleTags.EXPERIMENTAL
            }
    )
    private static boolean bringBackVoidlessVoidTradingEnabled = true;

    @Rule(
            key = "bringBackGracefulSOHandling",
            desc = "Reintroduces graceful StackOverflow handling",
            tags = {
                    RuleTags.REINTRODUCE,
                    RuleTags.NOT_VANILLA,
                    RuleTags.EXPERIMENTAL
            }
    )
    private static boolean bringBackGracefulSOHandlingEnabled = true;

    @Rule(
            key = "bringBackGracefulOOMHandling",
            desc = "Reintroduces graceful Out Of Memory handling",
            tags = {
                    RuleTags.REINTRODUCE,
                    RuleTags.NOT_VANILLA,
                    RuleTags.RISKY
            }
    )
    private static boolean bringBackGracefulOOMHandlingEnabled = true;

    @Rule(
            key = "cceSuppressorChatMessageEnabled",
            desc = "Displays a chat message when CCE suppression is triggered",
            tags = {
                    RuleTags.QOL,
                    RuleTags.NOT_VANILLA
            }
    )
    private static boolean cceSuppressorChatMessageEnabled = true;

    public static void loadConfig() {
        try {
            if (CONFIG_FILE == null) {
                throw new IllegalStateException("ConfigHandler was called before world load");
            }

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

            for (Field field : ConfigHandler.class.getDeclaredFields()) {

                Rule rule = field.getAnnotation(Rule.class);

                if (rule == null)
                    continue;

                if (field.getType() != boolean.class)
                    continue;

                field.setAccessible(true);

                boolean defaultValue = field.getBoolean(null);

                boolean value = Boolean.parseBoolean(
                        properties.getProperty(
                                rule.key(),
                                Boolean.toString(defaultValue)
                        )
                );

                field.setBoolean(null, value);
            }

        } catch (Exception e) {
            throw new IllegalStateException("[DSQExtension] Failed to load config file!\n".concat(e.getMessage()));
        }
    }

    public static void saveConfig() {
        try {
            if (CONFIG_FILE == null) {
                throw new IllegalStateException("ConfigHandler was called before world load");
            }

            if (!Files.exists(CONFIG_FILE.getParent())) {
                Files.createDirectories(CONFIG_FILE.getParent());
            }

            Properties properties = new Properties();

            for (Field field : ConfigHandler.class.getDeclaredFields()) {

                Rule rule = field.getAnnotation(Rule.class);

                if (rule == null)
                    continue;

                if (field.getType() != boolean.class)
                    continue;

                field.setAccessible(true);

                properties.setProperty(
                        rule.key(),
                        Boolean.toString(field.getBoolean(null))
                );
            }

            try (Writer writer = Files.newBufferedWriter(CONFIG_FILE)) {
                properties.store(writer, "DSQ Extension Config");
            }

        } catch (Exception e) {
            throw new IllegalStateException("[DSQExtension] Failed to save config file!\n".concat(e.getMessage()));
        }
    }

    public static boolean removeRaidOmenFromAdvancementsEnabled() {
        return removeRaidOmenFromAdvancementsEnabled;
    }

    public static boolean isSneakingItemsEnabled() {
        return sneakingItemsEnabled;
    }

    public static boolean isReIntroduceOldRaidsEnabled() {
        return reIntroduceOldRaidsEnabled;
    }

    public static boolean isReturnVoidTradeEnabled() {
        return returnVoidTradeEnabled;
    }

    public static boolean isReturnExpFromPigmansEnabled() {
        return returnExpFromPigmansEnabled;
    }

    public static boolean isAIDisableShovel() {
        return aiDisableShovelEnabled;
    }

    public static boolean isReIntroduceInstantBlockUpdatesEnabled() {
        return reIntroduceInstantBlockUpdatesEnabled;
    }

    public static boolean isDisableDeleteLightDataFixerEnabled() {
        return disableDeleteLightDataFixerEnabled;
    }

    public static boolean isReIntroduceLightSuppressionEnabled() {
        return reIntroduceLightSuppressionEnabled;
    }

    public static boolean isShulkerRefillEnabled() {
        return shulkerRefillEnabled;
    }

    public static boolean isBringBackSOSuppressionEnabled() {
        return bringBackSOSuppressionEnabled;
    }

    public static boolean isBringBackCCESuppressionEnabled() {
        return bringBackCCESuppressionEnabled;
    }

    public static boolean isBringBackTrapdoorUpdateSkippingEnabled() {
        return bringBackTrapdoorUpdateSkippingEnabled;
    }

    public static boolean isBringBackFloatingRedstoneComponentsOnTopOfTrapdoorEnabled() {
        return bringBackFloatingRedstoneComponentsOnTopOfTrapdoorEnabled;
    }

    public static boolean isBringBackFurnaceXPDupeEnabled() {
        return bringBackFurnaceXPDupeEnabled;
    }

    public static boolean isBringBackFullBlockInnerCollisionsEnabled() {
        return bringBackFullBlockInnerCollisionsEnabled;
    }

    public static boolean isBringBackBlockEntitySwapEnabled() {
        return bringBackBlockEntitySwapEnabled;
    }

    public static boolean isKeepBlocksWithSwappedBlockEntitiesEnabled() {
        return keepBlocksWithSwappedBlockEntitiesEnabled;
    }

    public static boolean isBringBackItemShadowing_1_17Enabled() {
        return bringBackItemShadowing_1_17Enabled;
    }

    public static boolean isBringBackItemShadowing_1_18Enabled() {
        return bringBackItemShadowing_1_18Enabled;
    }

    public static boolean isBringBackCurseBookOverstackingEnabled() {
        return bringBackCurseBookOverstackingEnabled;
    }

    public static boolean isBringBackOverstackedItemMovement_1_20Enabled() {
        return bringBackOverstackedItemMovement_1_20Enabled;
    }

    public static boolean isBringBackChunkSaveState_1_14Enabled() {
        return bringBackChunkSaveState_1_14Enabled;
    }

    public static boolean isBringBackChunkSaveState_1_21Enabled() {
        return bringBackChunkSaveState_1_21Enabled;
    }

    public static boolean isBringBackOldDragonFreezingEnabled() {
        return bringBackOldDragonFreezingEnabled;
    }

    public static boolean isBringBackArmorStandInvulnerableToWitherDamageEnabled() {
        return bringBackArmorStandInvulnerableToWitherDamageEnabled;
    }

    public static boolean isBringBackShadowItemsInMobInventoryEnabled() {
        return bringBackShadowItemsInMobInventoryEnabled;
    }

    public static boolean isBringBackVoidlessVoidTradingEnabled() {
        return bringBackVoidlessVoidTradingEnabled;
    }

    public static boolean isBringBackGracefulSOHandlingEnabled() {
        return bringBackGracefulSOHandlingEnabled;
    }

    public static boolean isBringBackGracefulOOMHandlingEnabled() {
        return bringBackGracefulOOMHandlingEnabled;
    }

    public static boolean isCceSuppressorChatMessageEnabled() {
        return cceSuppressorChatMessageEnabled;
    }
}