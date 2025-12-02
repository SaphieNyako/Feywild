package com.saphienyako.feywild.events;

import com.saphienyako.feywild.config.ModConfig;
import com.saphienyako.feywild.item.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventListener {

    @SubscribeEvent
    public void playerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.getEntity().level.isClientSide) {
           if (!FeywildPlayerData.get(event.getEntity()).getBoolean("feywild_got_lexicon") && ModConfig.COMMON.spawn_with_lexicon.get()) {
               event.getEntity().getInventory().add(new ItemStack(ModItems.FEYWILD_LEXICON.get()));
               FeywildPlayerData.get(event.getEntity()).putBoolean("feywild_got_lexicon", true);
            }
        }
    }
}
