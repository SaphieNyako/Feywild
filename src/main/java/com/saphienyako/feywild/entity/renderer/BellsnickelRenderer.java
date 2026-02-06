package com.saphienyako.feywild.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.BellsnickelEntity;
import com.saphienyako.feywild.entity.model.BellsnickelModel;
import com.saphienyako.feywild.entity.model.ModModelLayers;
import com.saphienyako.feywild.entity.renderer.layer.BellsnickelLanternLayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class BellsnickelRenderer extends MobRenderer<BellsnickelEntity, BellsnickelModel<BellsnickelEntity>> {

    public BellsnickelRenderer(EntityRendererProvider.Context context) {
        super(context, new BellsnickelModel<>(context.bakeLayer(ModModelLayers.BELLSNICKEL_LAYER)),  0.50f);
        this.addLayer(new BellsnickelLanternLayer(this, context.getBlockRenderDispatcher()));
    }

    @Override
    public void render(@NotNull BellsnickelEntity pEntity, float pEntityYaw, float pPartialTicks, @NotNull PoseStack pMatrixStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BellsnickelEntity bellsnickelEntity) {
        return ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "textures/entity/bellsnickel/" + bellsnickelEntity.getVariant().name().toLowerCase(Locale.ROOT) + ".png");
    }
}
