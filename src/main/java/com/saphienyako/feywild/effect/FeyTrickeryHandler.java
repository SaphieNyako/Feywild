package com.saphienyako.feywild.effect;

import com.saphienyako.feywild.Feywild;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Input;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;

@EventBusSubscriber(modid = Feywild.MOD_ID, value = Dist.CLIENT)
public class FeyTrickeryHandler {

    //TODO check if this works...
    @SubscribeEvent
    public static void onMovementInput(MovementInputUpdateEvent event) {
        if (!(event.getEntity() instanceof LocalPlayer player)) {
            return;
        }

        if (!(player.input instanceof FeyTrickeryInput)) {
            player.input = new FeyTrickeryInput(player.input, player);
        }
    }

}
