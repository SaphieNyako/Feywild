package com.feywild.feywild.entity;

import com.feywild.feywild.network.FeywildPacketHandler;
import com.feywild.feywild.network.ParticleMessage;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class FeyEntity extends CreatureEntity  {

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
    public boolean causeFallDamage(float distance, float damageMultiplier) {
        return false;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new LookAtGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.addGoal(6, new FeyMoveGoal(this, 6,0.01));
    }

    @Override
    public boolean canBeLeashed(PlayerEntity player) {
        return false;
    }

    @Override
    protected boolean canRide(Entity entityIn) {
        return false;
    }

    // on interact with cookie
    @Override
    public ActionResultType interactAt(PlayerEntity player, Vector3d vec, Hand hand) {
        if(!level.isClientSide && player.getItemInHand(hand).sameItem(new ItemStack(Items.COOKIE)) && this.getLastDamageSource() == null){
            this.follow = player;
            heal(4f);
            player.getItemInHand(hand).shrink(1);
            FeywildPacketHandler.sendToPlayersInRange(level,this.blockPosition(), new ParticleMessage(this.getX(),this.getY(),this.getZ(),0,0,0,5,1),32);
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public boolean isPushable() {
        return false;
    }


    //Attributes
    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {

        return MobEntity.createMobAttributes().add(Attributes.FLYING_SPEED, Attributes.FLYING_SPEED.getDefaultValue())
                .add(Attributes.MAX_HEALTH, 12.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.35D)
                .add(Attributes.LUCK, 0.2D);
    }

    @Override
    public boolean requiresCustomPersistence() {
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
    protected float getVoicePitch() {
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
        public void start() {
            super.start();
            this.targetPos = this.entity.position();
        }

        @Override
        public boolean canUse() {
            return true;
        }



        // do movement
        @Override
        public void tick() {
            this.entity.setNoGravity(true);
            super.tick();

            if (!level.isClientSide && follow == null) {
                if (this.entity.blockPosition().closerThan(this.targetPos, 1)) {
                    //Find position to go to
                    counter = 0;
                    do {
                        this.targetPos = new Vector3d(entity.getX() - range + random.nextInt(range * 2), entity.getY() - range + random.nextInt(range * 2), entity.getZ() - range + random.nextInt(range * 2));
                    } while (!level.getBlockState(new BlockPos(this.targetPos.x(), this.targetPos.y(), this.targetPos.z())).isAir());
                } else {
                    // Reset desired position in case timer runs out
                    if(counter > 100){
                        this.targetPos =this.entity.position();

                    }
                    counter++;
                    //Damaged Check
                    if(this.entity.getLastDamageSource() != null){
                        this.entity.setDeltaMovement((this.targetPos.x() - this.entity.getX()) * speed * 10, (this.targetPos.y() - this.entity.getY()) * speed* 10, (this.targetPos.z() - this.entity.getZ()) * speed* 10);
                        this.entity.lookAt(EntityAnchorArgument.Type.EYES, this.targetPos);

                    }else{
                        // move to pos
                        this.entity.setDeltaMovement((this.targetPos.x() - this.entity.getX()) * speed, (this.targetPos.y() - this.entity.getY()) * speed, (this.targetPos.z() - this.entity.getZ()) * speed);
                        this.entity.lookAt(EntityAnchorArgument.Type.EYES, this.targetPos);
                    }
               }
            }else if (!level.isClientSide && followPlayer >= 0){
                followPlayer();
                this.followPlayer--;
            }
        }

        // follow the player
        private void followPlayer() {
            if(this.entity.getLastDamageSource() != null){
                followPlayer = 400;
                follow = null;
                return;
            }
            this.targetPos = new Vector3d(follow.getX(), follow.getY() + 1, follow.getZ());
            if (this.followPlayer == 0) {
                this.followPlayer = 400;
                follow = null;
            }
            entity.setDeltaMovement((this.targetPos.x() - entity.getX()) * speed * 10, (this.targetPos.y() - entity.getY()) * speed * 10, (this.targetPos.z() - entity.getZ()) * speed * 10);
            this.entity.lookAt(EntityAnchorArgument.Type.EYES, this.targetPos);
        }

    }
}
