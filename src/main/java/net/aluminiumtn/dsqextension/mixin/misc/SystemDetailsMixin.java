package net.aluminiumtn.dsqextension.mixin.misc;

import net.minecraft.util.SystemDetails;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SystemDetails.class)
public class SystemDetailsMixin {

    //Unfortunately, it's impossible to fully revert stack overflow handling to pre 1.18(as described in MC-248200)
    //So we just prevent further error handling in the logger by removing the throwable from .warn() method, it shouldn't change the behaviour(at least I hope so).
    @Redirect(method = "tryAddGroup", at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V"))
    private void dsqextension$gracefulStackOverflowHandling(Logger instance, String s, Object o2, Object o) {
        // This empty method effectively swallows the log message.
    }

    @ModifyArg(method = "addSection(Ljava/lang/String;Ljava/util/function/Supplier;)V", at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V"),
            index = 2)
    private Object dsqextension$gracefulStackOverflowHandling2(Object throwable) {
        // Returning null removes the throwable from the logger arguments, preventing it from being logged.
        return null;
    }
} 