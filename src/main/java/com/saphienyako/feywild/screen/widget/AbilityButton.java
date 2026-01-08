package com.saphienyako.feywild.screen.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.network.FeywildNetwork;
import com.saphienyako.feywild.network.ToggleAbilityMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;


public class AbilityButton extends Button {

    public static final int WIDTH = 89;
    public static final int HEIGHT = 22;

    private static final ResourceLocation BUTTON_TEXTURE = new ResourceLocation(Feywild.MOD_ID, "textures/gui/button.png");

    private boolean abilityActive;

    private ITextComponent textComponent;

    private final int entityId;
    public AbilityButton(int x, int y, boolean abilityActive, int entityId) {
        super(x, y, WIDTH, HEIGHT, new TranslationTextComponent("") {
        }, b -> {
        });
        this.abilityActive = abilityActive;
        this.entityId = entityId;
        this.textComponent = abilityActive ? new TranslationTextComponent("message.feywild.ability_on") : new TranslationTextComponent("message.feywild.ability_off");
    }

    @Override
    public void onPress() {
        if (this.abilityActive) {
            this.abilityActive = false;
            this.textComponent = new TranslationTextComponent("message.feywild.ability_off");
            FeywildNetwork.sendToServer(new ToggleAbilityMessage(this.entityId,false));

        }
        else {
            this.abilityActive = true;
            this.textComponent = new TranslationTextComponent("message.feywild.ability_on");
            FeywildNetwork.sendToServer(new ToggleAbilityMessage(this.entityId,true));
        }
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        FontRenderer font = minecraft.font;
        minecraft.getTextureManager().getTexture(BUTTON_TEXTURE);
        blit(matrixStack,this.x, this.y, 0, 0, WIDTH, HEIGHT);
        drawString(matrixStack, font, this.textComponent,
                this.x + 22,
                this.y + (HEIGHT - font.lineHeight) / 2,
                0xFFFFFF);
    }
}
