package com.feywild.feywild.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FeyFlyingEffect extends MobEffect {

    protected FeyFlyingEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xf59ee8);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return false;
    }

    @Override
    public void applyInstantenousEffect(@Nullable Entity source, @Nullable Entity indirectSource, @Nonnull LivingEntity entity, int amplifier, double health) {
        super.applyInstantenousEffect(source, indirectSource, entity, amplifier, health);
        //TODO make this work?
        /*
        if (entity instanceof Player player) {
            var abilities = ((Player) entity).getAbilities();
            if (!player.isCreative() && !player.isSpectator()) {
                abilities.mayfly = player.getInventory().getArmor(2).getItem() instanceof FeyWing;
            }
        } */
    }
}
