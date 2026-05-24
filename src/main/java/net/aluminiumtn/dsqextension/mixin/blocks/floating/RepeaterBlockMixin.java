package net.aluminiumtn.dsqextension.mixin.blocks.floating;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.aluminiumtn.dsqextension.config.ConfigHandler;
import net.minecraft.world.level.block.state.BlockState; // Изменено: net.minecraft.block.BlockState -> net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.RepeaterBlock; // Изменено: net.minecraft.block.RepeaterBlock -> net.minecraft.world.level.block.RepeaterBlock
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(RepeaterBlock.class)
public class RepeaterBlockMixin {
    @ModifyReturnValue(
            method = "updateShape", // Изменено: getStateForNeighborUpdate -> updateShape
            at = @At(value = "RETURN", ordinal = 0)
    )
    private BlockState dsqextension$bringBackFloatingRepeaterOnTrapdoor(BlockState state, @Local(ordinal = 0, argsOnly = true) BlockState original) {
        if (ConfigHandler.isBringBackFloatingRedstoneComponentsOnTopOfTrapdoorEnabled()) {
            return original;
        }
        return state;
    }
}