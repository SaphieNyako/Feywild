package com.feywild.feywild.effects;

import com.feywild.feywild.particles.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;

import javax.annotation.Nonnull;
import java.util.Random;

public class FlowerWalkEffect extends MobEffect {


    protected FlowerWalkEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x67df8c);
    }

    public static void onEntityMoved(LivingEntity living, Level level, BlockPos pos, int levelConflicting) {
        if (living.isOnGround()) {
            BlockState blockstate = getDecorationBlock();
            float f = (float) Math.min(1, 2 + levelConflicting);
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

            for (BlockPos blockpos : BlockPos.betweenClosed(pos.offset((double) (-f), -1.0D, (double) (-f)), pos.offset((double) f, -1.0D, (double) f))) {
                if (blockpos.closerToCenterThan(living.position(), (double) f)) {
                    blockpos$mutableblockpos.set(blockpos.getX(), blockpos.getY() + 1, blockpos.getZ());
                    BlockState blockstate1 = level.getBlockState(blockpos$mutableblockpos);
                    if (blockstate1.isAir()) {
                        BlockState blockstate2 = level.getBlockState(blockpos.below());
                        if (blockstate2.getMaterial() == Material.DIRT
                                && blockstate.canSurvive(level, blockpos.above())
                                && level.isUnobstructed(blockstate, blockpos, CollisionContext.empty())
                                && !net.minecraftforge.event.ForgeEventFactory.onBlockPlace(living, net.minecraftforge.common.util.BlockSnapshot.create(level.dimension(), level, blockpos), net.minecraft.core.Direction.UP)) {
                            level.setBlockAndUpdate(blockpos.above(), blockstate);
                        }
                    }
                }
            }
        }
    }

    private static BlockState getDecorationBlock() {
        Random random = new Random();
        return switch (random.nextInt(20)) {
            case 0 -> Blocks.RED_TULIP.defaultBlockState();
            case 1 -> Blocks.DANDELION.defaultBlockState();
            case 2 -> Blocks.ORANGE_TULIP.defaultBlockState();
            case 3 -> Blocks.BLUE_ORCHID.defaultBlockState();
            case 4 -> Blocks.ALLIUM.defaultBlockState();
            case 5 -> Blocks.AZURE_BLUET.defaultBlockState();
            case 6 -> Blocks.WHITE_TULIP.defaultBlockState();
            case 7 -> Blocks.LILY_OF_THE_VALLEY.defaultBlockState();
            case 8 -> Blocks.GRASS.defaultBlockState();
            default -> Blocks.AIR.defaultBlockState();
        };
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyEffectTick(@Nonnull LivingEntity entity, int amplifier) {
        Level level = entity.level;
        Random random = new Random();
        if (entity instanceof Player player) {

            onEntityMoved(player, player.level, player.blockPosition(), 2);

            if (level.isClientSide) {
                level.addParticle(ModParticles.springLeafParticle, entity.getRandom().nextDouble() * 1.5 + entity.getX() - 1, entity.getRandom().nextDouble() * 2 + entity.getY() + 2, entity.getRandom().nextDouble() * 1.5 + entity.getZ() - 1, 0, -0.05, 0);
            }
        }
    }
}
