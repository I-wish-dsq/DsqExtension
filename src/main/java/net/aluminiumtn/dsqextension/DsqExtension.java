package net.aluminiumtn.dsqextension;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.aluminiumtn.dsqextension.config.ConfigHandler;
import net.aluminiumtn.dsqextension.handler.AutoCollectHandler;
import net.aluminiumtn.dsqextension.handler.OldRaidHandler;
import net.aluminiumtn.dsqextension.functions.*;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DsqExtension implements ModInitializer {

    public static final String MOD_ID = "dsqextension";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static boolean oldRaidsHandlerRegistered = false;

    @Override
    public void onInitialize() {
        AutoCollectHandler.register();
        TradeUnlocker.register();
        if (ConfigHandler.isReIntroduceOldRaidsEnabled()) {
            OldRaidHandler.register();
            oldRaidsHandlerRegistered = true;
        }
        registerCommands();
        ZombiePigmanAggro.register();
        AIDisableShovel.register();
    }

    private void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            registerDsqExtensionCommand(dispatcher);
        });
    }

    private void registerDsqExtensionCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("dsqextension")
                .then(Commands.literal("sneakingItems")
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                .executes(context -> {
                                    boolean enabled = BoolArgumentType.getBool(context, "enabled");
                                    ConfigHandler.setSneakingItemsEnabled(enabled);
                                    context.getSource().sendSuccess(() -> Component.literal("Sneaking items set to " + enabled), true);
                                    return 1;
                                })
                        )
                )
                .then(Commands.literal("reIntroduceOldRaids")
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                .executes(context -> {
                                    boolean enabled = BoolArgumentType.getBool(context, "enabled");
                                    ConfigHandler.setReIntroduceOldRaidsEnabled(enabled);
                                    context.getSource().sendSuccess(() -> Component.literal("Re-introduce old raids set to " + enabled), true);
                                    if (enabled) {
                                        if (!oldRaidsHandlerRegistered) {
                                            OldRaidHandler.register();
                                            oldRaidsHandlerRegistered = true;
                                        }
                                    } else {
                                        if (oldRaidsHandlerRegistered) {
                                            OldRaidHandler.unregister();
                                            oldRaidsHandlerRegistered = false;
                                        }
                                    }
                                    return 1;
                                })
                        )
                )
                .then(Commands.literal("returnVoidTrade")
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                .executes(context -> {
                                    boolean enabled = BoolArgumentType.getBool(context, "enabled");
                                    ConfigHandler.setReturnVoidTradeEnabled(enabled);
                                    context.getSource().sendSuccess(() -> Component.literal("Return void trade set to " + enabled), true);
                                    return 1;
                                })
                        )
                )
                .then(Commands.literal("returnExpFromPigmans")
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                .executes(context -> {
                                    boolean enabled = BoolArgumentType.getBool(context, "enabled");
                                    ConfigHandler.setReturnExpFromPigmansEnabled(enabled);
                                    context.getSource().sendSuccess(() -> Component.literal("Return exp from pigmans set to " + enabled), true);
                                    if (enabled) {
                                        ZombiePigmanAggro.register();
                                    } else {
                                        ZombiePigmanAggro.unregister();
                                    }
                                    return 1;
                                })
                        )
                )
                .then(Commands.literal("AIDisableShovel")
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                .executes(context -> {
                                    boolean enabled = BoolArgumentType.getBool(context, "enabled");
                                    ConfigHandler.setAIDisableShovel(enabled);
                                    context.getSource().sendSuccess(() -> Component.literal("Disabled AI for slapped mobs set to " + enabled), true);
                                    if (enabled) {
                                        AIDisableShovel.register();
                                    } else {
                                        AIDisableShovel.unregister();
                                    }
                                    return 1;
                                })
                        )
                )
                .then(Commands.literal("reIntroduceInstantBlockUpdates")
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                .executes(context -> {
                                    boolean enabled = BoolArgumentType.getBool(context, "enabled");
                                    ConfigHandler.setReIntroduceInstantBlockUpdatesEnabled(enabled);
                                    context.getSource().sendSuccess(() -> Component.literal("Instant block updates set to " + enabled), true);
                                    return 1;
                                })
                        )
                )
                .then(Commands.literal("updateSuppressionCrashFix")
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                .executes(context -> {
                                    boolean enabled = BoolArgumentType.getBool(context, "enabled");
                                    ConfigHandler.setBringBackSOSuppressionEnabled(enabled);
                                    context.getSource().sendSuccess(() -> Component.literal("Update suppression crash fix set to " + enabled), true);
                                    return 1;
                                })
                        )
                )
                .then(Commands.literal("disableDeleteLightDataFixer")
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                .executes(context -> {
                                    boolean enabled = BoolArgumentType.getBool(context, "enabled");
                                    ConfigHandler.setDisableDeleteLightDataFixerEnabled(enabled);
                                    context.getSource().sendSuccess(() -> Component.literal("Disable delete light data fixer set to " + enabled), true);
                                    return 1;
                                })
                        )
                )
        );
    }

}
