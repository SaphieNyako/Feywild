package com.feywild.feywild.entity.render;

import com.feywild.feywild.entity.AutumnPixieEntity;
import com.feywild.feywild.entity.model.AutumnPixieModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.World;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class AutumnPixieRenderer extends GeoEntityRenderer<AutumnPixieEntity> {

    String previousTick = "0.0";

    public AutumnPixieRenderer(EntityRendererManager renderManager) {
        super(renderManager, new AutumnPixieModel());
        this.shadowSize = 0.2F;
    }

    @Override
    public void render(AutumnPixieEntity entity, float entityYaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);

        World world = entity.world;

        if(world.rand.nextInt(9) == 0) {
            world.addParticle(
                    ParticleTypes.WITCH,
                    entity.getPosX() + (Math.random() - 0.5),
                    entity.getPosY() + 1 + (Math.random() - 0.5),
                    entity.getPosZ() + (Math.random() - 0.5),
                    0,
                    -0.1,
                    0);
        }
    }



}
