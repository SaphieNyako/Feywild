package com.saphienyako.feywild.screen.widget;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.network.FeywildNetwork;
import com.saphienyako.feywild.network.OpenQuestMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class QuestButton extends Button {

    public static final int WIDTH = 89;
    public static final int HEIGHT = 22;

    private static final ResourceLocation BUTTON_TEXTURE = new ResourceLocation(Feywild.MOD_ID, "textures/gui/button.png");

    protected final Screen screen;

    private final Component textComponent;

    private final boolean dismiss;

    public QuestButton(int x, int y, Screen screen, boolean dismiss) {
        super(x, y, WIDTH, HEIGHT, Component.translatable("message.feywild.test"), b -> {}, l -> Component.empty());
        this.screen = screen;
        this.textComponent = Component.translatable("message.feywild.quest");
        this.dismiss = dismiss;
    }

    @Override
    public void onPress() {
        FeywildNetwork.sendToServer(new OpenQuestMessage("sprite", "hexen_quest", dismiss));
        this.screen.onClose();
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;

        graphics.blit(BUTTON_TEXTURE, this.getX(), this.getY(), 0, 0, WIDTH, HEIGHT);

        graphics.pose().pushPose();
        graphics.pose().translate(0, 0, 10);
        int textWidth = font.width(this.textComponent);

        int textX = this.getX() + (WIDTH - textWidth) / 2;
        int textY = this.getY() + (HEIGHT - font.lineHeight) / 2;

        graphics.drawString(font, this.textComponent, textX, textY, 0xFFFFFF, true);
        //TODO 1.19 1.20 center text for all buttons
        //  graphics.drawString(Minecraft.getInstance().font, this.textComponent, this.getX() + 22, this.getY() + ((HEIGHT - font.lineHeight) / 2), 0xFFFFFF, true);
        graphics.pose().popPose();
    }
}
