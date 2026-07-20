package net.aluminiumtn.dsqextension.mixin;

import net.aluminiumtn.dsqextension.config.ConfigHandler;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gamerules.GameRules;

@Mixin(Raider.class)
public abstract class RaiderEntityMixin {

    @Shadow
    public abstract Raid getCurrentRaid();

    @Inject(
            method = "die",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/monster/PatrollingMonster;die(Lnet/minecraft/world/damagesource/DamageSource;)V"
            )
    )
    protected void dsq$onDeath(
            final DamageSource source,
            final CallbackInfo ci
    ) {
        if (!ConfigHandler.isReIntroduceOldRaidsEnabled()) {
            return;
        }

        final Raider current = (Raider) (Object) this;

        if (!(current.level() instanceof ServerLevel serverWorld)) {
            return;
        }

        if (!current.isPatrolLeader()
                || this.getCurrentRaid() != null
                || serverWorld.getRaidAt(current.blockPosition()) != null) {
            return;
        }

        final ItemStack itemStack = current.getItemBySlot(EquipmentSlot.HEAD);

        if (itemStack.isEmpty()
                || !ItemStack.matches(
                itemStack,
                Raid.getOminousBannerInstance(
                        current.registryAccess()
                                .lookupOrThrow(Registries.BANNER_PATTERN)))) {
            return;
        }

        final Player attackingPlayer = this.dsq$getAttackingPlayer(source);

        if (attackingPlayer == null) {
            return;
        }

        MobEffectInstance statusEffectInstance =
                attackingPlayer.getEffect(MobEffects.BAD_OMEN);

        int amplifier = 1;

        if (statusEffectInstance != null) {
            amplifier += statusEffectInstance.getAmplifier();
            attackingPlayer.removeEffectNoUpdate(MobEffects.BAD_OMEN);
        } else {
            amplifier--;
        }

        attackingPlayer.addEffect(
                new MobEffectInstance(
                        MobEffects.BAD_OMEN,
                        6000,
                        Mth.clamp(amplifier, 0, 4),
                        false,
                        false,
                        true
                )
        );
    }

    @Unique
    private @Nullable Player dsq$getAttackingPlayer(
            final DamageSource damageSource
    ) {
        final Entity attacker = damageSource.getEntity();

        if (attacker instanceof Player player) {
            return player;
        }

        if (attacker instanceof Wolf wolf
                && wolf.isTame()
                && wolf.getOwner() instanceof Player owner) {
            return owner;
        }

        return null;
    }
}