package com.feywild.feywild.entity.render;

import com.feywild.feywild.entity.AutumnPixieEntity;
import com.feywild.feywild.entity.model.AutumnPixieModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import software.bernie.geckolib3.geo.render.built.GeoModel;

import javax.annotation.Nullable;

public class AutumnPixieRenderer extends BasePixieRenderer<AutumnPixieEntity> {

    public AutumnPixieRenderer(EntityRendererManager renderManager) {
        super(renderManager, new AutumnPixieModel());
    }

    @Override
    protected BasicParticleType getParticleType() {
        return ParticleTypes.WITCH;
    }
}
