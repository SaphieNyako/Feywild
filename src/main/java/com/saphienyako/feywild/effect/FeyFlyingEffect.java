package com.saphienyako.feywild.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class FeyFlyingEffect extends MobEffect {

    public FeyFlyingEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xF59EE8);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(@Nonnull LivingEntity entity, int amplifier) {
        if (entity instanceof Player player && !player.level().isClientSide) {
            var abilities = player.getAbilities();

            if (!abilities.mayfly) {
                abilities.mayfly = true;
                abilities.flying = true;
                player.onUpdateAbilities();
            }
        }

        return true;
    }

    @Override
    public void removeAttributeModifiers(@Nonnull AttributeMap attributeMap) {
        super.removeAttributeModifiers(attributeMap);
    }

    public static void onPlayerTick(Player player) {
        if (!player.level().isClientSide && !player.hasEffect(ModEffects.FEY_FLYING)) {
            if (!player.isCreative() && !player.isSpectator()) {
                var abilities = player.getAbilities();
                abilities.mayfly = false;
                abilities.flying = false;
                player.onUpdateAbilities();
            }
        }
    }
}
