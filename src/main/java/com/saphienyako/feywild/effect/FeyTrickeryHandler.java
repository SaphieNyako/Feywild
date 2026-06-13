package com.saphienyako.feywild.effect;

import com.saphienyako.feywild.Feywild;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;

@EventBusSubscriber(modid = Feywild.MOD_ID, value = Dist.CLIENT)
public class FeyTrickeryHandler {


    @SubscribeEvent
    public static void onMovementInput(MovementInputUpdateEvent event) {

        if (!(event.getEntity() instanceof LocalPlayer player)) return;
        if (!player.hasEffect(ModEffects.FEY_TRICKERY)) return;

        var input = event.getInput();

        float forward = input.forwardImpulse;
        float strafe = input.leftImpulse;

        input.forwardImpulse = -forward;
        input.leftImpulse = -strafe;

        boolean jump = input.jumping;
        input.jumping = input.shiftKeyDown;
        input.shiftKeyDown = jump;
    }

}
