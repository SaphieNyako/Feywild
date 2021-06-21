package com.feywild.feywild.entity.util;

import com.feywild.feywild.network.FeywildPacketHandler;
import com.feywild.feywild.network.ParticleMessage;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public abstract class FeyEntity extends CreatureEntity {

    public BlockPos summonPos;
    private Random random = new Random();
    private PlayerEntity follow = null;

    protected FeyEntity(EntityType<? extends CreatureEntity> type, World worldIn) {
        super(type, worldIn);
    }

    /* ATTRIBUTES */
    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {

        return MobEntity.createMobAttributes().add(Attributes.FLYING_SPEED, Attributes.FLYING_SPEED.getDefaultValue())
                .add(Attributes.MAX_HEALTH, 12.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.35D)
                .add(Attributes.LUCK, 0.2D);
    }

    @Override
    protected PathNavigator createNavigation(World world) {
        FlyingPathNavigator flyingpathnavigator = new FlyingPathNavigator(this, world);
        flyingpathnavigator.setCanOpenDoors(false);
        flyingpathnavigator.setCanFloat(true);
        flyingpathnavigator.setCanPassDoors(true);
        return flyingpathnavigator;
    }

    public void travel(Vector3d positionIn) {
        if (this.isInWater()) {
            this.moveRelative(0.02F, positionIn);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.8F));
        } else if (this.isInLava()) {
            this.moveRelative(0.02F, positionIn);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.5D));
        } else {
            BlockPos ground = new BlockPos(this.getX(), this.getY() - 1.0D, this.getZ());
            float f = 0.91F;
            if (this.onGround) {
                f = this.level.getBlockState(ground).getSlipperiness(this.level, ground, this) * 0.91F;
            }

            float f1 = 0.16277137F / (f * f * f);
            f = 0.91F;
            if (this.onGround) {
                f = this.level.getBlockState(ground).getSlipperiness(this.level, ground, this) * 0.91F;
            }

            this.moveRelative(this.onGround ? 0.1F * f1 : 0.02F, positionIn);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(f));
        }

        this.animationSpeedOld = this.animationSpeed;
        double d1 = this.getX() - this.xo;
        double d0 = this.getZ() - this.zo;
        float f2 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;
        if (f2 > 1.0F) {
            f2 = 1.0F;
        }

        this.animationSpeed += (f2 - this.animationSpeed) * 0.4F;
        this.animationPosition += this.animationSpeed;
    }

    public boolean onClimbable() {
        return false;
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
    protected int getExperienceReward(PlayerEntity player) {
        return 0;
    }

    @Override
    public boolean canBeLeashed(PlayerEntity player) {
        return false;
    }

    @Override
    protected boolean canRide(Entity entityIn) {
        return false;
    }

    public PlayerEntity getFollow() {
        return follow;
    }

    // on interact with cookie
    @Override
    public ActionResultType interactAt(PlayerEntity player, Vector3d vec, Hand hand) {
        if (!level.isClientSide && player.getItemInHand(hand).sameItem(new ItemStack(Items.COOKIE)) && this.getLastDamageSource() == null) {
            //  this.follow = player;

            heal(4f);
            player.getItemInHand(hand).shrink(1);
            FeywildPacketHandler.sendToPlayersInRange(level, this.blockPosition(), new ParticleMessage(this.getX(), this.getY(), this.getZ(), 0, 0, 0, 5, 1,0), 32);
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public boolean isPushable() {
        return false;
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
    protected float getSoundVolume() {
        return 0.6F;
    }


    /* MOVEMENT - NOT USED */

/*
    public class FeyMoveGoal extends Goal {
        private Vector3d targetPos;
        private FeyEntity entity;
        private int range, followPlayer, counter;
        private double speed;
        private int timeMoving;


        public FeyMoveGoal(FeyEntity entity, int range, double speed){
            this.entity = entity;
            this.range = range;
            this.speed = speed;
        }

        //at start sets timeMoving to 100
        @Override
        public void start() {

            super.start();
            this.targetPos = this.entity.position();
            this.timeMoving = 180;
        }

        //Can continue to run when timeMoving is higher then 0;
        @Override
        public boolean canContinueToUse() {
            return this.timeMoving > 0;
        }


        //change for command to run, can be used on random check
        @Override
        public boolean canUse() {
           return entity.getCommandSenderWorld().random.nextInt(200) <= 1;
        }



        // do movement
        @Override
        public void tick() {
            this.entity.setNoGravity(true);
            //super.tick();

            this.timeMoving--;

            if (!level.isClientSide) {
                if (this.entity.blockPosition().closerThan(this.targetPos, 1)) { //check if you reached location

                    counter = 0;

                    do {
                        this.targetPos = new Vector3d(entity.getX() - range + random.nextInt(range * 2), entity.getY() - range + random.nextInt(range * 2), entity.getZ() - range + random.nextInt(range * 2));

                    } while (!level.getBlockState(new BlockPos(this.targetPos.x(), this.targetPos.y(), this.targetPos.z())).isAir()); //if air go to location, otherwise repeat(do)

                } else

                    // Reset desired position in case timer runs out
                    if(counter > 100) { this.targetPos = this.entity.position(); }

                    counter++;

                    //Damaged Check
                    if(this.entity.getLastDamageSource() != null){

                        this.entity.setDeltaMovement((this.targetPos.x() - this.entity.getX()) * speed * 10, (this.targetPos.y() - this.entity.getY()) * speed* 10, (this.targetPos.z() - this.entity.getZ()) * speed* 10);
                        this.entity.lookAt(EntityAnchorArgument.Type.EYES, this.targetPos);

                    }

                    else{  //if not damaged then move normally
                        // move to pos
                        this.entity.setDeltaMovement((this.targetPos.x() - this.entity.getX()) * speed, (this.targetPos.y() - this.entity.getY()) * speed, (this.targetPos.z() - this.entity.getZ()) * speed);
                        this.entity.lookAt(EntityAnchorArgument.Type.EYES, this.targetPos);
                    }
               }


            else if (!level.isClientSide && followPlayer >= 0){
                followPlayer();
                this.followPlayer--;
            } */
}

// follow the player
        /*
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
        } */



