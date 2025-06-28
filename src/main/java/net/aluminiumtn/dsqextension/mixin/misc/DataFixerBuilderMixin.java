package net.aluminiumtn.dsqextension.mixin.misc;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixerBuilder;
import net.aluminiumtn.dsqextension.config.ConfigHandler;
import net.minecraft.datafixer.fix.ChunkDeleteLightFix;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = DataFixerBuilder.class, remap = false)
public class DataFixerBuilderMixin {
    @Inject(at = @At("HEAD"), method = "addFixer", cancellable = true)
    private void dsqextension$disableDeleteLightDataFixer(DataFix fix, CallbackInfo ci) {
        if(ConfigHandler.isDisableDeleteLightDataFixerEnabled() && fix instanceof ChunkDeleteLightFix) {
            ci.cancel();
        }
    }
} 