package com.saphienyako.feywild.worldgen.processor;

import com.mojang.serialization.MapCodec;
import com.saphienyako.feywild.block.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SummerTreeProcessor extends FeyTreeProcessor{

    public static final SummerTreeProcessor WORLD_GEN = new SummerTreeProcessor(false);

    public static final SummerTreeProcessor SAPLING = new SummerTreeProcessor(true);

    private SummerTreeProcessor(boolean fromSapling) {
        super(fromSapling);
    }

    @Override
    protected @NotNull StructureProcessorType<?> getType() {
        return FeywildProcessors.SUMMER_TREE.get();
    }


    public static final MapCodec<SummerTreeProcessor> MAP_CODEC = MapCodec.unit(() -> WORLD_GEN);

    @Override
    protected Block getLogBlock() {
        return ModBlocks.SUMMER_TREE_LOG.get();
    }

    @Override
    protected Block getCrackedLogBlock() {
        return ModBlocks.SUMMER_TREE_CRACKED_LOG.get();
    }

    @Override
    protected List<Block> getLeafVariants() {
        return List.of(
                ModBlocks.SUMMER_TREE_LEAVES_YELLOW.get(),
                ModBlocks.SUMMER_TREE_LEAVES_ORANGE.get()
        );
    }

    @Override
    protected Block getWoodBlock() {
        return ModBlocks.SUMMER_TREE_WOOD.get();
    }
}
