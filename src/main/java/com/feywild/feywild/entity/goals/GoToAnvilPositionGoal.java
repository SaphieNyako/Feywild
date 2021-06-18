package com.feywild.feywild.entity.goals;

import com.feywild.feywild.block.entity.DwarvenAnvilEntity;
import com.feywild.feywild.entity.DwarfBlacksmithEntity;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import java.util.function.Supplier;

public class GoToAnvilPositionGoal extends MovementRestrictionGoal {

    protected int count = 0;
    DwarfBlacksmithEntity entity;
    Supplier<Boolean> shouldReturn;
    int maxMovementRange;
    private DwarvenAnvilEntity tile;

    public GoToAnvilPositionGoal(DwarfBlacksmithEntity entity, Supplier<BlockPos> pos, int maxMovementRange) {
        super(pos, maxMovementRange);
        this.entity = entity;
        this.shouldReturn = () -> true;
        this.maxMovementRange = maxMovementRange;

        TileEntity tileEntity = entity.level.getBlockEntity(entity.summonPos);
        if (tileEntity instanceof DwarvenAnvilEntity) {
            tile = (DwarvenAnvilEntity) tileEntity;
        }
    }

    @Override
    public void tick() {
        if (tile == null) {
            return;
        }
        count--;

        if (count == 0) {
            reset();
        } else if (count == 20 && tile != null && summoningPosition != null && tile.getCanCraft()) {
            entity.playSound(SoundEvents.ANVIL_USE, 1.0f, 1.0f);

        } else if (count == 50 && tile != null && summoningPosition != null && tile.getCanCraft()) {

            tile.updateInventory(-1, true);
            entity.setState(2);

        } else if (count <= 110 && summoningPosition != null && tile.getCanCraft()) {

            entity.getNavigation().moveTo(this.summoningPosition.getX(), this.summoningPosition.getY(), this.summoningPosition.getZ(), 0.5);
            entity.lookAt(EntityAnchorArgument.Type.EYES, new Vector3d(summoningPosition.getX(), summoningPosition.getY(), summoningPosition.getZ()));
        }

    }

    @Override
    public void start() {

        count = 120;
    }

    protected void reset() {
        entity.setState(0);
        tile.setCanCraft(false);
    }

    @Override
    public boolean canContinueToUse() {

        //Check for Correct Recipe Available
        //  tile.checkForViableRecipe();
        return summoningPosition != null && tile.getCanCraft();
    }

    @Override
    public boolean canUse() {
        if (tile == null) {
            return false;
        }

        tile.checkForViableRecipe();
        return entity.level.random.nextFloat() < 0.05f && summoningPosition != null && tile.getCanCraft();

    }
}

