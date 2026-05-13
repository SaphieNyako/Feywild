package com.saphienyako.feywild.screen.widget;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.network.FeywildNetwork;
import com.saphienyako.feywild.network.ToggleAbilityMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class AbilityButton extends Button {

    public static final int WIDTH = 89;
    public static final int HEIGHT = 22;

    private static final ResourceLocation BUTTON_TEXTURE = new ResourceLocation(Feywild.MOD_ID, "textures/gui/button.png");

    private boolean abilityActive;

    private Component textComponent;

    private final int entityId;
    public AbilityButton(int x, int y, boolean abilityActive, int entityId) {
        super(x, y, WIDTH, HEIGHT, Component.translatable("message.feywild.test"), b -> {}, l -> Component.empty());
        this.abilityActive = abilityActive;
        this.entityId = entityId;
        this.textComponent = abilityActive ? Component.translatable("message.feywild.ability_on") : Component.translatable("message.feywild.ability_off");
    }

    @Override
    public void onPress() {
        if (this.abilityActive) {
            this.abilityActive = false;
            this.textComponent = Component.translatable("message.feywild.ability_off");
            FeywildNetwork.sendToServer(new ToggleAbilityMessage(this.entityId,false));

        }
        else {
            this.abilityActive = true;
            this.textComponent = Component.translatable("message.feywild.ability_on");
            FeywildNetwork.sendToServer(new ToggleAbilityMessage(this.entityId,true));
        }
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
        graphics.pose().popPose();
    }
}
