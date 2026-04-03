package com.saphienyako.feywild.screen.widget;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.network.OpenBeeKnightMenuMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.PacketDistributor;

public class BeeKnightScreenButton extends Button {

    public static final int WIDTH = 89;
    public static final int HEIGHT = 22;

    private static final ResourceLocation BUTTON_TEXTURE = ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "textures/gui/button.png");

    private final Component textComponent;

    private final int entityId;
    public BeeKnightScreenButton(int x, int y, int entityId) {
        super(x, y, WIDTH, HEIGHT, Component.translatable("message.feywild.test"), b -> {}, l -> Component.empty());
        this.entityId = entityId;
        this.textComponent = Component.translatable("message.feywild.equipment");
    }

    @Override
    public void onPress() {
        PacketDistributor.sendToServer(new OpenBeeKnightMenuMessage(this.entityId));
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
