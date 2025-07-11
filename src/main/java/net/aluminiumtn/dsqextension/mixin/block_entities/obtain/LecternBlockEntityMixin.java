package net.aluminiumtn.dsqextension.mixin.block_entities.obtain;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LecternBlockEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.aluminiumtn.dsqextension.config.ConfigHandler;

@Mixin(LecternBlockEntity.class)
public abstract class LecternBlockEntityMixin {
    @Shadow public abstract void clear();

    // In 1.21.5 Mojang removed this method call
    @Inject(method = "onBlockReplaced", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z", shift = At.Shift.AFTER))
    private void dsqextension$updateBlockEntity(BlockPos pos, BlockState oldState, CallbackInfo ci) {
        if (ConfigHandler.isReIntroduceInstantBlockUpdatesEnabled()) {
            clear();
        }
    }

} 