package com.saphienyako.feywild.worldgen.processor;

import com.mojang.serialization.MapCodec;
import com.saphienyako.feywild.block.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SpringTreeProcessor extends FeyTreeProcessor{

    public static final SpringTreeProcessor WORLD_GEN = new SpringTreeProcessor(false);

    public static final SpringTreeProcessor SAPLING = new SpringTreeProcessor(true);

    private SpringTreeProcessor(boolean fromSapling) {
        super(fromSapling);
    }

    @Override
    protected @NotNull StructureProcessorType<?> getType() {
        return FeywildProcessors.SPRING_TREE.get();
    }


    public static final MapCodec<SpringTreeProcessor> MAP_CODEC = MapCodec.unit(() -> WORLD_GEN);
    @Override
    protected Block getLogBlock() {
        return ModBlocks.SPRING_TREE_LOG.get();
    }

    @Override
    protected Block getCrackedLogBlock() {
        return ModBlocks.SPRING_TREE_CRACKED_LOG.get();
    }

    @Override
    protected List<Block> getLeafVariants() {
        return List.of(
                ModBlocks.SPRING_TREE_LEAVES_CYAN.get(),
                ModBlocks.SPRING_TREE_LEAVES_GREEN.get(),
                ModBlocks.SPRING_TREE_LEAVES_LIME.get()
        );
    }

    @Override
    protected Block getWoodBlock() {
        return ModBlocks.SPRING_TREE_WOOD.get();
    }
}
