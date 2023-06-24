package com.feywild.feywild.screens.widget;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.network.RequestItemMessage;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.moddingx.libx.render.RenderHelper;

import javax.annotation.Nonnull;

public class BookWidget extends Button {

    public static final int WIDTH = 25;
    public static final int HEIGHT = 25;

    protected final Screen screen;
    protected final int idx;
    protected final ItemStack stack;

    public BookWidget(Screen screen, int x, int y, int idx, ItemStack stack) {
        super(x, y, WIDTH, HEIGHT, stack.getDisplayName(), b -> {}, l -> Component.empty());
        this.screen = screen;
        this.idx = idx;
        this.stack = stack;
    }

    public ItemStack getStack() {
        return this.stack;
    }

    @Override
    public void onPress() {
        super.onPress();
        FeywildMod.getNetwork().channel.sendToServer(new RequestItemMessage(RequestItemMessage.ScreenType.BOOKS, this.idx));
        this.screen.onClose();
    }

    public ResourceLocation getTexture() {
        return FeywildMod.getInstance().resource("textures/gui/librarian_gui.png");
    }

    @Override
    public void renderWidget(@Nonnull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        RenderHelper.resetColor();
        graphics.blit(getTexture(), this.getX(), this.getY(), 0, 0, 25, 25);
        if (this.isHovered(mouseX, mouseY)) {
            graphics.pose().pushPose();
            graphics.pose().translate(0, 0, 10);
            graphics.blit(getTexture(), this.getX(), this.getY(), 25, 0, 25, 25);
            graphics.pose().popPose();
        }
        graphics.pose().pushPose();
        graphics.pose().translate(0, 0, 50);
        graphics.renderFakeItem(this.stack, this.getX() + 4, this.getY() + 4);
        graphics.pose().popPose();
    }

    public boolean isHovered(int x, int y) {
        return this.getX() <= x && this.getX() + WIDTH >= x && this.getY() <= y && this.getY() + HEIGHT >= y;
    }
}
