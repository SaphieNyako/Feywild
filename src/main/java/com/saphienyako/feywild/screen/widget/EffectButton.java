package com.saphienyako.feywild.screen.widget;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.effect.ModEffects;
import com.saphienyako.feywild.network.DismissEntityMessage;
import com.saphienyako.feywild.network.GivePlayerEffectMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.PacketDistributor;

public class EffectButton extends Button {

    public static final int WIDTH = 89;
    public static final int HEIGHT = 22;

    private static final ResourceLocation BUTTON_TEXTURE = ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "textures/gui/button.png");

    protected final Screen screen;

    private final Component textComponent;

    private final int entityId;

    public EffectButton(int x, int y, Screen screen, int entityId) {
        super(x, y, WIDTH, HEIGHT, Component.translatable("message.feywild.test"), b -> {
        }, l -> Component.empty());
        this.screen = screen;
        this.entityId = entityId;
        this.textComponent = Component.translatable("message.feywild.effect");
    }

    @Override
    public void onPress() {
        PacketDistributor.sendToServer(new GivePlayerEffectMessage(18000, 1, entityId));
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

      //  graphics.drawString(Minecraft.getInstance().font, this.textComponent, this.getX() + 22, this.getY() + ((HEIGHT - font.lineHeight) / 2), 0xFFFFFF, true);
        graphics.pose().popPose();
    }
}
