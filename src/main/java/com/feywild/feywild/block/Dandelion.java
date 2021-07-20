package com.feywild.feywild.block;

import com.feywild.feywild.network.FeywildPacketHandler;
import com.feywild.feywild.network.ParticleMessage;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nonnull;
import java.util.Random;

public class Dandelion extends Block {

    public static final IntegerProperty VARIANT = IntegerProperty.create("variant", 0, 3);

    public Dandelion() {
        super(Properties.of(Material.PLANT).harvestTool(ToolType.AXE).sound(SoundType.BAMBOO).randomTicks().strength(1, 1).lightLevel(value -> 8));
        this.registerDefaultState(this.stateDefinition.any().setValue(VARIANT, 1));
    }

    @Override
    public void animateTick(BlockState p_180655_1_, World p_180655_2_, BlockPos p_180655_3_, Random p_180655_4_) {
        super.animateTick(p_180655_1_, p_180655_2_, p_180655_3_, p_180655_4_);
        if (p_180655_1_.getValue(VARIANT) == 2 && p_180655_4_.nextDouble() > 0.6) {

            double windStrength = Math.cos((double) p_180655_2_.getGameTime() / 2000) / 8;
            double windX = Math.cos((double) p_180655_2_.getGameTime() / 1200) * windStrength;
            double windZ = Math.sin((double) p_180655_2_.getGameTime() / 1000) * windStrength;

            p_180655_2_.addParticle(ParticleTypes.END_ROD, p_180655_3_.getX() + p_180655_4_.nextDouble(), p_180655_3_.getY() + p_180655_4_.nextDouble(), p_180655_3_.getZ() + p_180655_4_.nextDouble(), windX, 0, windZ);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(VARIANT);
    }

    @Override
    public boolean isRandomlyTicking(@Nonnull BlockState p_149653_1_) {
        return true;
    }

    @Override
    public void onRemove(@Nonnull BlockState p_196243_1_, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockState p_196243_4_, boolean p_196243_5_) {
        super.onRemove(p_196243_1_, world, pos, p_196243_4_, p_196243_5_);
        if (world.isClientSide) return;

        if (p_196243_1_.getValue(VARIANT) == 2) {
            FeywildPacketHandler.sendToPlayersInRange(world, pos, new ParticleMessage(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0.5, 0.7, 0.5, 40, -2, 0), 64);
            if (!world.getBlockState(pos.below()).isAir())
                world.setBlock(pos, ModBlocks.DANDELION.get().defaultBlockState().setValue(Dandelion.VARIANT, 3), 2, 1);
        }
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        return VoxelShapes.box(0.01, 0.01, 0.01, 0.99, 0.99, 0.99);
    }
}
