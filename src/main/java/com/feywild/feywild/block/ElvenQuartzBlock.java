package com.feywild.feywild.block;

import io.github.noeppi_noeppi.libx.base.decoration.DecoratedBlock;
import io.github.noeppi_noeppi.libx.base.decoration.DecorationContext;
import io.github.noeppi_noeppi.libx.base.decoration.DecorationType;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;

public class ElvenQuartzBlock extends DecoratedBlock {

    public static final DecorationContext DECORATION = new DecorationContext("elven_quartz", DecorationType.BASE, DecorationType.SLAB, DecorationType.STAIR);

    public ElvenQuartzBlock(ModX mod, Properties properties) {
        super(mod, DECORATION, properties);
    }

    public SlabBlock getSlabBlock() {return get(DecorationType.SLAB);}

    public StairBlock getStairBlock() {return get(DecorationType.STAIR);}

}
