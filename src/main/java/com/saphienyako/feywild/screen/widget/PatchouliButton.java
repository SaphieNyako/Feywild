package com.saphienyako.feywild.screen.widget;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.network.OpenPatchouliBookMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.jetbrains.annotations.NotNull;

public class PatchouliButton extends Button {

    public static final int WIDTH = 89;
    public static final int HEIGHT = 22;

    private static final Identifier BUTTON_TEXTURE = Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "textures/gui/button.png");

    protected final Screen screen;

    private final Component textComponent;


    public PatchouliButton(int x, int y, Screen screen) {
        super(x, y, WIDTH, HEIGHT, Component.translatable("message.feywild.test"), b -> {}, l -> Component.empty());
        this.screen = screen;
        this.textComponent = Component.translatable("message.feywild.guide_book");
    }

    @Override
    public void onPress(@NotNull InputWithModifiers input) {
        ClientPacketDistributor.sendToServer(new OpenPatchouliBookMessage(Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "feywild_lexicon")));
        this.screen.onClose();
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
