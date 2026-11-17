package com.saphienyako.feywild.entity.base.handler;

import com.saphienyako.feywild.entity.AshenLordEntity;
import com.saphienyako.feywild.entity.MabEntity;
import com.saphienyako.feywild.entity.OberonEntity;
import com.saphienyako.feywild.entity.TitaniaEntity;
import com.saphienyako.feywild.entity.base.BossBase;
import com.saphienyako.feywild.sound.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvent;

public class BossSoundHandler {

    private BossSoundHandler() {}

    public static void stopAmbientSound(BossBase boss) {
        SoundEvent sound = boss.getAmbientSoundForSoundHandler();

        if (sound != null) {
            stop(boss, sound);
        }
    }

    public static void stopSoundOnDeath(BossBase boss) {
        stopAmbientSound(boss);

        switch (boss) {
            case AshenLordEntity ignored -> {
                stop(boss, ModSounds.ASHEN_LORD_ATTACK.get());
                stop(boss, ModSounds.ASHEN_LORD_CHANNEL_01.get());
                stop(boss, ModSounds.ASHEN_LORD_CHANNEL_02.get());
            }
            case MabEntity ignored -> {
                stop(boss, ModSounds.MAB_INTIMIDATE.get());
                stop(boss, ModSounds.MAB_ATTACK.get());
                stop(boss, ModSounds.MAB_SUMMON.get());
            }
            case OberonEntity ignored -> {
                stop(boss, ModSounds.OBERON_REARING.get());
                stop(boss, ModSounds.OBERON_CHARGING.get());
                stop(boss, ModSounds.OBERON_KICKING.get());
            }
            case TitaniaEntity ignored -> {
                stop(boss, ModSounds.TITANIA_SUMMON.get());
                stop(boss, ModSounds.TITANIA_BLOSSOM.get());
                stop(boss, ModSounds.TITANIA_HEXEN.get());
                stop(boss, ModSounds.TITANIA_SPRING.get());
                stop(boss, ModSounds.TITANIA_SUMMER.get());
                stop(boss, ModSounds.TITANIA_AUTUMN.get());
                stop(boss, ModSounds.TITANIA_WINTER.get());
            }
            default -> {
            }
        }
    }

    private static void stop(BossBase boss, SoundEvent sound) {
        Minecraft.getInstance()
                .getSoundManager()
                .stop(sound.location(), boss.getSoundSource());
    }
}
