package com.feywild.feywild.entity.render;

import com.feywild.feywild.entity.base.Mandragora;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

public class MandragoraRenderer<T extends Mandragora> extends GeoEntityRenderer<T> {

    public MandragoraRenderer(EntityRendererProvider.Context manager, AnimatedGeoModel<T> model) {
        super(manager, model);
        this.shadowRadius = 0.2F;
    }

    public static <T extends Mandragora> EntityRendererProvider<T> create(Supplier<AnimatedGeoModel<T>> modelProvider) {
        return manager -> new MandragoraRenderer<>(manager, modelProvider.get());
    }

    @Override
    public void render(@Nonnull T entity, float entityYRot, float partialTicks, @Nonnull PoseStack stack, @Nonnull MultiBufferSource bufferIn, int packedLightIn) {
        super.render(entity, entityYRot, partialTicks, stack, bufferIn, packedLightIn);
        this.generateParticles(entity);
    }

    @Override
    public RenderType getRenderType(T animatable, float partialTicks, PoseStack stack, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexConsumer, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(this.getTextureLocation(animatable));
    }

    private void generateParticles(Mandragora entity) {
        if (entity.isCasting()) {
            Level level = entity.level;

            if (level.random.nextInt(20) == 0 && !Minecraft.getInstance().isPaused()) {
                level.addParticle(ParticleTypes.NOTE,
                        entity.getX() + (Math.random() - 0.5),
                        entity.getY() + 1 + (Math.random() - 0.5),
                        entity.getZ() + (Math.random() - 0.5),
                        0.917, 0, 0
                );
            }
        }

    }
}
