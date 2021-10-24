package com.feywild.feywild.world.structure.structures;

import com.feywild.feywild.config.WorldGenConfig;
import com.feywild.feywild.config.data.StructureData;

public class BlacksmithStructure extends BaseStructure {

    @Override
    public StructureData getStructureData() {
        return WorldGenConfig.structures.blacksmith;
    }

    @Override
    public String getStructureId() {
        return "blacksmith/start_pool";
    }

    @Override
    public int getSeedModifier() {
        return 567890123;
    }
}
