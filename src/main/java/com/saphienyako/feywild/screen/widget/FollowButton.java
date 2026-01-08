package com.saphienyako.feywild.screen.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.network.FeywildNetwork;
import com.saphienyako.feywild.network.ToggleFollowPlayerMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;

public class FollowButton extends Button {

    public static final int WIDTH = 89;
    public static final int HEIGHT = 22;

    private static final ResourceLocation BUTTON_TEXTURE = new ResourceLocation(Feywild.MOD_ID, "textures/gui/button.png");

    private boolean followPlayer;

    private ITextComponent textComponent;

    private final BlockPos currentBlockPos;

    private final int entityId;
    public FollowButton(int x, int y, boolean followPlayer, int entityId, BlockPos currentBlockPos) {
        super(x, y, WIDTH, HEIGHT, new TranslationTextComponent(""), b -> {});
        this.followPlayer = followPlayer;
        this.entityId = entityId;
        this.currentBlockPos = currentBlockPos;
        this.textComponent = followPlayer ? new TranslationTextComponent("message.feywild.follow_on") : new TranslationTextComponent("message.feywild.follow_off");
    }

    @Override
    public void onPress() {
        if (this.followPlayer) {
            this.followPlayer = false;
            this.textComponent = new TranslationTextComponent("message.feywild.follow_off");
            FeywildNetwork.sendToServer(new ToggleFollowPlayerMessage(this.entityId,false, this.currentBlockPos));

        }
        else {
            this.followPlayer = true;
            this.textComponent = new TranslationTextComponent("message.feywild.follow_on");
            FeywildNetwork.sendToServer(new ToggleFollowPlayerMessage(this.entityId,true, this.currentBlockPos));
        }
    }


    @Override
    public void renderButton(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        FontRenderer font = minecraft.font;
        minecraft.getTextureManager().getTexture(BUTTON_TEXTURE);


        blit(matrixStack, this.x, this.y, 0, 0, WIDTH, HEIGHT);

        drawString(
                matrixStack,
                font,
                this.textComponent,
                this.x + 22,
                this.y + (HEIGHT - font.lineHeight) / 2,
                0xFFFFFF
        );
    }
}
