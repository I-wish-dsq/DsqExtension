package net.aluminiumtn.dsqextension.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.redstone.InstantNeighborUpdater;
import net.minecraft.world.level.redstone.NeighborUpdater;
import net.minecraft.world.level.redstone.Orientation;
import org.jspecify.annotations.Nullable;


// УВАГА!!!1!! НЕЙРОСЛОП


public class SuppressionNeighborUpdater implements NeighborUpdater {

    private final InstantNeighborUpdater delegate;

    public SuppressionNeighborUpdater(Level level) {
        // Создаем ванильный мгновенный апдейтер
        this.delegate = new InstantNeighborUpdater(level);
    }

    @Override
    public void shapeUpdate(Direction direction, BlockState neighborState, BlockPos pos, BlockPos neighborPos, int updateFlags, int updateLimit) {
        try {
            this.delegate.shapeUpdate(direction, neighborState, pos, neighborPos, updateFlags, updateLimit);
        } catch (StackOverflowError e) {
            // Update Suppression сработал! Игра не крашится, цепочка обрывается (поведение 1.17)
        }
    }

    @Override
    public void neighborChanged(BlockPos pos, Block block, @Nullable Orientation orientation) {
        try {
            this.delegate.neighborChanged(pos, block, orientation);
        } catch (StackOverflowError e) {
            // Update Suppression сработал!
        }
    }

    @Override
    public void neighborChanged(BlockState state, BlockPos pos, Block block, @Nullable Orientation orientation, boolean movedByPiston) {
        try {
            this.delegate.neighborChanged(state, pos, block, orientation, movedByPiston);
        } catch (StackOverflowError e) {
            // Update Suppression сработал!
        }
    }

    @Override
    public void updateNeighborsAtExceptFromFacing(BlockPos pos, Block block, @Nullable Direction skipDirection, @Nullable Orientation orientation) {
        try {
            this.delegate.updateNeighborsAtExceptFromFacing(pos, block, skipDirection, orientation);
        } catch (StackOverflowError e) {
            // Update Suppression сработал!
        }
    }
}
