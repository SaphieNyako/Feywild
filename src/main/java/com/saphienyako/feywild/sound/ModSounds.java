package com.saphienyako.feywild.sound;

import com.saphienyako.feywild.Feywild;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Feywild.MOD_ID);

    public static final Supplier<SoundEvent> MANDRAKE_SCREAM = registerSoundEvent("mandrake_scream");
    public static final Supplier<SoundEvent> PIXIE_SPELL_CASTING = registerSoundEvent("pixie_spell_casting");
    public static final Supplier<SoundEvent> PIXIE_SPELL_CASTING_SHORT = registerSoundEvent("pixie_spell_casting_short");
    //TODO remove sounds
    public static final Supplier<SoundEvent> FEYWILD_MUSIC = registerSoundEvent("feywild_music");
    public static final ResourceKey<JukeboxSong> FEYWILD_MUSIC_KEY = createSong("feywild_music");

    public static final Supplier<SoundEvent> SPRING_PIXIE_GIGGLE = registerSoundEvent("spring_pixie_giggle");
    public static final Supplier<SoundEvent> SPRING_PIXIE_HURT = registerSoundEvent("spring_pixie_hurt");
    public static final Supplier<SoundEvent> SPRING_PIXIE_DEATH = registerSoundEvent("spring_pixie_death");
    public static final Supplier<SoundEvent> SPRING_PIXIE_COOKIE = registerSoundEvent("spring_pixie_cookie");
    public static final Supplier<SoundEvent> SPRING_PIXIE_NAME = registerSoundEvent("spring_pixie_name");
    public static final Supplier<SoundEvent> SPRING_PIXIE_SUMMON = registerSoundEvent("spring_pixie_summon");
    public static final Supplier<SoundEvent> SPRING_PIXIE_DISMISS = registerSoundEvent("spring_pixie_dismiss");
    public static final Supplier<SoundEvent> SPRING_PIXIE_FOLLOW = registerSoundEvent("spring_pixie_follow");
    public static final Supplier<SoundEvent> SPRING_PIXIE_STAY = registerSoundEvent("spring_pixie_stay");
    public static final Supplier<SoundEvent> SPRING_PIXIE_ABILITY_ON = registerSoundEvent("spring_pixie_ability_on");
    public static final Supplier<SoundEvent> SPRING_PIXIE_ABILITY_OFF = registerSoundEvent("spring_pixie_ability_off");

    public static final Supplier<SoundEvent> SUMMER_PIXIE_GIGGLE = registerSoundEvent("summer_pixie_giggle");
    public static final Supplier<SoundEvent> SUMMER_PIXIE_HURT = registerSoundEvent("summer_pixie_hurt");
    public static final Supplier<SoundEvent> SUMMER_PIXIE_DEATH = registerSoundEvent("summer_pixie_death");
    public static final Supplier<SoundEvent> SUMMER_PIXIE_COOKIE = registerSoundEvent("summer_pixie_cookie");
    public static final Supplier<SoundEvent> SUMMER_PIXIE_NAME = registerSoundEvent("summer_pixie_name");
    public static final Supplier<SoundEvent> SUMMER_PIXIE_SUMMON = registerSoundEvent("summer_pixie_summon");
    public static final Supplier<SoundEvent> SUMMER_PIXIE_DISMISS = registerSoundEvent("summer_pixie_dismiss");
    public static final Supplier<SoundEvent> SUMMER_PIXIE_FOLLOW = registerSoundEvent("summer_pixie_follow");
    public static final Supplier<SoundEvent> SUMMER_PIXIE_STAY = registerSoundEvent("summer_pixie_stay");
    public static final Supplier<SoundEvent> SUMMER_PIXIE_ABILITY_ON = registerSoundEvent("summer_pixie_ability_on");
    public static final Supplier<SoundEvent> SUMMER_PIXIE_ABILITY_OFF = registerSoundEvent("summer_pixie_ability_off");

    public static final Supplier<SoundEvent> AUTUMN_PIXIE_GIGGLE = registerSoundEvent("autumn_pixie_giggle");
    public static final Supplier<SoundEvent> AUTUMN_PIXIE_HURT = registerSoundEvent("autumn_pixie_hurt");
    public static final Supplier<SoundEvent> AUTUMN_PIXIE_DEATH = registerSoundEvent("autumn_pixie_death");
    public static final Supplier<SoundEvent> AUTUMN_PIXIE_COOKIE = registerSoundEvent("autumn_pixie_cookie");
    public static final Supplier<SoundEvent> AUTUMN_PIXIE_NAME = registerSoundEvent("autumn_pixie_name");
    public static final Supplier<SoundEvent> AUTUMN_PIXIE_SUMMON = registerSoundEvent("autumn_pixie_summon");
    public static final Supplier<SoundEvent> AUTUMN_PIXIE_DISMISS = registerSoundEvent("autumn_pixie_dismiss");
    public static final Supplier<SoundEvent> AUTUMN_PIXIE_FOLLOW = registerSoundEvent("autumn_pixie_follow");
    public static final Supplier<SoundEvent> AUTUMN_PIXIE_STAY = registerSoundEvent("autumn_pixie_stay");
    public static final Supplier<SoundEvent> AUTUMN_PIXIE_ABILITY_ON = registerSoundEvent("autumn_pixie_ability_on");
    public static final Supplier<SoundEvent> AUTUMN_PIXIE_ABILITY_OFF = registerSoundEvent("autumn_pixie_ability_off");

    public static final Supplier<SoundEvent> WINTER_PIXIE_GIGGLE = registerSoundEvent("winter_pixie_giggle");
    public static final Supplier<SoundEvent> WINTER_PIXIE_HURT = registerSoundEvent("winter_pixie_hurt");
    public static final Supplier<SoundEvent> WINTER_PIXIE_DEATH = registerSoundEvent("winter_pixie_death");
    public static final Supplier<SoundEvent> WINTER_PIXIE_COOKIE = registerSoundEvent("winter_pixie_cookie");
    public static final Supplier<SoundEvent> WINTER_PIXIE_NAME = registerSoundEvent("winter_pixie_name");
    public static final Supplier<SoundEvent> WINTER_PIXIE_SUMMON = registerSoundEvent("winter_pixie_summon");
    public static final Supplier<SoundEvent> WINTER_PIXIE_DISMISS = registerSoundEvent("winter_pixie_dismiss");
    public static final Supplier<SoundEvent> WINTER_PIXIE_FOLLOW = registerSoundEvent("winter_pixie_follow");
    public static final Supplier<SoundEvent> WINTER_PIXIE_STAY = registerSoundEvent("winter_pixie_stay");
    public static final Supplier<SoundEvent> WINTER_PIXIE_ABILITY_ON = registerSoundEvent("winter_pixie_ability_on");
    public static final Supplier<SoundEvent> WINTER_PIXIE_ABILITY_OFF = registerSoundEvent("winter_pixie_ability_off");

    public static final Supplier<SoundEvent> SHROOMLING_WAVE = registerSoundEvent("shroomling_wave");
    public static final Supplier<SoundEvent> SHROOMLING_AMBIANCE_01 = registerSoundEvent("shroomling_ambiance_01");
    public static final Supplier<SoundEvent> SHROOMLING_AMBIANCE_02 = registerSoundEvent("shroomling_ambiance_02");
    public static final Supplier<SoundEvent> SHROOMLING_HURT = registerSoundEvent("shroomling_hurt");
    public static final Supplier<SoundEvent> SHROOMLING_DEATH = registerSoundEvent("shroomling_death");
    public static final Supplier<SoundEvent> SHROOMLING_COOKIE = registerSoundEvent("shroomling_cookie");
    public static final Supplier<SoundEvent> SHROOMLING_NAME = registerSoundEvent("shroomling_name");
    public static final Supplier<SoundEvent> SHROOMLING_SUMMON = registerSoundEvent("shroomling_summon");
    public static final Supplier<SoundEvent> SHROOMLING_DISMISS = registerSoundEvent("shroomling_dismiss");
    public static final Supplier<SoundEvent> SHROOMLING_FOLLOW = registerSoundEvent("shroomling_follow");
    public static final Supplier<SoundEvent> SHROOMLING_STAY = registerSoundEvent("shroomling_stay");
    public static final Supplier<SoundEvent> SHROOMLING_ABILITY_ON = registerSoundEvent("shroomling_ability_on");
    public static final Supplier<SoundEvent> SHROOMLING_ABILITY_OFF = registerSoundEvent("shroomling_ability_off");
    public static final Supplier<SoundEvent> SHROOMLING_TRADE = registerSoundEvent("shroomling_trade");
    public static final Supplier<SoundEvent> SHROOMLING_SNEEZE = registerSoundEvent("shroomling_sneeze");

    private static ResourceKey<JukeboxSong> createSong(String name) {
                return ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, name));
    }

    private static Supplier<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }

}
