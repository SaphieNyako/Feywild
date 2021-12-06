package com.feywild.feywild.block;

import io.github.noeppi_noeppi.libx.base.BlockBase;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.Random;
import java.util.function.Consumer;

public class FeyMushroomBlock extends BlockBase implements BonemealableBlock {

    protected static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D);
    protected static final IntegerProperty LIGHT_LEVEL = IntegerProperty.create("light_level_mushroom", 0, 15);

    public FeyMushroomBlock(ModX mod) {
        super(mod, BlockBehaviour.Properties.copy(Blocks.RED_MUSHROOM)
                .lightLevel((mushroom) -> mushroom.getValue(LIGHT_LEVEL)));

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(LIGHT_LEVEL, 7));
    }

    /*
    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }*/

    @Override
    @OnlyIn(Dist.CLIENT)
    public void registerClient(ResourceLocation id, Consumer<Runnable> defer) {
        defer.accept(() -> ItemBlockRenderTypes.setRenderLayer(this, RenderType.cutout()));
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(LIGHT_LEVEL);
    }

    @Override
    public boolean isValidBonemealTarget(@Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(@Nonnull Level level, @Nonnull Random random, @Nonnull BlockPos pos, @Nonnull BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(@Nonnull ServerLevel level, @Nonnull Random random, @Nonnull BlockPos pos, BlockState state) {
        if (state.getValue(LIGHT_LEVEL) >= 0 && state.getValue(LIGHT_LEVEL) < 15) {

            int light_level = state.getValue(LIGHT_LEVEL);
            state = state.setValue(LIGHT_LEVEL, light_level + 1);
            level.setBlock(pos, state, 2);

        } else if (state.getValue(LIGHT_LEVEL) == 15) {
            state = state.setValue(LIGHT_LEVEL, 0);
            level.setBlock(pos, state, 2);
        }
    }
}
