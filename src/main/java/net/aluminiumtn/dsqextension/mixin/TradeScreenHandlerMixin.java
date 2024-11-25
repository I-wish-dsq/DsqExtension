package net.aluminiumtn.dsqextension.mixin;

import net.minecraft.screen.MerchantScreenHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.village.Merchant;
import net.minecraft.village.TradeOffer;
import java.lang.reflect.Field;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.aluminiumtn.dsqextension.ConfigHandler;

@Mixin(MerchantScreenHandler.class)
public class TradeScreenHandlerMixin {

    @Inject(method = "canUse", at = @At("HEAD"), cancellable = true)
    private void keepTradeOpen(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        if (ConfigHandler.isReturnVoidTradeEnabled()) {
            cir.setReturnValue(true);
        }
    }

}