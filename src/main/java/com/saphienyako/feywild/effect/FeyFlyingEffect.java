package com.saphienyako.feywild.effect;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nonnull;

public class FeyFlyingEffect extends MobEffect {

    public static final String FEYWILD_GRANTED_FLIGHT =
            "feywild_granted_flight";
    protected FeyFlyingEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xf59ee8);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyEffectTick(@Nonnull LivingEntity entity, int amplifier) {
        if (!(entity instanceof Player player)) {
            return;
        }

        if (player.level.isClientSide) {
            return;
        }

        Abilities abilities = player.getAbilities();
        CompoundTag data = player.getPersistentData();

        if (!abilities.mayfly) {
            abilities.mayfly = true;
            abilities.flying = true;

            data.putBoolean(FEYWILD_GRANTED_FLIGHT, true);

            player.onUpdateAbilities();
        }
    }
}
