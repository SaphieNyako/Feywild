package com.saphienyako.feywild.entity.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.saphienyako.feywild.entity.model.BeeKnightModel;
import com.saphienyako.feywild.entity.model.ModModelLayers;
import com.saphienyako.feywild.entity.state.BeeKnightRenderState;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class BeeKnightArmorLayer extends RenderLayer<@NotNull BeeKnightRenderState, @NotNull BeeKnightModel> {

    private final BeeKnightModel armorModel;

    public BeeKnightArmorLayer(
            RenderLayerParent<@NotNull BeeKnightRenderState, @NotNull BeeKnightModel> parent,
            EntityModelSet modelSet
    ) {
        super(parent);
        this.armorModel = new BeeKnightModel(modelSet.bakeLayer(ModModelLayers.BEE_KNIGHT_LAYER));
    }

    @Override
    public void submit(@NotNull PoseStack poseStack, @NotNull SubmitNodeCollector submitNodeCollector, int packedLight, @NotNull BeeKnightRenderState state, float limbSwing, float limbSwingAmount) {
        if (state.armorTexture == null || state.isInvisible) {
            return;
        }

        coloredCutoutModelCopyLayerRender(this.armorModel, state.armorTexture, poseStack, submitNodeCollector, packedLight, state, -1, 0);
    }
}