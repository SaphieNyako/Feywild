package com.saphienyako.feywild.screen.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.network.FeywildNetwork;
import com.saphienyako.feywild.network.ToggleFollowPlayerMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class FollowButton extends Button {

    public static final int WIDTH = 89;
    public static final int HEIGHT = 22;

    private static final ResourceLocation BUTTON_TEXTURE = new ResourceLocation(Feywild.MOD_ID, "textures/gui/button.png");

    private boolean followPlayer;

    private Component textComponent;

    private final BlockPos currentBlockPos;

    private final int entityId;
    public FollowButton(int x, int y, boolean followPlayer, int entityId, BlockPos currentBlockPos) {
        super(x, y, WIDTH, HEIGHT, new TranslatableComponent("message.feywild.test"), b -> {});
        this.followPlayer = followPlayer;
        this.entityId = entityId;
        this.currentBlockPos = currentBlockPos;
        this.textComponent = followPlayer ? new TranslatableComponent("message.feywild.follow_on") : new TranslatableComponent("message.feywild.follow_off");
    }

    @Override
    public void onPress() {
        if (this.followPlayer) {
            this.followPlayer = false;
            this.textComponent = new TranslatableComponent("message.feywild.follow_off");
            FeywildNetwork.sendToServer(new ToggleFollowPlayerMessage(this.entityId,false, this.currentBlockPos));

        }
        else {
            this.followPlayer = true;
            this.textComponent = new TranslatableComponent("message.feywild.follow_on");
            FeywildNetwork.sendToServer(new ToggleFollowPlayerMessage(this.entityId,true, this.currentBlockPos));
        }
    }


    @Override
    public void renderButton(@NotNull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;

        RenderSystem.setShaderTexture(0, BUTTON_TEXTURE);

        blit(poseStack, this.x, this.y, 0, 0, WIDTH, HEIGHT);

        drawString(
                poseStack,
                font,
                this.textComponent,
                this.x + 22,
                this.y + (HEIGHT - font.lineHeight) / 2,
                0xFFFFFF
        );
    }
}
