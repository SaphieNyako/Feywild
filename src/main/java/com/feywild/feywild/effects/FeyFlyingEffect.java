package com.feywild.feywild.effects;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.item.FeyWing;
import com.feywild.feywild.network.UpdateFlight;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nonnull;
import java.util.Objects;

public class FeyFlyingEffect extends MobEffect {

    protected FeyFlyingEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xf59ee8);
    }

    public boolean hasCorrectArmorOn(Player player) {
        return player.getInventory().getArmor(2).getItem() instanceof FeyWing;
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyEffectTick(@Nonnull LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {
            player.getAbilities().mayfly = (player.isCreative() || player.isSpectator()) || hasCorrectArmorOn(player) || Objects.requireNonNull(entity.getEffect(ModEffects.feyFlying)).getDuration() > 1;
        }
    }

    @Override
    public void removeAttributeModifiers(@Nonnull LivingEntity entity, @Nonnull AttributeMap map, int amplifier) {
        super.removeAttributeModifiers(entity, map, amplifier);
        if (entity instanceof Player player) {
            boolean canFly = player.isCreative() || player.isSpectator();
            player.getAbilities().mayfly = canFly;
            player.getAbilities().flying = canFly;
            FeywildMod.getNetwork().channel.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new UpdateFlight(canFly, canFly));
        }
    }
}
