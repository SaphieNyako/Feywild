package com.feywild.feywild.entity.goals;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.MandragoraEntity;
import com.feywild.feywild.network.ParticleSerializer;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SingGoal extends Goal {

    protected final World world;
    protected MandragoraEntity entity;
    private int ticksLeft = 0;

    public SingGoal(MandragoraEntity mandragoraEntity) {
        this.entity = mandragoraEntity;
        this.world = mandragoraEntity.level;

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

                    if (world.getBlockState(target).getBlock() instanceof CropsBlock && world.random.nextFloat() < 0.08f) {

                        ((CropsBlock) world.getBlockState(target).getBlock()).growCrops(world, target, world.getBlockState(target));
                        FeywildMod.getNetwork().sendParticles(world, ParticleSerializer.Type.CROPS_GROW, target);

                    }
                }
            }
        }
    }

    @Override
    public boolean canUse() {
        return world.random.nextFloat() < 0.003f && world.getBlockState(entity.blockPosition()).getBlock() instanceof FarmlandBlock; //if on farmblock
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
