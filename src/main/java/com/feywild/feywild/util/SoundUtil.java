package com.feywild.feywild.util;

import net.minecraft.client.resources.sounds.AbstractSoundInstance;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;

import java.util.function.Function;

public class SoundUtil {

    private static final RandomSource RANDOM = RandomSource.create();

    public static SoundInstance copySound(SoundEvent event, SoundInstance sound, Function<? super SoundEvent, ? extends SoundInstance> defaultFactory) {
        try {
            if (sound instanceof AbstractSoundInstance) {
                // Needs special handling as getter methods don't just return the plain values.
                return new SimpleLocatableSound(event, (AbstractSoundInstance) sound);
            } else {
                return new SimpleSoundInstance(event.getLocation(), sound.getSource(), sound.getVolume(), sound.getPitch(), RANDOM, sound.isLooping(), sound.getDelay(), sound.getAttenuation(), sound.getX(), sound.getY(), sound.getZ(), sound.isRelative());
            }
        } catch (Exception e) {
            // Might fail depending on the sound
            return defaultFactory.apply(event);
        }
    }

    private static class SimpleLocatableSound extends AbstractSoundInstance {
        
        protected SimpleLocatableSound(SoundEvent event, AbstractSoundInstance parent) {
            super(event, parent.getSource(), RANDOM);
            // Fields instead of methods as the methods need a resolved sound
            // which we might not have here.
            this.volume = parent.volume;
            this.pitch = parent.pitch;
            this.x = parent.getX();
            this.y = parent.getY();
            this.z = parent.getZ();
            this.looping = parent.isLooping();
            this.delay = parent.getDelay();
            this.attenuation = parent.getAttenuation();
            this.relative = parent.isRelative();
        }
    }
}
