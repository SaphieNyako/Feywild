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

    public static final RegistryObject<SoundEvent>  FEYWILD_SOUNDTRACK =
            Registration.SOUND_EVENTS.register("feywild_soundtrack",
                    () -> new SoundEvent(new ResourceLocation(FeywildMod.MOD_ID, "feywild_soundtrack")));

    public static final RegistryObject<SoundEvent>  SPRING_SOUNDTRACK =
            Registration.SOUND_EVENTS.register("spring_soundtrack",
                    () -> new SoundEvent(new ResourceLocation(FeywildMod.MOD_ID, "spring_soundtrack")));

    public static final RegistryObject<SoundEvent>  AUTUMN_SOUNDTRACK =
            Registration.SOUND_EVENTS.register("autumn_soundtrack",
                    () -> new SoundEvent(new ResourceLocation(FeywildMod.MOD_ID, "autumn_soundtrack")));

    public static final RegistryObject<SoundEvent>  SUMMER_SOUNDTRACK =
            Registration.SOUND_EVENTS.register("summer_soundtrack",
                    () -> new SoundEvent(new ResourceLocation(FeywildMod.MOD_ID, "summer_soundtrack")));

    public static final RegistryObject<SoundEvent>  WINTER_SOUNDTRACK =
            Registration.SOUND_EVENTS.register("winter_soundtrack",
                    () -> new SoundEvent(new ResourceLocation(FeywildMod.MOD_ID, "winter_soundtrack")));


    public static void register() {}
}
