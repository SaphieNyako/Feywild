package com.feywild.feywild.util.Configs;

import com.electronwill.nightconfig.core.AbstractConfig;
import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.ConfigFormat;
import net.minecraftforge.common.ForgeConfigSpec;

public class PerformanceConfig extends AbstractConfig {

    protected String name;
    protected boolean spawnTreeLeafParticles;

    protected ForgeConfigSpec.BooleanValue configTreeLeafParticles;

    private int cachedTreeParticlesValue = -1;

    //CONSTRUCTOR boolean concurrent has to be added...
    public PerformanceConfig(String name, boolean spawnTreeLeafParticles) {
        this(name, spawnTreeLeafParticles, true);
    }

    public PerformanceConfig(String name, boolean spawnTreeLeafParticles, boolean concurrent) {
        super(concurrent);
        this.name = name;
        this.spawnTreeLeafParticles = spawnTreeLeafParticles;
    }

    public boolean cachedTreeParticlesValue() {
        if (cachedTreeParticlesValue == -1) {
            cachedTreeParticlesValue = configTreeLeafParticles.get() ? 1 : 0;
        }
        return cachedTreeParticlesValue == 1;
    }

    protected void doApply(ForgeConfigSpec.Builder builder) {
        builder.comment(name + " config.").push(name);
        configTreeLeafParticles = builder.comment("Spawn tree particles.").define("spawnTreeParticles", spawnTreeLeafParticles);
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
