package com.saphienyako.feywild.block.trees;

import com.mojang.serialization.MapCodec;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.worldgen.processor.SpringTreeProcessor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SpringTreeSaplingBlock extends FeySaplingBlock{

    private static final List<Identifier> TREES = new ArrayList<>();
    static {
        for (int i = 0; i <= 4; i++) {
            TREES.add(Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "fey_tree_" + i));
        }
    }
    public SpringTreeSaplingBlock(Properties properties) {super(properties);}

    @Override
    protected StructureProcessor getProcessor() {
        return SpringTreeProcessor.SAPLING;
    }

    @Override
    protected List<Identifier> getTrees() {
        return TREES;
    }

    @Override
    public @NotNull MapCodec<BushBlock> codec() {
        return simpleCodec(SpringTreeSaplingBlock::new);
    }
}
