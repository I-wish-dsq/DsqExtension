package net.aluminiumtn.dsqextension.mixin.blocks.floating;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.aluminiumtn.dsqextension.config.ConfigHandler;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(RedStoneWireBlock.class)
public class RedstoneWireBlockMixin {

    @ModifyReturnValue(
            method = "updateShape",

            at = @At(value = "RETURN", ordinal = 0)
    )
    private BlockState dsqextension$bringBackFloatingRedstoneOnTrapdoor(
            BlockState returnedState,
            @Local(ordinal = 0, argsOnly = true) BlockState originalState) {

        if (ConfigHandler.isBringBackFloatingRedstoneComponentsOnTopOfTrapdoorEnabled()) {
            return originalState;
        }

        return returnedState;
    }
}