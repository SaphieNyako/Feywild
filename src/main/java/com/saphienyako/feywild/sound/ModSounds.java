package com.saphienyako.feywild.sound;

import com.saphienyako.feywild.Feywild;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Feywild.MOD_ID);

    public static final RegistryObject<SoundEvent> MANDRAKE_SCREAM = registerSoundEvents("mandrake_scream");
    public static final RegistryObject<SoundEvent> FEYWILD_MUSIC_DISC = registerSoundEvents("feywild_music_disc");
    public static final RegistryObject<SoundEvent> PIXIE_SPELL_CASTING = registerSoundEvents("pixie_spell_casting");
    public static final RegistryObject<SoundEvent> PIXIE_SPELL_CASTING_SHORT = registerSoundEvents("pixie_spell_casting_short");

    public static final RegistryObject<SoundEvent> SPRING_PIXIE_GIGGLE = registerSoundEvents("spring_pixie_giggle");
    public static final RegistryObject<SoundEvent> SPRING_PIXIE_HURT = registerSoundEvents("spring_pixie_hurt");
    public static final RegistryObject<SoundEvent> SPRING_PIXIE_DEATH = registerSoundEvents("spring_pixie_death");
    public static final RegistryObject<SoundEvent> SPRING_PIXIE_COOKIE = registerSoundEvents("spring_pixie_cookie");
    public static final RegistryObject<SoundEvent> SPRING_PIXIE_NAME = registerSoundEvents("spring_pixie_name");
    public static final RegistryObject<SoundEvent> SPRING_PIXIE_SUMMON = registerSoundEvents("spring_pixie_summon");
    public static final RegistryObject<SoundEvent> SPRING_PIXIE_DISMISS = registerSoundEvents("spring_pixie_dismiss");
    public static final RegistryObject<SoundEvent> SPRING_PIXIE_FOLLOW = registerSoundEvents("spring_pixie_follow");
    public static final RegistryObject<SoundEvent> SPRING_PIXIE_STAY = registerSoundEvents("spring_pixie_stay");
    public static final RegistryObject<SoundEvent> SPRING_PIXIE_ABILITY_ON = registerSoundEvents("spring_pixie_ability_on");
    public static final RegistryObject<SoundEvent> SPRING_PIXIE_ABILITY_OFF = registerSoundEvents("spring_pixie_ability_off");

    public static final RegistryObject<SoundEvent> SUMMER_PIXIE_GIGGLE = registerSoundEvents("summer_pixie_giggle");
    public static final RegistryObject<SoundEvent> SUMMER_PIXIE_HURT = registerSoundEvents("summer_pixie_hurt");
    public static final RegistryObject<SoundEvent> SUMMER_PIXIE_DEATH = registerSoundEvents("summer_pixie_death");
    public static final RegistryObject<SoundEvent> SUMMER_PIXIE_COOKIE = registerSoundEvents("summer_pixie_cookie");
    public static final RegistryObject<SoundEvent> SUMMER_PIXIE_NAME = registerSoundEvents("summer_pixie_name");
    public static final RegistryObject<SoundEvent> SUMMER_PIXIE_SUMMON = registerSoundEvents("summer_pixie_summon");
    public static final RegistryObject<SoundEvent> SUMMER_PIXIE_DISMISS = registerSoundEvents("summer_pixie_dismiss");
    public static final RegistryObject<SoundEvent> SUMMER_PIXIE_FOLLOW = registerSoundEvents("summer_pixie_follow");
    public static final RegistryObject<SoundEvent> SUMMER_PIXIE_STAY = registerSoundEvents("summer_pixie_stay");
    public static final RegistryObject<SoundEvent> SUMMER_PIXIE_ABILITY_ON = registerSoundEvents("summer_pixie_ability_on");
    public static final RegistryObject<SoundEvent> SUMMER_PIXIE_ABILITY_OFF = registerSoundEvents("summer_pixie_ability_off");

    public static final RegistryObject<SoundEvent> AUTUMN_PIXIE_GIGGLE = registerSoundEvents("autumn_pixie_giggle");
    public static final RegistryObject<SoundEvent> AUTUMN_PIXIE_HURT = registerSoundEvents("autumn_pixie_hurt");
    public static final RegistryObject<SoundEvent> AUTUMN_PIXIE_DEATH = registerSoundEvents("autumn_pixie_death");
    public static final RegistryObject<SoundEvent> AUTUMN_PIXIE_COOKIE = registerSoundEvents("autumn_pixie_cookie");
    public static final RegistryObject<SoundEvent> AUTUMN_PIXIE_NAME = registerSoundEvents("autumn_pixie_name");
    public static final RegistryObject<SoundEvent> AUTUMN_PIXIE_SUMMON = registerSoundEvents("autumn_pixie_summon");
    public static final RegistryObject<SoundEvent> AUTUMN_PIXIE_DISMISS = registerSoundEvents("autumn_pixie_dismiss");
    public static final RegistryObject<SoundEvent> AUTUMN_PIXIE_FOLLOW = registerSoundEvents("autumn_pixie_follow");
    public static final RegistryObject<SoundEvent> AUTUMN_PIXIE_STAY = registerSoundEvents("autumn_pixie_stay");
    public static final RegistryObject<SoundEvent> AUTUMN_PIXIE_ABILITY_ON = registerSoundEvents("autumn_pixie_ability_on");
    public static final RegistryObject<SoundEvent> AUTUMN_PIXIE_ABILITY_OFF = registerSoundEvents("autumn_pixie_ability_off");

    public static final RegistryObject<SoundEvent> WINTER_PIXIE_GIGGLE = registerSoundEvents("winter_pixie_giggle");
    public static final RegistryObject<SoundEvent> WINTER_PIXIE_HURT = registerSoundEvents("winter_pixie_hurt");
    public static final RegistryObject<SoundEvent> WINTER_PIXIE_DEATH = registerSoundEvents("winter_pixie_death");
    public static final RegistryObject<SoundEvent> WINTER_PIXIE_COOKIE = registerSoundEvents("winter_pixie_cookie");
    public static final RegistryObject<SoundEvent> WINTER_PIXIE_NAME = registerSoundEvents("winter_pixie_name");
    public static final RegistryObject<SoundEvent> WINTER_PIXIE_SUMMON = registerSoundEvents("winter_pixie_summon");
    public static final RegistryObject<SoundEvent> WINTER_PIXIE_DISMISS = registerSoundEvents("winter_pixie_dismiss");
    public static final RegistryObject<SoundEvent> WINTER_PIXIE_FOLLOW = registerSoundEvents("winter_pixie_follow");
    public static final RegistryObject<SoundEvent> WINTER_PIXIE_STAY = registerSoundEvents("winter_pixie_stay");
    public static final RegistryObject<SoundEvent> WINTER_PIXIE_ABILITY_ON = registerSoundEvents("winter_pixie_ability_on");
    public static final RegistryObject<SoundEvent> WINTER_PIXIE_ABILITY_OFF = registerSoundEvents("winter_pixie_ability_off");




    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Feywild.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
