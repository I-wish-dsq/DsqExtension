package net.aluminiumtn.dsqextension;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;

public class AutoCollectHandler {

    public static void register() {
        PlayerBlockBreakEvents.BEFORE.register(AutoCollectHandler::onBlockBreak);
    }

    private static boolean onBlockBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (!world.isClient && ConfigHandler.isSneakingItemsEnabled()) {
            if (player.isSneaking()) {
                Block block = state.getBlock();

                if (block instanceof ShulkerBoxBlock) {
                    return true;
                }

                ItemStack stack = new ItemStack(block.asItem());
                if (!stack.isEmpty()) {
                    if (!player.getInventory().insertStack(stack)) {
                        player.dropItem(stack, false);
                    }
                    world.setBlockState(pos, state.getFluidState().getBlockState(), 3);
                    return false; 
                }
            }
        }
        return true; 
    }
}
