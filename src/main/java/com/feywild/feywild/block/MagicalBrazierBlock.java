package com.feywild.feywild.block;

import com.feywild.feywild.block.entity.MagicalBrazier;
import com.feywild.feywild.block.render.MagicalBrazierRenderer;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.BlockTE;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import javax.annotation.Nonnull;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.ToIntFunction;

public class MagicalBrazierBlock extends BlockTE<MagicalBrazier> implements IWaterLoggable {

    public static final BooleanProperty BRAZIER_LIT = BooleanProperty.create("brazier_lit");
    protected static final VoxelShape SHAPE = Block.box(0.0D, 7.0D, 0.0D, 16.0D, 14.0D, 16.0D);
    private final int FIRE_DAMAGE = 3;
    private boolean spawnParticles;

    public MagicalBrazierBlock(ModX mod) {
        super(mod, MagicalBrazier.class, AbstractBlock.Properties.of(Material.METAL)
                .strength(0f)
                .lightLevel(litBlockEmission())
                .noOcclusion());

        this.spawnParticles = false;
        this.registerDefaultState(getStateDefinition().any().setValue(BRAZIER_LIT, false));
    }

    public static boolean isLit(BlockState state) {
        return state.hasProperty(BRAZIER_LIT) && state.getValue(BRAZIER_LIT);
    }

    public static void dowse(IWorld world, BlockPos pos, BlockState state) {
        world.playSound(null, pos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }

    private static ToIntFunction<BlockState> litBlockEmission() {
        return (brazier) -> brazier.getValue(BRAZIER_LIT) ? 10 : 0;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void entityInside(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!entity.fireImmune() && state.getValue(BRAZIER_LIT) && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) entity)) {
            entity.hurt(DamageSource.IN_FIRE, (float) this.FIRE_DAMAGE);
        }
        super.entityInside(state, world, pos, entity);
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public ActionResultType use(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand hand, @Nonnull BlockRayTraceResult hit) {
        if (!world.isClientSide) {
            if (player.getItemInHand(hand).getItem() == Items.FLINT_AND_STEEL || player.getItemInHand(hand).getItem() == Items.FIRE_CHARGE) {
                state = state.setValue(BRAZIER_LIT, true); //immutable?
                world.setBlock(pos, state, 2);
                this.spawnParticles = true;
                return ActionResultType.SUCCESS;

            } else if (player.getItemInHand(hand).isEmpty() && state.getValue(BRAZIER_LIT)) {
                state = state.setValue(BRAZIER_LIT, false);
                world.setBlock(pos, state, 2);
                this.spawnParticles = false;
                dowse(world, pos, state);
                return ActionResultType.SUCCESS;
            }
        }
        return super.use(state, world, pos, player, hand, hit);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(BRAZIER_LIT);
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        return SHAPE;
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.getValue(BRAZIER_LIT)) {
            if (random.nextInt(10) == 0) {
                world.playLocalSound((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, SoundEvents.CAMPFIRE_CRACKLE, SoundCategory.BLOCKS, 0.5F + random.nextFloat(), random.nextFloat() * 0.7F + 0.6F, false);
            }

            if (this.spawnParticles && random.nextInt(5) == 0) {
                for (int i = 0; i < random.nextInt(1) + 1; ++i) {
                    world.addParticle(ParticleTypes.LAVA, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, (double) (random.nextFloat() / 2.0F), 5.0E-5D, (double) (random.nextFloat() / 2.0F));
                    world.addAlwaysVisibleParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, true, (double) pos.getX() + 0.5D + random.nextDouble() / 3.0D * (double) (random.nextBoolean() ? 1 : -1), (double) pos.getY() + random.nextDouble() + random.nextDouble() + 0.5D, (double) pos.getZ() + 0.5D + random.nextDouble() / 3.0D * (double) (random.nextBoolean() ? 1 : -1), 0.0D, 0.07D, 0.0D);
                }
            }

        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void registerClient(ResourceLocation id, Consumer<Runnable> defer) {
        defer.accept(() -> ClientRegistry.bindTileEntityRenderer(this.getTileType(), MagicalBrazierRenderer::new));
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public BlockRenderType getRenderShape(@Nonnull BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }
}
