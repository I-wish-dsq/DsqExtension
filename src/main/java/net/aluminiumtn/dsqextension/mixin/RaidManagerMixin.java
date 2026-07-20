package net.aluminiumtn.dsqextension.mixin;

import net.aluminiumtn.dsqextension.config.ConfigHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.PoiTypeTags;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raids;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.phys.Vec3;

@Mixin(Raids.class)
public abstract class RaidManagerMixin {

    @Inject(
            method = "createOrExtendRaid",
            at = @At("HEAD"),
            cancellable = true
    )
    protected void dsq$startRaid(
            final ServerPlayer player,
            final BlockPos raidPosition,
            final CallbackInfoReturnable<Raid> cir
    ) {
        if (!ConfigHandler.isReIntroduceOldRaidsEnabled()) {
            return;
        }

        final ServerLevel serverWorld = player.level();

        if (player.isSpectator()
                || !serverWorld.getGameRules().get(GameRules.RAIDS)
                || !serverWorld.environmentAttributes()
                .getValue(EnvironmentAttributes.CAN_START_RAID, raidPosition)) {
            cir.setReturnValue(null);
            return;
        }

        int poiCount = 0;
        Vec3 villageCenter = Vec3.ZERO;

        for (final PoiRecord pointOfInterest : serverWorld.getPoiManager()
                .getInRange(
                        poiType -> poiType.is(PoiTypeTags.VILLAGE),
                        raidPosition,
                        64,
                        PoiManager.Occupancy.IS_OCCUPIED)
                .toList()) {

            final BlockPos blockPos = pointOfInterest.getPos();

            villageCenter = villageCenter.add(
                    blockPos.getX(),
                    blockPos.getY(),
                    blockPos.getZ()
            );

            poiCount++;
        }

        final Raid raid = this.getOrCreateRaid(
                serverWorld,
                poiCount > 0
                        ? BlockPos.containing(villageCenter.scale(1.0 / poiCount))
                        : raidPosition
        );

        boolean shouldStartRaid = false;

        if (!raid.isStarted()) {
            if (!this.raidMap.containsValue(raid)) {
                this.raidMap.put(this.getUniqueId(), raid);
            }

            shouldStartRaid = true;
        }
        else if (raid.getRaidOmenLevel() < raid.getMaxRaidOmenLevel()) {
            shouldStartRaid = true;
        }
        else {
            player.removeEffect(MobEffects.BAD_OMEN);
        }

        if (shouldStartRaid) {
            raid.absorbRaidOmen(player);

            if (!raid.hasFirstWaveSpawned()) {
                player.awardStat(Stats.RAID_TRIGGER);
            }
        }

        ((Raids) (Object) this).setDirty();

        cir.setReturnValue(raid);
    }

    @Shadow
    @Final
    private Int2ObjectMap<Raid> raidMap;

    @Shadow
    protected abstract int getUniqueId();

    @Shadow
    protected abstract Raid getOrCreateRaid(
            ServerLevel world,
            BlockPos pos
    );
}