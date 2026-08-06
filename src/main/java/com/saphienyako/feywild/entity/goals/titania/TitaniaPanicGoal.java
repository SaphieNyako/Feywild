package com.saphienyako.feywild.entity.goals.titania;

import com.saphienyako.feywild.entity.TitaniaEntity;
import com.saphienyako.feywild.network.ParticleMessage;
import com.saphienyako.feywild.particle.ModParticles;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

public class TitaniaPanicGoal extends Goal {

    private final TitaniaEntity entity;
    private final int range;
    private final double speed;
    private int panicTime;
    private long lastHurtTimestamp;

    public TitaniaPanicGoal(TitaniaEntity entity, double speed, int range) {
        this.entity = entity;
        this.speed = speed;
        this.range = range;
    }

    @Override
    public void tick() {
        panicTime--;
    }

    @Override
    public void start() {

        panicTime = 20;

        LivingEntity target = entity.getTarget();
        //fey trickery
        if (target != null && entity.getRandom().nextFloat() < 0.33F) {

            Vec3 look = target.getLookAngle();
            Vec3 behind = target.position().subtract(look.scale(2.5D));

            behind = new Vec3(behind.x, target.getY(), behind.z);

            Vec3 offset = behind.subtract(entity.position());

            if (entity.level().noCollision(entity, entity.getBoundingBox().move(offset))) {
                entity.setDeltaMovement(Vec3.ZERO);

                if (entity.level() instanceof ServerLevel serverLevel) {
                    spawnSummerTeleportPoof(serverLevel, entity.getX(), entity.getY(), entity.getZ());
                }

                entity.teleportTo(behind.x, behind.y, behind.z);

                entity.lookAt(EntityAnchorArgument.Anchor.EYES, target.position());

                if (entity.level() instanceof ServerLevel serverLevel) {
                    spawnSummerTeleportPoof(serverLevel, behind.x, behind.y, behind.z);

                    serverLevel.playSound(null, behind.x, behind.y, behind.z, SoundEvents.ENDERMAN_TELEPORT, SoundSource.HOSTILE, 1.0F, 1.0F);
                }

                return;
            }
        }

        Vec3 targetPos = null;

        for (int i = 0; i < 30; i++) {

            Vec3 candidate = new Vec3(
                    entity.getX() - range + entity.getRandom().nextInt(range * 2),
                    entity.getY() - range + entity.getRandom().nextInt(range * 2),
                    entity.getZ() - range + entity.getRandom().nextInt(range * 2)
            );

            Vec3 offset = candidate.subtract(entity.position());

            if (entity.level().noCollision(entity, entity.getBoundingBox().move(offset))) {
                targetPos = candidate;
                break;
            }
        }

        if (targetPos != null) {

            entity.setDeltaMovement(
                    (targetPos.x() - entity.getX()) * speed * 20,
                    (targetPos.y() - entity.getY()) * speed * 20,
                    (targetPos.z() - entity.getZ()) * speed * 20
            );

            entity.lookAt(EntityAnchorArgument.Anchor.EYES, targetPos);
        }
    }

    @Override
    public boolean canContinueToUse() {
        return panicTime > 0;
    }

    @Override
    public boolean canUse() {

        if (entity.hurtTime > 0 &&
                entity.tickCount != lastHurtTimestamp) {

            lastHurtTimestamp = entity.tickCount;
            return true;
        }

        return false;
    }

    @Override
    public void stop() {
        entity.setDeltaMovement(Vec3.ZERO);
    }

    private void spawnSummerTeleportPoof(ServerLevel serverLevel, double x, double y, double z) {
        PacketDistributor.sendToPlayersTrackingEntity(entity, new ParticleMessage(ParticleMessage.Particles.DANDELION_FLUFF, BlockPos.containing(x, y, z)));


        serverLevel.sendParticles(
                ModParticles.SUMMER_SPARKLE_PARTICLE.get(),
                x,
                y + entity.getBbHeight() * 0.55D,
                z,
                22,
                0.5D,
                0.55D,
                0.5D,
                0.03D
        );

        serverLevel.sendParticles(
                ParticleTypes.CHERRY_LEAVES,
                x,
                y + entity.getBbHeight() * 0.6D,
                z,
                12,
                0.45D,
                0.4D,
                0.45D,
                0.03D
        );
    }
}
