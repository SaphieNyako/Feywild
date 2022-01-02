package com.feywild.feywild.world.structure.structures;

import com.feywild.feywild.config.WorldGenConfig;
import com.feywild.feywild.config.data.StructureData;
import com.feywild.feywild.entity.ModEntityTypes;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;

import java.util.Collections;
import java.util.List;

public class SpringWorldTreeStructure extends BaseStructure {

    private static final List<MobSpawnSettings.SpawnerData> STRUCTURE_CREATURES = ImmutableList.of(
            new MobSpawnSettings.SpawnerData(ModEntityTypes.springPixie, 100, 4, 4),
            new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 10, 4, 1),
            new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 10, 1, 2)
    );

    public SpringWorldTreeStructure() {
        super("spring_world_tree/start_pool");
    }

    @Override
    public StructureData getStructureData() {
        return WorldGenConfig.structures.spring_world_tree;
    }

    @Override
    public int getSeedModifier() {
        return 1234567890;
    }

    @Override
    public List<MobSpawnSettings.SpawnerData> getDefaultSpawnList(MobCategory category) {
        return category == MobCategory.CREATURE ? STRUCTURE_CREATURES : Collections.emptyList();
    }
}
