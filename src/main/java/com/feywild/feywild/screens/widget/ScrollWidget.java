package com.feywild.feywild.screens.widget;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.network.RequestItemMessage;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.moddingx.libx.render.RenderHelper;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public class ScrollWidget extends Button {

    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;

    protected final Screen screen;
    protected final int idx;
    protected final ItemStack stack;

    public ScrollWidget(Screen screen, int x, int y, int idx, ItemStack stack) {
        super(x, y, WIDTH, HEIGHT, stack.getDisplayName(), b -> {}, l -> Component.empty());
        this.screen = screen;
        this.idx = idx;
        this.stack = stack;
    }


    @Override
    public void onPress() {
        FeywildMod.getNetwork().channel.sendToServer(new RequestItemMessage(RequestItemMessage.ScreenType.SCROLLS, this.idx));
        this.screen.onClose();
    }

    public ResourceLocation getTexture() {
        return new ResourceLocation(FeywildMod.getInstance().modid, "textures/gui/begin_atlas.png");
    }

    @Override
    public void renderWidget(@Nonnull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        RenderHelper.resetColor();
        graphics.blit(getTexture(), this.getX(), this.getY(), idx * WIDTH, 0, 64, 64);
        graphics.pose().pushPose();
        if (this.isHovered(mouseX, mouseY)) {
            graphics.pose().translate(this.getX() + 14, this.getY() + 14, 10);
            graphics.pose().scale(2, 2, 2);
        } else {
            graphics.pose().translate(this.getX() + 25, this.getY() + 22, 10);
        }
        graphics.renderFakeItem(this.stack, 0, 0);
        graphics.pose().popPose();

        if (this.isHovered(mouseX, mouseY)) {
            graphics.renderTooltip(Minecraft.getInstance().font,  List.of(this.stack.getHoverName()), Optional.empty(), mouseX, mouseY);
        }
    }

    public boolean isHovered(int x, int y) {
        return this.getX() <= x && this.getX() + WIDTH >= x && this.getY() <= y && this.getY() + HEIGHT >= y;
    }
}
