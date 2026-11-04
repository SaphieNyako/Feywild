package com.saphienyako.feywild.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.MandragoraEntity;
import com.saphienyako.feywild.entity.model.MandragoraModel;
import com.saphienyako.feywild.entity.model.ModModelLayers;
import com.saphienyako.feywild.entity.state.MandragoraRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class MandragoraRenderer extends MobRenderer<@NotNull MandragoraEntity, @NotNull MandragoraRenderState, @NotNull MandragoraModel> {

    private static final float MODEL_SCALE = 0.75F;

    public MandragoraRenderer(EntityRendererProvider.Context context) {
        super(context, new MandragoraModel(context.bakeLayer(ModModelLayers.MANDRAGORA_LAYER)), 0.50f);
    }

    @Override
    public MandragoraRenderState createRenderState() {
        return new MandragoraRenderState();
    }

    @Override
    public void extractRenderState(MandragoraEntity entity, MandragoraRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);

        state.idleAnimation.copyFrom(entity.IDLE_ANIMATION);
        state.singAnimation.copyFrom(entity.SING_ANIMATION);
        state.poseAnimation.copyFrom(entity.POSE_ANIMATION);
        state.walkAnimation.copyFrom(entity.WALK_ANIMATION);

        state.variantName = entity.getVariant().name().toLowerCase(Locale.ROOT);
    }

    @Override
    public @NotNull Identifier getTextureLocation(MandragoraRenderState state) {
        return Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "textures/entity/mandragora/" + state.variantName + ".png");
    }

    @Override
    protected void scale(MandragoraRenderState state, PoseStack poseStack) {
        poseStack.scale(MODEL_SCALE, MODEL_SCALE, MODEL_SCALE);
    }
}

