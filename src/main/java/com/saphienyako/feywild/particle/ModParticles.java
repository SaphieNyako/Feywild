package com.saphienyako.feywild.particle;

import com.saphienyako.feywild.Feywild;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public class ModParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Feywild.MOD_ID);

    public static final RegistryObject<BasicParticleType> SPRING_SPARKLE_PARTICLE =
            PARTICLES.register("spring_sparkle_particle", () -> new BasicParticleType(false));
    public static final RegistryObject<BasicParticleType> SUMMER_SPARKLE_PARTICLE =
            PARTICLES.register("summer_sparkle_particle", () -> new BasicParticleType(false));
    public static final RegistryObject<BasicParticleType> WINTER_SPARKLE_PARTICLE =
            PARTICLES.register("winter_sparkle_particle", () -> new BasicParticleType(false));
    public static final RegistryObject<BasicParticleType> AUTUMN_SPARKLE_PARTICLE =
            PARTICLES.register("autumn_sparkle_particle", () -> new BasicParticleType(false));
    public static final RegistryObject<BasicParticleType> FEY_SPARKLE_PARTICLE =
            PARTICLES.register("fey_sparkle_particle", () -> new BasicParticleType(false));
    public static final RegistryObject<BasicParticleType> AUTUMN_LEAF_PARTICLE =
            PARTICLES.register("autumn_leaf_particle", () -> new BasicParticleType(false));

    public static void register(IEventBus eventBus) {
        PARTICLES.register(eventBus);
    }
}
