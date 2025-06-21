package net.aluminiumtn.dsqextension.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.aluminiumtn.dsqextension.config.ConfigHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.BooleanSupplier;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
    @Shadow @Final private static Logger LOGGER;
    @Shadow private PlayerManager playerManager;

    @WrapOperation(method = "tickWorlds", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;tick(Ljava/util/function/BooleanSupplier;)V"))
    private void addTryCatchLevel(ServerWorld world, BooleanSupplier shouldKeepTicking, Operation<Void> original) {
        if (ConfigHandler.isBringBackSOSuppressionEnabled()) {
            try {
                original.call(world, shouldKeepTicking);
            } catch (Throwable e) {
                LOGGER.error("Caught a crash in world tick", e);
                playerManager.broadcast(Text.literal("A crash was prevented in a world tick. The server should continue to run.").styled(style -> style.withItalic(true).withColor(Formatting.GRAY)), false);
            }
        } else {
            original.call(world, shouldKeepTicking);
        }
    }

    @WrapMethod(method = "tickWorlds")
    private void addTryCatchAll(BooleanSupplier shouldKeepTicking, Operation<Void> original) {
        if (ConfigHandler.isBringBackSOSuppressionEnabled()) {
            try {
                original.call(shouldKeepTicking);
            } catch (Throwable e) {
                LOGGER.error("Caught a crash in server tick", e);
                playerManager.broadcast(Text.literal("A crash was prevented in the server tick. The server should continue to run.").styled(style -> style.withItalic(true).withColor(Formatting.GRAY)), false);
            }
        } else {
            original.call(shouldKeepTicking);
        }
    }
} 