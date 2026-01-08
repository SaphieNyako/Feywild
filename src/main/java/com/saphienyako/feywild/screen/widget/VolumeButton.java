package com.saphienyako.feywild.screen.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.network.FeywildNetwork;
import com.saphienyako.feywild.network.ToggleVoiceMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;

public class VolumeButton extends Button {

    public static final int WIDTH = 38;
    public static final int HEIGHT = 29;

    private static final float SCALE = 0.6f;

    private static final ResourceLocation BUTTON_VOLUME_TEXTURE = new ResourceLocation(Feywild.MOD_ID, "textures/gui/button_volume.png");


    private boolean voiceActive;

    private final int entityId;

    public VolumeButton(int x, int y, boolean voiceActive, int entityId) {
        super(x, y, (int)(WIDTH * SCALE),
                (int)(HEIGHT * SCALE), new TranslationTextComponent(""), b -> {});
        this.voiceActive = voiceActive;
        this.entityId = entityId;
    }

    @Override
    public void onPress() {
        if (this.voiceActive) {
            this.voiceActive = false;
            FeywildNetwork.sendToServer(new ToggleVoiceMessage(this.entityId,false));

        }
        else {
            this.voiceActive = true;
            FeywildNetwork.sendToServer(new ToggleVoiceMessage(this.entityId,true));
        }
    }


    @Override
    public void renderButton(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().getTexture(BUTTON_VOLUME_TEXTURE);
        int offset = this.voiceActive ? 0 : 38;

        //Manual in 1.16.5
        int scaledWidth = (int) (WIDTH * SCALE);
        int scaledHeight = (int) (HEIGHT * SCALE);

        blit(matrixStack, 0, 0, offset, 0, scaledWidth, scaledHeight);
    }
}
