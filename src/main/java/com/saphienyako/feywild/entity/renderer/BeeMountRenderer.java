package com.saphienyako.feywild.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.BeeKnightEntity;
import com.saphienyako.feywild.entity.BeeMountEntity;
import com.saphienyako.feywild.entity.model.BeeKnightModel;
import com.saphienyako.feywild.entity.model.BeeMountModel;
import com.saphienyako.feywild.entity.model.ModModelLayers;
import com.saphienyako.feywild.entity.renderer.layer.BeeMountArmorLayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class BeeMountRenderer extends MobRenderer<BeeMountEntity, BeeMountModel<BeeMountEntity>> {

    public BeeMountRenderer(EntityRendererProvider.Context context) {
        super(context, new BeeMountModel<>(context.bakeLayer(ModModelLayers.BEE_MOUNT_LAYER)),  0.50f);
        this.addLayer(new BeeMountArmorLayer(this, context.getModelSet()));
    }

    @Override
    public void render(@NotNull BeeMountEntity pEntity, float pEntityYaw, float pPartialTicks, @NotNull PoseStack pMatrixStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BeeMountEntity bee_mount) {
        if (bee_mount.getLinkedKnight() != null &&  bee_mount.getLinkedKnight().getState() == BeeKnightEntity.State.ATTACK) {
            return new ResourceLocation(Feywild.MOD_ID, "textures/entity/bee_knight/bee_mount_angry.png");
        } else {
            return new ResourceLocation(Feywild.MOD_ID, "textures/entity/bee_knight/bee_mount.png");
        }
    }
}