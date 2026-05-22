package net.aluminiumtn.dsqextension.functions;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.world.entity.monster.zombie.ZombifiedPiglin;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.Level;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.aluminiumtn.dsqextension.config.ConfigHandler;

public class ZombiePigmanAggro {
    private static boolean eventsRegistered = false;

    private static final AttackEntityCallback ATTACK_CALLBACK = (player, level, hand, entity, hitResult) -> {
        if (entity instanceof ZombifiedPiglin && ConfigHandler.isReturnExpFromPigmansEnabled()) {
            double radius = 16.0D;
            AABB box = new AABB(
                    entity.getX() - radius, entity.getY() - radius, entity.getZ() - radius,
                    entity.getX() + radius, entity.getY() + radius, entity.getZ() + radius
            );

            level.getEntitiesOfClass(ZombifiedPiglin.class, box, zombiePigman -> true)
                    .forEach(nearbyPigman -> {
                        nearbyPigman.setTarget(player.asLivingEntity());
                        nearbyPigman.setPersistentAngerEndTime(400);
                    });
        }
        return InteractionResult.PASS;
    };

    private static final ServerLivingEntityEvents.AfterDeath DEATH_CALLBACK = (entity, source) -> {
        if (entity instanceof ZombifiedPiglin zombiePiglin && ConfigHandler.isReturnExpFromPigmansEnabled()) {
            if (zombiePiglin.getPersistentAngerEndTime() > 0) {
                Level level = entity.level();
                if (!level.isClientSide()) {
                    int xp = 5;
                    while (xp > 0) {
                        int dropXp = Math.min(xp, 7);
                        xp -= dropXp;
                        ExperienceOrb experienceOrb = new ExperienceOrb(
                                level,
                                entity.getX(),
                                entity.getY(),
                                entity.getZ(),
                                dropXp
                        );
                        level.addFreshEntity(experienceOrb);
                    }
                }
            }
        }
    };

    public static void register() {
        if (!eventsRegistered) {
            AttackEntityCallback.EVENT.register(ATTACK_CALLBACK);
            ServerLivingEntityEvents.AFTER_DEATH.register(DEATH_CALLBACK);
            eventsRegistered = true;
        }
    }

    public static void unregister() {
        eventsRegistered = false;
    }
}