package com.feywild.feywild.world.gen.feature;

import com.feywild.feywild.FeywildMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SummerTreeFeature extends Feature<NoneFeatureConfiguration> {

    private static final ResourceLocation SUMMER_TREE_1 = new ResourceLocation("feywild:summer_tree_1");
    private static final ResourceLocation SUMMER_TREE_2 = new ResourceLocation("feywild:summer_tree_2");
    private static final ResourceLocation SUMMER_TREE_3 = new ResourceLocation("feywild:summer_tree_3");
    private static final ResourceLocation SUMMER_TREE_4 = new ResourceLocation("feywild:summer_tree_4");
    private static final ResourceLocation[] SUMMER_TREE = new ResourceLocation[]{SUMMER_TREE_1, SUMMER_TREE_2, SUMMER_TREE_3, SUMMER_TREE_4};

    public SummerTreeFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    public static boolean isAirOrLeavesOrLogsAt(LevelSimulatedReader reader, BlockPos pos) {
        return reader.isStateAtPosition(pos, (state) -> {
            return state.isAir() || state.is(BlockTags.LEAVES) || state.is(BlockTags.LOGS);
        });
    }

    private static boolean isDirtOrFarmlandAt(LevelSimulatedReader reader, BlockPos pos) {
        return reader.isStateAtPosition(pos, (state) -> {
            Block block = state.getBlock();
            return isDirt(state) || block == Blocks.FARMLAND;
        });
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel reader = context.level();
        BlockPos pos = context.origin();
        RandomSource random = context.random();
        int randomTree = random.nextInt(SUMMER_TREE.length);

        if (!isDirtOrFarmlandAt(reader, pos.below())) {
            return false;
        }

        for (int j = 0; j < 8; j++) {

            BlockPos upPos = new BlockPos(pos).above();
            for (int k = 0; k < j; k++) {
                upPos = upPos.above();
            }

            if (!isAirOrLeavesOrLogsAt(reader, upPos) || !isAirOrLeavesOrLogsAt(reader, upPos.north()) || !isAirOrLeavesOrLogsAt(reader, upPos.south())
                    || !isAirOrLeavesOrLogsAt(reader, upPos.east()) || !isAirOrLeavesOrLogsAt(reader, upPos.east().north()) || !isAirOrLeavesOrLogsAt(reader, upPos.east().south())
                    || !isAirOrLeavesOrLogsAt(reader, upPos.west()) || !isAirOrLeavesOrLogsAt(reader, upPos.west().north()) || !isAirOrLeavesOrLogsAt(reader, upPos.west().south())) {
                return false;
            }
        }

        if (isAirOrLeavesOrLogsAt(reader, pos.below().north()) || isAirOrLeavesOrLogsAt(reader, pos.below().south()) ||
                isAirOrLeavesOrLogsAt(reader, pos.below().east()) || isAirOrLeavesOrLogsAt(reader, pos.below().west())) {
            return false;
        }

        StructureTemplateManager templatemanager = reader.getLevel().getServer().getStructureManager();
        StructureTemplate template = templatemanager.getOrCreate(SUMMER_TREE[randomTree]);

        BlockPos halfLengths = new BlockPos(
                template.getSize().getX() / 2,
                template.getSize().getY() / 2,
                template.getSize().getZ() / 2);

        Rotation rotation = Rotation.getRandom(random);
        BlockPos.MutableBlockPos mutable = (new BlockPos.MutableBlockPos()).set(pos);

        StructurePlaceSettings placementsettings = (new StructurePlaceSettings()).setRotation(rotation).setRotationPivot(halfLengths).setIgnoreEntities(false);

        Optional<StructureProcessorList> processor = ((Registry) reader.getLevel().getServer().registryAccess().registry(Registry.PROCESSOR_LIST).get()).getOptional(
                FeywildMod.getInstance().resource("summer_tree_processor"));
        processor.ifPresent((structureProcessorList) -> {
            List<StructureProcessor> var10000 = structureProcessorList.list();
            Objects.requireNonNull(placementsettings);
            var10000.forEach(placementsettings::addProcessor);
        });

        BlockPos blockPos = mutable.set(pos).move(-halfLengths.getX(), -3, -halfLengths.getZ());
        template.placeInWorld(reader, blockPos, blockPos, placementsettings, random, 2);
        return true;
    }
}
