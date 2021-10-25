package com.feywild.feywild.screens.widget;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.network.RequestItemSerializer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class ScrollWidget extends Button {

    public static int WIDTH = 64;
    public static int HEIGHT = 64;

    protected final Screen screen;
    protected final int idx;
    protected final ItemStack stack;

    public ScrollWidget(Screen screen, int x, int y, int idx, ItemStack stack) {
        super(x, y, WIDTH, HEIGHT, stack.getDisplayName(), b -> {});
        this.screen = screen;
        this.idx = idx;
        this.stack = stack;
    }

    public ItemStack getStack() {
        return this.stack;
    }

    @Override
    public void onPress() {
        FeywildMod.getNetwork().instance.sendToServer(new RequestItemSerializer.Message(this.idx, RequestItemSerializer.State.scrolls));
        this.screen.onClose();
    }

    public ResourceLocation getTexture() {
        return new ResourceLocation(FeywildMod.getInstance().modid, "textures/gui/begin_atlas.png");
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft.getInstance().getTextureManager().bind(getTexture());
        RenderSystem.color4f(1, 1, 1, 1);
        this.blit(matrixStack, this.x - 20, this.y - 16, idx * WIDTH, 0, 64, 64);
        if (this.isHovered(mouseX, mouseY)) {
            this.setBlitOffset(this.getBlitOffset() + 10);
            this.blit(matrixStack, this.x - 20, this.y - 16, idx * WIDTH, 64, 32, 32);
            this.setBlitOffset(this.getBlitOffset() - 10);
        }else
            Minecraft.getInstance().getItemRenderer().renderGuiItem(this.stack,this.x + 4,this.y + 4);
        }
    public boolean isHovered(int x, int y) {
        return this.x <= x && this.x + this.WIDTH >= x && this.y <= y && this.y + this.HEIGHT >= y;
    }
}
