package com.saphienyako.feywild.events;

import com.saphienyako.feywild.config.FeywildConfig;
import com.saphienyako.feywild.effect.ModEffects;
import com.saphienyako.feywild.item.ModItems;
import com.saphienyako.feywild.sound.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class ModEventListener {

    private static final String FEYWILD_GRANTED_FLIGHT =
            "feywild_granted_flight";


    public ModEventListener() {
        NeoForge.EVENT_BUS.register(this);
    }

    @SuppressWarnings({"unused", "resource"})
    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {

        if (event.getEntity().level().isClientSide()) return;

        if (!FeywildConfig.spawnWithLexicon) return;

        var data = FeywildPlayerData.get(event.getEntity());
        if (!data.getBoolean("feywild_got_lexicon")) {
            event.getEntity().getInventory().add(new ItemStack(ModItems.FEYWILD_LEXICON.get()));
            data.putBoolean("feywild_got_lexicon", true);
        }
    }

    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent.Post event) {

        Player player = event.getEntity();

        if (player.level().isClientSide) {
            return;
        }

        if (player.isCreative() || player.isSpectator()) {
            return;
        }

        boolean hasFeyFlying =
                player.hasEffect(ModEffects.FEY_FLYING);

        CompoundTag data = player.getPersistentData();
        boolean grantedByFeywild =
                data.getBoolean(FEYWILD_GRANTED_FLIGHT);

        var abilities = player.getAbilities();

        if (!hasFeyFlying) {
            //fix for flying with other mods
            if (grantedByFeywild) {
                abilities.mayfly = false;
                abilities.flying = false;

                data.remove(FEYWILD_GRANTED_FLIGHT);
                player.onUpdateAbilities();
            }

            return;
        }

        MobEffectInstance effect =
                player.getEffect(ModEffects.FEY_FLYING);

        if (effect == null) {
            return;
        }

        int duration = effect.getDuration();

        if (duration < 20) {
            if (duration == 19) {
                player.level().playSound(null, player.blockPosition(), ModSounds.PIXIE_SPELL_CASTING_SHORT.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            }
            player.displayClientMessage(Component.literal("The magic of the pixie tiara is fading...").withStyle(ChatFormatting.LIGHT_PURPLE), true);
            player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 120, 0, false, false, true));
        }
    }
}
