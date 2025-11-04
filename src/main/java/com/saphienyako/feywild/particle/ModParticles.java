package com.saphienyako.feywild.particle;

import com.saphienyako.feywild.Feywild;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Feywild.MOD_ID);

    public static final RegistryObject<SimpleParticleType> SPRING_SPARKLE_PARTICLE = PARTICLE_TYPES.register("spring_sparkle_particle", ()-> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SUMMER_SPARKLE_PARTICLE = PARTICLE_TYPES.register("summer_sparkle_particle", ()-> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> WINTER_SPARKLE_PARTICLE = PARTICLE_TYPES.register("winter_sparkle_particle", ()-> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> AUTUMN_SPARKLE_PARTICLE = PARTICLE_TYPES.register("autumn_sparkle_particle", ()-> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> FEY_SPARKLE_PARTICLE = PARTICLE_TYPES.register("fey_sparkle_particle", ()-> new SimpleParticleType(true) );

    public static final RegistryObject<SimpleParticleType> AUTUMN_LEAF_PARTICLE = PARTICLE_TYPES.register("autumn_leaf_particle", ()-> new SimpleParticleType(true));

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
