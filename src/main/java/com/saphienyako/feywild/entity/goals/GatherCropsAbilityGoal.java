package com.saphienyako.feywild.entity.goals;

import com.saphienyako.feywild.entity.base.PixieBase;
import com.saphienyako.feywild.network.FeywildNetwork;
import com.saphienyako.feywild.network.ParticleMessage;
import com.saphienyako.feywild.sound.ModSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


import java.util.Objects;

public class GatherCropsAbilityGoal extends Goal {

    protected final World level;
    protected final PixieBase entity;

    protected ItemStack item;
    protected boolean foundViableCrop;

    private int ticksLeft = 0;

    public GatherCropsAbilityGoal(PixieBase entity, World level) {
        this.level = level;
        this.entity = entity;
    }

    @Override
    public void tick() {
        if (this.ticksLeft > 0) {
            this.ticksLeft--;

            if (this.ticksLeft <= 0) {
                this.reset();
            } else if (this.ticksLeft == 5) {
                if (foundViableCrop && this.item != null) {
                    if(this.item.getItem() == Items.WHEAT_SEEDS) {
                        this.entity.spawnAtLocation(Objects.requireNonNull(Items.WHEAT));
                    }
                    else if (this.item.getItem() == Items.BEETROOT_SEEDS) {
                        this.entity.spawnAtLocation(Objects.requireNonNull(Items.BEETROOT));
                    }
                    else {
                        this.entity.spawnAtLocation(Objects.requireNonNull(this.item));
                    }
                    this.entity.playSound((SoundEvents.COMPOSTER_EMPTY), 0.7f, 1);

                }

            } else if (this.ticksLeft == 20) {
                locateCrops(this.entity.blockPosition());
                if (foundViableCrop && this.item != null) {
                    this.spellCasting();
                }
            }
        }
    }

    private void locateCrops(BlockPos pos) {
        for (int xd = -8; xd <= 8; xd++) {
            for (int zd = -8; zd <= 8; zd++) {
                for (int yd = 8; yd >= -8; yd--) {
                    BlockPos target = pos.offset(xd, yd, zd);
                    if (level.getBlockState(target).getBlock() instanceof CropsBlock && ((CropsBlock)level.getBlockState(target).getBlock()).isMaxAge(level.getBlockState(target)) && level.random.nextFloat() < 0.16f) { //
                        this.foundViableCrop = true;
                        this.item = ((CropsBlock)level.getBlockState(target).getBlock()).getCloneItemStack(level, target, level.getBlockState(target));
                        resetCrops(((CropsBlock)level.getBlockState(target).getBlock()),level, target, level.getBlockState(target));
                        FeywildNetwork.sendParticles(level, ParticleMessage.Type.CROPS_RESET, target);
                        return;
                    }
                }
            }
        }
    }

    public void resetCrops(CropsBlock cropBlock, World pLevel, BlockPos pPos, BlockState pState) {
        int i = pState.getValue(cropBlock.getAgeProperty());
        if (i != 0) {

            pLevel.setBlock(pPos, cropBlock.getStateForAge(0), 2);
        }
    }

    private void spellCasting() {
        this.entity.setState(PixieBase.State.SPELL_CASTING);
        this.entity.playSound(ModSounds.PIXIE_SPELL_CASTING_SHORT.get(), 0.7f, 1);
    }

    protected void reset() {
        this.entity.setState(PixieBase.State.IDLE);
        this.ticksLeft = -1;
        this.foundViableCrop = false;
    }

    @Override
    public boolean canContinueToUse() {
        return this.ticksLeft > 0;
    }

    @Override
    public void start() {
        this.ticksLeft = 30;
        this.foundViableCrop = false;
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
