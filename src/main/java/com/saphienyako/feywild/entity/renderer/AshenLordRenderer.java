package com.saphienyako.feywild.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.AshenLordEntity;
import com.saphienyako.feywild.entity.OberonEntity;
import com.saphienyako.feywild.entity.model.AshenLordModel;
import com.saphienyako.feywild.entity.model.MabModel;
import com.saphienyako.feywild.entity.model.ModModelLayers;
import com.saphienyako.feywild.entity.model.OberonModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class AshenLordRenderer extends MobRenderer<AshenLordEntity, AshenLordModel<AshenLordEntity>> {


    public AshenLordRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new AshenLordModel<>(pContext.bakeLayer(ModModelLayers.ASHEN_LORD_LAYER)),  0.50f);
    }

    @Override
    public void render(@NotNull AshenLordEntity pEntity, float pEntityYaw, float pPartialTicks, @NotNull PoseStack pMatrixStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }


    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull AshenLordEntity ashenLordEntity) {
        return ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "textures/entity/ashen_lord.png");
    }
}
