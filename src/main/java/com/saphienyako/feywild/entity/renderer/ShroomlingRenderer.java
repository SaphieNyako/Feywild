package com.saphienyako.feywild.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.ShroomlingEntity;
import com.saphienyako.feywild.entity.model.ModModelLayers;
import com.saphienyako.feywild.entity.model.ShroomlingModel;
import com.saphienyako.feywild.entity.state.ShroomlingRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class ShroomlingRenderer extends MobRenderer<@NotNull ShroomlingEntity, @NotNull ShroomlingRenderState, @NotNull ShroomlingModel> {

    private static final float MODEL_SCALE = 0.75F;

    public ShroomlingRenderer(EntityRendererProvider.Context context) {
        super(context, new ShroomlingModel(context.bakeLayer(ModModelLayers.SHROOMLING_LAYER)), 0.50f);
    }

    @Override
    public ShroomlingRenderState createRenderState() {
        return new ShroomlingRenderState();
    }

    @Override
    public void extractRenderState(ShroomlingEntity entity, ShroomlingRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);

        state.idleAnimation.copyFrom(entity.IDLE_ANIMATION);
        state.sneezeAnimation.copyFrom(entity.SNEEZE_ANIMATION);
        state.waveAnimation.copyFrom(entity.WAVE_ANIMATION);
        state.poseAnimation.copyFrom(entity.POSE_ANIMATION);
        state.walkAnimation.copyFrom(entity.WALK_ANIMATION);

        state.variantName = entity.getVariant().name().toLowerCase(Locale.ROOT);
    }

    @Override
    public Identifier getTextureLocation(ShroomlingRenderState state) {
        return Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "textures/entity/shroomling/" + state.variantName + ".png");
    }

    @Override
    protected void scale(ShroomlingRenderState state, PoseStack poseStack) {
        poseStack.scale(MODEL_SCALE, MODEL_SCALE, MODEL_SCALE);
    }
}
