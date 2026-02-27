package com.saphienyako.feywild.worldgen.processor;

import com.mojang.serialization.MapCodec;
import com.saphienyako.feywild.block.ModBlocks;
import net.minecraft.world.level.block.Block;

import java.util.List;

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
    protected List<Block> getLeafVariants() {
        return List.of(
                ModBlocks.AUTUMN_TREE_LEAVES_BROWN.get(),
                ModBlocks.AUTUMN_TREE_LEAVES_RED.get(),
                ModBlocks.AUTUMN_TREE_LEAVES_LIGHT_GRAY.get(),
                ModBlocks.AUTUMN_TREE_LEAVES_DARK_GRAY.get()

        );
    }

    @Override
    protected Block getWoodBlock() {
        return ModBlocks.AUTUMN_TREE_WOOD.get();
    }
}
