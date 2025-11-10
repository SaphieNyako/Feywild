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
    public static final RegistryObject<SoundEvent> FAIRY_CRAFT_MUSIC_DISC = registerSoundEvents("fairy_craft_music_disc");
    public static final RegistryObject<SoundEvent> PIXIE_SPELL_CASTING = registerSoundEvents("pixie_spell_casting");
    public static final RegistryObject<SoundEvent> PIXIE_SPELL_CASTING_SHORT = registerSoundEvents("pixie_spell_casting_short");
    public static final RegistryObject<SoundEvent> PIXIE_AMBIENT = registerSoundEvents("pixie_ambient");
    public static final RegistryObject<SoundEvent> PIXIE_HURT = registerSoundEvents("pixie_hurt");
    public static final RegistryObject<SoundEvent> PIXIE_DEATH = registerSoundEvents("pixie_death");



    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Feywild.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
