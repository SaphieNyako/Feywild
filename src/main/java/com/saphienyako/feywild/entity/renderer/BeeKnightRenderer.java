package com.saphienyako.feywild.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.AutumnPixieEntity;
import com.saphienyako.feywild.entity.BeeKnightEntity;
import com.saphienyako.feywild.entity.model.AutumnPixieModel;
import com.saphienyako.feywild.entity.model.BeeKnightModel;
import com.saphienyako.feywild.entity.model.ModModelLayers;
import com.saphienyako.feywild.entity.renderer.layer.BeeKnightArmorLayer;
import com.saphienyako.feywild.entity.renderer.layer.BeeKnightSpearLayer;
import com.saphienyako.feywild.entity.renderer.layer.BeeMountArmorLayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class BeeKnightRenderer extends MobRenderer<BeeKnightEntity, BeeKnightModel<BeeKnightEntity>> {

    public BeeKnightRenderer(EntityRendererProvider.Context context) {
        super(context, new BeeKnightModel<>(context.bakeLayer(ModModelLayers.BEE_KNIGHT_LAYER)),  0.50f);
        this.addLayer(new BeeKnightArmorLayer(this, context.getModelSet()));
        this.addLayer(new BeeKnightSpearLayer(this, context.getItemRenderer()));
    }

    @Override
    public void render(@NotNull BeeKnightEntity pEntity, float pEntityYaw, float pPartialTicks, @NotNull PoseStack pMatrixStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BeeKnightEntity bee_knight) {
        if (bee_knight.getState() == BeeKnightEntity.State.ATTACK) {
            return ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "textures/entity/bee_knight/bee_knight_angry.png");
        } else {
            return ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "textures/entity/bee_knight/bee_knight.png");
        }
    }
}