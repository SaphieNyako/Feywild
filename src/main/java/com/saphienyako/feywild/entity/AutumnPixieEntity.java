package com.saphienyako.feywild.entity;

import com.saphienyako.feywild.compat.ModCompat;
import com.saphienyako.feywild.entity.base.PixieBase;
import com.saphienyako.feywild.entity.goals.pixie_goals.GatherCropsAbilityGoal;
import com.saphienyako.feywild.item.ModItems;
import com.saphienyako.feywild.particle.ModParticles;
import com.saphienyako.feywild.sound.ModSounds;
import com.saphienyako.quest_giver.quest.data.QuestData;
import com.saphienyako.quest_giver.quest.task.SpecialTask;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.Random;

public class AutumnPixieEntity extends PixieBase {
    protected AutumnPixieEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(20, new GatherCropsAbilityGoal(this, this.level()));
    }

    @Override
    public String getQuestLineId() {
        return "autumn_pixie";
    }

    @Override
    public String getBackground() {
        return "autumn_quest";
    }

    @Nullable
    @Override
    public SimpleParticleType getParticle() {
        return ModParticles.AUTUMN_SPARKLE_PARTICLE.get();
    }

    @Override
    public Alignment getAlignment() {
        return Alignment.AUTUMN;
    }

    @Override
    public ItemLike getDismissItem() {
        return ModItems.SUMMONING_SCROLL_AUTUMN_PIXIE.get();
    }

    @Override
    public SoundEvent getCookieSound() {
        return ModSounds.AUTUMN_PIXIE_COOKIE.get();
    }

    @Override
    public SoundEvent getNameSound() {
        return ModSounds.AUTUMN_PIXIE_NAME.get();
    }

    @Override
    public SoundEvent getSummonSound() {
        return ModSounds.AUTUMN_PIXIE_SUMMON.get();
    }

    @Override
    public SoundEvent getDismissSound() {
        return ModSounds.AUTUMN_PIXIE_DISMISS.get();
    }

    @Override
    public SoundEvent getFollowSound() {
        return ModSounds.AUTUMN_PIXIE_FOLLOW.get();
    }

    @Override
    public SoundEvent getStaySound() {
        return ModSounds.AUTUMN_PIXIE_STAY.get();
    }

    @Override
    public SoundEvent getAbilityOnSound() {
        return ModSounds.AUTUMN_PIXIE_ABILITY_ON.get();
    }

    @Override
    public SoundEvent getAbilityOffSound() {
        return ModSounds.AUTUMN_PIXIE_ABILITY_OFF.get();
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return ModSounds.AUTUMN_PIXIE_HURT.get();
    }


    @Override
    protected SoundEvent getDeathSound() { return ModSounds.AUTUMN_PIXIE_DEATH.get();
    }


    @Override
    protected SoundEvent getAmbientSound() {
        Random random = new Random();
        if(random.nextFloat() < 0.1f){
            return ModSounds.AUTUMN_PIXIE_GIGGLE.get();
        } else return null;
    }


    @NotNull
    @Override
    public InteractionResult interactAt(@NotNull Player player, @NotNull Vec3 hitVec, @NotNull InteractionHand hand) {
        //quest 07 healing the pixie
        ItemStack stack = player.getItemInHand(hand);

        if (!level().isClientSide && player instanceof ServerPlayer serverPlayer && stack.is(Items.POTION)) {

            PotionContents contents = stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);

            if (contents.is(Potions.HEALING) || contents.is(Potions.STRONG_HEALING)) {

                if (ModCompat.QUEST_GIVER_LOADED) {

                    boolean completed = QuestData.get(serverPlayer).checkComplete(SpecialTask.INSTANCE, "gift_healing_potion");

                    if (completed) {
                        if (!player.isCreative()) {
                            stack.shrink(1);
                        }

                        this.heal(8.0F);

                        if (level() instanceof ServerLevel serverLevel) {
                            serverLevel.sendParticles(ParticleTypes.HEART, getX(), getY() + getBbHeight() * 0.7, getZ(), 6, 0.35, 0.35, 0.35, 0.02);
                            serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, getX(), getY() + getBbHeight() * 0.5, getZ(), 10, 0.4, 0.45, 0.4, 0.02);
                        }

                        serverPlayer.playNotifySound(this.getDeathSound(), SoundSource.NEUTRAL, 1.0F, 1.0F);

                        player.swing(hand, true);

                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }
        return super.interactAt(player, hitVec, hand);
    }
}
