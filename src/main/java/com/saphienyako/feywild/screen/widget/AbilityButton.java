package com.saphienyako.feywild.screen.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.network.FeywildNetwork;
import com.saphienyako.feywild.network.ToggleAbilityMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class AbilityButton extends Button {

    public static final int WIDTH = 89;
    public static final int HEIGHT = 22;

    private static final ResourceLocation BUTTON_TEXTURE = new ResourceLocation(Feywild.MOD_ID, "textures/gui/button.png");

    private boolean abilityActive;

    private Component textComponent;

    private final int entityId;
    public AbilityButton(int x, int y, boolean abilityActive, int entityId) {
        super(x, y, WIDTH, HEIGHT, Component.translatable("message.feywild.test"), b -> {});
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
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;
        RenderSystem.setShaderTexture(0, BUTTON_TEXTURE);

        blit(poseStack, this.x, this.y, 0, 0, WIDTH, HEIGHT);

        int textWidth = font.width(this.textComponent);

        int textX = this.x + (WIDTH - textWidth) / 2;
        int textY = this.y + (HEIGHT - font.lineHeight) / 2;

        drawString(poseStack ,font, this.textComponent, textX, textY, 0xFFFFFF);
    }
}
