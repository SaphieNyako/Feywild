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
    public static final Supplier<SoundEvent> PIXIE_AMBIENT = registerSoundEvent("pixie_ambient");
    public static final Supplier<SoundEvent> PIXIE_HURT = registerSoundEvent("pixie_hurt");
    public static final Supplier<SoundEvent> PIXIE_DEATH = registerSoundEvent("pixie_death");
    public static final Supplier<SoundEvent> FEYWILD_MUSIC = registerSoundEvent("feywild_music");
    public static final ResourceKey<JukeboxSong> FEYWILD_MUSIC_KEY = createSong("feywild_music");

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
