package com.saphienyako.feywild.screen.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.network.DismissEntityMessage;
import com.saphienyako.feywild.network.FeywildNetwork;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class DismissButton extends Button {

    public static final int WIDTH = 89;
    public static final int HEIGHT = 22;

    private static final ResourceLocation BUTTON_TEXTURE = new ResourceLocation(Feywild.MOD_ID, "textures/gui/button.png");

    protected final Screen screen;

    private final Component textComponent;

    private final int entityId;

    public DismissButton(int x, int y, Screen screen, int entityId) {
        super(x, y, WIDTH, HEIGHT, new TranslatableComponent("message.feywild.test"), b -> {});
        this.screen = screen;
        this.entityId = entityId;
        this.textComponent = new TranslatableComponent("message.feywild.dismiss");
    }

    @Override
    public void onPress() {
        FeywildNetwork.sendToServer(new DismissEntityMessage(this.entityId));
        this.screen.onClose();
    }

    @Override
    public void renderButton(@NotNull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
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
