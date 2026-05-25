package net.aluminiumtn.dsqextension.util;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.redstone.CollectingNeighborUpdater;
import net.minecraft.world.level.redstone.InstantNeighborUpdater;
import net.minecraft.world.level.redstone.Orientation;
import org.jspecify.annotations.Nullable;


// УВАГА!!!1!! НЕЙРОСЛОП

public class SuppressionNeighborUpdater
        extends CollectingNeighborUpdater {

    /*
     * 1.17-style artificial suppression limit
     *
     * Чем меньше:
     * - тем безопаснее сервер
     * - тем раньше suppression
     *
     * 1000-3000 обычно хорошо.
     */
    private static final int MAX_CHAIN_DEPTH = 1500;

    private static final ThreadLocal<Integer> DEPTH =
            ThreadLocal.withInitial(() -> 0);

    private final InstantNeighborUpdater delegate;

    private final Level level;

    private long lastMessageTime;

    public SuppressionNeighborUpdater(Level level) {
        super(level, Integer.MAX_VALUE);

        this.level = level;

        this.delegate = new InstantNeighborUpdater(level);
    }

    private boolean enter(BlockPos pos) {

        int depth = DEPTH.get() + 1;

        DEPTH.set(depth);

        if (depth > MAX_CHAIN_DEPTH) {

            notifySuppression(pos, depth);

            return false;
        }

        return true;
    }

    private void exit() {

        DEPTH.set(DEPTH.get() - 1);
    }

    private void notifySuppression(
            BlockPos pos,
            int depth
    ) {
        long now = System.currentTimeMillis();

        if (now - this.lastMessageTime < 1000) {
            return;
        }

        this.lastMessageTime = now;

        MinecraftServer server = this.level.getServer();

        if (server == null) {
            return;
        }

        server.getPlayerList().broadcastSystemMessage(
                Component.literal(
                        "[DSQExtension] Update suppression triggered "
                                + "(depth="
                                + depth
                                + ") at "
                                + pos.getX()
                                + " "
                                + pos.getY()
                                + " "
                                + pos.getZ()
                ).withStyle(ChatFormatting.RED),
                false
        );
    }

    @Override
    public void shapeUpdate(
            Direction direction,
            BlockState neighborState,
            BlockPos pos,
            BlockPos neighborPos,
            int updateFlags,
            int updateLimit
    ) {
        if (!enter(pos)) {
            return;
        }

        try {

            this.delegate.shapeUpdate(
                    direction,
                    neighborState,
                    pos,
                    neighborPos,
                    updateFlags,
                    updateLimit
            );

        } finally {

            exit();
        }
    }

    @Override
    public void neighborChanged(
            BlockPos pos,
            Block block,
            @Nullable Orientation orientation
    ) {
        if (!enter(pos)) {
            return;
        }

        try {

            this.delegate.neighborChanged(
                    pos,
                    block,
                    orientation
            );

        } finally {

            exit();
        }
    }

    @Override
    public void neighborChanged(
            BlockState state,
            BlockPos pos,
            Block block,
            @Nullable Orientation orientation,
            boolean movedByPiston
    ) {
        if (!enter(pos)) {
            return;
        }

        try {

            this.delegate.neighborChanged(
                    state,
                    pos,
                    block,
                    orientation,
                    movedByPiston
            );

        } finally {

            exit();
        }
    }

    @Override
    public void updateNeighborsAtExceptFromFacing(
            BlockPos pos,
            Block block,
            @Nullable Direction skipDirection,
            @Nullable Orientation orientation
    ) {
        if (!enter(pos)) {
            return;
        }

        try {

            this.delegate.updateNeighborsAtExceptFromFacing(
                    pos,
                    block,
                    skipDirection,
                    orientation
            );

        } finally {

            exit();
        }
    }
}
