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

    public static final RegistryObject<SoundEvent> SHROOMLING_WAVE = registerSoundEvents("shroomling_wave");
    public static final RegistryObject<SoundEvent> SHROOMLING_AMBIANCE_01 = registerSoundEvents("shroomling_ambiance_01");
    public static final RegistryObject<SoundEvent> SHROOMLING_AMBIANCE_02 = registerSoundEvents("shroomling_ambiance_02");
    public static final RegistryObject<SoundEvent> SHROOMLING_HURT = registerSoundEvents("shroomling_hurt");
    public static final RegistryObject<SoundEvent> SHROOMLING_DEATH = registerSoundEvents("shroomling_death");
    public static final RegistryObject<SoundEvent> SHROOMLING_COOKIE = registerSoundEvents("shroomling_cookie");
    public static final RegistryObject<SoundEvent> SHROOMLING_NAME = registerSoundEvents("shroomling_name");
    public static final RegistryObject<SoundEvent> SHROOMLING_SUMMON = registerSoundEvents("shroomling_summon");
    public static final RegistryObject<SoundEvent> SHROOMLING_DISMISS = registerSoundEvents("shroomling_dismiss");
    public static final RegistryObject<SoundEvent> SHROOMLING_FOLLOW = registerSoundEvents("shroomling_follow");
    public static final RegistryObject<SoundEvent> SHROOMLING_STAY = registerSoundEvents("shroomling_stay");
    public static final RegistryObject<SoundEvent> SHROOMLING_ABILITY_ON = registerSoundEvents("shroomling_ability_on");
    public static final RegistryObject<SoundEvent> SHROOMLING_ABILITY_OFF = registerSoundEvents("shroomling_ability_off");
    public static final RegistryObject<SoundEvent> SHROOMLING_TRADE = registerSoundEvents("shroomling_trade");
    public static final RegistryObject<SoundEvent> SHROOMLING_SNEEZE = registerSoundEvents("shroomling_sneeze");

    public static final RegistryObject<SoundEvent> MANDRAGORA_SING = registerSoundEvents("mandragora_sing");
    public static final RegistryObject<SoundEvent> MANDRAGORA_AMBIANCE_01 = registerSoundEvents("mandragora_ambiance_01");
    public static final RegistryObject<SoundEvent> MANDRAGORA_AMBIANCE_02 = registerSoundEvents("mandragora_ambiance_02");
    public static final RegistryObject<SoundEvent> MANDRAGORA_HURT = registerSoundEvents("mandragora_hurt");
    public static final RegistryObject<SoundEvent> MANDRAGORA_DEATH = registerSoundEvents("mandragora_death");
    public static final RegistryObject<SoundEvent> MANDRAGORA_COOKIE = registerSoundEvents("mandragora_cookie");
    public static final RegistryObject<SoundEvent> MANDRAGORA_NAME = registerSoundEvents("mandragora_name");
    public static final RegistryObject<SoundEvent> MANDRAGORA_SUMMON = registerSoundEvents("mandragora_summon");
    public static final RegistryObject<SoundEvent> MANDRAGORA_DISMISS = registerSoundEvents("mandragora_dismiss");
    public static final RegistryObject<SoundEvent> MANDRAGORA_FOLLOW = registerSoundEvents("mandragora_follow");
    public static final RegistryObject<SoundEvent> MANDRAGORA_STAY = registerSoundEvents("mandragora_stay");
    public static final RegistryObject<SoundEvent> MANDRAGORA_ABILITY_ON = registerSoundEvents("mandragora_ability_on");
    public static final RegistryObject<SoundEvent> MANDRAGORA_ABILITY_OFF = registerSoundEvents("mandragora_ability_off");
    public static final RegistryObject<SoundEvent> MANDRAGORA_TRADE = registerSoundEvents("mandragora_trade");

    public static final RegistryObject<SoundEvent> BELLSNICKEL_AMBIANCE = registerSoundEvents("bellsnickel_ambiance");
    public static final RegistryObject<SoundEvent> BELLSNICKEL_HURT = registerSoundEvents("bellsnickel_hurt");
    public static final RegistryObject<SoundEvent> BELLSNICKEL_DEATH = registerSoundEvents("bellsnickel_death");
    public static final RegistryObject<SoundEvent> BELLSNICKEL_COOKIE = registerSoundEvents("bellsnickel_cookie");
    public static final RegistryObject<SoundEvent> BELLSNICKEL_NAME = registerSoundEvents("bellsnickel_name");
    public static final RegistryObject<SoundEvent> BELLSNICKEL_SUMMON = registerSoundEvents("bellsnickel_summon");
    public static final RegistryObject<SoundEvent> BELLSNICKEL_DISMISS = registerSoundEvents("bellsnickel_dismiss");
    public static final RegistryObject<SoundEvent> BELLSNICKEL_FOLLOW = registerSoundEvents("bellsnickel_follow");
    public static final RegistryObject<SoundEvent> BELLSNICKEL_STAY_01 = registerSoundEvents("bellsnickel_stay_01");
    public static final RegistryObject<SoundEvent> BELLSNICKEL_STAY_02 = registerSoundEvents("bellsnickel_stay_02");
    public static final RegistryObject<SoundEvent> BELLSNICKEL_CARRY_STUFF = registerSoundEvents("bellsnickel_carry_stuff");
    public static final RegistryObject<SoundEvent> BELLSNICKEL_TRADE = registerSoundEvents("bellsnickel_trade");

    public static final RegistryObject<SoundEvent> BEE_KNIGHT_AMBIANCE = registerSoundEvents("bee_knight_ambiance");
    public static final RegistryObject<SoundEvent> BEE_KNIGHT_HURT = registerSoundEvents("bee_knight_hurt");
    public static final RegistryObject<SoundEvent> BEE_KNIGHT_DEATH = registerSoundEvents("bee_knight_death");
    public static final RegistryObject<SoundEvent> BEE_KNIGHT_COOKIE = registerSoundEvents("bee_knight_cookie");
    public static final RegistryObject<SoundEvent> BEE_KNIGHT_NAME = registerSoundEvents("bee_knight_name");
    public static final RegistryObject<SoundEvent> BEE_KNIGHT_SUMMON = registerSoundEvents("bee_knight_summon");
    public static final RegistryObject<SoundEvent> BEE_KNIGHT_DISMISS = registerSoundEvents("bee_knight_dismiss");
    public static final RegistryObject<SoundEvent> BEE_KNIGHT_FOLLOW = registerSoundEvents("bee_knight_follow");
    public static final RegistryObject<SoundEvent> BEE_KNIGHT_STAY = registerSoundEvents("bee_knight_stay");
    public static final RegistryObject<SoundEvent> BEE_KNIGHT_GUARD = registerSoundEvents("bee_knight_guard");
    public static final RegistryObject<SoundEvent> BEE_KNIGHT_PROTECT = registerSoundEvents("bee_knight_protect");
    public static final RegistryObject<SoundEvent> BEE_KNIGHT_ATTACK_01 = registerSoundEvents("bee_knight_attack_01");
    public static final RegistryObject<SoundEvent> BEE_KNIGHT_ATTACK_02 = registerSoundEvents("bee_knight_attack_02");
    public static final RegistryObject<SoundEvent> BEE_KNIGHT_ATTACK_03 = registerSoundEvents("bee_knight_attack_03");
    public static final RegistryObject<SoundEvent> BEE_KNIGHT_TRADE = registerSoundEvents("bee_knight_trade");

    public static final RegistryObject<SoundEvent> TREE_ENT_AMBIANCE = registerSoundEvents("tree_ent_ambiance");
    public static final RegistryObject<SoundEvent> TREE_ENT_HURT = registerSoundEvents("tree_ent_hurt");
    public static final RegistryObject<SoundEvent> TREE_ENT_DEATH = registerSoundEvents("tree_ent_death");
    public static final RegistryObject<SoundEvent> TREE_ENT_NAME = registerSoundEvents("tree_ent_name");
    public static final RegistryObject<SoundEvent> TREE_ENT_SUMMON = registerSoundEvents("tree_ent_summon");
    public static final RegistryObject<SoundEvent> TREE_ENT_DISMISS = registerSoundEvents("tree_ent_dismiss");
    public static final RegistryObject<SoundEvent> TREE_ENT_MOUNT = registerSoundEvents("tree_ent_mount");
    public static final RegistryObject<SoundEvent> TREE_ENT_STAY = registerSoundEvents("tree_ent_stay");
    public static final RegistryObject<SoundEvent> TREE_ENT_WALKING = registerSoundEvents("tree_ent_walking");
    public static final RegistryObject<SoundEvent> TREE_ENT_BLESSING = registerSoundEvents("tree_ent_blessing");
    public static final RegistryObject<SoundEvent> TREE_ENT_ATTACKING = registerSoundEvents("tree_ent_attacking");
    public static final RegistryObject<SoundEvent> TREE_ENT_STORY_01 = registerSoundEvents("tree_ent_story_01");
    public static final RegistryObject<SoundEvent> TREE_ENT_STORY_02 = registerSoundEvents("tree_ent_story_02");
    public static final RegistryObject<SoundEvent> TREE_ENT_STORY_03 = registerSoundEvents("tree_ent_story_03");
    public static final RegistryObject<SoundEvent> TREE_ENT_STORY_04 = registerSoundEvents("tree_ent_story_04");
    public static final RegistryObject<SoundEvent> TREE_ENT_STORY_05 = registerSoundEvents("tree_ent_story_05");
    public static final RegistryObject<SoundEvent> TREE_ENT_STORY_06 = registerSoundEvents("tree_ent_story_06");

    public static final RegistryObject<SoundEvent> TITANIA_HURT = registerSoundEvents("titania_hurt");
    public static final RegistryObject<SoundEvent> TITANIA_DEATH = registerSoundEvents("titania_death");
    public static final RegistryObject<SoundEvent> TITANIA_SUMMON = registerSoundEvents("titania_summon");
    public static final RegistryObject<SoundEvent> TITANIA_SUMMER = registerSoundEvents("titania_summer");
    public static final RegistryObject<SoundEvent> TITANIA_SPRING = registerSoundEvents("titania_spring");
    public static final RegistryObject<SoundEvent> TITANIA_WINTER = registerSoundEvents("titania_winter");
    public static final RegistryObject<SoundEvent> TITANIA_AUTUMN = registerSoundEvents("titania_autumn");
    public static final RegistryObject<SoundEvent> TITANIA_HEXEN = registerSoundEvents("titania_hexen");
    public static final RegistryObject<SoundEvent> TITANIA_BLOSSOM = registerSoundEvents("titania_blossom");

    public static final RegistryObject<SoundEvent> MAB_HURT = registerSoundEvents("mab_hurt");
    public static final RegistryObject<SoundEvent> MAB_DEATH = registerSoundEvents("mab_death");
    public static final RegistryObject<SoundEvent> MAB_AMBIANCE = registerSoundEvents("mab_ambiance");
    public static final RegistryObject<SoundEvent> MAB_SUMMON = registerSoundEvents("mab_summon");
    public static final RegistryObject<SoundEvent> MAB_ATTACK = registerSoundEvents("mab_attack");
    public static final RegistryObject<SoundEvent> MAB_INTIMIDATE = registerSoundEvents("mab_intimidate");

    public static final RegistryObject<SoundEvent> OBERON_HURT = registerSoundEvents("oberon_hurt");
    public static final RegistryObject<SoundEvent> OBERON_DEATH = registerSoundEvents("oberon_death");
    public static final RegistryObject<SoundEvent> OBERON_AMBIANCE = registerSoundEvents("oberon_ambiance");
    public static final RegistryObject<SoundEvent> OBERON_SUMMON = registerSoundEvents("oberon_summon");
    public static final RegistryObject<SoundEvent> OBERON_CHARGING = registerSoundEvents("oberon_charging");
    public static final RegistryObject<SoundEvent> OBERON_KICKING = registerSoundEvents("oberon_kicking");
    public static final RegistryObject<SoundEvent> OBERON_REARING = registerSoundEvents("oberon_rearing");

    public static final RegistryObject<SoundEvent> ASHEN_LORD_HURT = registerSoundEvents("ashen_lord_hurt");
    public static final RegistryObject<SoundEvent> ASHEN_LORD_DEATH = registerSoundEvents("ashen_lord_death");
    public static final RegistryObject<SoundEvent> ASHEN_LORD_AMBIANCE = registerSoundEvents("ashen_lord_ambiance");
    public static final RegistryObject<SoundEvent> ASHEN_LORD_CHANNEL_01 = registerSoundEvents("ashen_lord_channel_01");
    public static final RegistryObject<SoundEvent> ASHEN_LORD_CHANNEL_02 = registerSoundEvents("ashen_lord_channel_02");
    public static final RegistryObject<SoundEvent> ASHEN_LORD_ATTACK = registerSoundEvents("ashen_lord_attack");

    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name,
                () -> new SoundEvent(new ResourceLocation(Feywild.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
