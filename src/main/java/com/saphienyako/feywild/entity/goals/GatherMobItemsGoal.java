package com.saphienyako.feywild.entity.goals;

import com.saphienyako.feywild.entity.base.PixieBase;
import com.saphienyako.feywild.network.FeywildNetwork;
import com.saphienyako.feywild.network.ParticleMessage;
import com.saphienyako.feywild.sound.ModSounds;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.*;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;


import javax.annotation.Nullable;
import java.util.List;

public class GatherMobItemsGoal extends Goal {

    private final PixieBase entity;
    private World level;
    private int ticksLeft = 0;

    private AnimalEntity targetMob;

    public GatherMobItemsGoal(PixieBase entity, World level) {
        this.entity = entity;
        this.level = level;
    }

    @Override
    public void tick() {
        if (this.ticksLeft > 0) {
            this.ticksLeft--;
            if (this.targetMob == null || !this.targetMob.isAlive() || this.targetMob.isBaby()) { //Als mob null is, als mob niet leeft, als mob age 0 is zoek target
                this.targetMob = this.findTarget();
                if (this.targetMob == null || !this.targetMob.isAlive() || this.targetMob.isBaby()) {
                    this.reset();
                    return;
                }
            }
            if (this.ticksLeft <= 0) {
                if (this.level instanceof ServerWorld) {
                   dropFromLootTable(targetMob);
                   this.entity.doHurtTarget(targetMob);
                   FeywildNetwork.sendParticles(level, ParticleMessage.Type.MOB_COLLECT, targetMob.blockPosition());
                   this.entity.playSound((SoundEvents.COMPOSTER_EMPTY), 0.7f, 1);
                }
                this.reset();
            } else if (this.ticksLeft == 45) {
                this.spellCasting();
            } else if (this.ticksLeft <= 35) {
                LookAtHelper.lookAt(this.targetMob, this.entity);
            }
        }
    }

    @Override
    public void start() {
        this.ticksLeft = 55;
        this.targetMob = null;
    }

    private void spellCasting() {
        this.entity.setState(PixieBase.State.SPELL_CASTING);
        this.entity.playSound(ModSounds.PIXIE_SPELL_CASTING.get(), 0.7f, 1);
    }

    protected void reset() {
        this.entity.setState(PixieBase.State.IDLE);
        this.targetMob = null;
        this.ticksLeft = -1;
    }

    @Override
    public boolean canContinueToUse() {
        return this.ticksLeft > 0;
    }


    @Override
    public boolean canUse() {
        PlayerEntity owning = this.entity.getOwningPlayer();
        if (owning instanceof ServerPlayerEntity && this.entity.getAbilityActive()) {
            return this.level.random.nextFloat() < 0.01f;
        } else {
            return false;
        }
    }
    private AnimalEntity findTarget() {
        double distance = Double.MAX_VALUE;
        AnimalEntity current = null;

        for (AnimalEntity mob : this.level.getEntitiesOfClass(AnimalEntity.class, this.entity.getBoundingBox().inflate(8))) {
            if (mob instanceof AbstractHorseEntity && ((AbstractHorseEntity) mob).isTamed()) continue;
            if (!mob.isAlive() || mob.isBaby()) continue;
            if (mob instanceof TameableEntity && ((TameableEntity) mob).isTame()) continue;

            double dist = this.entity.distanceToSqr(mob);
            if (dist < distance) {
                current = mob;
                distance = dist;
            }
        }
        return current;
    }

    protected void dropFromLootTable(MobEntity mob) {
        DamageSource source = DamageSource.MAGIC;

        ResourceLocation lootTableId = mob.getLootTable();
        LootTable lootTable = mob.level.getServer().getLootTables().get(lootTableId);

        LootContext.Builder builder = new LootContext.Builder((ServerWorld) mob.level)
                .withParameter(LootParameters.THIS_ENTITY, mob)
                .withParameter(LootParameters.ORIGIN, mob.position())
                .withParameter(LootParameters.DAMAGE_SOURCE, source);

        if (source.getEntity() != null) {
            builder.withOptionalParameter(LootParameters.KILLER_ENTITY, source.getEntity());
            builder.withOptionalParameter(LootParameters.DIRECT_KILLER_ENTITY, source.getDirectEntity());
        }

        LootContext lootContext = builder.create(LootParameterSets.ENTITY);

        List<ItemStack> drops = lootTable.getRandomItems(lootContext);

        for (ItemStack stack : drops) {
            mob.spawnAtLocation(stack, 0);
        }
    }
}
