package net.aluminiumtn.dsqextension.handler;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.aluminiumtn.dsqextension.config.ConfigHandler;

import java.util.Objects;

public class AutoCollectHandler {

    public static void register() {
        PlayerBlockBreakEvents.BEFORE.register(AutoCollectHandler::onBlockBreak);
    }

    private static boolean onBlockBreak(Level level, Player player, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (!level.isClientSide() && ConfigHandler.isSneakingItemsEnabled()) {
            if (player.gameMode().isSurvival()) {
                if (player.isShiftKeyDown()) {
                    Block block = state.getBlock();

                    if (block instanceof ShulkerBoxBlock) {
                        if (blockEntity instanceof ShulkerBoxBlockEntity) {
                            var droppedStacks = Block.getDrops(state, (ServerLevel) level, pos, blockEntity, player, player.getMainHandItem());

                            if (!droppedStacks.isEmpty()) {
                                ItemStack shulkerStack = droppedStacks.get(0);

                                if (!player.getInventory().add(shulkerStack)) {
                                    player.drop(shulkerStack, false);
                                }

                                level.setBlock(pos, state.getFluidState().createLegacyBlock(), 3);
                                return false;
                            }
                        }
                    }

                    ItemStack stack = new ItemStack(block.asItem());
                    if (!stack.isEmpty()) {
                        if (!player.getInventory().add(stack)) {
                            player.drop(stack, false);
                        }
                        level.setBlock(pos, state.getFluidState().createLegacyBlock(), 3);
                        return false;
                    }
                }
            }
        }
        return true;
    }
}