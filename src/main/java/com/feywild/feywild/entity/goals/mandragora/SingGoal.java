package com.feywild.feywild.entity.goals.mandragora;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.Mandragora;
import com.feywild.feywild.network.ParticleMessage;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FarmBlock;

public class SingGoal extends Goal {

    private static final TargetingConditions TARGETING = TargetingConditions.forNonCombat().range(4).ignoreLineOfSight();

    protected final Level level;
    protected final Mandragora entity;
    private int ticksLeft = 0;
    private Player targetPlayer;

    public SingGoal(Mandragora mandragoraEntity) {
        this.entity = mandragoraEntity;
        this.level = mandragoraEntity.level();
    }

    @Override
    public void tick() {
        if (this.ticksLeft > 0) {
            this.ticksLeft--;

            if (this.ticksLeft <= 0) {
                this.reset();
            } else if (this.ticksLeft == 20) {
                if (this.entity.isTamed()) {
                    this.growCropsBySinging(entity.blockPosition());
                } else {
                    if (this.targetPlayer == null || !this.targetPlayer.isAlive()) {
                        this.targetPlayer = this.findPlayer();
                        if (this.targetPlayer != null && this.targetPlayer.isAlive()) {
                            this.targetPlayer.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 5 * 60, 0));
                        }
                    }
                }

            } else if (this.ticksLeft == 100) {
                this.singing();
                this.entity.playSound(ModSoundEvents.mandragoraSinging.getSoundEvent(), 1, 1);
            }
        }
    }

    @Override
    public void start() {
        this.ticksLeft = 120;
        this.targetPlayer = null;
    }

    private void singing() {
        this.entity.setCasting(true);
    }

    private void growCropsBySinging(BlockPos pos) {
        for (int xd = -4; xd <= 4; xd++) {
            for (int zd = -4; zd <= 4; zd++) {
                for (int yd = 2; yd >= -2; yd--) {
                    BlockPos target = pos.offset(xd, yd, zd);
                    if (level.getBlockState(target).getBlock() instanceof CropBlock && level.random.nextFloat() < 0.16f) {
                        ((CropBlock) level.getBlockState(target).getBlock()).growCrops(level, target, level.getBlockState(target));
                        ((CropBlock) level.getBlockState(target).getBlock()).growCrops(level, target, level.getBlockState(target));
                        FeywildMod.getNetwork().sendParticles(level, ParticleMessage.Type.CROPS_GROW, target);
                    }
                }
            }
        }
    }

    private Player findPlayer() {
        double distance = Double.MAX_VALUE;
        Player current = null;
        for (Player player : this.entity.level().getNearbyEntities(Player.class, TARGETING, this.entity, this.entity.getBoundingBox().inflate(8))) {
            if (this.entity.distanceToSqr(player) < distance) {
                current = player;
                distance = this.entity.distanceToSqr(player);
            }
        }
        return current;
    }

    @Override
    public boolean canUse() {
        if (this.entity.isTamed()) {
            return level.random.nextFloat() < 0.01f && level.getBlockState(entity.blockPosition()).getBlock() instanceof FarmBlock;
        } else {
            return level.random.nextFloat() < 0.01f;
        }
    }

    private void reset() {
        this.entity.setCasting(false);
        this.targetPlayer = null;
        this.ticksLeft = -1;
    }

    @Override
    public boolean canContinueToUse() {
        return this.ticksLeft > 0;
    }
}
