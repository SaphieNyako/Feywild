package com.feywild.feywild.sound;

import com.feywild.feywild.FeywildMod;
import org.moddingx.libx.annotation.registration.RegisterClass;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

@RegisterClass
public class ModSoundEvents {

    public static final SoundEvent musicMenu = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "music_menu"));
    public static final SoundEvent musicCreative = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "music_creative"));
    public static final SoundEvent mandragoraAmbience = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "mandragora_ambience"));
    public static final SoundEvent mandragoraDeath = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "mandragora_death"));
    public static final SoundEvent mandragoraHurt = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "mandragora_hurt"));
    public static final SoundEvent mandragoraSinging = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "mandragora_singing"));
    public static final SoundEvent mandrakeScream = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "mandrake_scream"));
    public static final SoundEvent dwarfAttack = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "dwarf_attack"));
    public static final SoundEvent dwarfRubble = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "dwarf_rubble"));
    public static final SoundEvent pixieAmbient = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "pixie_ambient"));
    public static final SoundEvent pixieHurt = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "pixie_hurt"));
    public static final SoundEvent pixieDeath = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "pixie_death"));
    public static final SoundEvent pixieSpellcasting = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "pixie_spellcasting"));
    public static final SoundEvent summoningSpringPixie = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "summoning_spring_pixie"));
    public static final SoundEvent summoningSummerPixie = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "summoning_summer_pixie"));
    public static final SoundEvent summoningAutumnPixie = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "summoning_autumn_pixie"));
    public static final SoundEvent summoningWinterPixie = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "summoning_winter_pixie"));
    public static final SoundEvent feywildSoundtrack = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "feywild_soundtrack"));
    public static final SoundEvent springSoundtrack = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "spring_soundtrack"));
    public static final SoundEvent summerSoundtrack = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "summer_soundtrack"));
    public static final SoundEvent autumnSoundtrack = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "autumn_soundtrack"));
    public static final SoundEvent winterSoundtrack = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "winter_soundtrack"));
    public static final SoundEvent summoningSpringPixieShort = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "summoning_spring_pixie_short"));
    public static final SoundEvent summoningSummerPixieShort = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "summoning_summer_pixie_short"));
    public static final SoundEvent summoningAutumnPixieShort = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "summoning_autumn_pixie_short"));
    public static final SoundEvent summoningWinterPixieShort = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "summoning_winter_pixie_short"));
    public static final SoundEvent shroomlingAmbience = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "shroomling_ambience"));
    public static final SoundEvent shroomlingDeath = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "shroomling_death"));
    public static final SoundEvent shroomlingHurt = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "shroomling_hurt"));
    public static final SoundEvent shroomlingSneeze = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "shroomling_sneeze"));
    public static final SoundEvent shroomlingWave = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "shroomling_wave"));

}
