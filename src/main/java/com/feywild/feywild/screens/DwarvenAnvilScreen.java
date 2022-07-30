package com.feywild.feywild.screens;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.menu.DwarvenAnvilMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import org.moddingx.libx.render.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.Nonnull;

public class DwarvenAnvilScreen extends AbstractContainerScreen<DwarvenAnvilMenu> {

    private final ResourceLocation TEXTURE = new ResourceLocation(FeywildMod.getInstance().modid, "textures/gui/dwarven_anvil_gui.png");

    private final DwarvenAnvilMenu menu;

    public DwarvenAnvilScreen(DwarvenAnvilMenu menu, Inventory container, Component name) {
        super(menu, container, name);
        this.menu = menu;
    }

    @Override
    public void render(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(poseStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(@Nonnull PoseStack poseStack, int x, int y) {
        drawString(poseStack, Minecraft.getInstance().font, new TranslatableComponent("screen.feywild.mana_amount", this.menu.getBlockEntity().getMana()), 118, 8, 0xffffff);
    }

    @Override
    protected void renderBg(@Nonnull PoseStack poseStack, float partialTicks, int x, int y) {
        RenderHelper.resetColor();
        RenderSystem.setShaderTexture(0, this.TEXTURE);
        this.blit(poseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        this.blit(poseStack, this.leftPos + 13, this.topPos + 9, 176, 0, 11, 64 - (int) (this.menu.getBlockEntity().getMana() / 15.6f));
    }
}
