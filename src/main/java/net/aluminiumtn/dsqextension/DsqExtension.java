package net.aluminiumtn.dsqextension;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.aluminiumtn.dsqextension.OldRaidHandler;

public class DsqExtension implements ModInitializer {

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
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated, environment) -> {
            registerDsqExtensionCommand(dispatcher);
        });
    }

   private void registerDsqExtensionCommand(CommandDispatcher<ServerCommandSource> dispatcher) {
    dispatcher.register(CommandManager.literal("dsqextension")
        .then(CommandManager.literal("sneakingItems")
            .then(CommandManager.argument("enabled", BoolArgumentType.bool())
                .executes(context -> {
                    boolean enabled = BoolArgumentType.getBool(context, "enabled");
                    ConfigHandler.setSneakingItemsEnabled(enabled);
                    context.getSource().sendFeedback(() -> Text.literal("Sneaking items set to " + enabled), true);
                    return 1;
                })
            )
        )
        .then(CommandManager.literal("reIntroduceOldRaids")
            .then(CommandManager.argument("enabled", BoolArgumentType.bool())
                .executes(context -> {
                    boolean enabled = BoolArgumentType.getBool(context, "enabled");
                    ConfigHandler.setReIntroduceOldRaidsEnabled(enabled);
                    context.getSource().sendFeedback(() -> Text.literal("Re-introduce old raids set to " + enabled), true);
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
        .then(CommandManager.literal("returnVoidTrade")
            .then(CommandManager.argument("enabled", BoolArgumentType.bool())
                .executes(context -> {
                    boolean enabled = BoolArgumentType.getBool(context, "enabled");
                    ConfigHandler.setReturnVoidTradeEnabled(enabled);
                    context.getSource().sendFeedback(() -> Text.literal("Return void trade set to " + enabled), true);
                    return 1;
                })
            )
        )
        .then(CommandManager.literal("returnExpFromPigmans")
            .then(CommandManager.argument("enabled", BoolArgumentType.bool())
                .executes(context -> {
                    boolean enabled = BoolArgumentType.getBool(context, "enabled");
                    ConfigHandler.setReturnExpFromPigmansEnabled(enabled);
                    context.getSource().sendFeedback(() -> Text.literal("Return exp from pigmans set to " + enabled), true);
                    if (enabled) {
                        ZombiePigmanAggro.register();
                    } else {
                        ZombiePigmanAggro.unregister();
                    }
                    return 1;
                })
            )
        )
            .then(CommandManager.literal("AIDisableShovel")
                    .then(CommandManager.argument("enabled", BoolArgumentType.bool())
                            .executes(context -> {
                                boolean enabled = BoolArgumentType.getBool(context, "enabled");
                                ConfigHandler.setAIDisableShovel(enabled);
                                context.getSource().sendFeedback(() -> Text.literal("Disabled AI for slapped mobs set to " + enabled), true);
                                if (enabled) {
                                    AIDisableShovel.register();
                                } else {
                                    AIDisableShovel.unregister();
                                }
                                return 1;
                            })
                    )
            )

    );
}
}
