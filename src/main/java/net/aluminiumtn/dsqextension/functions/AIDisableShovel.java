package net.aluminiumtn.dsqextension.functions;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.aluminiumtn.dsqextension.config.ConfigHandler;

public class AIDisableShovel {
    private static boolean eventsRegistered = false;

    private static final AttackEntityCallback ATTACK_CALLBACK = (player, level, hand, entity, hitResult) -> {
        ItemStack stack = player.getItemInHand(hand);

        if (player.isShiftKeyDown() && stack.getItem() == Items.WOODEN_SHOVEL && ConfigHandler.isAIDisableShovel()) {
            if (entity instanceof LivingEntity target) {
                if (!level.isClientSide()) {
                    if (target instanceof Mob mobEntity) {
                        if (mobEntity.isNoAi()) {
                            mobEntity.setNoAi(false);
                            mobEntity.setHealth(mobEntity.getMaxHealth());
                            return InteractionResult.FAIL;
                        } else {
                            mobEntity.setNoAi(true);

                            player.sendOverlayMessage(Component.literal("AI disabled for " + mobEntity.getName().getString()));

                            return InteractionResult.SUCCESS;
                        }
                    }
                }
            }
        }
        return InteractionResult.PASS;
    };


    public static void register() {
        if (!eventsRegistered) {
            AttackEntityCallback.EVENT.register(ATTACK_CALLBACK);
            eventsRegistered = true;
        }
    }

    public static void unregister() {
            eventsRegistered = false;
    }
}
