package com.saphienyako.feywild.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.SpringPixieEntity;
import com.saphienyako.feywild.entity.model.ModModelLayers;
import com.saphienyako.feywild.entity.model.SpringPixieModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class SpringPixieRenderer extends MobRenderer<SpringPixieEntity, SpringPixieModel<SpringPixieEntity>> {

    private static final float MODEL_SCALE = 0.65F;

    public SpringPixieRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new SpringPixieModel<>(pContext.bakeLayer(ModModelLayers.SPRING_PIXIE_LAYER)),  0.50f);
    }

    @Override
    public void render(@NotNull SpringPixieEntity pEntity, float pEntityYaw, float pPartialTicks, @NotNull PoseStack pMatrixStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull SpringPixieEntity springPixieEntity) {
        return new ResourceLocation(Feywild.MOD_ID, "textures/entity/spring_pixie.png");
    }

    @Override
    protected void scale(SpringPixieEntity entity, PoseStack poseStack, float partialTick) {
        poseStack.scale(MODEL_SCALE, MODEL_SCALE, MODEL_SCALE);
    }
}
