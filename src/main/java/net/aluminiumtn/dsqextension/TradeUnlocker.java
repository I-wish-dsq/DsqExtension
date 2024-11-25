package net.aluminiumtn.dsqextension;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.MerchantScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.village.Merchant;
import net.minecraft.village.TradeOffer;
import net.aluminiumtn.dsqextension.mixin.MerchantScreenHandlerAccessor;

public class TradeUnlocker {

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                if (player.currentScreenHandler instanceof MerchantScreenHandler) {
                    MerchantScreenHandler handler = (MerchantScreenHandler) player.currentScreenHandler;
                    Merchant merchant = ((MerchantScreenHandlerAccessor) handler).getMerchant();
                    if (merchant != null) {
                        MerchantEntity merchantEntity = (MerchantEntity) merchant;
                        double distance = player.squaredDistanceTo(merchantEntity.getX(), merchantEntity.getY(), merchantEntity.getZ());
                        if (distance > 1000) { 
                            for (TradeOffer offer : merchant.getOffers()) {
                                offer.clearSpecialPrice();
                                offer.resetUses();
                            }
                        }
                    }
                }
            }
        });
    }
}
