package com.saphienyako.feywild.worldgen.features;

import com.mojang.serialization.Codec;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.worldgen.processor.FeyTreeProcessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StructureFeature extends Feature<NoneFeatureConfiguration> {

    private static final List<ResourceLocation> TREES = new ArrayList<>();
    private static boolean initialized = false;

    public StructureFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    private static void initialize() {
        if (initialized) return;
        for (int i = 0; i <= 4; i++) {
            TREES.add(ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "fey_tree_" + i));
        }
        initialized = true;
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        initialize();

        StructureTemplateManager manager = context.level().getLevel().getStructureManager();

        // Pick a random tree template
        ResourceLocation structureLocation = TREES.get(context.random().nextInt(TREES.size()));
        StructureTemplate template = manager.get(structureLocation).orElse(null);

        if (template == null) {
            Feywild.LOGGER.error("Structure not found: {}", structureLocation);
            return false;
        }

        // Random rotation and mirror
        Rotation rotation = Rotation.getRandom(context.random());
        Mirror mirror = Mirror.values()[context.random().nextInt(Mirror.values().length)];

        StructurePlaceSettings settings = new StructurePlaceSettings()
                .setRotation(rotation)
                .setMirror(mirror)
                .addProcessor(FeyTreeProcessor.INSTANCE)
                .setIgnoreEntities(false);

        Vec3i size = template.getSize();

        // Center and sink into ground by 1
        BlockPos placementPos = context.origin().offset(
                -size.getX() / 2,
                0,
                -size.getZ() / 2
        );

        for (int dx = 0; dx < size.getX(); dx++) {
            for (int dz = 0; dz < size.getZ(); dz++) {
                BlockPos checkPos = placementPos.offset(dx, -1, dz);
                BlockState state = context.level().getBlockState(checkPos);
                if (!state.isSolidRender(context.level(), checkPos) ||
                        (!state.is(Blocks.GRASS_BLOCK) && !state.is(Blocks.DIRT) && !state.is(Blocks.PODZOL))) {
                    return false; // Skip placement if ground unsuitable
                }
            }
        }

        // Place the structure
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
