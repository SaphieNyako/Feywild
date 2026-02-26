package com.saphienyako.feywild.worldgen.processor;

import com.mojang.serialization.MapCodec;
import com.saphienyako.feywild.block.ModBlocks;
import net.minecraft.world.level.block.Block;

public class SpringTreeProcessor extends FeyTreeProcessor{

    public static final SpringTreeProcessor INSTANCE = new SpringTreeProcessor();

    public static final MapCodec<SpringTreeProcessor> MAP_CODEC =
            MapCodec.unit(() -> INSTANCE);
    @Override
    protected Block getLogBlock() {
        return ModBlocks.SPRING_TREE_LOG.get();
    }

    @Override
    protected Block getCrackedLogBlock() {
        return ModBlocks.SPRING_TREE_CRACKED_LOG.get();
    }

    @Override
    protected Block getLeavesBlock() {
        return ModBlocks.SPRING_TREE_LEAVES.get();
    }

    @Override
    protected Block getWoodBlock() {
        return ModBlocks.SPRING_TREE_WOOD.get();
    }
}
