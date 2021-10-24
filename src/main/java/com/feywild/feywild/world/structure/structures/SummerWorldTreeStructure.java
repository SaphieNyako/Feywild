package com.feywild.feywild.world.structure.structures;

import com.feywild.feywild.config.WorldGenConfig;
import com.feywild.feywild.config.data.StructureData;
import com.feywild.feywild.entity.ModEntityTypes;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.level.biome.MobSpawnSettings;

import java.util.List;

public class SummerWorldTreeStructure extends BaseStructure {
    
    private static final List<MobSpawnSettings.SpawnerData> STRUCTURE_CREATURES = ImmutableList.of(
            new MobSpawnSettings.SpawnerData(ModEntityTypes.summerPixie, 100, 4, 4)
    );

    @Override
    public StructureData getStructureData() {
        return WorldGenConfig.structures.summer_world_tree;
    }

    @Override
    public String getStructureId() {
        return "summer_world_tree/start_pool";
    }

    @Override
    public int getSeedModifier() {
        return 890124567;
    }

    @Override
    public List<MobSpawnSettings.SpawnerData> getDefaultCreatureSpawnList() {
        return STRUCTURE_CREATURES;
    }
}
