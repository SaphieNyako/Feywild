package com.saphienyako.feywild.block.trees;

import com.mojang.serialization.MapCodec;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.worldgen.processor.SummerTreeProcessor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;

import java.util.ArrayList;
import java.util.List;

public class SummerTreeSaplingBlock extends FeySaplingBlock{

    private static final List<ResourceLocation> TREES = new ArrayList<>();
    static {
        for (int i = 0; i <= 4; i++) {
            TREES.add(ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "fey_tree_" + i));
        }
    }
    public SummerTreeSaplingBlock(Properties properties) {super(properties);}

    @Override
    protected StructureProcessor getProcessor() {
        return SummerTreeProcessor.INSTANCE;
    }

    @Override
    protected List<ResourceLocation> getTrees() {
        return TREES;
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return simpleCodec(SummerTreeSaplingBlock::new);
    }
}
