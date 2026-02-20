package com.saphienyako.feywild.worldgen;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class StructureFeature extends Feature<NoneFeatureConfiguration> {
    private final ResourceLocation structureLocation;

    public StructureFeature(ResourceLocation structureLocation, Codec<NoneFeatureConfiguration> codec) {
        super(codec);
        this.structureLocation = structureLocation;
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration>context) {
        ServerLevel level = context.level().getLevel();
        BlockPos pos = context.origin();
        RandomSource random = context.random();

        StructureTemplate template = level.getStructureManager().get(structureLocation).orElse(null);
        if (template == null) return false;

        StructurePlaceSettings settings = new StructurePlaceSettings()
                .setIgnoreEntities(true)
                .setRotation(Rotation.values()[random.nextInt(Rotation.values().length)])
                .setMirror(Mirror.values()[random.nextInt(Mirror.values().length)])
                .setRandom(random);

        return template.placeInWorld(level, pos, pos, settings, random, 2);
    }
}
