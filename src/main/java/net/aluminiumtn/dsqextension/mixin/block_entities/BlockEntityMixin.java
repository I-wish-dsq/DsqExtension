package net.aluminiumtn.dsqextension.mixin.block_entities;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.aluminiumtn.dsqextension.config.ConfigHandler;

@Mixin(BlockEntity.class)
public class BlockEntityMixin {

    @Inject(method = "isValidBlockState", at = @At("HEAD"), cancellable = true)
    private void dsqextension$bringBackSwappedBlockEntitiesExistence(BlockState blockState, CallbackInfoReturnable<Boolean> cir) {
        if (ConfigHandler.isReIntroduceInstantBlockUpdatesEnabled()) {
            cir.setReturnValue(true);
        }
    }
}