package com.saphienyako.feywild.events;

import com.saphienyako.feywild.config.ModConfig;
import com.saphienyako.feywild.item.ModItems;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventListener {

    @SubscribeEvent
    public void playerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.getEntity().level.isClientSide) {
            ServerPlayerEntity player = (ServerPlayerEntity) event.getEntity();

            if (!FeywildPlayerData.get(player).getBoolean("feywild_got_lexicon")
                    && ModConfig.COMMON.spawn_with_lexicon.get()) {
                player.inventory.add(new ItemStack(ModItems.FEYWILD_LEXICON.get()));
                FeywildPlayerData.get(player).putBoolean("feywild_got_lexicon", true);
            }
        }
    }
}
