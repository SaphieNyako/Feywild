package com.feywild.feywild.util.configs;

import com.electronwill.nightconfig.core.AbstractConfig;
import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.ConfigFormat;
import net.minecraftforge.common.ForgeConfigSpec;

public class OreConfig extends AbstractConfig {

    protected String name;
    protected int weight;
    protected int size;
    protected int minHeight;
    protected int maxHeight;

    protected ForgeConfigSpec.IntValue configWeight;
    protected ForgeConfigSpec.IntValue configSize;
    protected ForgeConfigSpec.IntValue configMinHeight;
    protected ForgeConfigSpec.IntValue configMaxHeight;

    private int cachedWeight = -1;
    private int cachedSize = -1;
    private int cachedMinHeight = -1;
    private int cachedMaxHeight = -1;

    //CONSTRUCTOR boolean concurrent has to be added...
    public OreConfig(String name, int weight, int size, int minHeight, int maxHeight) {
        this(name, weight, size, minHeight, maxHeight, true);
    }

    public OreConfig(String name, int weight, int size,int minHeight,int maxHeight, boolean concurrent) {
        super(concurrent);
        this.name = name;
        this.weight = weight;
        this.size = size;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
    }

    public int getCachedWeight() {
        if (cachedWeight == -1) {
            cachedWeight = configWeight.get();
        }
        return cachedWeight;
    }

    public int getCachedSize() {
        if (cachedSize == -1) {
            cachedSize = configSize.get();
        }
        return cachedSize;
    }

    public int getCachedMinHeight() {
        if (cachedMinHeight == -1) {
            cachedMinHeight = configMinHeight.get();
        }
        return cachedMinHeight;
    }

    public int getCachedMaxHeight() {
        if (cachedMaxHeight == -1) {
            cachedMaxHeight = configMaxHeight.get();
        }
        return cachedMaxHeight;
    }

    protected void doApply(ForgeConfigSpec.Builder builder) {
        builder.comment(name + " ore config.").push(name + " Ore");
        configWeight = builder.comment("Chance to spawn.").defineInRange("spawnChance", weight, 0, 100);
        configSize = builder.comment("Size of an ore vein.").defineInRange("size", size, 0, 24);
        configMinHeight = builder.comment("Min y value at which the ore can spawn.").defineInRange("minHeight",minHeight,0,64);
        configMaxHeight = builder.comment("Max y value at which the ore can spawn.").defineInRange("maxHeight",maxHeight,0,64);
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
