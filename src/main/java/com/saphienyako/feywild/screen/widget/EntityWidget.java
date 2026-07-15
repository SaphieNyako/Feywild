package com.saphienyako.feywild.screen.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.BeeKnightEntity;
import com.saphienyako.feywild.entity.BeeMountEntity;
import com.saphienyako.feywild.entity.base.FeyBase;
import com.saphienyako.feywild.particle.ModParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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
    public void renderWidget(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        if(this.entity instanceof BeeMountEntity mount){

            BeeKnightEntity knight = mount.getLinkedKnight();

            float scale = (float)(this.height / this.entity.getBbHeight()) * 0.5f;

            int centerX = this.getX() + this.width / 2;
            float centerY = this.getY() + this.height / 2f;

            // Normalize mouse offset (-1 to 1 range)
            float deltaX = (mouseX - centerX) / (this.width / 2f);
            float deltaY = (mouseY - centerY) / (this.height / 2f);

            // Clamp so it doesn't spin too much
            deltaX = Mth.clamp(deltaX, -1f, 1f);
            deltaY = Mth.clamp(deltaY, -1f, 1f);

            // Apply small rotation multipliers
            float yawOffset = deltaX * 25.0F;   // horizontal turn
            float pitchOffset = deltaY * 15.0F; // vertical tilt

            int bottomY = this.getY() + this.height;

            PoseStack pose = graphics.pose();
            pose.pushPose();

            pose.translate(centerX, bottomY, 50.0F);
            pose.scale(scale, scale, scale);
            //   pose.mulPose(Axis.ZP.rotationDegrees(180.0F));
            pose.mulPose(Axis.XP.rotationDegrees(180F));
            pose.mulPose(Axis.YP.rotationDegrees(180F));


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
                        partialTick,
                        pose,
                        buffer,
                        15728880
                );

            }

            if (knight != null && knight.isAlive() && !knight.isRemoved()) {

                knight.setYRot(yaw);
                knight.yBodyRot = yaw + yawOffset;
                knight.yHeadRot = yaw + yawOffset;
                knight.setXRot(pitchOffset);

                pose.pushPose();
                pose.translate(0, 0.45, 0);
                dispatcher.render(
                        knight,
                        0, 0, 0,
                        0,
                        partialTick,
                        pose,
                        buffer,
                        15728880
                );
                pose.popPose();

            }

            buffer.endBatch();
            dispatcher.setRenderShadow(true);

            pose.popPose();
        } else {
            double scale = ((double) this.height / this.entity.getType().getHeight());

            int x1 = this.getX();
            int y1 = this.getY();
            int x2 = x1 + (this.width * 2);
            int y2 = y1 + this.height + 30 + (int) (scale);

            float yOffset = 0f;

            InventoryScreen.renderEntityInInventoryFollowsMouse(
                    graphics,
                    x1, y1,
                    x2, y2,
                    (int) scale,
                    yOffset,
                    mouseX,
                    mouseY,
                    this.entity
            );
        }
    }


    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput output) {
        //
    }
}
