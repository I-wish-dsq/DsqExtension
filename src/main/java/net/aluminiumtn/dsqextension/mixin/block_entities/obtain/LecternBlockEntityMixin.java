package net.aluminiumtn.dsqextension.mixin.block_entities.obtain;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.aluminiumtn.dsqextension.config.ConfigHandler;

@Mixin(LecternBlockEntity.class)
public abstract class LecternBlockEntityMixin {

    @Shadow public abstract void clearContent();

    @Inject(method = "preRemoveSideEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z", shift = At.Shift.AFTER))
    private void dsqextension$updateBlockEntity(BlockPos pos, BlockState state, CallbackInfo ci) {
        if (ConfigHandler.isReIntroduceInstantBlockUpdatesEnabled()) {
            clearContent();
        }
    }
}