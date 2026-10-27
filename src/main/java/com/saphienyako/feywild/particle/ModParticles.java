package com.saphienyako.feywild.particle;

import com.saphienyako.feywild.Feywild;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;


public class ModParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Feywild.MOD_ID);

    public static final DeferredHolder<ParticleType<?>, @NotNull SimpleParticleType> SPRING_SPARKLE_PARTICLE =
            PARTICLE_TYPES.register("spring_sparkle_particle",
                    () -> new SimpleParticleType(true));

    public static final DeferredHolder<ParticleType<?>, @NotNull SimpleParticleType> SUMMER_SPARKLE_PARTICLE =
            PARTICLE_TYPES.register("summer_sparkle_particle",
                    () -> new SimpleParticleType(true));

    public static final DeferredHolder<ParticleType<?>, @NotNull SimpleParticleType> WINTER_SPARKLE_PARTICLE =
            PARTICLE_TYPES.register("winter_sparkle_particle",
                    () -> new SimpleParticleType(true));

    public static final DeferredHolder<ParticleType<?>, @NotNull SimpleParticleType> AUTUMN_SPARKLE_PARTICLE =
            PARTICLE_TYPES.register("autumn_sparkle_particle",
                    () -> new SimpleParticleType(true));

    public static final DeferredHolder<ParticleType<?>, @NotNull SimpleParticleType> HEXEN_SPARKLE_PARTICLE =
            PARTICLE_TYPES.register("hexen_sparkle_particle",
                    () -> new SimpleParticleType(true));

    public static final DeferredHolder<ParticleType<?>, @NotNull SimpleParticleType> FEY_SPARKLE_PARTICLE =
            PARTICLE_TYPES.register("fey_sparkle_particle",
                    () -> new SimpleParticleType(true));

    public static final DeferredHolder<ParticleType<?>, @NotNull SimpleParticleType> AUTUMN_LEAF_PARTICLE =
            PARTICLE_TYPES.register("autumn_leaf_particle",
                    ()-> new SimpleParticleType(true));

    public static final DeferredHolder<ParticleType<?>, @NotNull SimpleParticleType> SPRING_LEAF_PARTICLE =
            PARTICLE_TYPES.register("spring_leaf_particle",
                    ()-> new SimpleParticleType(true));

    public static final DeferredHolder<ParticleType<?>, @NotNull SimpleParticleType> SUMMER_LEAF_PARTICLE =
            PARTICLE_TYPES.register("summer_leaf_particle",
                    ()-> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, @NotNull SimpleParticleType> WINTER_LEAF_PARTICLE =
            PARTICLE_TYPES.register("winter_leaf_particle",
                    ()-> new SimpleParticleType(true));

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
