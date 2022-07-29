package com.feywild.feywild.world.dimension.feywild.setup;

import com.feywild.feywild.world.dimension.feywild.FeywildDimension;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Optional;

public class FeywildBiomeSource {

    public static final Codec<Source> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RegistryOps.retrieveRegistry(Registry.BIOME_REGISTRY).forGetter(s -> s.biomeRegistry)
    ).apply(instance, instance.stable(FeywildBiomeSource::createSource)));

    private static Source createSource(Registry<Biome> biomeRegistry) {
        return new Source(biomeRegistry, parameters(biomeRegistry), preset(biomeRegistry));
    }

    private static Climate.ParameterList<Holder<Biome>> parameters(Registry<Biome> biomeRegistry) {
        return FeywildDimension.buildFeywildClimate(biomeRegistry::getHolder);
    }

    private static MultiNoiseBiomeSource.PresetInstance preset(Registry<Biome> biomeRegistry) {
        return new MultiNoiseBiomeSource.PresetInstance(new MultiNoiseBiomeSource
                .Preset(FeywildDimension.FEYWILD_DIMENSION.location(), FeywildBiomeSource::parameters), biomeRegistry);
    }

    public static class Source extends MultiNoiseBiomeSource {

        private final Registry<Biome> biomeRegistry;
        private final FeywildClimateModifier modifier;

        public Source(Registry<Biome> biomeRegistry, Climate.ParameterList<Holder<Biome>> parameters, PresetInstance preset) {
            super(parameters, Optional.ofNullable(preset));
            this.biomeRegistry = biomeRegistry;
            this.modifier = new FeywildClimateModifier(FeywildDimension.buildAllClimateParameters());
        }

        @NotNull
        @Override
        protected Codec<? extends BiomeSource> codec() {
            return FeywildBiomeSource.CODEC;
        }

        @Nonnull
        @Override
        public Holder<Biome> getNoiseBiome(@Nonnull Climate.TargetPoint target) {
            return super.getNoiseBiome(this.modifier.modify(target));
        }
    }
}
