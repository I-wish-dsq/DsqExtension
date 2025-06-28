package net.aluminiumtn.dsqextension.mixin.block_updates;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.aluminiumtn.dsqextension.config.ConfigHandler;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;


@Mixin(ShulkerBoxBlock.class)
public class ShulkerBoxBlockMixin
{
	@WrapOperation(
			method = "getComparatorOutput",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/ScreenHandler;calculateComparatorOutput(Lnet/minecraft/block/entity/BlockEntity;)I")
	)
	private int dsqextension$bringBackCCESuppression(BlockEntity blockEntity, Operation<Integer> original) {
		if (ConfigHandler.isReIntroduceInstantBlockUpdatesEnabled()) {
			return ScreenHandler.calculateComparatorOutput((Inventory) blockEntity);
		}
		return original.call(blockEntity);
	}
} 