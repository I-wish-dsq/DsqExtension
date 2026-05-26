package net.aluminiumtn.dsqextension.config;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.aluminiumtn.dsqextension.util.Rule;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import org.objectweb.asm.tree.analysis.Value;

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

        if (currentValue) {
            optionsText.append(Component.literal("[false]").withStyle(ChatFormatting.DARK_GRAY)
                    .withStyle(style -> style
                            .withClickEvent(new ClickEvent.SuggestCommand("/dsqextension " + field.getName() + " false"))
                            .withHoverEvent(new HoverEvent.ShowText(Component.literal("Изменить на false").withStyle(ChatFormatting.GRAY)))
                    ));

            optionsText.append(Component.literal(" "));

            // Кнопка [true]
            optionsText.append(Component.literal("[true]").withStyle(ChatFormatting.GREEN, ChatFormatting.UNDERLINE)
                    .withStyle(style -> style
                            .withClickEvent(new ClickEvent.SuggestCommand("/dsqextension " + field.getName() + " true"))
                            .withHoverEvent(new HoverEvent.ShowText(Component.literal("Изменить на true").withStyle(ChatFormatting.GRAY)))
                    ));
        }
        else {
            optionsText.append(Component.literal("[false]").withStyle(ChatFormatting.RED, ChatFormatting.UNDERLINE)
                    .withStyle(style -> style
                            .withClickEvent(new ClickEvent.SuggestCommand("/dsqextension " + field.getName() + " false"))
                            .withHoverEvent(new HoverEvent.ShowText(Component.literal("Изменить на false").withStyle(ChatFormatting.GRAY)))
                    ));

            optionsText.append(Component.literal(" "));

            // Кнопка [true]
            optionsText.append(Component.literal("[true]").withStyle(ChatFormatting.DARK_GRAY)
                    .withStyle(style -> style
                            .withClickEvent(new ClickEvent.SuggestCommand("/dsqextension " + field.getName() + " true"))
                            .withHoverEvent(new HoverEvent.ShowText(Component.literal("Изменить на true").withStyle(ChatFormatting.GRAY)))
                    ));
        }


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
}
