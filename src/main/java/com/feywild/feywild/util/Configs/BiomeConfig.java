package com.feywild.feywild.util.Configs;

import com.electronwill.nightconfig.core.AbstractConfig;
import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.ConfigFormat;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BiomeConfig extends AbstractConfig {

    protected String name;
    protected int weight;
    protected double size;

    protected ForgeConfigSpec.IntValue configWeight;
    protected ForgeConfigSpec.DoubleValue configSize;

    private int cachedWeight = -1;
    private double cachedSize = -1;

    //CONSTRUCTOR boolean concurrent has to be added...
    public BiomeConfig(String name, int weight, double size) {
        this(name, weight, size, true);
    }

    public BiomeConfig(String name, int weight, double size, boolean concurrent) {
        super(concurrent);
        this.name = name;
        this.weight = weight;
        this.size = size;
    }

    public int getCachedWeight() {
        if (cachedWeight == -1) {
            cachedWeight = configWeight.get();
        }
        return cachedWeight;
    }

    public double getCachedSize() {
        if (cachedSize == -1) {
            cachedSize = configSize.get();
        }
        return cachedSize;
    }

    protected void doApply(ForgeConfigSpec.Builder builder) {
        builder.comment(name + " biome config.").push(name + " Biome");
        configWeight = builder.comment("Chance to spawn.").defineInRange("spawnChance", weight, 0, 256);
        configSize = builder.comment("Size of a biome.").defineInRange("size", size, 0f, 1f);
    }

    protected void postApply(ForgeConfigSpec.Builder builder) {
        builder.pop();
    }


    public void apply(ForgeConfigSpec.Builder builder) {
        doApply(builder);
        postApply(builder);
    }
    @Override
    public AbstractConfig clone() {
        return null;
    }

    @Override
    public Config createSubConfig() {
        return null;
    }

    @Override
    public ConfigFormat<?> configFormat() {
        return null;
    }
}
