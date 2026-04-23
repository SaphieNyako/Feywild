package com.saphienyako.feywild.screen.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.network.FeywildNetwork;
import com.saphienyako.feywild.network.OpenBeeKnightMenuMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;


public class BeeKnightScreenButton extends Button {

    public static final int WIDTH = 89;
    public static final int HEIGHT = 22;

    private static final ResourceLocation BUTTON_TEXTURE = new ResourceLocation(Feywild.MOD_ID, "textures/gui/button.png");

    private final Component textComponent;

    private final int entityId;
    public BeeKnightScreenButton(int x, int y, int entityId) {
        super(x, y, WIDTH, HEIGHT, Component.translatable("message.feywild.test"), b -> {});
        this.entityId = entityId;
        this.textComponent = Component.translatable("message.feywild.equipment");
    }

    @Override
    public void onPress() {
        FeywildNetwork.sendToServer(new OpenBeeKnightMenuMessage(this.entityId));
    }

    @Override
    public void renderButton(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;

        RenderSystem.setShaderTexture(0, BUTTON_TEXTURE);
        blit(poseStack, this.x, this.y, 0, 0, WIDTH, HEIGHT);

        drawString(poseStack, font, this.textComponent,
                this.x + 22,
                this.y + (HEIGHT - font.lineHeight) / 2,
                0xFFFFFF);
    }
}
