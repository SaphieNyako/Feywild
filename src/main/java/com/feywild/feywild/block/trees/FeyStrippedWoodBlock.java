package com.feywild.feywild.block.trees;

import io.github.noeppi_noeppi.libx.base.decoration.DecoratedBlock;
import io.github.noeppi_noeppi.libx.mod.ModX;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class FeyStrippedWoodBlock extends DecoratedBlock {

    public FeyStrippedWoodBlock(ModX mod, Properties properties) {
        super(mod, FeyWoodBlock.DECORATION, properties);
    }
}
