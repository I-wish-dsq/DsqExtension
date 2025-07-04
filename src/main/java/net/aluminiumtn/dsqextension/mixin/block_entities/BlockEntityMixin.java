package net.aluminiumtn.dsqextension.mixin.block_entities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.aluminiumtn.dsqextension.config.ConfigHandler;


@Mixin(BlockEntity.class)
public class BlockEntityMixin {

    @Inject(method = "supports", at = @At("HEAD"), cancellable = true)
    private void dsqextension$bringBackSwappedBlockEntitiesExistence(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (ConfigHandler.isReIntroduceInstantBlockUpdatesEnabled()) {
            cir.setReturnValue(true);
        }
    }
} 