package com.feywild.feywild.entity.render;

import com.feywild.feywild.entity.WinterPixieEntity;
import com.feywild.feywild.entity.model.WinterPixieModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.World;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class WinterPixieRenderer extends GeoEntityRenderer<WinterPixieEntity> {

    public WinterPixieRenderer(EntityRendererManager renderManager) {

        super(renderManager, new WinterPixieModel());
        this.shadowSize = 0.2F;
    }

    @Override
    public void render(WinterPixieEntity entity, float entityYaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);

        World world = entity.world;

        if(world.rand.nextInt(9) == 0) {
            world.addParticle(
                    ParticleTypes.ITEM_SNOWBALL,
                    entity.getPosX() + (Math.random() - 0.5),
                    entity.getPosY() + 1 + (Math.random() - 0.5),
                    entity.getPosZ() + (Math.random() - 0.5),
                    0,
                    -0.1,
                    0);
        }
    }
}
