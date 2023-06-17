package com.feywild.feywild.block.trees;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.moddingx.libx.base.decoration.DecoratedBlock;
import org.moddingx.libx.base.decoration.DecorationContext;
import org.moddingx.libx.base.decoration.DecorationType;
import org.moddingx.libx.mod.ModX;

import javax.annotation.Nullable;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class FeyWoodBlock extends DecoratedBlock {

    public static final DecorationContext DECORATION = new DecorationContext("fey_wood", DecorationContext.BaseMaterial.WOOD, DecorationType.BASE, DecorationType.WALL, DecorationType.SLAB, DecorationType.STAIRS);

    private final FeyStrippedWoodBlock feyStrippedWood;

    public FeyWoodBlock(ModX mod, FeyStrippedWoodBlock feyStrippedWood, Properties properties, Item.Properties itemProperties) {
        super(mod, DECORATION, properties, itemProperties);
        this.feyStrippedWood = feyStrippedWood;
    }

    @Nullable
    @Override
    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        return toolAction == ToolActions.AXE_STRIP ? feyStrippedWood.defaultBlockState() : super.getToolModifiedState(state, context, toolAction, simulate);
    }
}
