package com.saphienyako.feywild.entity.base;

import com.saphienyako.feywild.entity.Alignment;
import com.saphienyako.feywild.entity.base.intereface.IOwnable;
import com.saphienyako.feywild.entity.base.intereface.ISummonable;
import com.saphienyako.feywild.entity.goals.GoToTargetPositionGoal;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.item.TieredItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.util.SoundEvent;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.Random;
import java.util.UUID;

@SuppressWarnings("removal")
public abstract class FeyBase extends CreatureEntity implements IOwnable, ISummonable, IAnimatable {
    protected UUID owner;

    private BlockPos summonPos = null;
    private boolean followingPlayer = false;
    private boolean abilityActive = false;
    private boolean voiceActive = true;
    private final AnimationFactory factory = new AnimationFactory(this);

    protected FeyBase(EntityType<? extends CreatureEntity> entityType, World level) {
        super(entityType, level);
        //this.noCulling = true;
    }

    public static AttributeModifierMap.MutableAttribute getDefaultAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.35D)
                .add(Attributes.FLYING_SPEED, 0.4D)
                .add(Attributes.MAX_HEALTH, 12.0D)
                .add(Attributes.LUCK, 0.2D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.FOLLOW_RANGE, 24.0D);
    }

    public static boolean canSpawn(EntityType<? extends FeyBase> entity, IWorld level, SpawnReason reason, BlockPos pos, Random random) {
        return isBrightEnoughToSpawn(level, pos);
    }

    protected static boolean isBrightEnoughToSpawn(IWorldReader level, BlockPos pos) {
        return level.getBrightness(pos) > 8;
    }


    public IParticleData getParticle() {
        return null;
    }


    public Vector3d getCurrentPointOfInterest() {
        if (this.getFollowingPlayer()) {
            PlayerEntity player = this.getOwningPlayer();
            return player == null ? null : player.position();
        } else if (this.summonPos != null) {
            return new Vector3d(this.summonPos.getX() + 0.5, this.summonPos.getY(), this.summonPos.getZ() + 0.5);
        } else {
            return null;
        }
    }

    public float getTargetPositionSpeed() {
        return 1.5f;
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this)); //Instead of FloatGoal
        this.goalSelector.addGoal(30, new LookAtGoal(this, PlayerEntity.class, 8f));
        this.goalSelector.addGoal(11, new GoToTargetPositionGoal(this, this::getCurrentPointOfInterest, 6, this.getTargetPositionSpeed()));
        this.goalSelector.addGoal(30, new LookRandomlyGoal(this));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getParticle() != null && random.nextInt(11) == 0) {
            for (int i = 0; i < 4; i++) {
                level.addParticle(
                        this.getParticle(),
                        this.getX() + (Math.random() - 0.5),
                        this.getY() + 1 + (Math.random() - 0.5),
                        this.getZ() + (Math.random() - 0.5),
                        0, 0, 0
                );
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);
        if (owner != null) nbt.putUUID("Owner", owner);
        if (summonPos != null) nbt.put("SummonPos", NBTUtil.writeBlockPos(summonPos));

        nbt.putBoolean("FollowingPlayer", followingPlayer);
        nbt.putBoolean("AbilityActive", abilityActive);
        nbt.putBoolean("VoiceActive", voiceActive);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT nbt) {
        super.readAdditionalSaveData(nbt);

        owner = nbt.hasUUID("Owner") ? nbt.getUUID("Owner") : null;
        summonPos = nbt.contains("SummonPos") ? NBTUtil.readBlockPos(nbt.getCompound("SummonPos")) : null;

        followingPlayer = nbt.getBoolean("FollowingPlayer");
        abilityActive = nbt.getBoolean("AbilityActive");
        voiceActive = nbt.getBoolean("VoiceActive");
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        Entity attacker = damageSource.getEntity();
        if (attacker instanceof LivingEntity) {
            ItemStack held = ((LivingEntity) attacker).getMainHandItem();
            if (!isIronTool(held)) {
                return true; // block damage if not iron tool
            }
        }
        return super.isInvulnerableTo(damageSource);
    }

    private boolean isIronTool(ItemStack stack) {
        if (!(stack.getItem() instanceof TieredItem)) return false;
        TieredItem tiered = (TieredItem) stack.getItem();
        return tiered.getTier() == ItemTier.IRON;
    }
    @javax.annotation.Nullable
    @Override
    public UUID getOwner() {
        return this.owner;
    }

    @Override
    public void setOwner(@javax.annotation.Nullable UUID uid) {
        this.owner = uid;
    }


    @Override
    public BlockPos getSummonPos() {
        return this.summonPos;
    }

    @Override
    public void setSummonPos(BlockPos pos) {
        this.summonPos = pos == null ? null : new BlockPos(pos.getX(), pos.getY(), pos.getZ());
    }

    public void setFollowingPlayer(Boolean followingPlayer){
        this.followingPlayer = followingPlayer;
    }

    public Boolean getFollowingPlayer(){
        return this.followingPlayer;
    }

    public Boolean getAbilityActive() {
        return this.abilityActive;
    }

    public void setAbilityActive(Boolean abilityActive) {
        this.abilityActive = abilityActive;
    }

    public Boolean getVoiceActive() {return this.voiceActive;}

    public void setVoiceActive(Boolean voiceActive){this.voiceActive = voiceActive;}

    @Override
    public World getEntityLevel() {
        return this.level;
    }

    @Override
    public boolean onClimbable() {
        return false;
    }

    @Override
    protected int calculateFallDamage(float distance, float damageMultiplier) {
        return 0;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier) {
        return false;
    }

    @Override
    protected int getExperienceReward(@Nonnull PlayerEntity entity) {
        return this.isTamed() ? 0 : super.getExperienceReward(entity);
    }

    @Override
    public boolean canBeLeashed(@Nonnull PlayerEntity player) {
        return false;
    }

    @Override
    protected boolean canRide(@Nonnull Entity entity) {
        return false;
    }

    @Override
    protected float getVoicePitch() {
        return 1;
    }

    @Override
    public boolean requiresCustomPersistence() {
        return true;
    }

    @Override
    public boolean removeWhenFarAway(double distanceSq) {
        return false;
    }



    public abstract SoundEvent getCookieSound();
    public abstract SoundEvent getNameSound();
    public abstract SoundEvent getSummonSound();
    public abstract SoundEvent getDismissSound();
    public abstract SoundEvent getFollowSound();
    public abstract SoundEvent getStaySound();
    public abstract SoundEvent getAbilityOnSound();
    public abstract SoundEvent getAbilityOffSound();

    public abstract Alignment getAlignment();

    public abstract Item  getDismissItem();

    public String getEntityName(){
        EntityType<?> type = this.getType();
        ResourceLocation id = type.getRegistryName();
        return id == null ? "" : id.getPath();
    }

    public ITextComponent getFeyNameMessage(){
        return  new TranslationTextComponent("message.feywild."+ getEntityName() + "_name");
    }

    public ITextComponent getFeyCookieMessage(){
        return  new TranslationTextComponent("message.feywild."+ getEntityName() + "_cookie");
    }

    public ITextComponent getFeyFollowMessage(){
        return  new TranslationTextComponent("message.feywild."+ getEntityName() + "_follow");
    }

    public ITextComponent getFeyStayMessage(){
        return  new TranslationTextComponent("message.feywild."+ getEntityName() + "_stay");
    }

    public ITextComponent getFeySummonMessage(){
        return  new TranslationTextComponent("message.feywild."+ getEntityName() + "_summon");
    }

   public ITextComponent getFeyDismissMessage(){
       return  new TranslationTextComponent("message.feywild."+ getEntityName() + "_dismiss");
   }

   public ITextComponent getFeyAbilityOnMessage(){
       return  new TranslationTextComponent("message.feywild."+ getEntityName() + "_ability_on");
   }

    public ITextComponent getFeyAbilityOffMessage(){
        return  new TranslationTextComponent("message.feywild."+ getEntityName() + "_ability_off");
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
