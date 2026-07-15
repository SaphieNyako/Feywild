package com.saphienyako.feywild.screen.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.saphienyako.feywild.entity.BeeKnightEntity;
import com.saphienyako.feywild.entity.BeeMountEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class EntityWidget extends AbstractWidget {

    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;

    private final LivingEntity entity;
    public EntityWidget(int x, int y, LivingEntity entity) {
        super(x, y, WIDTH, HEIGHT, Component.empty());
        this.entity = entity;
    }


    @Override
    public void renderButton(@NotNull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        //Note; in 1.19 make all intermediate calculations double before casting to int to prevent flickering of the model.
        if(this.entity instanceof BeeMountEntity mount){

            BeeKnightEntity knight = mount.getLinkedKnight();

            float scale = (float)(this.height / this.entity.getBbHeight());

            int centerX = this.x + this.width / 2;
            float centerY = this.y + this.height / 2f;

            // Normalize mouse offset (-1 to 1 range)
            float deltaX = (mouseX - centerX) / (this.width / 2f);
            float deltaY = (mouseY - centerY) / (this.height / 2f);

            // Clamp so it doesn't spin too much
            deltaX = Mth.clamp(deltaX, -1f, 1f);
            deltaY = Mth.clamp(deltaY, -1f, 1f);

            // Apply small rotation multipliers
            float yawOffset = deltaX * 25.0F;   // horizontal turn
            float pitchOffset = deltaY * 15.0F; // vertical tilt

            int bottomY = this.y + this.height;


            poseStack.pushPose();

            poseStack.translate(centerX, bottomY, 50.0F);
            poseStack.scale(scale, scale, scale);
            //   pose.mulPose(Axis.ZP.rotationDegrees(180.0F));
            poseStack.mulPose(Vector3f.XP.rotationDegrees(180F));
            poseStack.mulPose(Vector3f.YP.rotationDegrees(180F));


            EntityRenderDispatcher dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
            dispatcher.setRenderShadow(false);

            MultiBufferSource.BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();


            float yaw = 20.0F; // faces forward in GUI

            if(mount != null & mount.isAlive() && !mount.isRemoved()) {
                mount.setYRot(yaw);
                mount.yBodyRot = yaw + yawOffset;
                mount.yHeadRot = yaw + yawOffset;
                mount.setXRot(pitchOffset);

                dispatcher.render(
                        mount,
                        0, 0, 0,
                        0,
                        partialTicks,
                        poseStack,
                        buffer,
                        15728880
                );
            }

            if (knight != null && knight.isAlive() && !knight.isRemoved()) {
                knight.setYRot(yaw);
                knight.yBodyRot = yaw + yawOffset;
                knight.yHeadRot = yaw + yawOffset;
                knight.setXRot(pitchOffset);

                poseStack.pushPose();
                poseStack.translate(0, 0.45, 0);
                dispatcher.render(
                    knight,
                    0, 0, 0,
                    0,
                    partialTicks,
                    poseStack,
                    buffer,
                    15728880
             );

                poseStack.popPose();
            }

            buffer.endBatch();
            dispatcher.setRenderShadow(true);

            poseStack.popPose();
        } else {

            double scale = ((double) this.height / this.entity.getType().getHeight()) * 2;
            double posX = this.x + this.width / 2.0;
            double posY = this.y + this.height + scale * 48.0 / 85.0;
            double centerX = this.x + this.width / 2.0;
            double centerY = this.y + this.height / 2.0;

            float deltaX = (float) (centerX - mouseX);
            float deltaY = (float) (centerY - mouseY);

            InventoryScreen.renderEntityInInventory(
                    (int) posX,
                    (int) posY,
                    (int) scale,
                    (float) deltaX,
                    (float) deltaY,
                    this.entity
            );
        }
    }

    @Override
    public void updateNarration(@NotNull NarrationElementOutput output) {
        //
    }
}
