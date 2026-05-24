package net.aluminiumtn.dsqextension.mixin.blocks.floating;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.aluminiumtn.dsqextension.config.ConfigHandler;
import net.minecraft.world.level.block.ComparatorBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ComparatorBlock.class)
public class ComparatorBlockMixin {

    @ModifyReturnValue(
            method = "updateShape",

            at = @At(value = "RETURN", ordinal = 0)
    )
    private BlockState dsqextension$bringBackFloatingComparatorOnTrapdoor(
            BlockState returnedState,
            @Local(ordinal = 0, argsOnly = true) BlockState originalState) {

        if (ConfigHandler.isBringBackFloatingRedstoneComponentsOnTopOfTrapdoorEnabled()) {
            return originalState;
        }

        return returnedState;
    }

}