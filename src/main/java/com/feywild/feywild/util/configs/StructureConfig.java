package com.feywild.feywild.util.configs;

import com.electronwill.nightconfig.core.AbstractConfig;
import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.ConfigFormat;
import net.minecraftforge.common.ForgeConfigSpec;

public class StructureConfig extends AbstractConfig {

    protected String name;
    protected int averageDistance, minDistance;

    protected ForgeConfigSpec.IntValue configDistance, configMinDistance;
    private int cachedDistance = -1;
    private int cachedMinDistance = -1;

    //CONSTRUCTOR boolean concurrent has to be added...
    public StructureConfig(String name, int averageDistance, int minDistance) {
        this(name, averageDistance, minDistance, true);
    }

    public StructureConfig(String name, int averageDistance, int minDistance, boolean concurrent) {
        super(concurrent);
        this.name = name;
        this.averageDistance = averageDistance;
        this.minDistance = minDistance;
    }

    public int getCachedDistance() {
        if (cachedDistance == -1) {
            cachedDistance = configDistance.get();
        }
        return cachedDistance;
    }

    public int getCachedMinDistance() {
        if (cachedMinDistance == -1) {
            cachedMinDistance = configMinDistance.get();
        }
        return cachedMinDistance;
    }

    protected void doApply(ForgeConfigSpec.Builder builder) {
        builder.comment("Average distance should always be higher then the Minimum distance, and cannot be the same.").push(name + " Structure");
        configDistance = builder.comment("Average distance between structure.").defineInRange("averageDistance", averageDistance, 0, 500);
        configMinDistance = builder.comment("Minimum distance between structure.").defineInRange("minDistance", minDistance, 0, 500);
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
