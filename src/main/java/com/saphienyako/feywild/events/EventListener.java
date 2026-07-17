package com.saphienyako.feywild.events;

import com.saphienyako.feywild.config.ModConfig;
import com.saphienyako.feywild.effect.FeyFlyingEffect;
import com.saphienyako.feywild.effect.ModEffects;
import com.saphienyako.feywild.item.ModItems;
import com.saphienyako.feywild.sound.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventListener {

    @SubscribeEvent
    public void playerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.getEntity().level().isClientSide) {
           if (!FeywildPlayerData.get(event.getEntity()).getBoolean("feywild_got_lexicon") && ModConfig.COMMON.spawn_with_lexicon.get()) {
               event.getEntity().getInventory().add(new ItemStack(ModItems.FEYWILD_LEXICON.get()));
               FeywildPlayerData.get(event.getEntity()).putBoolean("feywild_got_lexicon", true);
            }
        }
    }


    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        Player player = event.player;

        if (player.level().isClientSide) {
            return;
        }

        if (player.isCreative() || player.isSpectator()) {
            return;
        }

        MobEffectInstance feyFlying = player.getEffect(ModEffects.FEY_FLYING.get());

        boolean hasEffect = feyFlying != null;

        CompoundTag data = player.getPersistentData();

        boolean grantedByFeywild = data.getBoolean(FeyFlyingEffect.FEYWILD_GRANTED_FLIGHT);

        Abilities abilities = player.getAbilities();

        if (!hasEffect) {

            if (grantedByFeywild) {
                abilities.mayfly = false;
                abilities.flying = false;

                data.remove(FeyFlyingEffect.FEYWILD_GRANTED_FLIGHT);

                player.onUpdateAbilities();
            }

            return;
        }

        int duration = feyFlying.getDuration();

        if (duration < 20) {
            if (duration == 19) {
                player.level().playSound(null, player.blockPosition(), ModSounds.PIXIE_SPELL_CASTING_SHORT.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            }

            player.displayClientMessage(Component.literal("The magic of the pixie tiara is fading...").withStyle(ChatFormatting.LIGHT_PURPLE), true);
            player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 120, 0, false, false, true));
        }
    }
}
