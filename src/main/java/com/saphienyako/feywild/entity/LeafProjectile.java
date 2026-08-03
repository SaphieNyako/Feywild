package com.saphienyako.feywild.entity;

import com.saphienyako.feywild.particle.ModParticles;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class LeafProjectile extends ThrowableProjectile {

    private static final EntityDataAccessor<Integer> LEAF_TYPE = SynchedEntityData.defineId(LeafProjectile.class, EntityDataSerializers.INT);

    private static final float DAMAGE = 5.0F;
    private static final double KNOCKBACK_STRENGTH = 2.0D;

    public LeafProjectile(
            EntityType<? extends LeafProjectile> entityType,
            Level level
    ) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(
            SynchedEntityData.@NotNull Builder builder
    ) {
        builder.define(
                LEAF_TYPE,
                LeafType.AUTUMN.ordinal()
        );
    }

    public void setLeafType(LeafType leafType) {
        this.entityData.set(
                LEAF_TYPE,
                leafType.ordinal()
        );
    }

    public LeafType getLeafType() {
        LeafType[] values = LeafType.values();

        int index = Mth.clamp(
                this.entityData.get(LEAF_TYPE),
                0,
                values.length - 1
        );

        return values[index];
    }

    private ParticleOptions getParticle() {
        return switch (getLeafType()) {
            case SPRING ->
                    ModParticles.SPRING_LEAF_PARTICLE.get();

            case SUMMER ->
                    ModParticles.SUMMER_LEAF_PARTICLE.get();

            case AUTUMN ->
                    ModParticles.AUTUMN_LEAF_PARTICLE.get();

            case WINTER ->
                    ModParticles.WINTER_LEAF_PARTICLE.get();
        };
    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        super.onHitEntity(hitResult);

        if (level().isClientSide()) {
            return;
        }

        Entity hitEntity = hitResult.getEntity();
        Entity owner = getOwner();

        if (!(owner instanceof LivingEntity livingOwner)) {
            discard();
            return;
        }

        boolean damaged = hitEntity.hurt(
                damageSources().mobProjectile(
                        this,
                        livingOwner
                ),
                DAMAGE
        );

        if (damaged) {
            applyKnockback(hitEntity);
        }

        spawnImpactParticles();
        discard();
    }

    @Override
    protected void onHitBlock(
            @NotNull BlockHitResult hitResult
    ) {
        super.onHitBlock(hitResult);

        if (!level().isClientSide()) {
            spawnImpactParticles();
            discard();
        }
    }

    private void applyKnockback(Entity hitEntity) {
        Vec3 movement = getDeltaMovement();

        Vec3 horizontalDirection = new Vec3(
                movement.x,
                0.0D,
                movement.z
        );

        if (horizontalDirection.lengthSqr() < 1.0E-4D) {
            return;
        }

        horizontalDirection =
                horizontalDirection.normalize();

        hitEntity.push(
                horizontalDirection.x * KNOCKBACK_STRENGTH,
                0.25D,
                horizontalDirection.z * KNOCKBACK_STRENGTH
        );

        hitEntity.hurtMarked = true;
    }

    private void spawnImpactParticles() {
        if (!(level() instanceof ServerLevel serverLevel)) {
            return;
        }

        serverLevel.sendParticles(
                getParticle(),
                getX(),
                getY(),
                getZ(),
                8,
                0.25D,
                0.25D,
                0.25D,
                0.04D
        );

        playSound(
                SoundEvents.AZALEA_LEAVES_BREAK,
                1.0F,
                1.0F
        );
    }

    @Override
    public void tick() {
        super.tick();

        if (level().isClientSide()) {
            spawnTrailParticles();
        }

        if (!level().isClientSide()
                && tickCount > 40) {
            discard();
        }
    }

    private void spawnTrailParticles() {
        Vec3 movement = getDeltaMovement();

        ParticleOptions particle = getParticle();

        for (int i = 0; i < 3; i++) {
            double progress = i / 3.0D;

            double x = getX() - movement.x * progress;
            double y = getY() - movement.y * progress;
            double z = getZ() - movement.z * progress;

            level().addParticle(
                    particle,
                    x + random.nextGaussian() * 0.05D,
                    y + random.nextGaussian() * 0.05D,
                    z + random.nextGaussian() * 0.05D,
                    -movement.x * 0.05D,
                    0.01D,
                    -movement.z * 0.05D
            );
        }
    }

    public enum LeafType {
        SPRING,
        SUMMER,
        AUTUMN,
        WINTER
    }
}
