package com.saphienyako.feywild.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.AutumnPixieEntity;
import com.saphienyako.feywild.entity.MandragoraEntity;
import com.saphienyako.feywild.entity.ShroomlingEntity;
import com.saphienyako.feywild.entity.model.AutumnPixieModel;
import com.saphienyako.feywild.entity.model.ModModelLayers;
import com.saphienyako.feywild.entity.model.ShroomlingModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class ShroomlingRenderer extends MobRenderer<ShroomlingEntity, ShroomlingModel<ShroomlingEntity>> {

    private static final float MODEL_SCALE = 0.75F;
    public ShroomlingRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new ShroomlingModel<>(pContext.bakeLayer(ModModelLayers.SHROOMLING_LAYER)),  0.50f);
    }

    @Override
    public void render(@NotNull ShroomlingEntity pEntity, float pEntityYaw, float pPartialTicks, @NotNull PoseStack pMatrixStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull ShroomlingEntity shroomlingEntity) {
        return ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "textures/entity/shroomling/" + shroomlingEntity.getVariant().name().toLowerCase(Locale.ROOT) + ".png");
    }

    @Override
    protected void scale(ShroomlingEntity entity, PoseStack poseStack, float partialTick) {
        poseStack.scale(MODEL_SCALE, MODEL_SCALE, MODEL_SCALE);
    }
}
