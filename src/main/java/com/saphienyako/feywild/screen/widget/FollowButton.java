package com.saphienyako.feywild.screen.widget;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.network.ToggleFollowPlayerMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.jetbrains.annotations.NotNull;

public class FollowButton extends Button {

    public static final int WIDTH = 89;
    public static final int HEIGHT = 22;

    private static final Identifier BUTTON_TEXTURE = Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "textures/gui/button.png");

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
    public void onPress(@NotNull InputWithModifiers input) {
        if (this.followPlayer) {
            this.followPlayer = false;
            this.textComponent = Component.translatable("message.feywild.follow_off");
            ClientPacketDistributor.sendToServer(new ToggleFollowPlayerMessage(this.entityId,false, this.currentBlockPos));

        }
        else {
            this.followPlayer = true;
            this.textComponent = Component.translatable("message.feywild.follow_on");
            ClientPacketDistributor.sendToServer(new ToggleFollowPlayerMessage(this.entityId,true, this.currentBlockPos));
        }
    }


    @Override
    protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;

        graphics.blit(RenderPipelines.GUI_TEXTURED, BUTTON_TEXTURE, this.getX(), this.getY(), 0, 0, WIDTH, HEIGHT, 256, 256);

        int textWidth = font.width(this.textComponent);
        int textX = this.getX() + (WIDTH - textWidth) / 2;
        int textY = this.getY() + (HEIGHT - font.lineHeight) / 2;

        graphics.text(font, this.textComponent, textX, textY, 0xFFFFFFFF, true);
    }
}
