package com.saphienyako.feywild.entity.goals.mab;

import com.saphienyako.feywild.entity.MabEntity;
import com.saphienyako.feywild.network.ParticleMessage;
import com.saphienyako.feywild.particle.ModParticles;
import com.saphienyako.feywild.sound.ModSounds;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Random;

public class PhysicalAttackGoal extends Goal {

    private static final int ATTACK_DURATION = 36;

    private static final int MIN_COOLDOWN = 20 * 4;
    private static final int EXTRA_COOLDOWN = 20 * 3;

    private static final int RETALIATION_COOLDOWN = 8;

    private static final float ATTACK_DAMAGE = 6.0F;

    private final MabEntity entity;

    private int ticksLeft;
    private int cooldownTicks;

    public PhysicalAttackGoal(MabEntity mab) {
        this.entity = mab;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        Player attacker = entity.getRecentAttacker();

        boolean retaliating =
                attacker != null
                        && attacker.isAlive()
                        && entity.distanceToSqr(attacker) <= 24.0D * 24.0D;

        if (cooldownTicks > 0) {
            if (retaliating) {
                cooldownTicks = Math.min(cooldownTicks, RETALIATION_COOLDOWN);
            }

            cooldownTicks--;
            return false;
        }

        LivingEntity target = retaliating
                ? attacker
                : entity.getTarget();

        if (target == null || !target.isAlive()) {
            return false;
        }

        if (entity.getState() == MabEntity.State.CHANNEL
                || entity.getState() == MabEntity.State.INTIMIDATION
                || entity.getState() == MabEntity.State.ATTACKING) {
            return false;
        }

        return retaliating || entity.getRandom().nextFloat() < 0.06F;
    }

    @Override
    public void start() {
        ticksLeft = ATTACK_DURATION;

        entity.getNavigation().stop();
        entity.setState(MabEntity.State.ATTACKING);
    }

    @Override
    public void tick() {
        LivingEntity target = getAttackTarget();

        if (target == null || !target.isAlive()) {
            ticksLeft = 0;
            return;
        }

        entity.getNavigation().stop();

        entity.lookAt(EntityAnchorArgument.Anchor.EYES, target.getEyePosition());

        if (ticksLeft == 35) {
            moveToTarget(target);
        }

        if (ticksLeft == 33) {
            entity.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1.0F, 1.0F);

            if (entity.getRandom().nextInt(3) == 0) {
                entity.playSound(ModSounds.MAB_ATTACK.get(), 1.0F, 1.0F);
            }
        }

        if (ticksLeft == 30) {
            performAttack(target);
        }

        ticksLeft--;
    }

   @Nullable
    private LivingEntity getAttackTarget() {
        Player attacker = entity.getRecentAttacker();

        if (attacker != null && attacker.isAlive()) {
            return attacker;
        }

        return entity.getTarget();
    }

    private void performAttack(LivingEntity target) {

        if (entity.distanceToSqr(target) > 4.0D * 4.0D) {
            return;
        }

        target.hurt(entity.damageSources().mobAttack(entity), ATTACK_DAMAGE);

        Vec3 direction = target.position().subtract(entity.position());

        direction = new Vec3(direction.x, 0.0D, direction.z);

        if (direction.lengthSqr() > 1.0E-4D) {
            direction = direction.normalize();

            target.push(direction.x * 0.8D, 0.25D, direction.z * 0.8D);

            target.hurtMarked = true;
        }
    }

    private void moveToTarget(LivingEntity target) {
        Vec3 behind = target.position().subtract(target.getLookAngle().scale(2.0D));
        teleportToTarget(behind);
    }

    @Override
    public boolean canContinueToUse() {
        return ticksLeft > 0 && entity.isAlive();
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void stop() {
        entity.setState(MabEntity.State.IDLE_FLYING);

        ticksLeft = 0;

        cooldownTicks = MIN_COOLDOWN + entity.getRandom().nextInt(EXTRA_COOLDOWN + 1);
    }

    private void teleportToTarget(Vec3 position) {
        if (!(entity.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        spawnTeleportEffect(serverLevel, entity.getX(), entity.getY(), entity.getZ());

        entity.teleportTo(position.x, position.y, position.z);

        spawnTeleportEffect(serverLevel, position.x, position.y, position.z);

        entity.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
    }

    private void spawnTeleportEffect(ServerLevel serverLevel, double x, double y, double z) {

        PacketDistributor.sendToPlayersTrackingEntity(entity, new ParticleMessage(ParticleMessage.Particles.DANDELION_FLUFF, BlockPos.containing(x, y, z)));

        serverLevel.sendParticles(
                ParticleTypes.SNOWFLAKE,
                x,
                y + entity.getBbHeight() * 0.6D,
                z,
                24,
                0.5D,
                0.7D,
                0.5D,
                0.01D
        );

        serverLevel.sendParticles(
                ModParticles.WINTER_SPARKLE_PARTICLE.get(),
                x,
                y + entity.getBbHeight() * 0.6D,
                z,
                18,
                0.45D,
                0.45D,
                0.45D,
                0.02D
        );
    }
}