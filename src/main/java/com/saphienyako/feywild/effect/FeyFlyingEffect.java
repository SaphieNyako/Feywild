package com.saphienyako.feywild.effect;

import com.saphienyako.feywild.Feywild;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;

import javax.annotation.Nonnull;

public class FeyFlyingEffect extends MobEffect {

    private static final String FEYWILD_GRANTED_FLIGHT =
            "feywild_granted_flight";

    public FeyFlyingEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xF59EE8);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(@Nonnull LivingEntity entity, int amplifier) {
        if (!(entity instanceof Player player)) {
            return true;
        }

        if (player.level().isClientSide) {
            return true;
        }

        var abilities = player.getAbilities();
        CompoundTag data = player.getPersistentData();

        //fix for flying with other mods
        if (!abilities.mayfly) {
            abilities.mayfly = true;
            abilities.flying = true;

            data.putBoolean(FEYWILD_GRANTED_FLIGHT, true);
            player.onUpdateAbilities();
        }

        return true;
    }

    @Override
    public void removeAttributeModifiers(@Nonnull AttributeMap attributeMap) {
        super.removeAttributeModifiers(attributeMap);
    }
}
