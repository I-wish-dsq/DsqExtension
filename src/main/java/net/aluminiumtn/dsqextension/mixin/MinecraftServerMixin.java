package net.aluminiumtn.dsqextension.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.aluminiumtn.dsqextension.config.ConfigHandler;
import net.aluminiumtn.dsqextension.util.LightUpdatesTracker;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.players.PlayerList;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
    @Shadow @Final private static Logger LOGGER;
    @Shadow private PlayerList playerList;

    @WrapOperation(method = "tickChildren", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;tick(Ljava/util/function/BooleanSupplier;)V"))
    private void addTryCatchLevel(ServerLevel world, BooleanSupplier shouldKeepTicking, Operation<Void> original) {
        if (ConfigHandler.isBringBackSOSuppressionEnabled()) {
            try {
                original.call(world, shouldKeepTicking);
            } catch (Throwable e) {
                LOGGER.error("Caught a crash in world tick", e);
                playerList.broadcastSystemMessage(Component.literal("A crash was prevented in a world tick. The server should continue to run.").withStyle(style -> style.withItalic(true).withColor(ChatFormatting.GRAY)), false);
            }
        } else {
            original.call(world, shouldKeepTicking);
        }
    }

    @WrapMethod(method = "tickChildren")
    private void addTryCatchAll(BooleanSupplier shouldKeepTicking, Operation<Void> original) {
        if (ConfigHandler.isBringBackSOSuppressionEnabled()) {
            try {
                original.call(shouldKeepTicking);
            } catch (Throwable e) {
                LOGGER.error("Caught a crash in server tick", e);
                playerList.broadcastSystemMessage(Component.literal("A crash was prevented in the server tick. The server should continue to run.").withStyle(style -> style.withItalic(true).withColor(ChatFormatting.GRAY)), false);
            }
        } else {
            original.call(shouldKeepTicking);
        }
    }

    @Inject(
            method = "tickServer",
            at = @At("HEAD")
    )
    private void dsqextension$resetLightCounter(
            CallbackInfo ci
    ) {
        LightUpdatesTracker.simulateLightEngineWork117();
    }
}