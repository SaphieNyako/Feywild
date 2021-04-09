package com.feywild.feywild.entity;

import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.misc.DwarfTrades;
import com.feywild.feywild.sound.ModSoundEvents;
import com.mojang.brigadier.LiteralMessage;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.IMerchant;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DwarfBlacksmithEntity extends CreatureEntity implements IAnimatable {

    private HashMap<ItemStack, ItemStack> trades = new HashMap<>();
    private List<Integer> tradeId = new LinkedList<>();
    private int level = 1;

    //Geckolib variable
    private AnimationFactory factory = new AnimationFactory(this);

    /* CONSTRUCTOR */
    public DwarfBlacksmithEntity(EntityType<? extends CreatureEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected PathNavigator createNavigator(World worldIn) {
        return new GroundPathNavigator(this,worldIn);
    }

    @Override
    public ActionResultType applyPlayerInteraction(PlayerEntity player, Vector3d vec, Hand hand) {
        if(player.getEntityWorld().isRemote) return ActionResultType.SUCCESS;

        if(level >= 6 && trades.size() < 3){
            boolean worked = true;
            do{
                worked = addTrade(rand.nextInt(DwarfTrades.getTrades().size()));
            }while (!worked);
            level = 1;
        }


        if(player.getHeldItem(hand).isEmpty()){
            player.sendStatusMessage(new TranslationTextComponent("dwarf.feywild.dialogue"), false);
            trades.keySet().forEach(itemStack -> {
                player.sendStatusMessage(new TranslationTextComponent("").appendString("You give me "+ itemStack.getCount()
                        + " " + itemStack.getItem().getName().getString() + " and I'll trade you " + DwarfTrades.getTrades().get(itemStack).getCount() + " " + DwarfTrades.getTrades().get(itemStack).getItem().getName().getString() + "."), false);
            });
        }else{
            trades.keySet().forEach(itemStack -> {
                if(player.getHeldItem(hand).isItemEqual(itemStack) && player.getHeldItem(hand).getCount() >= itemStack.getCount()){
                    player.addItemStackToInventory(DwarfTrades.getTrades().get(itemStack).copy());
                    player.getHeldItem(hand).shrink(itemStack.copy().getCount());
                    level++;
                    player.sendStatusMessage(new TranslationTextComponent("dwarf.feywild.trade"), false);
                }
            });
            // add a check for item holding
        }
        //Add trade
        return ActionResultType.SUCCESS;
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
      //Add a random first trade
        addTrade(rand.nextInt(DwarfTrades.getTrades().size()));
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    //Add trade to list
    private boolean addTrade(int i){
        if(tradeId.contains(i)){
            return false;
        }
        AtomicInteger trade = new AtomicInteger(i);
        tradeId.add(trade.get());
        DwarfTrades.getTrades().keySet().forEach(itemStack -> {
            if(trade.get() == 0){
                trades.put(itemStack, DwarfTrades.getTrades().get(itemStack));
            }
            trade.set(trade.decrementAndGet());
        });

        return true;
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("level", level);
        compound.putIntArray("trade_id" , tradeId);
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.level = compound.getInt("level");
        int[] array = compound.getIntArray("trade_id");
        //Initialize trades
        for (int j : array) {
            addTrade(j);
        }
    }

    @Override
    public boolean canSpawn(IWorld worldIn, SpawnReason spawnReasonIn) {
        return super.canSpawn(worldIn, spawnReasonIn) && !this.world.canSeeSky(this.getPosition()) && this.getPosition().getY() < 64;
    }

    /* GOALS */
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(5, new MoveTowardsTargetGoal(this, 0.1f,15));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 0.8f,false));
        this.goalSelector.addGoal(3, new MoveTowardsRestrictionGoal(this,0.4D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 0.8D));
    }


    @Override
    protected void updateMovementGoalFlags()
    {
        super.updateMovementGoalFlags();
            this.goalSelector.setFlag(Goal.Flag.MOVE, true);
            this.goalSelector.setFlag(Goal.Flag.JUMP, true);
            this.goalSelector.setFlag(Goal.Flag.LOOK, true);
    }

    /* ATTRIBUTES */
    @Override
    public boolean canBeLeashedTo(PlayerEntity player) {
        return false;
    }

    @Override
    protected boolean canBeRidden(Entity entityIn) {
        return false;
    }

    @Override
    public boolean canBePushed() {
        return true;
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {

        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MOVEMENT_SPEED, Attributes.MOVEMENT_SPEED.getDefaultValue())
                .createMutableAttribute(Attributes.MAX_HEALTH, 24.0D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 4D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.1D);
    }

    @Override
    public boolean preventDespawn() {
        return true;
    }


    /* SOUND EFFECTS */
    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_VILLAGER_HURT;
    }


    //Ancient's note : we can keep them but adjust the pitch
    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        //Implement other Sound
        return SoundEvents.ENTITY_VILLAGER_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        //Implement other Sound

        return SoundEvents.ENTITY_VILLAGER_CELEBRATE;
    }

    @Override
    protected float getSoundPitch() {
        return 0.6f;
    }

    @Override
    protected float getSoundVolume ()
    {
        return 1F;
    }

    /* Animation */
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        if(event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.dwarf_blacksmith.walk", true));
        }else{
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.dwarf_blacksmith.stand", true));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        //Animation controller
        animationData.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {

        return this.factory;
    }

    public static boolean canSpawn(EntityType<DwarfBlacksmithEntity> dwarfBlacksmithEntityEntityType, IServerWorld iServerWorld, SpawnReason spawnReason, BlockPos pos, Random random) {
        if (pos.getY() >= iServerWorld.getSeaLevel()) {
            return false;
        } else {
            return canSpawnOn(dwarfBlacksmithEntityEntityType, iServerWorld, spawnReason, pos, random);
        }
    }
}
