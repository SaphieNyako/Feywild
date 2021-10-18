package com.feywild.feywild.entity.render;

import com.feywild.feywild.entity.base.MandragoraEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

public class MandragoraRenderer<T extends MandragoraEntity> extends GeoEntityRenderer<T> {

    public MandragoraRenderer(EntityRendererManager renderManager, AnimatedGeoModel<T> model) {
        super(renderManager, model);
        this.shadowRadius = 0.2F;
    }

    public static <T extends MandragoraEntity> IRenderFactory<T> create(Supplier<AnimatedGeoModel<T>> modelProvider) {
        return manager -> new MandragoraRenderer<>(manager, modelProvider.get());
    }

    @Override
    public void render(@Nonnull T entity, float entityYaw, float partialTicks, @Nonnull MatrixStack stack, @Nonnull IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
        this.generateParticles(entity);
    }

    @Override
    public RenderType getRenderType(T animatable, float partialTicks, MatrixStack stack, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(this.getTextureLocation(animatable));
    }

    private void generateParticles(MandragoraEntity entity) {
        if (entity.isCasting()) {
            World world = entity.level;

            if (world.random.nextInt(20) == 0 && !Minecraft.getInstance().isPaused()) {
                world.addParticle(ParticleTypes.NOTE,
                        entity.getX() + (Math.random() - 0.5),
                        entity.getY() + 1 + (Math.random() - 0.5),
                        entity.getZ() + (Math.random() - 0.5),
                        0.917, 0, 0
                );
            }
        }

    }
}
