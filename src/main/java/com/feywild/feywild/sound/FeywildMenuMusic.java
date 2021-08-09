package com.feywild.feywild.sound;

import com.feywild.feywild.config.ClientConfig;
import com.feywild.feywild.util.SoundUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.client.event.sound.PlaySoundEvent;

public class FeywildMenuMusic {

    private static ISound currentFeywildMenuMusic = null;
    
    public static void playSound(PlaySoundEvent event) {
        if (ClientConfig.replace_menu && event.getSound().canPlaySound()) {
            if (event.getSound().getLocation().equals(SoundEvents.MUSIC_MENU.getLocation())) {
                handleSoundReplace(ModSoundEvents.musicMenu, event);
            } else if (event.getSound().getLocation().equals(SoundEvents.MUSIC_CREATIVE.getLocation())) {
                handleSoundReplace(ModSoundEvents.musicCreative, event);
            }
        }
    }
    
    private static void handleSoundReplace(SoundEvent sound, PlaySoundEvent event) {
        if (currentFeywildMenuMusic != null && sound.getLocation().equals(currentFeywildMenuMusic.getLocation())
                && Minecraft.getInstance().getSoundManager().isActive(currentFeywildMenuMusic)) {
            event.setResultSound(null);
        } else {
            currentFeywildMenuMusic = SoundUtil.copySound(sound, event.getSound(), SimpleSound::forMusic);
            event.setResultSound(currentFeywildMenuMusic);
        }
    }
}
