package com.feywild.feywild.data;

import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.moddingx.libx.annotation.data.Datagen;
import org.moddingx.libx.datagen.provider.SoundDefinitionProviderBase;
import org.moddingx.libx.mod.ModX;

@Datagen
public class SoundProvider extends SoundDefinitionProviderBase {

    public SoundProvider(ModX mod, DataGenerator generator, ExistingFileHelper helper) {
        super(mod, generator, helper);
    }

    @Override
    @SuppressWarnings("Convert2MethodRef")
    protected void setup() {
        this.sound(ModSoundEvents.musicMenu)
                .with("menu/menu1", sound -> sound.stream().weight(3))
                .with("menu/menu2", sound -> sound.stream().weight(1));

        this.sound(ModSoundEvents.musicCreative)
                .event(ModSoundEvents.musicMenu)
                .with("menu/menu1", sound -> sound.stream().weight(1));

        this.sound(ModSoundEvents.feywildSoundtrack).with("feywild_soundtrack", sound -> sound.stream());
        this.sound(ModSoundEvents.springSoundtrack).with("spring_soundtrack", sound -> sound.stream());
        this.sound(ModSoundEvents.summerSoundtrack).with("summer_soundtrack", sound -> sound.stream());
        this.sound(ModSoundEvents.autumnSoundtrack).with("autumn_soundtrack", sound -> sound.stream());
        this.sound(ModSoundEvents.winterSoundtrack).with("winter_soundtrack", sound -> sound.stream());

        this.sound(ModSoundEvents.mandragoraAmbience).withRange("mandragora_ambience", 2);
        this.sound(ModSoundEvents.shroomlingAmbience).withRange("shroomling_ambience", 2);
        this.sound(ModSoundEvents.shroomlingWave).withRange("shroomling_wave", 2);
        this.sound(ModSoundEvents.titaniaHurt).withRange("titania_hurt", 8);
        this.sound(ModSoundEvents.titaniaDeath).withRange("titania_death", 4);
        this.sound(ModSoundEvents.titaniaAmbience).withRange("titania_ambience", 10);
        this.sound(ModSoundEvents.titaniaFireAttack).withRange("titania_fire_attack", 6);
        this.sound(ModSoundEvents.titaniaSummonBee).withRange("titania_summon_bee", 5);
        this.sound(ModSoundEvents.mabAmbience).withRange("mab_ambience", 5);
        this.sound(ModSoundEvents.mabAttack).withRange("mab_attack", 2);
        this.sound(ModSoundEvents.mabDeath).withRange("mab_death", 4);
        this.sound(ModSoundEvents.mabHurt).withRange("mab_hurt", 4);
        this.sound(ModSoundEvents.mabIntimidate).withRange("mab_intimidate", 4);
        this.sound(ModSoundEvents.mabSummon).withRange("mab_summon", 3);
    }
}
