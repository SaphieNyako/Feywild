package com.saphienyako.feywild.screen.widget;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.network.ToggleFollowPlayerMessage;
import com.saphienyako.feywild.network.ToggleVoiceMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.PacketDistributor;

public class VolumeButton extends Button {

    public static final int WIDTH = 38;
    public static final int HEIGHT = 29;

    private static final float SCALE = 0.6f;

    private static final ResourceLocation BUTTON_VOLUME_TEXTURE = ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "textures/gui/button_volume.png");


    private boolean voiceActive;

    private final int entityId;

    public VolumeButton(int x, int y, boolean voiceActive, int entityId) {
        super(x, y, (int)(WIDTH * SCALE),
                (int)(HEIGHT * SCALE), Component.translatable("message.feywild.test"), b -> {}, l -> Component.empty());
        this.voiceActive = voiceActive;
        this.entityId = entityId;
    }

    @Override
    public void onPress() {
        if (this.voiceActive) { //if true go set false on click
            this.voiceActive = false;

            PacketDistributor.sendToServer(new ToggleVoiceMessage(this.entityId,false));

        }
        else {
            this.voiceActive = true;

            PacketDistributor.sendToServer(new ToggleVoiceMessage(this.entityId,true));
        }
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        graphics.pose().pushPose();

        graphics.pose().translate(this.getX(), this.getY(), 0);
        graphics.pose().scale(SCALE, SCALE, 1.0f);

        int offset = this.voiceActive ? 0 : 38;

        graphics.blit(
                BUTTON_VOLUME_TEXTURE,
                0,
                0,
                offset,
                0,
                WIDTH,
                HEIGHT
        );

        graphics.pose().popPose();

    }
}
