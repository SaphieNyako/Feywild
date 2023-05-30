package com.feywild.feywild.effects;

import com.feywild.feywild.particles.ModParticles;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.FrostWalkerEnchantment;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class FrostWalkEffect extends MobEffect {

    protected FrostWalkEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x01ddff);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyEffectTick(@Nonnull LivingEntity entity, int amplifier) {
        Level level = entity.level;
        if (entity instanceof Player player) {
            FrostWalkerEnchantment.onEntityMoved(player, player.level, player.blockPosition(), 2);
            if (level.isClientSide) {
                level.addParticle(ModParticles.frostWalkParticle, entity.getRandom().nextDouble() * 1.5 + entity.getX() - 1, entity.getRandom().nextDouble() * 2 + entity.getY() + 2, entity.getRandom().nextDouble() * 1.5 + entity.getZ() - 1, 0, -0.05, 0);
            }
        }

    }
}
