package com.saphienyako.feywild.screen.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.network.DismissEntityMessage;
import com.saphienyako.feywild.network.FeywildNetwork;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;

public class DismissButton extends Button {

    public static final int WIDTH = 89;
    public static final int HEIGHT = 22;

    private static final ResourceLocation BUTTON_TEXTURE = new ResourceLocation(Feywild.MOD_ID, "textures/gui/button.png");

    protected final Screen screen;

    private final ITextComponent textComponent;

    private final int entityId;

    public DismissButton(int x, int y, Screen screen, int entityId) {
        super(x, y, WIDTH, HEIGHT, new TranslationTextComponent(""), b -> {});
        this.screen = screen;
        this.entityId = entityId;
        this.textComponent = new TranslationTextComponent("message.feywild.dismiss");
    }

    @Override
    public void onPress() {
        FeywildNetwork.sendToServer(new DismissEntityMessage(this.entityId));
        this.screen.onClose();
    }

    @Override
    public void renderButton(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
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
