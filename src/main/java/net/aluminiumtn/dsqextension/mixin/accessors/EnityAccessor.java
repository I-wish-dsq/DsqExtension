package net.aluminiumtn.dsqextension.mixin.accessors;


import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.concurrent.atomic.AtomicInteger;

@Mixin(Entity.class)
public interface EnityAccessor {

    @Accessor(
            "ENTITY_COUNTER"
    )
    static AtomicInteger getEntityCounter() {
        throw new AssertionError();
    }
}
