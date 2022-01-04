package com.feywild.feywild.world.feature;

import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

public class ModFeatureProperties {

    public static final RandomPatchConfiguration PLAIN_FLOWERS = flowers(new WeightedStateProvider(
                    SimpleWeightedRandomList.<BlockState>builder()
                            .add(Blocks.DANDELION.defaultBlockState(), 3)
                            .add(Blocks.POPPY.defaultBlockState(), 1)
                            .add(Blocks.ALLIUM.defaultBlockState(), 1)
                            .add(Blocks.AZURE_BLUET.defaultBlockState(), 1)
                            .add(Blocks.RED_TULIP.defaultBlockState(), 1)
                            .add(Blocks.ORANGE_TULIP.defaultBlockState(), 1)
                            .add(Blocks.WHITE_TULIP.defaultBlockState(), 1)
                            .add(Blocks.PINK_TULIP.defaultBlockState(), 1)
                            .add(Blocks.OXEYE_DAISY.defaultBlockState(), 1)
                            .add(Blocks.CORNFLOWER.defaultBlockState(), 1)
                            .add(Blocks.LILY_OF_THE_VALLEY.defaultBlockState(), 1)
                            .build()
            )
    );

    public static final RandomPatchConfiguration WARM_FLOWERS = flowers(new WeightedStateProvider(
                    SimpleWeightedRandomList.<BlockState>builder()
                            .add(Blocks.POPPY.defaultBlockState(), 2)
                            .add(Blocks.DANDELION.defaultBlockState(), 1)
                            .build()
            )
    );

    public static final RandomPatchConfiguration SWAMP_FLOWERS = flowers(new WeightedStateProvider(
                    SimpleWeightedRandomList.<BlockState>builder()
                            .add(Blocks.BLUE_ORCHID.defaultBlockState(), 2)
                            .build()
            )
    );

    public static final RandomPatchConfiguration SMALL_MUSHROOMS = flowers(new WeightedStateProvider(
                    SimpleWeightedRandomList.<BlockState>builder()
                            .add(Blocks.BROWN_MUSHROOM.defaultBlockState(), 2)
                            .add(Blocks.RED_MUSHROOM.defaultBlockState(), 1)
                            .build()
            )
    );

    public static final RandomPatchConfiguration COLD_FLOWERS = flowers(new WeightedStateProvider(
                    SimpleWeightedRandomList.<BlockState>builder()
                            .add(Blocks.LILY_OF_THE_VALLEY.defaultBlockState(), 2)
                            .add(Blocks.POPPY.defaultBlockState(), 1)
                            .build()
            )
    );

    private static RandomPatchConfiguration flowers(BlockStateProvider provider) {
        return new RandomPatchConfiguration(64, 7, 3, () -> Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(provider)).onlyWhenEmpty());
    }
}
