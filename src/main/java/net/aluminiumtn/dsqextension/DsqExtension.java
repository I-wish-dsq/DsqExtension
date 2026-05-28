package net.aluminiumtn.dsqextension;

import com.mojang.brigadier.CommandDispatcher;
import net.aluminiumtn.dsqextension.config.ConfigCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.Commands;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.aluminiumtn.dsqextension.config.ConfigHandler;
import net.aluminiumtn.dsqextension.handler.AutoCollectHandler;
import net.aluminiumtn.dsqextension.handler.OldRaidHandler;
import net.aluminiumtn.dsqextension.functions.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.aluminiumtn.dsqextension.mixin.accessors.EnityAccessor.getEntityCounter;
import static net.aluminiumtn.dsqextension.util.LightUpdatesTracker.getLightEngineQueue;
import static net.aluminiumtn.dsqextension.util.LightUpdatesTracker.MAX_LIGHT_UPDATES_PER_TICK;

public class DsqExtension implements ModInitializer {

    public static final String MOD_ID = "dsqextension";
    public static final String MOD_DESC = "Мод разработанный dsqteam. Fuck mojang fixes";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static boolean oldRaidsHandlerRegistered = false;

    @Override
    public void onInitialize() {
        ConfigHandler.loadConfig();

        AutoCollectHandler.register();
        TradeUnlocker.register();

        if (ConfigHandler.isReIntroduceOldRaidsEnabled()) {
            OldRaidHandler.register();
            oldRaidsHandlerRegistered = true;
        }
        if (ConfigHandler.isReturnExpFromPigmansEnabled()) {
            ZombiePigmanAggro.register();
        }
        if (ConfigHandler.isAIDisableShovel()) {
            AIDisableShovel.register();
        }

        registerCommands();
    }

    private void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            ConfigCommand.register(dispatcher);
            registerCustomCommands(dispatcher);
        });
    }

    private void registerCustomCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal(MOD_ID)
                .then(Commands.literal("getLightEngineQueue")
                        .executes(context -> {
                            int queue = getLightEngineQueue();
                            ChatFormatting COLOR = (queue > MAX_LIGHT_UPDATES_PER_TICK) ? ChatFormatting.RED : ChatFormatting.GREEN;
                            context.getSource().sendSystemMessage(
                                    Component.literal("Light engine queue: " + queue).withStyle(COLOR)
                            );
                            return 1;
                        })
                )

                .then(Commands.literal("getEntityID")
                        .executes(context -> {
                            context.getSource().sendSystemMessage(Component.literal("Current entity id: " + getEntityCounter()));
                            return 1;
                        })
                )
        );
    }

    public static void onRuleChanged(String ruleName, boolean newValue) {
        switch (ruleName) {
            case "reIntroduceOldRaidsEnabled":
                if (newValue && !oldRaidsHandlerRegistered) {
                    OldRaidHandler.register();
                    oldRaidsHandlerRegistered = true;
                } else if (!newValue && oldRaidsHandlerRegistered) {
                    OldRaidHandler.unregister();
                    oldRaidsHandlerRegistered = false;
                }
                break;

            case "returnExpFromPigmansEnabled":
                if (newValue) ZombiePigmanAggro.register();
                else ZombiePigmanAggro.unregister();
                break;

            case "AIDisableShovelEnabled":
                if (newValue) AIDisableShovel.register();
                else AIDisableShovel.unregister();
                break;
        }
    }
}
