package com.feywild.feywild.block.trees;

import io.github.noeppi_noeppi.libx.base.decoration.DecoratedBlock;
import io.github.noeppi_noeppi.libx.base.decoration.DecorationContext;
import io.github.noeppi_noeppi.libx.base.decoration.DecorationType;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;

public class FeyPlankBlock extends DecoratedBlock {

    public static final DecorationContext DECORATION = new DecorationContext("fey_plank", DecorationType.BASE, DecorationType.FENCE, DecorationType.FENCE_GATE, DecorationType.SLAB, DecorationType.STAIR);

    public FeyPlankBlock(ModX mod, Properties properties, Item.Properties itemProperties) {
        super(mod, DECORATION, properties, itemProperties);
    }

    public SlabBlock getSlabBlock() {
        return get(DecorationType.SLAB);
    }

    public StairBlock getStairBlock() {
        return get(DecorationType.STAIR);
    }

    public FenceBlock getFenceBlock() {
        return get(DecorationType.FENCE);
    }

    public FenceGateBlock getFenceGateBlock() {
        return get(DecorationType.FENCE_GATE);
    }

}
