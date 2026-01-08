package com.saphienyako.feywild.block;

import com.saphienyako.feywild.item.ModItems;
import com.saphienyako.feywild.sound.ModSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class MandrakeCropBlock extends CropsBlock {

    private static final VoxelShape[] SHAPES = new VoxelShape[]{
            box(0, 0, 0, 16, 4, 16),
            box(0, 0, 0, 16, 6, 16),
            box(0, 0, 0, 16, 8, 16),
            box(0, 0, 0, 16, 10, 16),
            box(0, 0, 0, 16, 12, 16),
            box(0, 0, 0, 16, 14, 16),
            box(0, 0, 0, 16, 16, 16)
    };


    public MandrakeCropBlock(Properties pProperties) {
        super(pProperties);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state,@Nonnull IBlockReader reader,@Nonnull BlockPos pos,@Nonnull ISelectionContext context) {
        return SHAPES[state.getValue(this.getAgeProperty())];
    }

    @Nonnull
    @Override
    protected IItemProvider getBaseSeedId() {
        return ModItems.MANDRAKE_ROOT.get();
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public ActionResultType use(@Nonnull BlockState state, @Nonnull World level, @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand hand, @Nonnull BlockRayTraceResult hit) {
        level.playSound(player, pos, ModSounds.MANDRAKE_SCREAM.get(), SoundCategory.BLOCKS, 0.6f, 0.8f);
        return super.use(state, level, pos, player, hand, hit);
    }
}
