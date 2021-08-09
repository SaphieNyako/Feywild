package com.feywild.feywild.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.LocatableSound;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.util.SoundEvent;

import java.util.function.Function;

public class SoundUtil {
    
    public static ISound copySound(SoundEvent event, ISound sound, Function<? super SoundEvent, ? extends ISound> defaultFactory) {
        try {
            if (sound instanceof LocatableSound) {
                // Needs special handling as getter methods don't just return the plain values.
                return new SimpleLocatableSound(event, (LocatableSound) sound);
            } else {
                return new SimpleSound(event.getLocation(), sound.getSource(), sound.getVolume(), sound.getPitch(), sound.isLooping(), sound.getDelay(), sound.getAttenuation(), sound.getX(), sound.getY(), sound.getZ(), sound.isRelative());
            }
        } catch (Exception e) {
            // Might fail depending on the sound
            return defaultFactory.apply(event);
        }
    }

    private static class SimpleLocatableSound extends LocatableSound {

        protected SimpleLocatableSound(SoundEvent event, LocatableSound parent) {
            super(event, parent.getSource());
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
