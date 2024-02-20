package com.feywild.feywild.sound;

import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import org.moddingx.libx.registration.Registerable;
import org.moddingx.libx.registration.RegistrationContext;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.Optional;

public class FeySound implements Registerable {
    
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private final Optional<Float> range;
    private SoundEvent event;

    public FeySound() {
        this.range = Optional.empty();
    }
    
    public FeySound(float range) {
        this.range = Optional.of(range);
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    public void registerAdditional(RegistrationContext ctx, EntryCollector builder) {
        if (this.event == null) {
            this.event = this.range.map(r -> SoundEvent.createFixedRangeEvent(ctx.id(), r)).orElseGet(() -> SoundEvent.createVariableRangeEvent(ctx.id()));
        }
        builder.register(Registries.SOUND_EVENT, this.event);
    }
    
    public SoundEvent getSoundEvent() {
        if (this.event == null) throw new IllegalStateException("SoundEvent not registered.");
        return this.event;
    }
}
