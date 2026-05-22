package net.aluminiumtn.dsqextension.mixin.block_updates;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.aluminiumtn.dsqextension.config.ConfigHandler;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;

@Mixin(RedstoneWireBlockMixin.class)
public class RedstoneWireBlockMixin {

    @WrapOperation(
            method = "getRenderConnectionType(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;Z)Lnet/minecraft/block/enums/WireConnection;",
            constant = @Constant(classValue = TrapDoorBlock.class))
    private boolean dsqextension$bringBackTrapdoorUpdateSkipping(Object obj, Operation<Boolean> original) {
        if (ConfigHandler.isReIntroduceInstantBlockUpdatesEnabled()) {
            return false;
        }
        return original.call(obj);
    }
} 