package com.feywild.feywild.screens;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.container.DwarvenAnvilContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;

public class DwarvenAnvilScreen extends ContainerScreen<DwarvenAnvilContainer> {

    private final ResourceLocation TEXTURE = new ResourceLocation(FeywildMod.getInstance().modid, "textures/gui/dwarven_anvil_gui.png");
    
    private final DwarvenAnvilContainer container;

    public DwarvenAnvilScreen(DwarvenAnvilContainer container, PlayerInventory inventory, ITextComponent name) {
        super(container, inventory, name);
        this.container = container;
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(@Nonnull MatrixStack matrixStack, int x, int y) {
        drawString(matrixStack, Minecraft.getInstance().font, new TranslationTextComponent("screen.feywild.mana_amount", this.container.tile.getMana()), 118, 8, 0xffffff);
    }

    @Override
    protected void renderBg(@Nonnull MatrixStack matrixStack, float partialTicks, int x, int y) {
        //noinspection deprecation
        RenderSystem.color4f(1, 1, 1, 1);
        //noinspection ConstantConditions
        this.minecraft.getTextureManager().bind(this.TEXTURE);
        this.blit(matrixStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight); //might be wrong
        //position to place x, y position to get x, y width height and height is determined by the manalevel
        this.blit(matrixStack, this.leftPos + 13, this.topPos + 9, 176, 0, 11, 64 - (int) (this.container.tile.getMana() / 15.6f));
    }
}
