package com.feywild.feywild.screens.widget;

import com.feywild.feywild.FeywildMod;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import org.moddingx.libx.render.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

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
        super(x, y, WIDTH, HEIGHT, stack.getDisplayName(), b -> {});
        this.screen = screen;
        this.idx = idx;
        this.stack = stack;
    }


    @Override
    public void onPress() {
        FeywildMod.getNetwork().channel.sendToServer(new RequestItemSerializer.Message(this.idx, RequestItemSerializer.State.scrolls));
        this.screen.onClose();
    }

    public ResourceLocation getTexture() {
        return new ResourceLocation(FeywildMod.getInstance().modid, "textures/gui/begin_atlas.png");
    }

    @Override
    public void render(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.setShaderTexture(0, getTexture());
        RenderHelper.resetColor();
        this.blit(poseStack, this.x, this.y, idx * WIDTH, 0, 64, 64);
        poseStack.pushPose();
        if (this.isHovered(mouseX, mouseY)) {
            poseStack.translate(this.x + 14, this.y + 14, 0);
            poseStack.scale(2, 2, 2);
        } else {
            poseStack.translate(this.x + 25, this.y + 22, 0);
        }
        RenderSystem.getModelViewStack().pushPose();
        RenderSystem.getModelViewStack().mulPoseMatrix(poseStack.last().pose());
        RenderSystem.applyModelViewMatrix();
        Minecraft.getInstance().getItemRenderer().renderGuiItem(this.stack, 0, 0);
        RenderSystem.getModelViewStack().popPose();
        RenderSystem.applyModelViewMatrix();
        poseStack.popPose();

        if (this.isHovered(mouseX, mouseY)) {
            this.screen.renderTooltip(poseStack, List.of(this.stack.getHoverName()), Optional.empty(), mouseX, mouseY);
        }
    }

    public boolean isHovered(int x, int y) {
        return this.x <= x && this.x + WIDTH >= x && this.y <= y && this.y + HEIGHT >= y;
    }
}
