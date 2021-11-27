package com.feywild.feywild.entity.goals;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.Mandragora;
import com.feywild.feywild.network.ParticleSerializer;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FarmBlock;

public class SingGoal extends Goal {

    protected final Level level;
    protected Mandragora entity;
    private int ticksLeft = 0;

    public SingGoal(Mandragora mandragoraEntity) {
        this.entity = mandragoraEntity;
        this.level = mandragoraEntity.level;

    }

    //Random moment Sing
    // Animation Sing
    // Grow crops around Mandragora

    @Override
    public void tick() {
        if (this.ticksLeft > 0) {
            this.ticksLeft--;

            if (this.ticksLeft <= 0) {
                this.reset();
            } else if (this.ticksLeft == 20) {
                this.growCropsBySinging(entity.blockPosition());
            } else if (this.ticksLeft == 160) {
                this.singing();
                this.entity.playSound(ModSoundEvents.mandragoraSinging, 1, 1);
            }
        }
    }

    @Override
    public void start() {
        this.ticksLeft = 180;
    }

    private void singing() {
        this.entity.setCasting(true);
    }

    private void growCropsBySinging(BlockPos pos) {
        // some weird math

        for (int xd = -4; xd <= 4; xd++) {
            for (int zd = -4; zd <= 4; zd++) {
                for (int yd = 2; yd >= -2; yd--) {
                    BlockPos target = pos.offset(xd, yd, zd);

                    if (level.getBlockState(target).getBlock() instanceof CropBlock && level.random.nextFloat() < 0.08f) {

                        ((CropBlock) level.getBlockState(target).getBlock()).growCrops(level, target, level.getBlockState(target));
                        FeywildMod.getNetwork().sendParticles(level, ParticleSerializer.Type.CROPS_GROW, target);

                    }
                }
            }
        }
    }

    @Override
    public boolean canUse() {
        return level.random.nextFloat() < 0.003f && level.getBlockState(entity.blockPosition()).getBlock() instanceof FarmBlock; //if on farmblock
    }

    private void reset() {
        this.entity.setCasting(false);
        this.ticksLeft = -1;
    }

    @Override
    public boolean canContinueToUse() {
        return this.ticksLeft > 0;
    }
}
