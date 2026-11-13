package com.saphienyako.feywild.screen.widget;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.network.ToggleVoiceMessage;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.jetbrains.annotations.NotNull;

public class VolumeButton extends Button {

    public static final int WIDTH = 38;
    public static final int HEIGHT = 29;
    private static final float SCALE = 0.6f;
    private static final Identifier BUTTON_VOLUME_TEXTURE = Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "textures/gui/button_volume.png");

    private boolean voiceActive;
    private final int entityId;

    public VolumeButton(int x, int y, boolean voiceActive, int entityId) {
        super(x, y, (int)(WIDTH * SCALE), (int)(HEIGHT * SCALE), Component.translatable("message.feywild.test"), b -> {}, l -> Component.empty());
        this.voiceActive = voiceActive;
        this.entityId = entityId;
    }

    @Override
    public void onPress(@NotNull InputWithModifiers input) {
        if (this.voiceActive) {
            this.voiceActive = false;
            ClientPacketDistributor.sendToServer(new ToggleVoiceMessage(this.entityId,false));
        }
        else {
            this.voiceActive = true;
            ClientPacketDistributor.sendToServer(new ToggleVoiceMessage(this.entityId,true));
        }
    }

    @Override
    protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        graphics.pose().pushMatrix();

        graphics.pose().translate(this.getX(), this.getY());
        graphics.pose().scale(SCALE, SCALE);

        int offset = this.voiceActive ? 0 : WIDTH;

        graphics.blit(RenderPipelines.GUI_TEXTURED, BUTTON_VOLUME_TEXTURE, 0, 0, offset, 0, WIDTH, HEIGHT, 76, 29);

        graphics.pose().popMatrix();
    }
}
