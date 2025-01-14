package net.aluminiumtn.dsqextension;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;

public class ZombiePigmanAggro {
    private static boolean eventsRegistered = false;
    
    private static final AttackEntityCallback ATTACK_CALLBACK = (player, world, hand, entity, hitResult) -> {
        if (entity instanceof ZombifiedPiglinEntity && ConfigHandler.isReturnExpFromPigmansEnabled()) {
            double radius = 16.0D;
            Box box = new Box(
                entity.getX() - radius, entity.getY() - radius, entity.getZ() - radius,
                entity.getX() + radius, entity.getY() + radius, entity.getZ() + radius
            );

            world.getEntitiesByClass(ZombifiedPiglinEntity.class, box, zombiePigman -> true)
                .forEach(nearbyPigman -> {
                    nearbyPigman.setAngryAt(player.getUuid());
                    nearbyPigman.setAngerTime(400);
                });
        }
        return ActionResult.PASS;
    };

    private static final ServerLivingEntityEvents.AfterDeath DEATH_CALLBACK = (entity, source) -> {
        if (entity instanceof ZombifiedPiglinEntity zombiePiglin && ConfigHandler.isReturnExpFromPigmansEnabled()) {
            if (zombiePiglin.getAngerTime() > 0) {
                World world = entity.getWorld();
                if (!world.isClient) {
                    int xp = 5;
                    while (xp > 0) {
                        int dropXp = Math.min(xp, 7);
                        xp -= dropXp;
                        ExperienceOrbEntity experienceOrb = new ExperienceOrbEntity(
                            world,
                            entity.getX(),
                            entity.getY(),
                            entity.getZ(),
                            dropXp
                        );
                        world.spawnEntity(experienceOrb);
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