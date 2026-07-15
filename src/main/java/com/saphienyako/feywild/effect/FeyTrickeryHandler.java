package com.saphienyako.feywild.effect;

import com.saphienyako.feywild.Feywild;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = Feywild.MOD_ID, value = Dist.CLIENT)
public class FeyTrickeryHandler {


    @SubscribeEvent
    public static void onMovementInput(MovementInputUpdateEvent event) {

        if (!(event.getEntity() instanceof LocalPlayer player)) return;


        if (!ModEffects.FEY_TRICKERY.isPresent()) {
            Feywild.LOGGER.error(
                    "FEY_TRICKERY is absent on the client registry!"
            );
            return;
        }

        if (!player.hasEffect(ModEffects.FEY_TRICKERY.get())) return;

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
