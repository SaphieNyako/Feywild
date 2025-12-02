package com.saphienyako.feywild.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.FrostWalkerEnchantment;

import javax.annotation.Nonnull;

public class WinterBlessingEffect extends MobEffect {

    protected WinterBlessingEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x01ddff);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyEffectTick(@Nonnull LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {
            FrostWalkerEnchantment.onEntityMoved(player, player.level, player.blockPosition(), amplifier);
        }
    }
}
