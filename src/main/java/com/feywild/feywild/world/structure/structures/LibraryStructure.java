package com.feywild.feywild.world.structure.structures;

import com.feywild.feywild.config.WorldGenConfig;
import com.feywild.feywild.config.data.StructureData;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;

import java.util.Collections;
import java.util.List;

public class LibraryStructure extends BaseStructure {

    private static final List<MobSpawnSettings.SpawnerData> STRUCTURE_CREATURES = ImmutableList.of(
            new MobSpawnSettings.SpawnerData(EntityType.VILLAGER, 1, 1, 2)
    );

    public LibraryStructure() {
        super("library/start_pool");
    }
    
    @Override
    public StructureData getStructureData() {
        return WorldGenConfig.structures.library;
    }

    @Override
    public int getSeedModifier() {
        return 1238904567;
    }

    @Override
    public List<MobSpawnSettings.SpawnerData> getDefaultSpawnList(MobCategory category) {
        return category == MobCategory.CREATURE ? STRUCTURE_CREATURES : Collections.emptyList();
    }
}




