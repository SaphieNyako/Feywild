package com.feywild.feywild.entity;

import com.feywild.feywild.network.FeywildPacketHandler;
import com.feywild.feywild.network.ParticleMessage;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class FeyEntity extends CreatureEntity {

    private Random random = new Random();
    private PlayerEntity follow = null;
    /* THIS IS A TEMPLATE FOR THE FEY , ALL CODE THAT SHOULD BE SHARED BETWEEN THEM WILL BE PLACED HERE */

    protected FeyEntity(EntityType<? extends CreatureEntity> type, World worldIn) {

        super(type, worldIn);
    }

    @Override
    protected int calculateFallDamage(float distance, float damageMultiplier) {
        return 0;
    }


    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new LookAtGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.addGoal(6, new FeyMoveGoal(this, 6,0.01));
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
            this.follow = player;
            heal(4f);
            player.getHeldItem(hand).shrink(1);
            FeywildPacketHandler.sendToPlayersInRange(world,this.getPosition(), new ParticleMessage(this.getPosX(),this.getPosY(),this.getPosZ(),0,0,0,5),32);
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public boolean canBePushed() {
        return false;
    }


    //Attributes (could be moved in FeyEntities)
    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {

        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.FLYING_SPEED, Attributes.FLYING_SPEED.getDefaultValue())
                .createMutableAttribute(Attributes.MAX_HEALTH, 12.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.35D)
                .createMutableAttribute(Attributes.LUCK, 0.2D);
    }


    @Override
    public boolean preventDespawn() {
        return true;
    }


    /* SOUND EFFECTS */
    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return ModSoundEvents.PIXIE_HURT.get();
    }


    //Ancient's note : we can keep them but adjust the pitch
    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        //Implement other Sound
        return ModSoundEvents.PIXIE_DEATH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        //Implement other Sound

        return random.nextBoolean() ? ModSoundEvents.PIXIE_AMBIENT.get() : null;
    }

    @Override
    protected float getSoundPitch() {
        return 1.0f;
    }

    @Override
    protected float getSoundVolume ()
    {
        return 0.6F;
    }


    /* CLASS */
    //Added a custom walk goal for the fey
    public class FeyMoveGoal extends Goal {
        private Vector3d targetPos;
        private FeyEntity entity;
        private int range, followPlayer, counter;
        private double speed;
        public FeyMoveGoal(FeyEntity entity, int range, double speed){
            this.entity = entity;
            this.range = range;
            this.speed = speed;
        }

        @Override
        public void startExecuting() {
            super.startExecuting();
            this.targetPos = this.entity.getPositionVec();
        }

        @Override
        public boolean shouldExecute() {
            return true;
        }

        // do movement
        @Override
        public void tick() {
            this.entity.setNoGravity(true);
            super.tick();

            if (!world.isRemote && follow == null) {
                if (this.entity.getPosition().withinDistance(this.targetPos, 1)) {
                    counter = 0;
                    do {
                        this.targetPos = new Vector3d(entity.getPosX() - range + random.nextInt(range * 2), entity.getPosY() - range + random.nextInt(range * 2), entity.getPosZ() - range + random.nextInt(range * 2));
                    } while (!world.getBlockState(new BlockPos(this.targetPos.getX(), this.targetPos.getY(), this.targetPos.getZ())).isAir());
                } else {
                    if(counter > 100){
                        this.targetPos =this.entity.getPositionVec();
                    }
                    counter++;
                    this.entity.setMotion((this.targetPos.getX() - this.entity.getPosX()) * speed, (this.targetPos.getY() - this.entity.getPosY()) * speed, (this.targetPos.getZ() - this.entity.getPosZ()) * speed);
                    this.entity.lookAt(EntityAnchorArgument.Type.EYES, this.targetPos);
                }
            }else if (!world.isRemote && followPlayer >= 0){
                followPlayer();
                this.followPlayer--;
            }
        }

        // follow the player
        private void followPlayer() {
            this.targetPos = new Vector3d(follow.getPosX(), follow.getPosY() + 1, follow.getPosZ());
            if (this.followPlayer == 0) {
                this.followPlayer = 400;
                follow = null;
            }
            entity.setMotion((this.targetPos.getX() - entity.getPosX()) * speed * 10, (this.targetPos.getY() - entity.getPosY()) * speed * 10, (this.targetPos.getZ() - entity.getPosZ()) * speed * 10);
            entity.lookAt(EntityAnchorArgument.Type.EYES, this.targetPos);
        }

    }
}
