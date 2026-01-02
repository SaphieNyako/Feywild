package com.saphienyako.feywild.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.AutumnPixieEntity;
import com.saphienyako.feywild.entity.model.AutumnPixieModel;
import com.saphienyako.feywild.entity.model.ModModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class AutumnPixieRenderer extends GeoEntityRenderer<AutumnPixieEntity> {

    public AutumnPixieRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new AutumnPixieModel());
        this.shadowRadius = 0.50f;
    }

    @Override
    public RenderType getRenderType(AutumnPixieEntity animatable, float partialTick, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return RenderType.entityTranslucent(this.getTextureLocation(animatable));
    }
}
