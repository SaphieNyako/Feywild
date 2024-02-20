package com.feywild.feywild.screens.widget;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.network.quest.SelectQuestMessage;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.quest.util.SelectableQuest;
import com.feywild.feywild.util.FeywildTextProcessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class SelectQuestWidget extends Button {

    public static final int WIDTH = 210;
    public static final int HEIGHT = 65;
    
    private static final Map<Alignment, ResourceLocation> TEXTURES3 = Map.of(
            Alignment.SPRING, FeywildMod.getInstance().resource("textures/gui/spring_quest_background_03.png"),
            Alignment.SUMMER, FeywildMod.getInstance().resource("textures/gui/summer_quest_background_03.png"),
            Alignment.AUTUMN, FeywildMod.getInstance().resource("textures/gui/autumn_quest_background_03.png"),
            Alignment.WINTER, FeywildMod.getInstance().resource("textures/gui/winter_quest_background_03.png")
    );
    
    private final Alignment alignment;
    private final SelectableQuest quest;
    private final ItemStack iconStack;

    public SelectQuestWidget(int x, int y, SelectableQuest quest, Alignment alignment) {
        super(x, y, WIDTH, HEIGHT, FeywildTextProcessor.INSTANCE.processLine(quest.display().title), b -> {}, l -> Component.empty());
        this.quest = quest;
        this.iconStack = new ItemStack(quest.icon());
        this.alignment = alignment;
    }

    @Override
    public void onPress() {
        super.onPress();
        FeywildMod.getNetwork().channel.sendToServer(new SelectQuestMessage(this.quest.id()));
    }

    @Override
    public void renderWidget(@Nonnull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;
        
        graphics.blit(TEXTURES3.get(alignment), this.getX(), this.getY(), 0, 0, WIDTH, HEIGHT);
        
        graphics.pose().pushPose();
        graphics.pose().translate(0, 0, 10);
        graphics.renderFakeItem(this.iconStack, this.getX() + 20, this.getY() + (this.height - 16) / 2);
        
        graphics.drawString(Minecraft.getInstance().font, quest.display().title, this.getX() + 38, this.getY() + ((HEIGHT - font.lineHeight) / 2), 0xFFFFFF, true);
        graphics.pose().popPose();
    }
}
