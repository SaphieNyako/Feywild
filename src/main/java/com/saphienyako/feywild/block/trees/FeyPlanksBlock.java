package com.saphienyako.feywild.block.trees;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class FeyPlanksBlock extends Block {
    public FeyPlanksBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFlammable(@Nonnull BlockState state,@Nonnull BlockGetter level,@Nonnull BlockPos pos,@Nonnull Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(@Nonnull BlockState state,@Nonnull BlockGetter level,@Nonnull BlockPos pos,@Nonnull Direction direction) {
        return 5;
    }

    @Override
    public int getFireSpreadSpeed(@Nonnull BlockState state,@Nonnull BlockGetter level,@Nonnull BlockPos pos,@Nonnull Direction direction) {
        return 5;
    }

}
