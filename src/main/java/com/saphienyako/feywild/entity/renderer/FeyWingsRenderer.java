package com.saphienyako.feywild.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.FeyWingsEntity;
import com.saphienyako.feywild.entity.model.FeyWingsModel;
import com.saphienyako.feywild.entity.model.ModModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class FeyWingsRenderer extends LivingEntityRenderer<FeyWingsEntity, FeyWingsModel<FeyWingsEntity>> {

    public FeyWingsRenderer(EntityRendererProvider.Context context) {
        super(context, new FeyWingsModel<>(context.bakeLayer(ModModelLayers.FEY_WINGS_LAYER)), 0.50f);

    }

    @Override
    public void render(@NotNull FeyWingsEntity pEntity, float pEntityYaw, float pPartialTicks, @NotNull PoseStack pMatrixStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull FeyWingsEntity feyWingsEntity) {
        return ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, feyWingsEntity.getTexture() + ".png");
    }
}