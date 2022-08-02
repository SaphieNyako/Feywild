package com.feywild.feywild.block;

import com.feywild.feywild.block.entity.MagicalBrazier;
import com.feywild.feywild.block.render.MagicalBrazierRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.moddingx.libx.base.tile.BlockBE;
import org.moddingx.libx.mod.ModX;
import org.moddingx.libx.registration.SetupContext;

import javax.annotation.Nonnull;

public class MagicalBrazierBlock extends BlockBE<MagicalBrazier> {

    public static final BooleanProperty BRAZIER_LIT = BooleanProperty.create("brazier_lit");
    public static final int FIRE_DAMAGE = 3;
    protected static final VoxelShape SHAPE = Block.box(0.0D, 7.0D, 0.0D, 16.0D, 14.0D, 16.0D);

    public MagicalBrazierBlock(ModX mod) {
        super(mod, MagicalBrazier.class, Properties.of(Material.METAL)
                .strength(0f)
                .lightLevel((brazier) -> brazier.getValue(BRAZIER_LIT) ? 10 : 0)
                .noOcclusion());

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(BRAZIER_LIT, false));
    }

    public static boolean isLit(BlockState state) {
        return state.hasProperty(BRAZIER_LIT) && state.getValue(BRAZIER_LIT);
    }

    public static void dowse(Level level, BlockPos pos, BlockState state) {
        level.playSound(null, pos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    @Override
    public void registerClient(SetupContext ctx) {
        ctx.enqueue(() -> BlockEntityRenderers.register(this.getBlockEntityType(), MagicalBrazierRenderer::new));
    }

    @Override
    @SuppressWarnings("deprecation")
    public void entityInside(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, Entity entity) {
        if (!entity.fireImmune() && state.getValue(BRAZIER_LIT) && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) entity)) {
            entity.hurt(DamageSource.IN_FIRE, (float) FIRE_DAMAGE);
        }
        super.entityInside(state, level, pos, entity);
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult hit) {

        if (player.getItemInHand(hand).isEmpty() && state.getValue(BRAZIER_LIT)) {
            if (!level.isClientSide) {
                state = state.setValue(BRAZIER_LIT, false);
                level.setBlock(pos, state, 2);
                dowse(level, pos, state);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.use(state, level, pos, player, hand, hit);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(BRAZIER_LIT);
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        return SHAPE;
    }

    @Override
    public void animateTick(BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull RandomSource random) {
        if (state.getValue(BRAZIER_LIT)) {
            if (random.nextInt(10) == 0) {
                level.playLocalSound((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 0.5F + random.nextFloat(), random.nextFloat() * 0.7F + 0.6F, false);
            }

            if (state.getValue(BRAZIER_LIT) && random.nextInt(5) == 0) {
                for (int i = 0; i < random.nextInt(1) + 1; ++i) {
                    level.addParticle(ParticleTypes.LAVA, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, random.nextFloat() / 2.0F, 5.0E-5D, random.nextFloat() / 2.0F);
                    level.addAlwaysVisibleParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, true, (double) pos.getX() + 0.5D + random.nextDouble() / 3.0D * (double) (random.nextBoolean() ? 1 : -1), (double) pos.getY() + random.nextDouble() + random.nextDouble() + 0.5D, (double) pos.getZ() + 0.5D + random.nextDouble() / 3.0D * (double) (random.nextBoolean() ? 1 : -1), 0.0D, 0.07D, 0.0D);
                }
            }

        }
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public RenderShape getRenderShape(@Nonnull BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }
}
