package com.saphienyako.feywild.events;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.config.FeywildConfig;
import com.saphienyako.feywild.item.ModItems;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class ModEventListener {

    public ModEventListener() {
        // Register the instance with NeoForge's EVENT_BUS
        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        // Skip client side
        if (event.getEntity().level().isClientSide()) return;

        // Only give lexicon if config allows it
        if (!FeywildConfig.spawnWithLexicon) return;

        // Check if player already received it
        var data = FeywildPlayerData.get(event.getEntity());
        if (!data.getBoolean("feywild_got_lexicon")) {
            event.getEntity().getInventory().add(new ItemStack(ModItems.FEYWILD_LEXICON.get()));
            data.putBoolean("feywild_got_lexicon", true);
        }
    }
}
