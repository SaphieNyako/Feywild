package com.feywild.feywild.entity.goals;

import com.feywild.feywild.entity.AutumnPixieEntity;
import com.feywild.feywild.network.FeywildPacketHandler;
import com.feywild.feywild.network.ParticleMessage;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarvedPumpkinBlock;
import net.minecraft.block.PumpkinBlock;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class PumpkinCarverGoal extends Goal {

    protected final World worldLevel;
    protected AutumnPixieEntity entity;
    protected BlockPos pumpkinPosition;
    protected BlockPos carvedPumpkinPosition;
    protected int count = 0;
    private Vector3d targetPos;
    private BlockState block;

    public PumpkinCarverGoal(AutumnPixieEntity entity) {
        this.entity = entity;
        this.worldLevel = entity.level;

    }

    //add to list
    //Loop over list

    @Override
    public void tick() {
        if (block == null) {
            return;
        }
        count--;

        if (count == 0 && block.getBlock() != Blocks.JACK_O_LANTERN.getBlock()) {
            pumpkinCarving();
            reset();

        } else if (count == 110 && block.getBlock() != Blocks.JACK_O_LANTERN.getBlock()) {
            spellCasting();
        }

    }

    @Override
    public void start() {

        count = 120;
        block = null;

        World world = entity.getCommandSenderWorld();
        for (BlockPos b : BlockPos.betweenClosed(entity.blockPosition().north(4).west(4).below(3), entity.blockPosition().south(4).east(4).above(3))) {
            BlockState state = world.getBlockState(b);
            if (state.getBlock() instanceof PumpkinBlock) {
                pumpkinPosition = b;
                block = state;

                return;
            }
            if (state.getBlock() instanceof CarvedPumpkinBlock) {
                carvedPumpkinPosition = b;
                block = state;
                return;
            }

        }

    }

    private void spellCasting() {

        if (block.getBlock() instanceof PumpkinBlock) {
            this.targetPos = new Vector3d(pumpkinPosition.getX(), pumpkinPosition.getY(), pumpkinPosition.getZ());

        }
        if (block.getBlock() instanceof CarvedPumpkinBlock) {
            this.targetPos = new Vector3d(carvedPumpkinPosition.getX(), carvedPumpkinPosition.getY(), carvedPumpkinPosition.getZ());
        }

        entity.getNavigation().moveTo(targetPos.x, targetPos.y, targetPos.z, 0.5);
        entity.lookAt(EntityAnchorArgument.Type.EYES, this.targetPos);
        entity.setCasting(true);
        entity.playSound(ModSoundEvents.PIXIE_SPELLCASTING.get(), 1, 1);
    }

    private void pumpkinCarving() {

        if (block.getBlock() instanceof PumpkinBlock) {
            entity.level.removeBlock(pumpkinPosition, false);
            entity.level.setBlock(pumpkinPosition, Blocks.CARVED_PUMPKIN.defaultBlockState(), 1);
        }
        if (block.getBlock() instanceof CarvedPumpkinBlock) {
            worldLevel.removeBlock(carvedPumpkinPosition, false);
            worldLevel.setBlock(carvedPumpkinPosition, Blocks.JACK_O_LANTERN.defaultBlockState(), 1);
            //add tag
        }

        FeywildPacketHandler.sendToPlayersInRange(worldLevel, entity.blockPosition()
                , new ParticleMessage(targetPos.x, targetPos.y + 1, targetPos.z, 0, 0, 0, 10, 0,0)
                , 64);

    }

    private void dropItems() {

        ItemEntity item = new ItemEntity(worldLevel, targetPos.x, targetPos.y, targetPos.z, new ItemStack(Items.PUMPKIN_SEEDS));
    }

    private void reset() {
        entity.setCasting(false);
        pumpkinPosition = null;
        carvedPumpkinPosition = null;
        targetPos = null;
    }

    @Override
    public boolean canContinueToUse() {
        return count > 0;
    }

    @Override
    public boolean canUse() {
        return entity.level.random.nextFloat() < 0.005f;
    }
}
