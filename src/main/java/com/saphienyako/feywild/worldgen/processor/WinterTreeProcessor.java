package com.saphienyako.feywild.worldgen.processor;

import com.mojang.serialization.MapCodec;
import com.saphienyako.feywild.block.ModBlocks;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class WinterTreeProcessor extends FeyTreeProcessor{

    public static final WinterTreeProcessor INSTANCE = new WinterTreeProcessor();

    public static final MapCodec<WinterTreeProcessor> MAP_CODEC =
            MapCodec.unit(() -> INSTANCE);
    @Override
    protected Block getLogBlock() {
        return ModBlocks.WINTER_TREE_LOG.get();
    }

    @Override
    protected Block getCrackedLogBlock() {
        return ModBlocks.WINTER_TREE_CRACKED_LOG.get();
    }

    @Override
    protected List<Block> getLeafVariants() {
        return List.of(
                ModBlocks.WINTER_TREE_LEAVES_LIGHT_BLUE.get(),
                ModBlocks.WINTER_TREE_LEAVES_BLUE.get()
        );
    }

    @Override
    protected Block getWoodBlock() {
        return ModBlocks.WINTER_TREE_WOOD.get();
    }
}
