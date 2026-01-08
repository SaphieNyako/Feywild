package com.saphienyako.feywild.network.handler;

import com.saphienyako.feywild.entity.Alignment;
import com.saphienyako.feywild.screen.FeyMenuScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

public class OpenMenuMessageClientHandler {

    public static void openMenu(Component name, int entityId, Alignment alignment, boolean followingPlayer, BlockPos currentBlockPos, boolean abilityActive, boolean voiceActive) {
        Minecraft.getInstance().setScreen(new FeyMenuScreen(
                name,
                entityId,
                alignment,
                followingPlayer,
                currentBlockPos,
                abilityActive,
                voiceActive
        ));
    }

}
