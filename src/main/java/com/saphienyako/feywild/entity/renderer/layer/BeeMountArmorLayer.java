package com.saphienyako.feywild.entity.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.saphienyako.feywild.entity.model.BeeMountModel;
import com.saphienyako.feywild.entity.model.ModModelLayers;
import com.saphienyako.feywild.entity.state.BeeMountRenderState;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class BeeMountArmorLayer extends RenderLayer<@NotNull BeeMountRenderState, @NotNull BeeMountModel> {

    private final BeeMountModel armorModel;

    public BeeMountArmorLayer(
            RenderLayerParent<@NotNull BeeMountRenderState, @NotNull BeeMountModel> parent,
            EntityModelSet modelSet
    ) {
        super(parent);
        this.armorModel = new BeeMountModel(
                modelSet.bakeLayer(ModModelLayers.BEE_MOUNT_LAYER)
        );
    }

    @Override
    public void submit(@NotNull PoseStack poseStack, @NotNull SubmitNodeCollector submitNodeCollector, int packedLight, BeeMountRenderState state, float limbSwing, float limbSwingAmount) {
        if (state.armorTexture == null || state.isInvisible) {
            return;
        }

        coloredCutoutModelCopyLayerRender(this.armorModel, state.armorTexture, poseStack, submitNodeCollector, packedLight, state, -1, 0);
    }
}