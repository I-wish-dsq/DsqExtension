package net.aluminiumtn.dsqextension.mixin;

import net.aluminiumtn.dsqextension.config.ConfigHandler;
import net.aluminiumtn.dsqextension.util.LightUpdatesTracker;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.lighting.LevelLightEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelLightEngine.class)
public class LevelLightEngineMixin {

    @Inject(
            method = "checkBlock",
            at = @At("HEAD"),
            cancellable = true
    )
    private void dsqextension$suppressLightUpdates(
            BlockPos pos,
            CallbackInfo ci
    ) {

        if (!ConfigHandler.isReIntroduceLightSuppressionEnabled()) {
            return;
        }

        if (LightUpdatesTracker.shouldSupress()) {
            ci.cancel();
        }
    }
}
