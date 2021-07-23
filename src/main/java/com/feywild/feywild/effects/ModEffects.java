package com.feywild.feywild.effects;

import com.feywild.feywild.util.Registration;
import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;

public class ModEffects {
    public static RegistryObject<WindWalkEffect> WIND_WALK_EFFECT = register("wind_walk", new WindWalkEffect());


    public static <T extends Effect> RegistryObject<T> register(String name , T object){
        return Registration.EFFECTS.register(name,()->object);
    }

    public static void register(){

    }
}
