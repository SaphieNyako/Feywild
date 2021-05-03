package com.feywild.feywild.world.feature;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.util.Registration;
import com.feywild.feywild.world.biome.LoggingSurfaceBuilder;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModFeatures {

    public static final Feature<NoFeatureConfig> AUTUMN_PUMPKINS =
          register("autumn_pumpkins", new AutumnPumpkinsFeature());




    public static void register(){}


    private static <C extends IFeatureConfig, F extends Feature<C>> F register(String key, F value) {
        value.setRegistryName(new ResourceLocation(FeywildMod.MOD_ID, key));
        ForgeRegistries.FEATURES.register(value);
        return value;
    }

}

