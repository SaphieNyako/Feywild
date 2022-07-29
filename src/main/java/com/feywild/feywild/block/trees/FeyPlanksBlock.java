package com.feywild.feywild.block.trees;

import io.github.noeppi_noeppi.libx.base.decoration.DecoratedBlock;
import io.github.noeppi_noeppi.libx.base.decoration.DecorationContext;
import io.github.noeppi_noeppi.libx.base.decoration.DecorationType;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;

public class FeyPlanksBlock extends DecoratedBlock {

    public static final DecorationContext DECORATION = new DecorationContext("fey_planks", DecorationType.BASE, DecorationType.FENCE, DecorationType.FENCE_GATE,
            DecorationType.SLAB, DecorationType.STAIR, DecorationType.WOOD_BUTTON, DecorationType.WOOD_PRESSURE_PLATE, DecorationType.DOOR, DecorationType.TRAPDOOR);

    public FeyPlanksBlock(ModX mod, Properties properties, Item.Properties itemProperties) {
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

    public WoodButtonBlock getButtonBlock() {
        return get(DecorationType.WOOD_BUTTON);
    }

    public PressurePlateBlock getPressurePlateBlock() {
        return get(DecorationType.WOOD_PRESSURE_PLATE);
    }

    public DoorBlock getDoorBlock() {return get(DecorationType.DOOR);}

    public TrapDoorBlock getTrapDoorBlock() {return get(DecorationType.TRAPDOOR);}
}
