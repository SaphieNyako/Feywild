package com.feywild.feywild.block;

import org.moddingx.libx.base.decoration.DecoratedBlock;
import org.moddingx.libx.base.decoration.DecorationContext;
import org.moddingx.libx.base.decoration.DecorationType;
import org.moddingx.libx.mod.ModX;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class ElvenQuartzBlock extends DecoratedBlock {

    public static final DecorationContext DECORATION = new DecorationContext("elven_quartz", DecorationType.BASE, DecorationType.SLAB, DecorationType.STAIRS);

    public ElvenQuartzBlock(ModX mod, Properties properties) {
        super(mod, DECORATION, properties);
    }

    public SlabBlock getSlabBlock() {return get(DecorationType.SLAB);}

    public StairBlock getStairBlock() {return get(DecorationType.STAIRS);}

}
