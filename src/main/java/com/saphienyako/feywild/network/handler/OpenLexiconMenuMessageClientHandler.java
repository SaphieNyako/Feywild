package com.saphienyako.feywild.network.handler;

import com.saphienyako.feywild.network.OpenLexiconMenuMessage;
import com.saphienyako.feywild.network.OpenMenuMessage;
import com.saphienyako.feywild.screen.LexiconMenuScreen;
import net.minecraft.client.Minecraft;

public class OpenLexiconMenuMessageClientHandler {
    public static void openMenu(int entityId) {
            Minecraft mc = Minecraft.getInstance();
            mc.setScreen(new LexiconMenuScreen(entityId));
    }

}
