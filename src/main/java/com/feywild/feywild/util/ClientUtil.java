package com.feywild.feywild.util;

import com.feywild.feywild.quest.MessageQuest;
import com.feywild.feywild.screens.PixieScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.StringTextComponent;

import java.util.List;

public class ClientUtil {

    public static void openQuestScreen(List<MessageQuest> quest, int id) {
        Minecraft.getInstance().setScreen(new PixieScreen(new StringTextComponent("Fey Quest"), quest, id));
    }
}
