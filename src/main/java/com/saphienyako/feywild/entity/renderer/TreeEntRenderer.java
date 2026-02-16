package com.saphienyako.feywild.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.base.TreeEntBase;
import com.saphienyako.feywild.entity.model.ModModelLayers;
import com.saphienyako.feywild.entity.model.TreeEntModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class TreeEntRenderer extends MobRenderer<TreeEntBase, TreeEntModel<TreeEntBase>> {

    public TreeEntRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new TreeEntModel<>(pContext.bakeLayer(ModModelLayers.TREE_ENT_LAYER)),  0.50f);
    }

    @Override
    public void render(@NotNull TreeEntBase pEntity, float pEntityYaw, float pPartialTicks, @NotNull PoseStack pMatrixStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull TreeEntBase treeEntBase) {
        return ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "textures/entity/tree_ent/" + treeEntBase.getAlignment().name().toLowerCase(Locale.ROOT) + ".png");
    }
}
