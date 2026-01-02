package com.saphienyako.feywild.screen.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.network.FeywildNetwork;
import com.saphienyako.feywild.network.ToggleVoiceMessage;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

public class VolumeButton extends Button {

    public static final int WIDTH = 38;
    public static final int HEIGHT = 29;

    private static final float SCALE = 0.6f;

    private static final ResourceLocation BUTTON_VOLUME_TEXTURE = new ResourceLocation(Feywild.MOD_ID, "textures/gui/button_volume.png");


    private boolean voiceActive;

    private final int entityId;

    public VolumeButton(int x, int y, boolean voiceActive, int entityId) {
        super(x, y, (int)(WIDTH * SCALE),
                (int)(HEIGHT * SCALE), new TranslatableComponent("message.feywild.test"), b -> {});
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
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        poseStack.pushPose();
        poseStack.translate(this.x, this.y, 0);
        poseStack.scale(SCALE, SCALE, 1.0f);

        RenderSystem.setShaderTexture(0, BUTTON_VOLUME_TEXTURE);
        int offset = this.voiceActive ? 0 : 38;
        blit(poseStack, 0, 0, offset, 0, WIDTH, HEIGHT);

        poseStack.popPose();
    }
}
