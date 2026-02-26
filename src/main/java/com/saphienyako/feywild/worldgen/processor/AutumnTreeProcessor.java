package com.saphienyako.feywild.worldgen.processor;

import com.mojang.serialization.MapCodec;
import com.saphienyako.feywild.block.ModBlocks;
import net.minecraft.world.level.block.Block;

public class AutumnTreeProcessor extends FeyTreeProcessor{

    public static final AutumnTreeProcessor INSTANCE = new AutumnTreeProcessor();

    public static final MapCodec<AutumnTreeProcessor> MAP_CODEC =
            MapCodec.unit(() -> INSTANCE);
    @Override
    protected Block getLogBlock() {
        return ModBlocks.AUTUMN_TREE_LOG.get();
    }

    @Override
    protected Block getCrackedLogBlock() {
        return ModBlocks.AUTUMN_TREE_CRACKED_LOG.get();
    }

    @Override
    protected Block getLeavesBlock() {
        return ModBlocks.AUTUMN_TREE_LEAVES.get();
    }

    @Override
    protected Block getWoodBlock() {
        return ModBlocks.AUTUMN_TREE_WOOD.get();
    }
}
