package com.feywild.feywild.util.configs;

import com.electronwill.nightconfig.core.AbstractConfig;
import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.ConfigFormat;
import net.minecraftforge.common.ForgeConfigSpec;

public class TreePatchesConfig extends AbstractConfig {

    protected boolean spawnSpring;
    protected boolean spawnSummer;
    protected boolean spawnAutumn;
    protected boolean spawnWinter;


    protected double spawnChance;
    protected int spawnSize;

    protected ForgeConfigSpec.BooleanValue configSpawnSpring;
    protected ForgeConfigSpec.BooleanValue configSpawnSummer;
    protected ForgeConfigSpec.BooleanValue configSpawnAutumn;
    protected ForgeConfigSpec.BooleanValue configSpawnWinter;

    protected ForgeConfigSpec.DoubleValue configSpawnChance;
    protected ForgeConfigSpec.IntValue configSpawnSize;

    private int cachedSpawnSpring = -1;
    private int cachedSpawnSummer = -1;
    private int cachedSpawnAutumn = -1;
    private int cachedSpawnWinter = -1;


    private double cachedSpawnChance = -1;
    private int cachedSpawnSize = -1;

    //CONSTRUCTOR boolean concurrent has to be added...
    public TreePatchesConfig( boolean spawnSpring,boolean spawnSummer, boolean spawnAutumn, boolean spawnWinter, double spawnChance, int spawnSize) {
        this( spawnSpring,spawnSummer,spawnAutumn,spawnWinter,spawnChance,spawnSize, true);
    }

    public TreePatchesConfig( boolean spawnSpring,boolean spawnSummer, boolean spawnAutumn, boolean spawnWinter, double spawnChance, int spawnSize, boolean concurrent) {
        super(concurrent);
        this.spawnSpring = spawnSpring;
        this.spawnSummer = spawnSummer;
        this.spawnAutumn = spawnAutumn;
        this.spawnWinter = spawnWinter;
        this.spawnChance = spawnChance;
        this.spawnSize = spawnSize;
    }

    public boolean getCachedSpawnSpring() {
        if (cachedSpawnSpring == -1) {
            cachedSpawnSpring = configSpawnSpring.get() ? 1 : 0;
        }
        return cachedSpawnSpring == 1;
    }

    public boolean getCachedSpawnSummer() {
        if (cachedSpawnSummer == -1) {
            cachedSpawnSummer = configSpawnSummer.get() ? 1 : 0;
        }
        return cachedSpawnSummer == 1;
    }

    public boolean getCachedSpawnAutumn() {
        if (cachedSpawnAutumn == -1) {
            cachedSpawnAutumn = configSpawnAutumn.get() ? 1 : 0;
        }
        return cachedSpawnAutumn == 1;
    }

    public boolean getCachedSpawnWinter() {
        if (cachedSpawnWinter == -1) {
            cachedSpawnWinter = configSpawnWinter.get() ? 1 : 0;
        }
        return cachedSpawnWinter == 1;
    }


    public double getCachedSpawnChance() {
        if (cachedSpawnChance == -1) {
            cachedSpawnChance = configSpawnChance.get();
        }
        return cachedSpawnChance;
    }

    public int getCachedSpawnSize() {
        if (cachedSpawnSize == -1) {
            cachedSpawnSize = configSpawnSize.get();
        }
        return cachedSpawnSize;
    }

    protected void doApply(ForgeConfigSpec.Builder builder) {
        builder.comment("Tree Patches Config").push("Tree Patches");
        configSpawnSpring = builder.comment("Spawn spring tree.").define("spawnSpringTree", spawnSpring);
        configSpawnAutumn = builder.comment("Spawn autumn tree.").define("spawnAutumnTree", spawnAutumn);
        configSpawnSummer = builder.comment("Spawn summer tree.").define("spawnSummerTree", spawnSummer);
        configSpawnWinter = builder.comment("Spawn spring tree.").define("spawnWinterTree", spawnWinter);

        configSpawnChance = builder.comment("Tree patch spawn chance.").defineInRange("spawnChance", spawnChance, 0,1);
        configSpawnSize = builder.comment("Tree patch size.").defineInRange("spawnSize", spawnSize, 0,10);
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
