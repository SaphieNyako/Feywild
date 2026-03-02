package com.saphienyako.feywild.entity.goals.pixie_goals;

import com.saphienyako.feywild.entity.base.PixieBase;
import com.saphienyako.feywild.item.ModItems;
import com.saphienyako.feywild.network.FeywildNetwork;
import com.saphienyako.feywild.network.ParticleMessage;
import com.saphienyako.feywild.sound.ModSounds;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CarrotBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.network.PlayMessages;

import javax.annotation.Nullable;
import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class GatherCropsAbilityGoal extends Goal {

    protected final Level level;
    protected final PixieBase entity;

    protected ItemStack item;
    protected boolean foundViableCrop;

    private int ticksLeft = 0;

    public GatherCropsAbilityGoal(PixieBase entity, Level level) {
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
                    if (level.getBlockState(target).getBlock() instanceof CropBlock cropBlock && cropBlock.isMaxAge(level.getBlockState(target)) && level.random.nextFloat() < 0.16f) { //
                        this.foundViableCrop = true;
                        this.item = cropBlock.getCloneItemStack(level, target, level.getBlockState(target));
                        resetCrops(cropBlock,level, target, level.getBlockState(target));
                        FeywildNetwork.sendParticles(level, ParticleMessage.Type.CROPS_RESET, target);
                        return;
                    }
                }
            }
        }
    }

    public void resetCrops(CropBlock cropBlock, Level pLevel, BlockPos pPos, BlockState pState) {
        int i = cropBlock.getAge(pState);
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
        Player owning = this.entity.getOwningPlayer();
        if (owning instanceof ServerPlayer && this.entity.getAbilityActive()) {
            return this.level.random.nextFloat() < 0.01f;
        } else {
            return false;
        }
    }
}
