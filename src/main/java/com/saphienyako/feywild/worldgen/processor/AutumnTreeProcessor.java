package com.saphienyako.feywild.worldgen.processor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.saphienyako.feywild.block.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

import java.util.List;

public class AutumnTreeProcessor extends FeyTreeProcessor {

    public static final AutumnTreeProcessor WORLDGEN = new AutumnTreeProcessor(false);
    public static final AutumnTreeProcessor SAPLING = new AutumnTreeProcessor(true);

    private AutumnTreeProcessor(boolean fromSapling) {
        super(fromSapling);
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return FeywildProcessors.AUTUMN_TREE;
    }

    public static final Codec<AutumnTreeProcessor> CODEC =
            Codec.unit(WORLDGEN);

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
