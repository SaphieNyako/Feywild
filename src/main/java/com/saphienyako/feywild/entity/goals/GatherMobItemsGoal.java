package com.saphienyako.feywild.entity.goals;

import com.saphienyako.feywild.entity.base.PixieBase;
import com.saphienyako.feywild.network.FeywildNetwork;
import com.saphienyako.feywild.network.ParticleMessage;
import com.saphienyako.feywild.sound.ModSounds;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import javax.annotation.Nullable;
import java.util.List;

public class GatherMobItemsGoal extends Goal {

    private static final TargetingConditions TARGETING = TargetingConditions.forNonCombat().range(8).ignoreLineOfSight();

    private final PixieBase entity;
    private Level level;
    private int ticksLeft = 0;

    private Animal targetMob;

    public GatherMobItemsGoal(PixieBase entity, Level level) {
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
                if (this.level instanceof ServerLevel) {
                   dropFromLootTable(targetMob);
                   this.entity.doHurtTarget(targetMob);
                   FeywildNetwork.sendParticles(level, ParticleMessage.Type.MOB_COLLECT, targetMob.blockPosition());
                   this.entity.playSound((SoundEvents.COMPOSTER_EMPTY), 0.7f, 1);
                }
                this.reset();
            } else if (this.ticksLeft == 45) {
                this.spellCasting();
            } else if (this.ticksLeft <= 35) {
                this.entity.lookAt(EntityAnchorArgument.Anchor.EYES, this.targetMob.position());
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
        Player owning = this.entity.getOwningPlayer();
        if (owning instanceof ServerPlayer && this.entity.getAbilityActive()) {
            return this.level.random.nextFloat() < 0.01f;
        } else {
            return false;
        }
    }

    @Nullable
    private Animal findTarget() {
        double distance = Double.MAX_VALUE;
        Animal current = null;
        for (Animal mob : this.level.getNearbyEntities(Animal.class, TARGETING, this.entity, this.entity.getBoundingBox().inflate(8))) {

            if(mob instanceof AbstractHorse horse && horse.isTamed()) continue;
            if (!mob.isAlive() || mob.isBaby()) continue;
            if (mob instanceof TamableAnimal tameable && tameable.isTame()) continue;

            if (this.entity.distanceToSqr(mob) < distance ) {
                current = mob;
                distance = this.entity.distanceToSqr(mob);
            }
        }
        return current;
    }


    protected void dropFromLootTable(Mob mob) {
        // Damage source — magic
        DamageSource source = DamageSource.MAGIC; // 1.19.2 uses static fields

        // Get the loot table
        ResourceLocation lootTableId = mob.getLootTable();
        LootTable lootTable = mob.level.getServer().getLootTables().get(lootTableId);

        // Build the loot context
        LootContext.Builder builder = new LootContext.Builder((ServerLevel) mob.level)
                .withParameter(LootContextParams.THIS_ENTITY, mob)
                .withParameter(LootContextParams.ORIGIN, mob.position())
                .withParameter(LootContextParams.DAMAGE_SOURCE, source);

        if (source.getEntity() != null) {
            builder.withOptionalParameter(LootContextParams.KILLER_ENTITY, source.getEntity());
            builder.withOptionalParameter(LootContextParams.DIRECT_KILLER_ENTITY, source.getDirectEntity());
        }

        LootContext lootContext = builder.create(LootContextParamSets.ENTITY);

        // This is the correct 1.19.2 method
        List<ItemStack> drops = lootTable.getRandomItems(lootContext);

        for (ItemStack stack : drops) {
            mob.spawnAtLocation(stack, 0);
        }

        //TODO does this work?

    }
}
