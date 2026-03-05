package com.saphienyako.feywild.effect;

import com.saphienyako.feywild.particle.ModParticles;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.enchantment.FrostWalkerEnchantment;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nonnull;

public class WinterTreeEntProtectionEffect extends MobEffect {

    protected WinterTreeEntProtectionEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x4A90E2);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyEffectTick(@Nonnull LivingEntity living, int amplifier) {
        if (!living.level().isClientSide) {

            AABB box = new AABB(living.blockPosition()).inflate(amplifier + 1);

            living.level().getEntities(null, box).forEach(entity -> {
                if (entity instanceof Monster || entity instanceof Projectile) {

                    double dx = entity.getX() - living.getX();
                    double dy = entity.getY() - living.getY();
                    double dz = entity.getZ() - living.getZ();

                    entity.setDeltaMovement(
                            dx * 0.8,
                            dy * 0.8,
                            dz * 0.8
                    );

                    entity.playSound(SoundEvents.AZALEA_LEAVES_BREAK);
                }
            });

            if (living.level() instanceof ServerLevel serverLevel) {

                serverLevel.sendParticles(
                        ModParticles.WINTER_LEAF_PARTICLE.get(),
                        living.getX() + living.getRandom().nextDouble() * 1.5 - 0.75,
                        living.getY() + living.getRandom().nextDouble() * 2.0 + 2.0,
                        living.getZ() + living.getRandom().nextDouble() * 1.5 - 0.75,
                        1,
                        0.0, 0.0, 0.0,
                        -0.05
                );
            }
        }
    }
}
