package com.feywild.feywild.block;

import com.feywild.feywild.item.ModItems;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.BlockBase;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class FeyMushroomBlock extends BlockBase {

    protected static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D);

    public FeyMushroomBlock(ModX mod) {
        super(mod, AbstractBlock.Properties.copy(Blocks.RED_MUSHROOM)
                .lightLevel((mushroom) -> mushroom.getValue(BlockStateProperties.LEVEL)));

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(BlockStateProperties.LEVEL, 7));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void registerClient(ResourceLocation id, Consumer<Runnable> defer) {
        defer.accept(() -> RenderTypeLookup.setRenderLayer(this, RenderType.cutout()));
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader reader, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(BlockStateProperties.LEVEL);
    }

    @Nonnull
    @Override
    public ActionResultType use(@Nonnull BlockState state, @Nonnull World level, @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand hand, @Nonnull BlockRayTraceResult hit) {
        if (player.getItemInHand(hand).getItem() == ModItems.feyDust || player.getItemInHand(hand).getItem() == Items.BONE_MEAL) {
            if (!level.isClientSide) {

                level.setBlock(pos, state.cycle(BlockStateProperties.LEVEL), 3);
                if (!player.isCreative()) player.getItemInHand(hand).shrink(1);
            }
            return ActionResultType.sidedSuccess(level.isClientSide);
        }
        return super.use(state, level, pos, player, hand, hit);
    }
}
