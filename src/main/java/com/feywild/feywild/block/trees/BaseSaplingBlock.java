package com.feywild.feywild.block.trees;

import com.google.common.collect.ImmutableSet;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.Registerable;
import net.minecraft.block.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nonnull;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;

public class BaseSaplingBlock extends BushBlock implements IGrowable, Registerable {

    public static final IntegerProperty STAGE = BlockStateProperties.STAGE;

    private final BaseTree tree;
    private final BlockItem item;

    public BaseSaplingBlock(ModX mod, BaseTree tree) {
        super(AbstractBlock.Properties.copy(Blocks.OAK_SAPLING));
        this.tree = tree;
        Item.Properties properties = mod.tab == null ? new Item.Properties() : new Item.Properties().tab(mod.tab);
        this.item = new BlockItem(this, properties);
    }

    @Override
    public Set<Object> getAdditionalRegisters() {
        return ImmutableSet.of(item);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void registerClient(ResourceLocation id, Consumer<Runnable> defer) {
        defer.accept(() -> RenderTypeLookup.setRenderLayer(this, RenderType.cutout()));
    }

    @Override
    public boolean isValidBonemealTarget(@Nonnull IBlockReader world, @Nonnull BlockPos pos, @Nonnull BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(World world, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull BlockState state) {
        return (double) world.random.nextFloat() < 0.5;
    }

    @Override
    public boolean isRandomlyTicking(@Nonnull BlockState state) {
        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(@Nonnull BlockState state, @Nonnull ServerWorld world, @Nonnull BlockPos pos, @Nonnull Random rand) {
        super.randomTick(state, world, pos, rand);
        if (world.isAreaLoaded(pos, 1)) {
            if (world.getMaxLocalRawBrightness(pos.above()) >= 9 && rand.nextInt(7) == 0) {
                this.performBonemeal(world, rand, pos, state);
            }
        }
    }

    @Override
    public void performBonemeal(@Nonnull ServerWorld world, @Nonnull Random random, @Nonnull BlockPos pos, BlockState state) {
        if (state.getValue(STAGE) == 0) {
            world.setBlock(pos, state.setValue(STAGE, 1), 4);
        } else {
            if (ForgeEventFactory.saplingGrowTree(world, random, pos)) {
                if (this.tree.growTree(world, world.getChunkSource().getGenerator(), pos, state, random)) {
                    for (int xd = -4; xd <= 4; xd++) {
                        for (int zd = -4; zd <= 4; zd++) {
                            // Try to find the block pos directly above ground
                            // to prevent floating pumpkins
                            for (int yd = 2; yd >= -2; yd--) {
                                BlockPos target = pos.offset(xd, yd, zd);
                                //noinspection deprecation
                                if (world.getBlockState(target).isAir() || world.getBlockState(target).getMaterial().isReplaceable()) {
                                    if (world.getBlockState(target.below()).isFaceSturdy(world, pos.below(), Direction.UP)) {
                                        this.tree.decorateSaplingGrowth(world, target, random);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(STAGE);
    }
}
