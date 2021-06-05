package com.feywild.feywild.entity;

import com.feywild.feywild.block.entity.DwarvenAnvilEntity;
import com.feywild.feywild.entity.goals.GoToSummoningPositionGoal;
import com.feywild.feywild.entity.goals.RefreshStockGoal;
import com.feywild.feywild.entity.util.TraderEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.*;

public class DwarfBlacksmithEntity extends TraderEntity implements IAnimatable {

    public BlockPos summonPos;
    //Geckolib variable
    private AnimationFactory factory = new AnimationFactory(this);
    private boolean tamed = false;
    private HashMap<ItemStack, ItemStack> trades = new HashMap<>();
    private List<Integer> tradeId = new LinkedList<>();
    private int levelInt = 1;

    public DwarfBlacksmithEntity(EntityType<? extends TraderEntity> type, World worldIn, boolean isTamed) {
        super(type, worldIn, isTamed);
        //Geckolib check
        this.noCulling = true;
        this.moveControl = new MovementController(this);
        addGoalsAfterConstructor();
    }

    public DwarfBlacksmithEntity(EntityType<? extends TraderEntity> type, World worldIn) {
        super(type, worldIn, false);
        //Geckolib check
        this.noCulling = true;
        this.moveControl = new MovementController(this);
        addGoalsAfterConstructor();
    }

    public DwarfBlacksmithEntity(World worldIn, boolean isTamed, BlockPos pos) {
        this(ModEntityTypes.DWARF_BLACKSMITH.get(), worldIn, isTamed);
        //Geckolib check
        this.noCulling = true;
        this.moveControl = new MovementController(this);
        setTamed(isTamed);
        this.summonPos = pos;
        addGoalsAfterConstructor();
    }


    /* MOVEMENT */

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {

        return MobEntity.createMobAttributes().add(Attributes.MOVEMENT_SPEED, Attributes.MOVEMENT_SPEED.getDefaultValue())
                .add(Attributes.MAX_HEALTH, 24.0D)
                .add(Attributes.ATTACK_DAMAGE, 4D)
                .add(Attributes.MOVEMENT_SPEED, 0.1D);
    }

    public static boolean canSpawn(EntityType<DwarfBlacksmithEntity> dwarfBlacksmithEntityEntityType, IServerWorld iServerWorld, SpawnReason spawnReason, BlockPos pos, Random random) {
        if (pos.getY() >= iServerWorld.getSeaLevel()) {
            return false;
        } else {
            return checkMobSpawnRules(dwarfBlacksmithEntityEntityType, iServerWorld, spawnReason, pos, random);
        }
    }

    @Override
    protected PathNavigator createNavigation(World worldIn) {
        return new GroundPathNavigator(this, worldIn);
    }


    /* TRADING */

    @Override
    public ActionResultType interactAt(PlayerEntity player, Vector3d vec, Hand hand) {
        if (player.getCommandSenderWorld().isClientSide) return ActionResultType.SUCCESS;

        this.setTradingPlayer(player); //added
        this.openTradingScreen(player, new TranslationTextComponent("Dwarven Trader"), 1); //added

        player.displayClientMessage(new TranslationTextComponent("dwarf.feywild.dialogue"), false);

        /* This is a test to see if restocking works should be in a goal
        if (player.getItemInHand(hand).isEmpty() && this.shouldRestock()) {
            this.restock(); // this works
        }*/

        return ActionResultType.SUCCESS;
    }

    /* TAMED */

    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason
            reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {

        this.restrictTo(blockPosition(), 7);
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }


    /* GOALS */

    public boolean isTamed() {
        return tamed;
    }

    public void setTamed(boolean tamed) {
        this.tamed = tamed;
        if (tamed) {
            this.trades.clear();
            this.tradeId.clear();
            this.levelInt = 8;
        }
    }

    protected void addGoalsAfterConstructor() {
        if (this.level.
                isClientSide())
            return;

        for (PrioritizedGoal goal : getGoals()) {
            this.goalSelector.addGoal(goal.getPriority(), goal.getGoal());
        }
    }

    public List<PrioritizedGoal> getGoals() {
        return this.tamed ? getTamedGoals() : getUntamedGoals();
    }

    /* SAVE DATA */

