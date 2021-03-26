package com.feywild.feywild.entity;

import com.feywild.feywild.network.FeywildPacketHandler;
import com.feywild.feywild.network.ParticleMessage;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.Random;

public class FeyEntity extends CreatureEntity {

    private Random random = new Random();
    private PlayerEntity follow = null;
    /* THIS IS A TEMPLATE FOR THE FEY , ALL CODE THAT SHOULD BE SHARED BETWEEN THEM WILL BE PLACED HERE */

    protected FeyEntity(EntityType<? extends CreatureEntity> type, World worldIn) {

        super(type, worldIn);
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
    }


    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new LookAtGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.addGoal(4, new FeyMoveGoal(this, 8,0.009));
        this.goalSelector.addGoal(2, new LookRandomlyGoal(this));
    }

    @Override
    public boolean canBeLeashedTo(PlayerEntity player) {
        return false;
    }

    @Override
    protected boolean canBeRidden(Entity entityIn) {
        return false;
    }

    // on interact with cookie
    @Override
    public ActionResultType applyPlayerInteraction(PlayerEntity player, Vector3d vec, Hand hand) {
        if(!world.isRemote && player.getHeldItem(hand).isItemEqual(new ItemStack(Items.COOKIE))){
            follow = player;
            player.getHeldItem(hand).shrink(1);
            FeywildPacketHandler.sendToPlayersInRange(world,this.getPosition(), new ParticleMessage(this.getPosX(),this.getPosY(),this.getPosZ(),0,0,0,5),32);

        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    //Added a custom walk goal for the fey
    public class FeyMoveGoal extends Goal {
        private Vector3d targetPos;
        private CreatureEntity entity;
        private int range, counter;
        private double speed;
        public FeyMoveGoal(CreatureEntity entity, int range, double speed){
            this.entity = entity;
            this.range = range;
            this.speed = speed;
        }

        @Override
        public void startExecuting() {
            super.startExecuting();
            this.targetPos = entity.getPositionVec();
        }

        @Override
        public boolean shouldExecute() {
            return true;
        }

        // do movement
        @Override
        public void tick() {
            entity.setNoGravity(true);
            super.tick();

            if (!world.isRemote && follow == null) {
                if (entity.getPosition().withinDistance(targetPos, 3)) {
                    do {
                        targetPos = new Vector3d(entity.getPosX() - range + random.nextInt(range * 2), entity.getPosY() - range + random.nextInt(range * 2), entity.getPosZ() - range + random.nextInt(range * 2));
                    } while (!world.getBlockState(new BlockPos(targetPos.getX(), targetPos.getY(), targetPos.getZ())).isAir());
                } else if (!world.isRemote) {
                    entity.setMotion((targetPos.getX() - entity.getPosX()) * speed, (targetPos.getY() - entity.getPosY()) * speed, (targetPos.getZ() - entity.getPosZ()) * speed);
                    entity.lookAt(EntityAnchorArgument.Type.EYES, targetPos);
                }
            }else if (!world.isRemote && counter >= 0){
                followPlayer();
                counter--;
            }
        }

        // follow the player
        private void followPlayer() {
            targetPos = new Vector3d(follow.getPosX(), follow.getPosY() + 1, follow.getPosZ());
            if (counter == 0) {
                counter = 400;
                follow = null;
            }
            entity.setMotion((targetPos.getX() - entity.getPosX()) * speed * 10, (targetPos.getY() - entity.getPosY()) * speed * 10, (targetPos.getZ() - entity.getPosZ()) * speed * 10);
            entity.lookAt(EntityAnchorArgument.Type.EYES, targetPos);
        }

    }

}
