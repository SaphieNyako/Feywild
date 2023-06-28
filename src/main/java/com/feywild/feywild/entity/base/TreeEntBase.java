package com.feywild.feywild.entity.base;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.trees.BaseTree;
import com.feywild.feywild.entity.goals.TameCheckingGoal;
import com.feywild.feywild.entity.goals.tree_ent.TreeEntMeleeAttackGoal;
import com.feywild.feywild.entity.goals.tree_ent.TreeEntMoveAndSoundGoal;
import com.feywild.feywild.entity.goals.tree_ent.TreeEntResetTargetGoal;
import com.feywild.feywild.network.ParticleMessage;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.tag.ModItemTags;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class TreeEntBase extends GroundFeyBase {
    
    public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(TreeEntBase.class, EntityDataSerializers.INT);
    
    protected TreeEntBase(EntityType<? extends TreeEntBase> entityType, @Nullable Alignment alignment, Level level) {
        super(entityType, alignment, level);
    }

    public static AttributeSupplier.Builder getDefaultAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, Attributes.MOVEMENT_SPEED.getDefaultValue())
                .add(Attributes.MAX_HEALTH, 120)
                .add(Attributes.KNOCKBACK_RESISTANCE, 2)
                .add(Attributes.ARMOR_TOUGHNESS, 5)
                .add(Attributes.ARMOR, 15)
                .add(Attributes.ATTACK_DAMAGE, 15)
                .add(Attributes.ATTACK_KNOCKBACK, 2)
                .add(Attributes.MOVEMENT_SPEED, 0.35);
    }

    protected abstract BaseTree getTree();

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new TreeEntMeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(50, new TreeEntMoveAndSoundGoal(this, 0.5D));
        this.targetSelector.addGoal(2, new TameCheckingGoal(this, false, new NearestAttackableTargetGoal<>(this, Player.class, true)));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Monster.class, false));
        this.targetSelector.addGoal(3, new TreeEntResetTargetGoal<>(this));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(STATE, 0);
    }

    public void stopBeingAngry() {
        this.setLastHurtByMob(null);
        this.setTarget(null);
    }

    public TreeEntBase.State getState() {
        TreeEntBase.State[] states = TreeEntBase.State.values();
        return states[Mth.clamp(this.entityData.get(STATE), 0, states.length - 1)];
    }

    public void setState(TreeEntBase.State state) {
        this.entityData.set(STATE, state.ordinal());
    }

    @Nonnull
    @Override
    public InteractionResult interactAt(@Nonnull Player player, @Nonnull Vec3 hitVec, @Nonnull InteractionHand hand) {
        InteractionResult superResult = super.interactAt(player, hitVec, hand);
        ItemStack stack = player.getItemInHand(hand);
        if (superResult == InteractionResult.PASS) {
            BaseTree tree = this.getTree();
            Block holdingBlock = Block.byItem(stack.getItem());
            boolean isLog = holdingBlock == tree.getLogBlock() || holdingBlock == tree.getWoodBlock() || holdingBlock == tree.getStrippedLogBlock() || holdingBlock == tree.getStrippedWoodBlock();
            boolean isAttacked = this.getLastHurtByMob() != null && this.getLastHurtByMob().isAlive();
            if ((isLog || stack.is(ModItemTags.COOKIES)) && !isAttacked) {
                this.heal(4);
                if (!player.isCreative()) player.getItemInHand(hand).shrink(1);
                FeywildMod.getNetwork().sendParticles(this.level(), ParticleMessage.Type.FEY_HEART, this.getX(), this.getY() + 4, this.getZ());
                player.swing(hand, true);
            } else if (stack.getItem() == Items.BONE_MEAL && !isAttacked) {
                if (!player.isCreative()) player.getItemInHand(hand).shrink(1);
                FeywildMod.getNetwork().sendParticles(this.level(), ParticleMessage.Type.CROPS_GROW, this.getX(), this.getY() + 4, this.getZ());
                player.swing(hand, true);
                
                if (random.nextInt(3) == 0) this.spawnAtLocation(new ItemStack(Blocks.MYCELIUM));
                this.spawnAtLocation(this.getTree().getSapling());
                this.playSound(SoundEvents.COMPOSTER_READY, 1, 0.6f);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            return superResult;
        }
    }
    
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, event -> {
            if (this.getState() == TreeEntBase.State.ATTACKING) {
                event.getController().setAnimation(RawAnimation.begin().thenLoop("attack"));
                return PlayState.CONTINUE;
            }

            if (event.isMoving()) {
                event.getController().setAnimation(RawAnimation.begin().thenLoop("walk"));
            } else {
                event.getController().setAnimation(RawAnimation.begin().thenLoop("idle"));
            }
            return PlayState.CONTINUE;
        }));
    }

    public enum State {
        IDLE, ATTACKING
    }
}
