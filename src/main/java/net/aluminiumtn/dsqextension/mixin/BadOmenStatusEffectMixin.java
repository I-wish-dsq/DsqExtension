package net.aluminiumtn.dsqextension.mixin;

import net.aluminiumtn.dsqextension.config.ConfigHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.LivingEntity;

@Mixin(targets = "net.minecraft.world.effect.BadOmenMobEffect")
public abstract class BadOmenStatusEffectMixin {

    @Inject(
            method = "applyEffectTick",
            at = @At("HEAD"),
            cancellable = true
    )
    protected void dsq$applyEffectTick(
            final ServerLevel level,
            final LivingEntity mob,
            final int amplification,
            final CallbackInfoReturnable<Boolean> cir
    ) {
        if (!ConfigHandler.isReIntroduceOldRaidsEnabled()) {
            return;
        }

        if (!(mob instanceof ServerPlayer player)) {
            return;
        }

        if (player.isSpectator() || level.getDifficulty() == Difficulty.PEACEFUL) {
            return;
        }

        final BlockPos playerBlockPos = player.blockPosition();

        if (level.isVillage(playerBlockPos)) {
            level.getRaids().createOrExtendRaid(player, playerBlockPos);
        }

        cir.setReturnValue(true);
    }
}