package com.feywild.feywild.screens;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.container.PixieContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class PixieScreen extends ContainerScreen<PixieContainer> {

    private final ResourceLocation GUI = new ResourceLocation(FeywildMod.MOD_ID,
            "textures/gui/pixie_quest_gui.png");

    private PixieContainer container;

    public PixieScreen(PixieContainer container, PlayerInventory inventory, ITextComponent name) {
        super(container, inventory, name);
        this.container = container;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {

        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override //drawGuiContainerForegroundLayer
    protected void renderLabels(MatrixStack matrixStack, int x, int y) {

        //set text, position x, y, color
        drawString(matrixStack, Minecraft.getInstance().font, new TranslationTextComponent("spring_quest_pixie.feywild.quest_01_message_01"), 9, 9, 0xffffff);
        drawString(matrixStack, Minecraft.getInstance().font, new TranslationTextComponent("spring_quest_pixie.feywild.quest_01_message_02"), 9, 18, 0xffffff);
        drawString(matrixStack, Minecraft.getInstance().font, new TranslationTextComponent("spring_quest_pixie.feywild.quest_01_message_03"), 9, 27, 0xffffff);
        drawString(matrixStack, Minecraft.getInstance().font, new TranslationTextComponent("spring_quest_pixie.feywild.quest_01_message_04"), 9, 36, 0xffffff);
        drawString(matrixStack, Minecraft.getInstance().font, new TranslationTextComponent("spring_quest_pixie.feywild.quest_01_message_05"), 9, 45, 0xffffff);
        drawString(matrixStack, Minecraft.getInstance().font, new TranslationTextComponent("spring_quest_pixie.feywild.quest_01_message_06"), 9, 54, 0xffffff);
        drawString(matrixStack, Minecraft.getInstance().font, new TranslationTextComponent("spring_quest_pixie.feywild.quest_01_message_07"), 9, 63, 0xffffff);
        drawString(matrixStack, Minecraft.getInstance().font, new TranslationTextComponent("spring_quest_pixie.feywild.quest_01_message_08"), 9, 72, 0xffffff);
        drawString(matrixStack, Minecraft.getInstance().font, new TranslationTextComponent("spring_quest_pixie.feywild.quest_01_message_09"), 9, 81, 0xffffff);

    }

    @Override //drawGuiContainerBackgroundLayer
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {

        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bind(GUI);
        int i = this.leftPos;
        int j = this.topPos;
        this.blit(matrixStack, i, j, 0, 0, 256, this.imageHeight); //might be wrong

    }
}
