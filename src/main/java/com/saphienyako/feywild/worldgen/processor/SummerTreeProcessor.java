package com.saphienyako.feywild.worldgen.processor;

import com.mojang.serialization.MapCodec;
import com.saphienyako.feywild.block.ModBlocks;
import net.minecraft.world.level.block.Block;

public class SummerTreeProcessor extends FeyTreeProcessor{

    public static final SummerTreeProcessor INSTANCE = new SummerTreeProcessor();

    public static final MapCodec<SummerTreeProcessor> MAP_CODEC =
            MapCodec.unit(() -> INSTANCE);


    @Override
    protected Block getLogBlock() {
        return ModBlocks.SUMMER_TREE_LOG.get();
    }

    @Override
    protected Block getCrackedLogBlock() {
        return ModBlocks.SUMMER_TREE_CRACKED_LOG.get();
    }

    @Override
    protected Block getLeavesBlock() {
        return ModBlocks.SUMMER_TREE_LEAVES.get();
    }

    @Override
    protected Block getWoodBlock() {
        return ModBlocks.SUMMER_TREE_WOOD.get();
    }
}
