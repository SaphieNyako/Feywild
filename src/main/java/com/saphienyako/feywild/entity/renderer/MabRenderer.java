package com.saphienyako.feywild.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.MabEntity;
import com.saphienyako.feywild.entity.TitaniaEntity;
import com.saphienyako.feywild.entity.model.MabModel;
import com.saphienyako.feywild.entity.model.ModModelLayers;
import com.saphienyako.feywild.entity.model.TitaniaModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class MabRenderer extends MobRenderer<MabEntity, MabModel<MabEntity>> {
public MabRenderer(EntityRendererProvider.Context pContext) {
    super(pContext, new MabModel<>(pContext.bakeLayer(ModModelLayers.MAB_LAYER)),  0.50f);
}

@Override
public void render(@NotNull MabEntity pEntity, float pEntityYaw, float pPartialTicks, @NotNull PoseStack pMatrixStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
    super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
}

@Override
public @NotNull ResourceLocation getTextureLocation(@NotNull MabEntity mabEntity) {
    return new ResourceLocation(Feywild.MOD_ID, "textures/entity/mab.png");
}
}