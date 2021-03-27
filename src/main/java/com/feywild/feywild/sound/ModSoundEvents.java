package com.feywild.feywild.sound;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.util.Registration;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;

public class ModSoundEvents {

    public static final RegistryObject<SoundEvent>  PIXIE_AMBIENT =
            Registration.SOUND_EVENTS.register("pixie_ambient",
                    () -> new SoundEvent(new ResourceLocation(FeywildMod.MOD_ID, "pixie_ambient")));

    public static final RegistryObject<SoundEvent>  PIXIE_HURT =
            Registration.SOUND_EVENTS.register("pixie_hurt",
                    () -> new SoundEvent(new ResourceLocation(FeywildMod.MOD_ID, "pixie_hurt")));

    public static final RegistryObject<SoundEvent>  PIXIE_DEATH =
            Registration.SOUND_EVENTS.register("pixie_death",
                    () -> new SoundEvent(new ResourceLocation(FeywildMod.MOD_ID, "pixie_death")));


    public static void register() {}
}
