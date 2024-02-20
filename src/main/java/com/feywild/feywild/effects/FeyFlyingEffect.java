package com.feywild.feywild.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nonnull;

public class FeyFlyingEffect extends MobEffect {

    protected FeyFlyingEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xf59ee8);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyEffectTick(@Nonnull LivingEntity entity, int amplifier) {
        if (entity instanceof Player player && !player.level().isClientSide) {
            player.getAbilities().mayfly = true;
            player.onUpdateAbilities();
        }
    }

    @Override
    public void removeAttributeModifiers(@Nonnull LivingEntity entity, @Nonnull AttributeMap map, int amplifier) {
        super.removeAttributeModifiers(entity, map, amplifier);
        if (entity instanceof Player player && !player.level().isClientSide) {
            boolean canFly = player.isCreative() || player.isSpectator();
            player.getAbilities().mayfly = canFly;
            player.getAbilities().flying = player.getAbilities().flying && canFly;
            player.onUpdateAbilities();
        }
    }
}
