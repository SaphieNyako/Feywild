package com.feywild.feywild.block.trees;

import net.minecraft.world.item.Item;
import org.moddingx.libx.base.decoration.DecoratedBlock;
import org.moddingx.libx.base.decoration.DecorationContext;
import org.moddingx.libx.base.decoration.DecorationMaterial;
import org.moddingx.libx.base.decoration.DecorationType;
import org.moddingx.libx.mod.ModX;

public class FeyPlanksBlock extends DecoratedBlock {

    public static final DecorationContext DECORATION = new DecorationContext("fey_planks", DecorationMaterial.WOOD,
            DecorationType.BASE, DecorationType.FENCE, DecorationType.FENCE_GATE, DecorationType.SLAB,
            DecorationType.STAIRS, DecorationType.WOOD_BUTTON, DecorationType.WOOD_PRESSURE_PLATE,
            DecorationType.DOOR, DecorationType.TRAPDOOR
    );

    public FeyPlanksBlock(ModX mod, Properties properties, Item.Properties itemProperties) {
        super(mod, DECORATION, properties, itemProperties);
    }
}
