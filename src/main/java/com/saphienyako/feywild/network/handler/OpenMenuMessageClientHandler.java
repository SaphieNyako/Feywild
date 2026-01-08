package com.saphienyako.feywild.network.handler;

import com.saphienyako.feywild.network.OpenMenuMessage;
import com.saphienyako.feywild.screen.FeyMenuScreen;
import net.minecraft.client.Minecraft;

public class OpenMenuMessageClientHandler {

    public static void openMenu(OpenMenuMessage msg) {
        if (msg.entityId() != -1) {
            Minecraft mc = Minecraft.getInstance();
            mc.setScreen(new FeyMenuScreen(
                    msg.entityId(),
                    msg.alignment(),
                    msg.followingPlayer(),
                    msg.currentBlockPos(),
                    msg.abilityActive(),
                    msg.voiceActive()
            ));
        }
    }

}
