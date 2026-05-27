package net.aluminiumtn.dsqextension.mixin;

import net.aluminiumtn.dsqextension.config.ConfigHandler;
import net.aluminiumtn.dsqextension.util.ShulkerRefill;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

@Mixin(BlockItem.class)
public class BlockItemMixin {

    private static final Logger LOGGER = LogUtils.getLogger();

    @Inject(
            method = "place",
            at = @At("RETURN")
    )
    private void shulkerRefill(
            BlockPlaceContext context,
            CallbackInfoReturnable<InteractionResult> cir
    ) {
        if (!ConfigHandler.isShulkerRefillEnabled()) {
            return;
        }
        if (!cir.getReturnValue().consumesAction()) {
            return;
        }

        if (!(context.getPlayer() instanceof ServerPlayer player)) {
            return;
        }

        ShulkerRefill.tryRefill(player);
    }
}
