package net.aluminiumtn.dsqextension.mixin.block_entities.obtain;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import net.aluminiumtn.dsqextension.config.ConfigHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.UpgradeData;
import net.minecraft.world.level.chunk.PalettedContainerFactory;
import net.minecraft.world.level.levelgen.blending.BlendingData;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LevelChunk.class)
public abstract class LevelChunkMixin extends ChunkAccess {

    public LevelChunkMixin(ChunkPos pos, UpgradeData upgradeData, LevelHeightAccessor levelHeightAccessor, PalettedContainerFactory palettedContainerFactory, long inhabitedTime, @Nullable LevelChunkSection[] sectionArray, @Nullable BlendingData blendingData) {
        super(pos, upgradeData, levelHeightAccessor, palettedContainerFactory, inhabitedTime, sectionArray, blendingData);
    }

    @WrapWithCondition(
            method = "setBlockState",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/BlockEntity;preRemoveSideEffects(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V", ordinal = 0)
    )
    private boolean dsqextension$preventUnwantedSideEffects(BlockEntity blockEntity, BlockPos pos, BlockState oldState) {
        if (ConfigHandler.isReIntroduceInstantBlockUpdatesEnabled()) {
            return blockEntity.getType().isValid(oldState);
        }
        return true;
    }

    @WrapWithCondition(
            method = "setBlockState",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/chunk/LevelChunk;removeBlockEntity(Lnet/minecraft/core/BlockPos;)V", ordinal = 0)
    )
    private boolean dsqextension$preventRemoveBlockEntity(LevelChunk instance, BlockPos pos, @Local(ordinal = 1) BlockState oldState) {
        if (ConfigHandler.isReIntroduceInstantBlockUpdatesEnabled()) {
            return !ConfigHandler.isBringBackFurnaceXPDupeEnabled() && oldState.is(Blocks.FURNACE);
        }
        return true;
    }

    /*
    @Inject(method = "setBlockState", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z", ordinal = 1))
    private void dsqextension$moveRemoveBlockEntity(BlockPos pos, BlockState newState, int flags, CallbackInfoReturnable<BlockState> cir, @Local(ordinal = 1) BlockState oldState) {
        if (ConfigHandler.isReIntroduceInstantBlockUpdatesEnabled()) {
            boolean shouldRemoveIfFurnace = ConfigHandler.isBringBackFurnaceXPDupeEnabled() || !oldState.is(Blocks.FURNACE);

            if (!oldState.is(newState.getBlock()) && oldState.hasBlockEntity() && shouldRemoveIfFurnace) {
                removeBlockEntity(pos);
            }
        }
    }
    */

    @Redirect(method = "setBlockEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/BlockEntityType;isValid(Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    private boolean dsqextension$allowSettingUnsupportedBlockEntity(BlockEntityType<?> instance, BlockState state) {
        if (ConfigHandler.isReIntroduceInstantBlockUpdatesEnabled()) {
            return true;
        }
        return instance.isValid(state);
    }
}