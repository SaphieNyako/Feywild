package com.saphienyako.feywild.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.WinterPixieEntity;
import com.saphienyako.feywild.entity.model.ModModelLayers;
import com.saphienyako.feywild.entity.model.WinterPixieModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class WinterPixieRenderer extends MobRenderer<WinterPixieEntity, WinterPixieModel<WinterPixieEntity>> {

    public WinterPixieRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new WinterPixieModel<>(pContext.bakeLayer(ModModelLayers.WINTER_PIXIE_LAYER)),  0.50f);
    }

    @Override
    public void render(@NotNull WinterPixieEntity pEntity, float pEntityYaw, float pPartialTicks, @NotNull PoseStack pMatrixStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull WinterPixieEntity winterPixieEntity) {
        return new ResourceLocation(Feywild.MOD_ID, "textures/entity/winter_pixie.png");
    }
}
