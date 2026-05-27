package net.aluminiumtn.dsqextension.util;

import com.mojang.logging.LogUtils;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import org.slf4j.Logger;


public class ShulkerRefill {

    private static final int REFILL_THRESHOLD = 16;

    public static boolean tryRefill(ServerPlayer player) {

        ItemStack handStack = player.getMainHandItem();

        if (handStack.isEmpty()) {
            return false;
        }

        if (!(handStack.getItem() instanceof BlockItem)) {
            return false;
        }

        if (handStack.getCount() >= REFILL_THRESHOLD) {
            return false;
        }

        int needed = handStack.getMaxStackSize() - handStack.getCount();

        if (needed <= 0) {
            return false;
        }

        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {

            ItemStack shulkerStack = player.getInventory().getItem(slot);

            if (!isShulkerBox(shulkerStack)) {
                continue;
            }

            ItemContainerContents contents =
                    shulkerStack.get(DataComponents.CONTAINER);

            if (contents == null) {
                continue;
            }

            NonNullList<ItemStack> items =
                    NonNullList.withSize(27, ItemStack.EMPTY);

            contents.copyInto(items);

            boolean changed = false;
            int movedTotal = 0;

            for (int i = 0; i < items.size(); i++) {

                ItemStack inside = items.get(i);

                if (inside.isEmpty()) {
                    continue;
                }

                if (!ItemStack.isSameItemSameComponents(handStack, inside)) {
                    continue;
                }

                int move = Math.min(needed, inside.getCount());

                if (move <= 0) {
                    continue;
                }

                handStack.grow(move);
                inside.shrink(move);

                needed -= move;
                movedTotal += move;

                changed = true;

                if (inside.isEmpty()) {
                    items.set(i, ItemStack.EMPTY);
                }

                if (needed <= 0) {
                    break;
                }
            }

            if (!changed) {
                continue;
            }

            shulkerStack.set(
                    DataComponents.CONTAINER,
                    ItemContainerContents.fromItems(items)
            );

            sync(player);

            return true;
        }

        return false;
    }

    private static boolean isShulkerBox(ItemStack stack) {

        if (!(stack.getItem() instanceof BlockItem blockItem)) {
            return false;
        }

        return blockItem.getBlock() instanceof ShulkerBoxBlock;
    }

    private static void sync(ServerPlayer player) {

        player.getInventory().setChanged();

        AbstractContainerMenu menu = player.containerMenu;

        menu.broadcastChanges();

        player.containerMenu.slotsChanged(player.getInventory());
    }
}
