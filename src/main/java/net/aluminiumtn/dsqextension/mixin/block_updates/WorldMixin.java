package net.aluminiumtn.dsqextension.mixin.block_updates;

import net.aluminiumtn.dsqextension.config.ConfigHandler;
import net.aluminiumtn.dsqextension.util.SuppressionNeighborUpdater;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.redstone.InstantNeighborUpdater;
import net.minecraft.world.level.redstone.CollectingNeighborUpdater;
import net.minecraft.world.level.redstone.NeighborUpdater;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Level.class)
public class WorldMixin {

    @Mutable
    @Shadow
    @Final
    protected CollectingNeighborUpdater neighborUpdater;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void dsqextension$replaceNeighborUpdater(
            CallbackInfo ci
    ) {
        if (!ConfigHandler.isReIntroduceInstantBlockUpdatesEnabled()) {
            return;
        }

        Level level = (Level)(Object)this;

        this.neighborUpdater =
                new SuppressionNeighborUpdater(level);
    }
}