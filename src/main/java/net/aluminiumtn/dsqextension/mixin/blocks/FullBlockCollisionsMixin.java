package net.aluminiumtn.dsqextension.mixin.blocks;

import net.aluminiumtn.dsqextension.config.ConfigHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.EndPortalBlock;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class FullBlockCollisionsMixin {
    @Mixin(NetherPortalBlock.class)
    public static class NetherPortalBlockMixin {
        @Inject(
                method = "getShape",
                at = @At("HEAD"),
                cancellable = true
        )
        private void dsqextension$bringBackFullBlockCollision(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
            if (ConfigHandler.isBringBackFullBlockInnerCollisionsEnabled()) {
                cir.setReturnValue(Shapes.block());
            }
        }
    }

    @Mixin(EndPortalBlock.class)
    public static class EndPortalBlockMixin {
        @Redirect(
                method = "getEntityInsideCollisionShape",
                at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/phys/shapes/VoxelShape;")
        )
        private VoxelShape dsqextension$bringBackFullBlockCollision(BlockState state, BlockGetter level, BlockPos pos) {
            if (ConfigHandler.isBringBackFullBlockInnerCollisionsEnabled()) {
                return Shapes.block();
            }
            return state.getShape(level, pos);
        }
    }

    @Mixin(PowderSnowBlock.class)
    public static class PowderSnowBlockMixin {
        @Inject(
                method = "getEntityInsideCollisionShape",
                at = @At("HEAD"),
                cancellable = true
        )
        private void dsqextension$bringBackFullBlockCollision(BlockState state, BlockGetter level, BlockPos pos, Entity entity, CallbackInfoReturnable<VoxelShape> cir) {
            if (ConfigHandler.isBringBackFullBlockInnerCollisionsEnabled()) {
                cir.setReturnValue(Shapes.block());
            }
        }
    }
}