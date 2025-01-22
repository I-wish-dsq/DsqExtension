package net.aluminiumtn.dsqextension;


import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;

public class AIDisableShovel {
    private static boolean eventsRegistered = false;

    private static final AttackEntityCallback ATTACK_CALLBACK = (player, world, hand, entity, hitResult) -> {
        ItemStack stack = player.getStackInHand(hand);

        if (stack.getItem() == Items.WOODEN_SHOVEL && ConfigHandler.isAIDisableShovel()) {
            if (entity instanceof LivingEntity target) {
                if (!world.isClient) {
                    if (target instanceof MobEntity mobEntity) {
                        if (mobEntity.isAiDisabled()) {
                            mobEntity.setAiDisabled(false);
                        } else {
                            mobEntity.setAiDisabled(true);
                            return ActionResult.SUCCESS;
                        }
                    }
                }
            }
        }
        return ActionResult.PASS;
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
