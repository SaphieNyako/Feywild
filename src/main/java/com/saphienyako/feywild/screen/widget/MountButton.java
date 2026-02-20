package com.saphienyako.feywild.screen.widget;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.effect.ModEffects;
import com.saphienyako.feywild.network.GivePlayerEffectMessage;
import com.saphienyako.feywild.network.MountTreeEntMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.PacketDistributor;

public class MountButton extends Button {

    public static final int WIDTH = 89;
    public static final int HEIGHT = 22;

    private static final ResourceLocation BUTTON_TEXTURE = ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "textures/gui/button.png");

    protected final Screen screen;

    private final Component textComponent;

    private final int entityId;

    public MountButton(int x, int y, Screen screen, int entityId) {
        super(x, y, WIDTH, HEIGHT, Component.translatable("message.feywild.test"), b -> {
        }, l -> Component.empty());
        this.screen = screen;
        this.entityId = entityId;
        this.textComponent = Component.translatable("message.feywild.mount");
    }

    @Override
    public void onPress() {
        PacketDistributor.sendToServer(new MountTreeEntMessage(entityId));
        this.screen.onClose();
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;

        graphics.blit(BUTTON_TEXTURE, this.getX(), this.getY(), 0, 0, WIDTH, HEIGHT);

        graphics.pose().pushPose();
        graphics.pose().translate(0, 0, 10);

        graphics.drawString(Minecraft.getInstance().font, this.textComponent, this.getX() + 22, this.getY() + ((HEIGHT - font.lineHeight) / 2), 0xFFFFFF, true);
        graphics.pose().popPose();
    }
}
