package com.saphienyako.feywild.screen.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.network.FeywildNetwork;
import com.saphienyako.feywild.network.OpenQuestMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
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
    private final String questLineId;

    private final String backgroundName;

    private final int entyityId;

    public QuestButton(int x, int y, Screen screen, String questLineId, String backgroundName, boolean dismiss,  int entityId) {
        super(x, y, WIDTH, HEIGHT, Component.translatable("message.feywild.test"), b -> {});
        this.screen = screen;
        this.textComponent = Component.translatable("message.feywild.quest");
        this.dismiss = dismiss;
        this.questLineId = questLineId;
        this.backgroundName = backgroundName;
        this.entyityId = entityId;
    }

    @Override
    public void onPress() {
        FeywildNetwork.sendToServer(new OpenQuestMessage(questLineId, backgroundName, dismiss, entyityId));
        this.screen.onClose();
    }

    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;

        RenderSystem.setShaderTexture(0, BUTTON_TEXTURE);

        blit(poseStack, this.x, this.y, 0, 0, WIDTH, HEIGHT);

     //   poseStack.pushPose();
     //   poseStack.translate(0, 0, 10);
        int textWidth = font.width(this.textComponent);

        int textX = this.x + (WIDTH - textWidth) / 2;
        int textY = this.y + (HEIGHT - font.lineHeight) / 2;

        drawString(poseStack ,font, this.textComponent, textX, textY, 0xFFFFFF);

    //    poseStack.popPose();
    }
}