    public List<PrioritizedGoal> getTamedGoals() {
        List<PrioritizedGoal> list = new ArrayList<>();
        list.add(new PrioritizedGoal(0, new SwimGoal(this)));
        list.add(new PrioritizedGoal(5, new MoveTowardsTargetGoal(this, 0.1f, 8)));
        list.add(new PrioritizedGoal(1, new MeleeAttackGoal(this, 0.8, false)));
        list.add(new PrioritizedGoal(3, new MoveTowardsTargetGoal(this, 0.4f, 8)));
        list.add(new PrioritizedGoal(3, new WaterAvoidingRandomWalkingGoal(this, 0.5D)));
        list.add(new PrioritizedGoal(2, new LookRandomlyGoal(this)));
        list.add(new PrioritizedGoal(2, new GoToSummoningPositionGoal(this, () -> this.summonPos, 5)));
        list.add(new PrioritizedGoal(6, new RefreshStockGoal(this)));

        return list;
    }

    public List<PrioritizedGoal> getUntamedGoals() {
        List<PrioritizedGoal> list = new ArrayList<>();
        list.add(new PrioritizedGoal(0, new SwimGoal(this)));
        list.add(new PrioritizedGoal(5, new MoveTowardsTargetGoal(this, 0.1f, 8)));
        list.add(new PrioritizedGoal(2, new MeleeAttackGoal(this, 0.8, false)));
        list.add(new PrioritizedGoal(2, new MoveTowardsTargetGoal(this, 0.4f, 8)));
        list.add(new PrioritizedGoal(3, new WaterAvoidingRandomWalkingGoal(this, 0.5D)));
        list.add(new PrioritizedGoal(2, new LookRandomlyGoal(this)));
        list.add(new PrioritizedGoal(6, new RefreshStockGoal(this)));
        //Easy way to test tamed/untamed:
        // list.add(new PrioritizedGoal(1, new TemptGoal(this, 1.25D,Ingredient.of(Items.COOKIE),false)));

        return list;
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("tamed", tamed);
        compound.putInt("level", levelInt);
        //     compound.putIntArray("trade_id", tradeId);

    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.levelInt = compound.getInt("level");
        this.tamed = compound.getBoolean("tamed");

        tryResetGoals();
    }

    public void tryResetGoals() {
        this.goalSelector.availableGoals = new LinkedHashSet<>();
        this.addGoalsAfterConstructor();
    }

    @Override
    public boolean checkSpawnRules(IWorld worldIn, SpawnReason spawnReasonIn) {
        return super.checkSpawnRules(worldIn, spawnReasonIn) && this.blockPosition().getY() < 60 && !worldIn.canSeeSky(this.blockPosition());
    }

    @Override
    protected void updateControlFlags() {
        super.updateControlFlags();
        this.goalSelector.setControlFlag(Goal.Flag.MOVE, true);
        this.goalSelector.setControlFlag(Goal.Flag.JUMP, true);
        this.goalSelector.setControlFlag(Goal.Flag.LOOK, true);
    }

    /* ATTRIBUTES */
    @Override
    public boolean canBeLeashed(PlayerEntity player) {
        return false;
    }

    @Override
    protected boolean canRide(Entity entityIn) {
        return false;
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    public boolean requiresCustomPersistence() {
        return true;
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
    public void die(DamageSource p_706451) {
        super.die(p_706451);
        if (this.isTamed() && this.level.getBlockEntity(summonPos) != null && this.level.getBlockEntity(summonPos) instanceof DwarvenAnvilEntity) {
            ((DwarvenAnvilEntity) Objects.requireNonNull(this.level.getBlockEntity(summonPos))).setDwarfPresent(false);

            //THIS SHOULD ONLY APPLY TO A TAMED DWARF
        }
    }

    /* SOUND EFFECTS */
    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.VILLAGER_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {

        return SoundEvents.VILLAGER_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {

        return SoundEvents.VILLAGER_CELEBRATE;
    }

    @Override
    protected float getVoicePitch() {
        return 0.6f;
    }

    /* Animation */

    //TODO: Add working animation

    @Override
    protected float getSoundVolume() {
        return 1F;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.dwarf_blacksmith.walk", true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.dwarf_blacksmith.stand", true));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {

        animationData.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {

        return this.factory;
    }

}
