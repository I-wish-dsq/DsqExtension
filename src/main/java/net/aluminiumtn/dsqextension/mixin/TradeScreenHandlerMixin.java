package net.aluminiumtn.dsqextension.mixin;

import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import java.lang.reflect.Field;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.aluminiumtn.dsqextension.config.ConfigHandler;

@Mixin(MerchantMenu.class)
public class TradeScreenHandlerMixin {

    @Inject(method = "stillValid", at = @At("HEAD"), cancellable = true)
    private void keepTradeOpen(Player player, CallbackInfoReturnable<Boolean> cir) {
        if (ConfigHandler.isReturnVoidTradeEnabled()) {
            cir.setReturnValue(true);
        }
    }

}