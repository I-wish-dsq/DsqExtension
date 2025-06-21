package net.aluminiumtn.dsqextension.mixin.block_updates;

import net.minecraft.world.World;
import net.minecraft.world.block.NeighborUpdater;
import net.minecraft.world.block.SimpleNeighborUpdater;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.aluminiumtn.dsqextension.config.ConfigHandler;

@Mixin(World.class)
public class WorldMixin {

    @Mutable
    @Shadow
    @Final
    protected NeighborUpdater neighborUpdater;

    //Just replaces the default neighbor updater with the simple one(for some reason Mojang left the pre 1.19 neighbor updater in the game)
    @Inject(method = "<init>", at = @At("TAIL"))
    private void antishadowpatch$bringBackStackOverflowSuppression(CallbackInfo ci) {
        if (ConfigHandler.isReIntroduceInstantBlockUpdatesEnabled()) {
            this.neighborUpdater = new SimpleNeighborUpdater((World) (Object) this);
        }
    }


} 