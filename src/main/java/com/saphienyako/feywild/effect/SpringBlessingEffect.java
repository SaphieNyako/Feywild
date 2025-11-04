package com.saphienyako.feywild.effect;


import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;


public class SpringBlessingEffect extends MobEffect {

    //Reserved for Dryad Fawn
    
    protected SpringBlessingEffect() {
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
                        //TODO add Flower Tag
                      /*  BlockState flower = Objects.requireNonNull(ForgeRegistries.BLOCKS.tags()).getTag(ModBlockTags.FLOWER_WALK_FLOWERS).getRandomElement(level.random).map(Block::defaultBlockState).orElse(null);
                        if (flower != null && current.is(BlockTags.DIRT) && flower.canSurvive(level, pos) && level.isUnobstructed(flower, pos, CollisionContext.empty())) {
                            level.setBlockAndUpdate(pos, flower);
                        }*/
                    }
                }
            }
        }
    }
}
