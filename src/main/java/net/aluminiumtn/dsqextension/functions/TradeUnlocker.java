package net.aluminiumtn.dsqextension.functions;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.aluminiumtn.dsqextension.mixin.MerchantScreenHandlerAccessor;

public class TradeUnlocker {

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                if (player.containerMenu instanceof MerchantMenu) {
                    MerchantMenu handler = (MerchantMenu) player.containerMenu;
                    Merchant merchant = ((MerchantScreenHandlerAccessor) handler).getMerchant();
                    if (merchant != null) {
                        AbstractVillager merchantEntity = (AbstractVillager) merchant;
                        double distance = player.distanceToSqr(merchantEntity.getX(), merchantEntity.getY(), merchantEntity.getZ());
                        if (distance > 1000) {
                            for (MerchantOffer offer : merchant.getOffers()) {
                                offer.resetSpecialPriceDiff();
                                offer.resetUses();
                            }
                        }
                    }
                }
            }
        });
    }
}