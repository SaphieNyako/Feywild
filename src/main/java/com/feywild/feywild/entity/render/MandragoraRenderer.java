package com.feywild.feywild.entity.render;

import com.feywild.feywild.entity.MandragoraEntity;
import com.feywild.feywild.entity.model.MandragoraModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class MandragoraRenderer extends GeoEntityRenderer<MandragoraEntity> {

    public MandragoraRenderer(EntityRendererManager renderManager) {
        super(renderManager, new MandragoraModel());
        this.shadowRadius = 0.2F;
    }

    @Override
    public void render(MandragoraEntity entity, float entityYaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
        this.generateParticles(entity);
    }

    @Override
    public RenderType getRenderType(MandragoraEntity animatable, float partialTicks, MatrixStack stack, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        //return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
        return RenderType.entityTranslucent(this.getTextureLocation(animatable));
    }

    private void generateParticles(MandragoraEntity entity) {
        if (entity.isCasting()) {
            World world = entity.level;

            if (world.random.nextInt(20) == 0 && !Minecraft.getInstance().isPaused()) {
                world.addParticle(
                        ParticleTypes.NOTE,
                        entity.getX() + (Math.random() - 0.5),
                        entity.getY() + 1 + (Math.random() - 0.5),
                        entity.getZ() + (Math.random() - 0.5),
                        0.917,
                        0,
                        0);
            }
        }

    }
}
