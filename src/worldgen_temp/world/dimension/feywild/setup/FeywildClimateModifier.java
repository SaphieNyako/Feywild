package com.feywild.feywild.world.dimension.feywild.setup;

import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Climate;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class FeywildClimateModifier {

    private final NoiseParameterRange temperature;
    private final NoiseParameterRange humidity;
    private final NoiseParameterRange continentalness;
    private final NoiseParameterRange erosion;
    private final NoiseParameterRange depth;
    private final NoiseParameterRange weirdness;

    public FeywildClimateModifier(List<Climate.ParameterPoint> biomeData) {
        this.temperature = build("temperature", biomeData, 1, Climate.ParameterPoint::temperature);
        this.humidity = build("humidity", biomeData, 1, Climate.ParameterPoint::humidity);
        this.continentalness = build("continentalness", biomeData, 7, Climate.ParameterPoint::continentalness);
        this.erosion = build("erosion", biomeData, 1, Climate.ParameterPoint::erosion);
        this.depth = build("depth", biomeData, 0.25, Climate.ParameterPoint::depth);
        this.weirdness = build("weirdness", biomeData, 2.5, Climate.ParameterPoint::weirdness);
    }

    private static NoiseParameterRange build(String name, List<Climate.ParameterPoint> biomeData, double bias, Function<Climate.ParameterPoint, Climate.Parameter> extractor) {
        List<Climate.Parameter> params = biomeData.stream().map(extractor).toList();
        long min = params.stream().map(Climate.Parameter::min).min(Comparator.comparingLong(l -> l)).orElse(-10000L);
        long max = params.stream().map(Climate.Parameter::max).max(Comparator.comparingLong(l -> l)).orElse(10000L);
        if (min == max)
            throw new IllegalStateException("Fixed value in climate value: " + name + ". Feywild can't generate.");
        return new NoiseParameterRange(Math.min(min, max), Math.max(min, max), bias);
    }

    //bias a small deviation of the expected value??

    public Climate.TargetPoint modify(Climate.TargetPoint target) {
        return new Climate.TargetPoint(
                this.temperature.modify(target.temperature()),
                this.humidity.modify(target.humidity()),
                this.continentalness.modify(target.continentalness()),
                this.erosion.modify(target.erosion()),
                this.depth.modify(target.depth()),
                this.weirdness.modify(target.weirdness())
        );
    }

    private record NoiseParameterRange(long min, long max, double bias) {

        public long modify(long value) {
            if (this.min() == this.max()) return this.min();
            return Mth.clamp(Math.round(this.min() + ((this.max() - this.min()) * Math.pow((value + 10000) / 20000d, this.bias()))), this.min(), this.max());
        }
    }

}
