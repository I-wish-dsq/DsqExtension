package net.aluminiumtn.dsqextension.handler;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.monster.illager.Pillager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.server.level.ServerPlayer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.world.entity.item.ItemEntity;
import net.aluminiumtn.dsqextension.config.ConfigHandler;

public class OldRaidHandler {

    private static boolean eventsRegistered = false;

    public static void register() {
        if (!ConfigHandler.isReIntroduceOldRaidsEnabled() || eventsRegistered) {
            return;
        }

        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((server, entity, killedEntity, damageSource) -> {
            if (eventsRegistered && killedEntity instanceof Pillager pillager) {
                if (isFlaggedPillager(pillager)) {
                    if (entity instanceof ServerPlayer player) {
                        applyOrIncreaseBadOmen(player);
                    }
                }
            }
        });

        ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> {
            if (eventsRegistered && entity instanceof ItemEntity itemEntity) {
                if (itemEntity.getItem().getItem() == Items.OMINOUS_BOTTLE) {
                    itemEntity.discard();
                }
            }
        });

        eventsRegistered = true;
    }

    public static void unregister() {
        eventsRegistered = false;
    }

    private static boolean isFlaggedPillager(Pillager pillager) {
        ItemStack headItem = pillager.getItemBySlot(EquipmentSlot.HEAD);
        return headItem.getItem() == Items.WHITE_BANNER;
    }

    private static void applyOrIncreaseBadOmen(ServerPlayer player) {
        MobEffectInstance currentEffect = player.getEffect(MobEffects.BAD_OMEN);
        if (currentEffect != null) {
            int currentLevel = currentEffect.getAmplifier();
            if (currentLevel < 4) {
                player.addEffect(new MobEffectInstance(MobEffects.BAD_OMEN, 6000, currentLevel + 1));
            }
        } else {
            player.addEffect(new MobEffectInstance(MobEffects.BAD_OMEN, 6000, 0));
        }
    }
}