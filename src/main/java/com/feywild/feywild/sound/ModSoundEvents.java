package com.feywild.feywild.sound;

import com.feywild.feywild.FeywildMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import org.moddingx.libx.annotation.registration.RegisterClass;

@RegisterClass(registry = "SOUND_EVENT_REGISTRY")
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
    public static final SoundEvent spellcastingShort = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "spellcasting_short"));
    public static final SoundEvent summoningSpringPixie = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "summoning_spring_pixie"));
    public static final SoundEvent summoningSummerPixie = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "summoning_summer_pixie"));
    public static final SoundEvent summoningAutumnPixie = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "summoning_autumn_pixie"));
    public static final SoundEvent summoningWinterPixie = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "summoning_winter_pixie"));
    public static final SoundEvent summoningSpringPixieShort = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "summoning_spring_pixie_short"));
    public static final SoundEvent summoningSummerPixieShort = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "summoning_summer_pixie_short"));
    public static final SoundEvent summoningAutumnPixieShort = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "summoning_autumn_pixie_short"));
    public static final SoundEvent summoningWinterPixieShort = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "summoning_winter_pixie_short"));
    public static final SoundEvent feywildSoundtrack = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "feywild_soundtrack"));
    public static final SoundEvent springSoundtrack = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "spring_soundtrack"));
    public static final SoundEvent summerSoundtrack = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "summer_soundtrack"));
    public static final SoundEvent autumnSoundtrack = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "autumn_soundtrack"));
    public static final SoundEvent winterSoundtrack = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "winter_soundtrack"));
    public static final SoundEvent shroomlingAmbience = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "shroomling_ambience"));
    public static final SoundEvent shroomlingDeath = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "shroomling_death"));
    public static final SoundEvent shroomlingHurt = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "shroomling_hurt"));
    public static final SoundEvent shroomlingSneeze = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "shroomling_sneeze"));
    public static final SoundEvent shroomlingWave = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "shroomling_wave"));
    public static final SoundEvent iceCracking = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "ice_cracking"));
    public static final SoundEvent beatingWings = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "beating_wings"));
    public static final SoundEvent swordSwing = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "sword_swing"));
    public static final SoundEvent titaniaHurt = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "titania_hurt"));
    public static final SoundEvent titaniaDeath = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "titania_death"));
    public static final SoundEvent titaniaAmbience = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "titania_ambience"));
    public static final SoundEvent titaniaFireAttack = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "titania_fire_attack"));
    public static final SoundEvent titaniaSummonBee = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "titania_summon_bee"));
    public static final SoundEvent mabHurt = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "mab_hurt"));
    public static final SoundEvent mabDeath = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "mab_death"));
    public static final SoundEvent mabAmbience = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "mab_ambience"));
    public static final SoundEvent mabIntimidate = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "mab_intimidate"));
    public static final SoundEvent mabSummon = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "mab_summon"));
    public static final SoundEvent mabAttack = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "mab_attack"));
    public static final SoundEvent treeEntWalking = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "tree_ent_walking"));
    public static final SoundEvent treeEntAttacking = new SoundEvent(new ResourceLocation(FeywildMod.getInstance().modid, "tree_ent_attacking"));
}
