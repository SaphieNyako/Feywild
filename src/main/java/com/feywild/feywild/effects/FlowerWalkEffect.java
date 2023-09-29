package com.feywild.feywild.effects;

import com.feywild.feywild.particles.ModParticles;
import com.feywild.feywild.tag.ModBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.Objects;

public class FlowerWalkEffect extends MobEffect {
    
    protected FlowerWalkEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x67df8c);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyEffectTick(@Nonnull LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {
            onEntityMoved(player, player.level(), player.blockPosition(), amplifier);
            if (entity.level().isClientSide) {
                entity.level().addParticle(ModParticles.springLeafParticle, entity.getRandom().nextDouble() * 1.5 + entity.getX() - 1, entity.getRandom().nextDouble() * 2 + entity.getY() + 2, entity.getRandom().nextDouble() * 1.5 + entity.getZ() - 1, 0, -0.05, 0);
            }
        }
    }

    public static void onEntityMoved(LivingEntity living, Level level, BlockPos basePos, int amplifier) {
        if (living.onGround()) {
            int radius = Math.min(2, 2 + amplifier);
            BlockPos.MutableBlockPos mpos = new BlockPos.MutableBlockPos();
            for (BlockPos pos : BlockPos.betweenClosed(basePos.offset(-radius, 0, -radius), basePos.offset(radius, 0, radius))) {
                if (pos.closerToCenterThan(living.position(), radius)) {
                    mpos.set(pos.getX(), pos.getY(), pos.getZ());
                    BlockState current = level.getBlockState(mpos);
                    if (current.isAir()) {
                        current = level.getBlockState(pos.below());
                        BlockState flower = Objects.requireNonNull(ForgeRegistries.BLOCKS.tags()).getTag(ModBlockTags.FLOWER_WALK_FLOWERS).getRandomElement(level.random).map(Block::defaultBlockState).orElse(null);
                        if (flower != null && current.is(BlockTags.DIRT) && flower.canSurvive(level, pos) && level.isUnobstructed(flower, pos, CollisionContext.empty())) {
                            level.setBlockAndUpdate(pos, flower);
                        }
                    }
                }
            }
        }
    }
}
