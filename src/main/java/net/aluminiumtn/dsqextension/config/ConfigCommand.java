package net.aluminiumtn.dsqextension.config;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.aluminiumtn.dsqextension.util.Rule;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static net.aluminiumtn.dsqextension.DsqExtension.MOD_DESC;

public class ConfigCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> cmd = Commands.literal("dsqextension")
                .executes(context -> {
                    context.getSource().sendSuccess(() -> Component.literal(MOD_DESC), false);
                    return 1;
                });

        for (Field field : getRuleFields()) {
            cmd.then(Commands.literal(field.getName())
                    .executes(context -> displayRuleInfo(context.getSource(), field))

                    .then(Commands.argument("value", BoolArgumentType.bool())
                            .executes(context -> setRule(context, field))
                    )
            );
        }

        dispatcher.register(cmd);
    }

    private static int displayRuleInfo(CommandSourceStack source, Field field) {
        Rule rule = field.getAnnotation(Rule.class);
        String desc = rule != null ? rule.desc() : "Описание отсутствует";
        boolean defaultValue = rule != null && rule.defaultValue();

        boolean currentValue = false;

        try {
            currentValue = field.getBoolean(null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        source.sendSystemMessage(Component.literal("=======================================").withStyle(ChatFormatting.WHITE));

        source.sendSystemMessage(Component.literal(field.getName()).withStyle(ChatFormatting.GOLD, ChatFormatting.BOLD));

        source.sendSystemMessage(Component.literal(desc).withStyle(ChatFormatting.WHITE));

        source.sendSystemMessage(Component.literal("Type: ").withStyle(ChatFormatting.GRAY)
                .append(Component.literal("boolean").withStyle(ChatFormatting.WHITE)));

        source.sendSystemMessage(Component.literal("Default value: ").withStyle(ChatFormatting.GRAY)
                .append(Component.literal(String.valueOf(defaultValue)).withStyle(ChatFormatting.AQUA)));

        MutableComponent optionsText = Component.literal("Suggested options: ").withStyle(ChatFormatting.GRAY);

        optionsText.append(createToggleButton("[false]", currentValue ? ChatFormatting.DARK_GRAY : ChatFormatting.RED, !currentValue, "/dsqextension " + field.getName() + " false", "Изменить на false"));

        optionsText.append(Component.literal(" "));

        optionsText.append(createToggleButton("[true]", currentValue ? ChatFormatting.GREEN : ChatFormatting.DARK_GRAY, currentValue, "/dsqextension " + field.getName() + " true", "Изменить на true"));


        source.sendSystemMessage(optionsText);
        source.sendSystemMessage(Component.literal("=======================================").withStyle(ChatFormatting.WHITE));

        return 1;
    }

    private static int setRule(CommandContext<CommandSourceStack> context, Field field) {
        boolean newValue = BoolArgumentType.getBool(context, "value");
        ChatFormatting color = (newValue) ? ChatFormatting.GREEN : ChatFormatting.RED;
        try {
            field.setBoolean(null, newValue);

            net.aluminiumtn.dsqextension.DsqExtension.onRuleChanged(field.getName(), newValue);
            ConfigHandler.saveConfig();

            context.getSource().sendSystemMessage(
                    Component.literal("Правило ")
                            .append(Component.literal(field.getName()).withStyle(ChatFormatting.WHITE))
                            .append(" установлено на ")
                            .append(Component.literal(String.valueOf(newValue)).withStyle(color, ChatFormatting.BOLD))
            );
        } catch (IllegalAccessException e) {
            context.getSource().sendSystemMessage(Component.literal("Ошибка при изменении правила!").withStyle(ChatFormatting.RED));
            e.printStackTrace();
        }
        return 1;
    }

    private static List<Field> getRuleFields() {
        List<Field> fields = new ArrayList<>();
        for (Field field : ConfigHandler.class.getDeclaredFields()) {
            if (field.isAnnotationPresent(Rule.class) && field.getType() == boolean.class) {
                field.setAccessible(true);
                fields.add(field);
            }
        }
        return fields;
    }

    private static Component createToggleButton(String text, ChatFormatting color, boolean underline, String command, String hoverText) {
        return Component.literal(text).setStyle(Style.EMPTY.withColor(color).withUnderlined(underline).withClickEvent(new ClickEvent.SuggestCommand(command)).withHoverEvent(new HoverEvent.ShowText(Component.literal(hoverText).withStyle(ChatFormatting.GRAY))));
    }
}
