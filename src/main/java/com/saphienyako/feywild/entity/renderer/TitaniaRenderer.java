package com.saphienyako.feywild.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.TitaniaEntity;
import com.saphienyako.feywild.entity.model.ModModelLayers;
import com.saphienyako.feywild.entity.model.TitaniaModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class TitaniaRenderer extends MobRenderer<TitaniaEntity, TitaniaModel<TitaniaEntity>> {
    public TitaniaRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new TitaniaModel<>(pContext.bakeLayer(ModModelLayers.TITANIA_LAYER)),  0.50f);
    }

    @Override
    public void render(@NotNull TitaniaEntity pEntity, float pEntityYaw, float pPartialTicks, @NotNull PoseStack pMatrixStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull TitaniaEntity titaniaEntity) {
        return new ResourceLocation(Feywild.MOD_ID, "textures/entity/titania.png");
    }
}

