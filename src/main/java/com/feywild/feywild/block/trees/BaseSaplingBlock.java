package com.feywild.feywild.block.trees;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.event.ForgeEventFactory;
import org.moddingx.libx.mod.ModX;
import org.moddingx.libx.registration.Registerable;
import org.moddingx.libx.registration.RegistrationContext;
import org.moddingx.libx.registration.SetupContext;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;

public class BaseSaplingBlock extends BushBlock implements BonemealableBlock, Registerable {

    public static final IntegerProperty STAGE = BlockStateProperties.STAGE;

    private final BaseTree tree;
    private final BlockItem item;

    public BaseSaplingBlock(ModX mod, BaseTree tree) {
        super(BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING));
        this.tree = tree;
        Item.Properties properties = mod.tab == null ? new Item.Properties() : new Item.Properties().tab(mod.tab);
        this.item = new BlockItem(this, properties);
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    public void registerAdditional(RegistrationContext ctx, EntryCollector builder) {
        builder.register(Registry.ITEM_REGISTRY, this.item);
    }

    @Override
    public void registerCommon(SetupContext ctx) {
        ctx.enqueue(() -> ComposterBlock.add(0.4f, this));
    }

    @Override
    public boolean isValidBonemealTarget(@Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, @Nonnull RandomSource rand, @Nonnull BlockPos pos, @Nonnull BlockState state) {
        return (double) level.random.nextFloat() < 0.5;
    }

    @Override
    public boolean isRandomlyTicking(@Nonnull BlockState state) {
        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(@Nonnull BlockState state, @Nonnull ServerLevel level, @Nonnull BlockPos pos, @Nonnull RandomSource rand) {
        super.randomTick(state, level, pos, rand);
        if (level.isAreaLoaded(pos, 1)) {
            if (level.getMaxLocalRawBrightness(pos.above()) >= 9 && rand.nextInt(7) == 0) {
                this.performBonemeal(level, rand, pos, state);
            }
        }
    }

    @Override
    public void performBonemeal(@Nonnull ServerLevel level, @Nonnull RandomSource random, @Nonnull BlockPos pos, BlockState state) {


        if (state.getValue(STAGE) == 0) {
            level.setBlock(pos, state.setValue(STAGE, 1), 4);
        } else {
            //??
            if (ForgeEventFactory.saplingGrowTree(level, random, pos)) {

                //checks if tree can be grown.
                if (this.tree.growTree(level, level.getChunkSource().getGenerator(), pos, state, random)) {
                    //  loadTreeTemplate();

                    //decorations
                    for (int xd = -4; xd <= 4; xd++) {
                        for (int zd = -4; zd <= 4; zd++) {
                            // Try to find the block pos directly above ground
                            // to prevent floating pumpkins
                            for (int yd = 2; yd >= -2; yd--) {
                                BlockPos target = pos.offset(xd, yd, zd);
                                if (level.getBlockState(target).isAir() || level.getBlockState(target).getMaterial().isReplaceable()) {
                                    if (level.getBlockState(target.below()).isFaceSturdy(level, pos.below(), Direction.UP)) {
                                        this.tree.decorateSaplingGrowth(level, target, random);
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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STAGE);
    }
}
