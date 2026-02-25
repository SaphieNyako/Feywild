package com.saphienyako.feywild.worldgen;

import com.mojang.serialization.Codec;
import com.saphienyako.feywild.Feywild;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class StructureFeature extends Feature<NoneFeatureConfiguration> {
    private final ResourceLocation structureLocation;

    public StructureFeature(ResourceLocation structureLocation, Codec<NoneFeatureConfiguration> codec) {
        super(codec);
        this.structureLocation = structureLocation;
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        StructureTemplateManager manager = context.level().getLevel().getStructureManager();
        StructureTemplate template = manager.get(structureLocation).orElse(null);

   //     Feywild.LOGGER.info("Template loaded: {}", template != null);

        if (template == null) {
            Feywild.LOGGER.error("Structure not found: {}", structureLocation);
            return false;
        }

        Rotation rotation = Rotation.getRandom(context.random());
        Mirror mirror = Mirror.NONE;

        StructurePlaceSettings settings = new StructurePlaceSettings()
                .setRotation(rotation)
                .setMirror(mirror)
              //  .addProcessor() TODO add processor
                .setIgnoreEntities(false);

        Vec3i size = template.getSize(rotation);
        BlockPos placementPos = context.origin().offset(
                -size.getX() / 2,
                0,
                -size.getZ() / 2
        );

        template.placeInWorld(
                context.level(),
                placementPos,
                placementPos,
                settings,
                context.random(),
                2
        );

        return true;
    }
}
