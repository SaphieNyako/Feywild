package com.feywild.feywild.world.dimension.feywild.features;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.world.dimension.feywild.features.specialfeatures.AutumnPumpkinsFeature;
import com.feywild.feywild.world.dimension.feywild.features.specialfeatures.GiantFlowerFeature;
import com.feywild.feywild.world.feature.structure.structures.*;
import io.github.noeppi_noeppi.libx.annotation.registration.NoReg;
import io.github.noeppi_noeppi.libx.annotation.registration.RegisterClass;
import net.minecraft.data.worldgen.PlainVillagePools;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

@RegisterClass
public class FeywildDimensionWorldGeneration {

    public static final Feature<NoneFeatureConfiguration> autumnPumpkins = new AutumnPumpkinsFeature();
    public static final Feature<NoneFeatureConfiguration> sunflowers = new GiantFlowerFeature(ModBlocks.sunflower);
    public static final Feature<NoneFeatureConfiguration> dandelions = new GiantFlowerFeature(ModBlocks.dandelion);
    public static final Feature<NoneFeatureConfiguration> crocus = new GiantFlowerFeature(ModBlocks.crocus);

    public static final StructureFeature<JigsawConfiguration> beekeep = new BeekeepStructure();
    public static final StructureFeature<JigsawConfiguration> springWorldTree = new SpringWorldTreeStructure("spring_world_tree");
    public static final StructureFeature<JigsawConfiguration> summerWorldTree = new SummerWorldTreeStructure("summer_world_tree");
    public static final StructureFeature<JigsawConfiguration> autumnWorldTree = new AutumnWorldTreeStructure("autumn_world_tree");
    public static final StructureFeature<JigsawConfiguration> winterWorldTree = new WinterWorldTreeStructure("winter_world_tree");

    public static final StructureFeature<JigsawConfiguration> feyCircle = new FeyCircleStructure();

    @NoReg
    public static final JigsawConfiguration dummyJigsaw = new JigsawConfiguration(PlainVillagePools.START, 0);

}
