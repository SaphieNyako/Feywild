package com.saphienyako.feywild.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.AutumnPixieEntity;
import com.saphienyako.feywild.entity.SpringPixieEntity;
import com.saphienyako.feywild.entity.WinterPixieEntity;
import com.saphienyako.feywild.entity.model.AutumnPixieModel;
import com.saphienyako.feywild.entity.model.ModModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class AutumnPixieRenderer extends MobRenderer<AutumnPixieEntity, AutumnPixieModel<AutumnPixieEntity>> {

    private static final float MODEL_SCALE = 0.65F;

    public AutumnPixieRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new AutumnPixieModel<>(pContext.bakeLayer(ModModelLayers.AUTUMN_PIXIE_LAYER)),  0.50f);
    }

    @Override
    public void render(@NotNull AutumnPixieEntity pEntity, float pEntityYaw, float pPartialTicks, @NotNull PoseStack pMatrixStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull AutumnPixieEntity autumnPixieEntity) {
        return ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "textures/entity/autumn_pixie.png");
    }

    @Override
    protected void scale(AutumnPixieEntity entity, PoseStack poseStack, float partialTick) {
        poseStack.scale(MODEL_SCALE, MODEL_SCALE, MODEL_SCALE);
    }
}
