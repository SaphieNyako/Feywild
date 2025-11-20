package com.saphienyako.feywild.screen.widget;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.base.FeyBase;
import com.saphienyako.feywild.network.FeywildNetwork;
import com.saphienyako.feywild.network.ToggleFollowPlayerMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class FollowButton extends Button {

    public static final int WIDTH = 89;
    public static final int HEIGHT = 22;

    private static final ResourceLocation BUTTON_TEXTURE = new ResourceLocation(Feywild.MOD_ID, "textures/gui/button.png");

    private boolean followPlayer;

    private Component textComponent;

    private final BlockPos currentBlockPos;

    private final int entityId;
    public FollowButton(int x, int y, boolean followPlayer, int entityId, BlockPos currentBlockPos) {
        super(x, y, WIDTH, HEIGHT, Component.translatable("message.feywild.test"), b -> {}, l -> Component.empty());
        this.followPlayer = followPlayer;
        this.entityId = entityId;
        this.currentBlockPos = currentBlockPos;
        this.textComponent = followPlayer ? Component.translatable("message.feywild.follow_on") : Component.translatable("message.feywild.follow_off");
    }

    @Override
    public void onPress() {
        if (this.followPlayer) {
            this.followPlayer = false;
            this.textComponent = Component.translatable("message.feywild.follow_off");
            FeywildNetwork.sendToServer(new ToggleFollowPlayerMessage(this.entityId,false, this.currentBlockPos));

        }
        else {
            this.followPlayer = true;
            this.textComponent = Component.translatable("message.feywild.follow_on");
            FeywildNetwork.sendToServer(new ToggleFollowPlayerMessage(this.entityId,true, this.currentBlockPos));
        }
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
