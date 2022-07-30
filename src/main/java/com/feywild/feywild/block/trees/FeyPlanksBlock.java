package com.feywild.feywild.block.trees;

import org.moddingx.libx.base.decoration.DecoratedBlock;
import org.moddingx.libx.base.decoration.DecorationContext;
import org.moddingx.libx.base.decoration.DecorationType;
import org.moddingx.libx.mod.ModX;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class FeyPlanksBlock extends DecoratedBlock {

    public static final DecorationContext DECORATION = new DecorationContext("fey_planks", DecorationType.BASE, DecorationType.FENCE, DecorationType.FENCE_GATE,
            DecorationType.SLAB, DecorationType.STAIRS, DecorationType.WOOD_BUTTON, DecorationType.WOOD_PRESSURE_PLATE, DecorationType.DOOR, DecorationType.TRAPDOOR);

    public FeyPlanksBlock(ModX mod, Properties properties, Item.Properties itemProperties) {
        super(mod, DECORATION, properties, itemProperties);
    }

    public SlabBlock getSlabBlock() {
        return get(DecorationType.SLAB);
    }

    public StairBlock getStairBlock() {
        return get(DecorationType.STAIRS);
    }

    public FenceBlock getFenceBlock() {
        return get(DecorationType.FENCE);
    }

    public FenceGateBlock getFenceGateBlock() {
        return get(DecorationType.FENCE_GATE);
    }

    public WoodButtonBlock getButtonBlock() {
        return get(DecorationType.WOOD_BUTTON);
    }

    public PressurePlateBlock getPressurePlateBlock() {
        return get(DecorationType.WOOD_PRESSURE_PLATE);
    }

    public DoorBlock getDoorBlock() {return get(DecorationType.DOOR);}

    public TrapDoorBlock getTrapDoorBlock() {return get(DecorationType.TRAPDOOR);}
}
