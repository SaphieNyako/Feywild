package com.feywild.feywild.entity;

import com.feywild.feywild.entity.goals.GoToSummoningPositionGoal;
import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.misc.DwarfTrades;
import com.feywild.feywild.network.FeywildPacketHandler;
import com.feywild.feywild.network.ParticleMessage;
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
import java.util.concurrent.atomic.AtomicInteger;

public class DwarfBlacksmithEntity extends CreatureEntity implements IAnimatable {

    public BlockPos summonPos;
    //Geckolib variable
    private AnimationFactory factory = new AnimationFactory(this);
    private boolean tamed = false;
    private HashMap<ItemStack, ItemStack> trades = new HashMap<>();
    private List<Integer> tradeId = new LinkedList<>();
    private int levelInt = 1;

    public DwarfBlacksmithEntity(EntityType<? extends CreatureEntity> type, World worldIn) {
        super(type, worldIn);
        //Geckolib check
        this.noCulling = true;
        this.moveControl = new MovementController(this);
        addGoalsAfterConstructor();
    }

    public DwarfBlacksmithEntity(World worldIn, boolean isTamed, BlockPos pos) {
        super(ModEntityTypes.DWARF_BLACKSMITH.get(), worldIn);
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

    /* TRADING */

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

    @Override
    public ActionResultType interactAt(PlayerEntity player, Vector3d vec, Hand hand) {
        if (player.getCommandSenderWorld().isClientSide) return ActionResultType.SUCCESS;

        if (!tamed && levelInt >= 6) {
            if (trades.size() < 3) {
                FeywildPacketHandler.sendToPlayersInRange(player.getCommandSenderWorld(), blockPosition(), new ParticleMessage(blockPosition().getX() + 0.5, blockPosition().getY() + 1.2, blockPosition().getZ() + 0.5, -4, -2, -4, 10, 3), 32);
                boolean worked = true;
                do {
                    worked = addTrade(random.nextInt(DwarfTrades.getTrades().size()));
                } while (!worked);
                levelInt = 1;
            } else {
                player.addItem(new ItemStack(ModItems.SUMMONING_SCROLL_DWARF_BLACKSMITH.get()));
                player.displayClientMessage(new TranslationTextComponent("dwarf.feywild.scroll"), false);
                this.remove();
                return ActionResultType.FAIL;
            }
        } else {
            addTrade(0);
        }

        if (player.getItemInHand(hand).isEmpty()) {
            player.displayClientMessage(new TranslationTextComponent("dwarf.feywild.dialogue"), false);
            trades.keySet().forEach(itemStack -> {
                if (!tamed)
                    player.displayClientMessage(new TranslationTextComponent("").append("You give me " + itemStack.getCount()
                            + " " + itemStack.getItem().getDescription().getString() + " and I'll trade you " + DwarfTrades.getTrades().get(itemStack).getCount() + " " + DwarfTrades.getTrades().get(itemStack).getItem().getDescription().getString() + "."), false);
                else
                    player.displayClientMessage(new TranslationTextComponent("").append("You give me " + itemStack.getCount()
                            + " " + itemStack.getItem().getDescription().getString() + " and I'll trade you " + DwarfTrades.getTamedTrades().get(itemStack).getCount() + " " + DwarfTrades.getTamedTrades().get(itemStack).getItem().getDescription().getString() + "."), false);

            });
        } else {
            trades.keySet().forEach(itemStack -> {
                if (player.getItemInHand(hand).sameItem(itemStack) && player.getItemInHand(hand).getCount() >= itemStack.getCount()) {
                    if (!tamed) {
                        levelInt++;
                        player.addItem(DwarfTrades.getTrades().get(itemStack).copy());
                    } else {
                        player.addItem(DwarfTrades.getTamedTrades().get(itemStack).copy());
                    }
                    player.getItemInHand(hand).shrink(itemStack.copy().getCount());
                    player.displayClientMessage(new TranslationTextComponent("dwarf.feywild.trade"), false);
                }
            });
            // add a check for item holding
        }
        //Add trade
        return ActionResultType.SUCCESS;
    }

    /* TAMED */

    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        //Add a random first trade
        if (!tamed)
            addTrade(random.nextInt(DwarfTrades.getTrades().size()));
        else
            this.restrictTo(blockPosition(), 7);
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    //Add trade to list
    private boolean addTrade(int i) {
        if (!tamed) {
            if (tradeId.contains(i)) {
                return false;
            }
            AtomicInteger trade = new AtomicInteger(i);
            tradeId.add(trade.get());
            DwarfTrades.getTrades().keySet().forEach(itemStack -> {
                if (trade.get() == 0) {
                    trades.put(itemStack, DwarfTrades.getTrades().get(itemStack));
                }
                trade.set(trade.decrementAndGet());
            });
        } else {
            if (tradeId.contains(i)) {
                return false;
            }
            AtomicInteger trade = new AtomicInteger(i);
            tradeId.add(trade.get());
            DwarfTrades.getTamedTrades().keySet().forEach(itemStack -> {
                if (trade.get() == 0) {
                    trades.put(itemStack, DwarfTrades.getTamedTrades().get(itemStack));
                }
                trade.set(trade.decrementAndGet());
            });
        }

        return true;
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
        //Easy way to test tamed/untamed:
        // list.add(new PrioritizedGoal(1, new TemptGoal(this, 1.25D,Ingredient.of(Items.COOKIE),false)));


        return list;
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("tamed", tamed);
        compound.putInt("level", levelInt);
        compound.putIntArray("trade_id", tradeId);


    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.levelInt = compound.getInt("level");
        this.tamed = compound.getBoolean("tamed");

        if (!tamed) {
            int[] array = compound.getIntArray("trade_id");
            //Initialize trades
            for (int j : array) {
                addTrade(j);
            }
        }

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