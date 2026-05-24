package net.aluminiumtn.dsqextension.mixin.block_updates;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.aluminiumtn.dsqextension.config.ConfigHandler;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ShulkerBoxBlock.class)
public class ShulkerBoxBlockMixin {

	@WrapOperation(
			method = "getAnalogOutputSignal",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/inventory/AbstractContainerMenu;getRedstoneSignalFromBlockEntity(Lnet/minecraft/world/level/block/entity/BlockEntity;)I"
			)
	)
	private int dsqextension$bringBackCCESuppression(BlockEntity blockEntity, Operation<Integer> original) {
		if (ConfigHandler.isReIntroduceInstantBlockUpdatesEnabled()) {
			return AbstractContainerMenu.getRedstoneSignalFromContainer((Container) blockEntity);
		}
		return original.call(blockEntity);
	}
}