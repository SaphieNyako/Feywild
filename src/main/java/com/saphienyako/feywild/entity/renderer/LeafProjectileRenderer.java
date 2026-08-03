package com.saphienyako.feywild.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.saphienyako.feywild.entity.LeafProjectile;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class LeafProjectileRenderer extends EntityRenderer<LeafProjectile> {

    public LeafProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(LeafProjectile entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        // Nothing to render.
        // The projectile is represented entirely by particles.
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull LeafProjectile entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}