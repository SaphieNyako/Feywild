package com.feywild.feywild.entity;

import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.misc.DwarfTrades;
import com.feywild.feywild.network.FeywildPacketHandler;
import com.feywild.feywild.network.ParticleMessage;
import com.feywild.feywild.sound.ModSoundEvents;
import com.mojang.brigadier.LiteralMessage;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.IMerchant;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
    private boolean tamed = false;

    //Geckolib variable
    private AnimationFactory factory = new AnimationFactory(this);

    /* CONSTRUCTOR */
    public DwarfBlacksmithEntity(EntityType<? extends CreatureEntity> type, World worldIn) {
        super(type, worldIn);
    }
    @Override
    protected PathNavigator createNavigation(World worldIn) {
        return new GroundPathNavigator(this,worldIn);
    }

    @Override
    public ActionResultType interactAt(PlayerEntity player, Vector3d vec, Hand hand) {
        if(player.getCommandSenderWorld().isClientSide) return ActionResultType.SUCCESS;

            if(!tamed && level >= 6){
                if(trades.size() < 3) {
                    FeywildPacketHandler.sendToPlayersInRange(player.getCommandSenderWorld(), blockPosition(), new ParticleMessage(blockPosition().getX() + 0.5, blockPosition().getY() + 1.2, blockPosition().getZ() + 0.5, -4, -2, -4, 10, 3), 32);
                    boolean worked = true;
                    do {
                        worked = addTrade(random.nextInt(DwarfTrades.getTrades().size()));
                    } while (!worked);
                    level = 1;
                }else{
                    player.addItem(new ItemStack(ModItems.SUMMONING_SCROLL_DWARF_BLACKSMITH.get()));
                    player.displayClientMessage(new TranslationTextComponent("dwarf.feywild.scroll"), false);
                    this.remove();
                    return ActionResultType.FAIL;
                }
            }else{
                addTrade(0);
            }

            if (player.getItemInHand(hand).isEmpty()) {
                player.displayClientMessage(new TranslationTextComponent("dwarf.feywild.dialogue"), false);
                trades.keySet().forEach(itemStack -> {
                    if(!tamed)
                    player.displayClientMessage(new TranslationTextComponent("").append("You give me " + itemStack.getCount()
                            + " " + itemStack.getItem().getDescription().getString() + " and I'll trade you " + DwarfTrades.getTrades().get(itemStack).getCount() + " " + DwarfTrades.getTrades().get(itemStack).getItem().getDescription().getString() + "."), false);
                    else player.displayClientMessage(new TranslationTextComponent("").append("You give me " + itemStack.getCount()
                                + " " + itemStack.getItem().getDescription().getString() + " and I'll trade you " + DwarfTrades.getTamedTrades().get(itemStack).getCount() + " " + DwarfTrades.getTamedTrades().get(itemStack).getItem().getDescription().getString() + "."), false);

                });
            } else {
                trades.keySet().forEach(itemStack -> {
                    if (player.getItemInHand(hand).sameItem(itemStack) && player.getItemInHand(hand).getCount() >= itemStack.getCount()) {
                        if(!tamed) {
                            level++;
                            player.addItem(DwarfTrades.getTrades().get(itemStack).copy());
                        }else{
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

    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
      //Add a random first trade
        if(!tamed)
        addTrade(random.nextInt(DwarfTrades.getTrades().size()));
        else
            this.restrictTo(blockPosition(),7);
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    public void setTamed(boolean tamed) {
        this.tamed = tamed;
        if(tamed){
            this.trades.clear();
            this.tradeId.clear();
            this.level = 8;
        }
    }

    public boolean isTamed() {
        return tamed;
    }

    //Add trade to list
    private boolean addTrade(int i){
        if(!tamed) {
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
        }else{
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

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("tamed", tamed);
        compound.putInt("level", level);
        compound.putIntArray("trade_id" , tradeId);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.level = compound.getInt("level");
        this.tamed = compound.getBoolean("tamed");
        if(!tamed) {
            int[] array = compound.getIntArray("trade_id");
            //Initialize trades
            for (int j : array) {
                addTrade(j);
            }
        }
    }

    @Override
    public boolean checkSpawnRules(IWorld worldIn, SpawnReason spawnReasonIn) {
        return true;//super.checkSpawnRules(worldIn, spawnReasonIn) && !this.isInWater() && this.blockPosition().getY() < 60;  // && !this.level.canSeeSky(this.blockPosition())
    }

    /* GOALS */
    @Override
    protected void registerGoals() {
        if (tamed) {
            registerTamedGoals();
        }
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(5, new MoveTowardsTargetGoal(this, 0.1f,15));
        this.targetSelector.addGoal(1,new MeleeAttackGoal(this,0.8,false));
        this.goalSelector.addGoal(3, new MoveTowardsRestrictionGoal(this,0.4D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 0.8D));
    }
    private void registerTamedGoals(){

    }


    @Override
    protected void updateControlFlags()
    {
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

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {

        return MobEntity.createMobAttributes().add(Attributes.MOVEMENT_SPEED, Attributes.MOVEMENT_SPEED.getDefaultValue())
                .add(Attributes.MAX_HEALTH, 24.0D)
                .add(Attributes.ATTACK_DAMAGE, 4D)
                .add(Attributes.MOVEMENT_SPEED, 0.1D);
    }

    @Override
    public boolean requiresCustomPersistence() {
        return true;
    }


    /* SOUND EFFECTS */
    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.VILLAGER_HURT;
    }


    //Ancient's note : we can keep them but adjust the pitch
    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        //Implement other Sound
        return SoundEvents.VILLAGER_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        //Implement other Sound

        return SoundEvents.VILLAGER_CELEBRATE;
    }

    @Override
    protected float getVoicePitch() {
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
            return checkMobSpawnRules(dwarfBlacksmithEntityEntityType, iServerWorld, spawnReason, pos, random);
        }
    }
}
