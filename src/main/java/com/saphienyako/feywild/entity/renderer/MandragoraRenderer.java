package com.saphienyako.feywild.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.MandragoraEntity;
import com.saphienyako.feywild.entity.model.ModModelLayers;
import com.saphienyako.feywild.entity.model.MandragoraModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class MandragoraRenderer extends MobRenderer<MandragoraEntity, MandragoraModel<MandragoraEntity>> {

    public MandragoraRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new MandragoraModel<>(pContext.bakeLayer(ModModelLayers.MANDRAGORA_LAYER)),  0.50f);
    }

    @Override
    public void render(@NotNull MandragoraEntity pEntity, float pEntityYaw, float pPartialTicks, @NotNull PoseStack pMatrixStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull MandragoraEntity mandragoraEntity) {
        return new ResourceLocation(Feywild.MOD_ID, "textures/entity/mandragora/" + mandragoraEntity.getVariant().name().toLowerCase(Locale.ROOT) + ".png");
    }
}

