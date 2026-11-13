package com.saphienyako.feywild.screen.widget;

import com.saphienyako.feywild.entity.BeeKnightEntity;
import com.saphienyako.feywild.entity.BeeMountEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class EntityWidget extends AbstractWidget {

    public static final int WIDTH = 84;
    public static final int HEIGHT = 104;
    private final LivingEntity entity;
    public EntityWidget(int x, int y, LivingEntity entity) {
        super(x, y, WIDTH, HEIGHT, Component.empty());
        this.entity = entity;
    }

    @Override
    protected void extractWidgetRenderState(@NotNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        if (this.entity instanceof BeeMountEntity mount) {
            this.extractBeeMount(graphics, mount, mouseX, mouseY, partialTick);
        } else {
            double scale = (double) this.height / this.entity.getType().getHeight();

            int x1 = this.getX();
            int y1 = this.getY();
            int x2 = x1 + (this.width * 2);
            int y2 = y1 + this.height + 30 + (int) scale;

            InventoryScreen.extractEntityInInventoryFollowsMouse(graphics, x1, y1, x2, y2, (int) scale, 0f, mouseX, mouseY, this.entity);
        }
    }

    private void extractBeeMount(GuiGraphicsExtractor graphics, BeeMountEntity mount, int mouseX, int mouseY, float partialTick) {
        BeeKnightEntity knight = mount.getLinkedKnight();

        float scale = this.height / mount.getBbHeight() * 0.5f;

        int centerX = this.getX() + this.width / 2;
        int centerY = this.getY() + this.height / 2;

        float deltaX = Mth.clamp((mouseX - centerX) / (this.width / 2f), -1f, 1f);
        float deltaY = Mth.clamp((mouseY - centerY) / (this.height / 2f), -1f, 1f);

        float yaw = 20.0F;
        float yawOffset = deltaX * 25.0F;
        float pitchOffset = deltaY * 15.0F;

        EntityRenderDispatcher dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();

        Quaternionf rotation = new Quaternionf().rotateX(Mth.PI).rotateY(Mth.PI);
        Quaternionf xRotation = new Quaternionf();

        int x0 = this.getX();
        int y0 = this.getY();
        int x1 = this.getRight();
        int y1 = this.getBottom();

        if (mount.isAlive() && !mount.isRemoved()) {
            EntityRenderState mountState = dispatcher.extractEntity(mount, partialTick);

            this.applyPreviewRotation(mountState, yaw, yawOffset, pitchOffset);

            Vector3f translation = new Vector3f(0.0F, mountState.boundingBoxHeight / 2.0F, 0.0F);

            graphics.entity(mountState, scale, translation, rotation, xRotation, x0, y0, x1, y1);
        }

        //noinspection ConstantValue
        if (knight != null && knight.isAlive() && !knight.isRemoved()) {
            EntityRenderState knightState = dispatcher.extractEntity(knight, partialTick);

            this.applyPreviewRotation(knightState, yaw, yawOffset, pitchOffset);

            Vector3f translation = new Vector3f(0.0F, knightState.boundingBoxHeight / 2.0F + 0.45F, 0.0F);
            graphics.entity(knightState, scale, translation, rotation, xRotation, x0, y0, x1, y1);
        }
    }

    private void applyPreviewRotation(EntityRenderState renderState, float yaw, float yawOffset, float pitchOffset) {
        if (renderState instanceof LivingEntityRenderState livingState) {
            livingState.bodyRot = yaw + yawOffset;
            livingState.yRot = yaw + yawOffset;
            livingState.xRot = pitchOffset;
        }
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput output) {
        //
    }
}
