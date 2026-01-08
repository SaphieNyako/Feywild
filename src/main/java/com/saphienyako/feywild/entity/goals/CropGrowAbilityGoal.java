package com.saphienyako.feywild.entity.goals;

import com.saphienyako.feywild.entity.base.PixieBase;
import com.saphienyako.feywild.network.FeywildNetwork;
import com.saphienyako.feywild.network.ParticleMessage;
import com.saphienyako.feywild.sound.ModSounds;
import net.minecraft.block.CropsBlock;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class CropGrowAbilityGoal extends Goal {

    protected final World  level;
    protected final PixieBase entity;
    protected boolean foundViableCrop;
    private int ticksLeft = 0;

    public CropGrowAbilityGoal(PixieBase entity, World level) {
        this.level = level;
        this.entity = entity;
    }

    @Override
    public void tick() {
        if (this.ticksLeft > 0) {
            this.ticksLeft--;

            if (this.ticksLeft <= 0) {
                this.reset();
            }
            else if (this.ticksLeft == 20){
                this.growCrops(entity.blockPosition());
                if(foundViableCrop){
                    spellCasting();
                }

            }
        }
    }

    @Override
    public void start() {
        this.ticksLeft = 30;
        this.foundViableCrop = false;
    }

    private void reset() {
        this.entity.setState(PixieBase.State.IDLE);
        this.ticksLeft = -1;
        this.foundViableCrop = false;
    }

    private void spellCasting() {
        this.entity.setState(PixieBase.State.SPELL_CASTING);
        this.entity.playSound(ModSounds.PIXIE_SPELL_CASTING_SHORT.get(), 0.7f, 1);
    }

    private void growCrops(BlockPos pos) {
        for (int xd = -8; xd <= 8; xd++) {
            for (int zd = -8; zd <= 8; zd++) {
                for (int yd = 8; yd >= -8; yd--) {
                    BlockPos target = pos.offset(xd, yd, zd);
                    if (level.getBlockState(target).getBlock() instanceof CropsBlock && level.random.nextFloat() < 0.16f) {
                        this.foundViableCrop = true;
                        ((CropsBlock) level.getBlockState(target).getBlock()).growCrops(level, target, level.getBlockState(target));
                        FeywildNetwork.sendParticles(level, ParticleMessage.Type.CROPS_GROW, target);
                    }
                }
            }
        }
    }

    @Override
    public boolean canContinueToUse() {
        return this.ticksLeft > 0;
    }

    @Override
    public boolean canUse() {
        PlayerEntity owning = this.entity.getOwningPlayer();
        if (owning instanceof ServerPlayerEntity && this.entity.getAbilityActive()) {
            return this.level.random.nextFloat() < 0.01f;
        } else {
            return false;
        }
    }

}
