package com.saphienyako.feywild.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.AutumnPixieEntity;
import com.saphienyako.feywild.entity.model.AutumnPixieModel;
import com.saphienyako.feywild.entity.model.ModModelLayers;
import com.saphienyako.feywild.entity.state.PixieRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class AutumnPixieRenderer extends MobRenderer<@NotNull AutumnPixieEntity, @NotNull PixieRenderState, @NotNull AutumnPixieModel> {

    private static final float MODEL_SCALE = 0.65F;

    public AutumnPixieRenderer(EntityRendererProvider.Context context) {
        super(context, new AutumnPixieModel(context.bakeLayer(ModModelLayers.AUTUMN_PIXIE_LAYER)), 0.50f);
    }

    @Override
    public PixieRenderState createRenderState() {
        return new PixieRenderState();
    }

    @Override
    protected void scale(PixieRenderState state, PoseStack poseStack) {
        poseStack.scale(MODEL_SCALE, MODEL_SCALE, MODEL_SCALE);
    }

    @Override
    public @NotNull Identifier getTextureLocation(PixieRenderState PixieRenderState) {
        return Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "textures/entity/autumn_pixie.png");
    }

    @Override
    public void extractRenderState(AutumnPixieEntity entity, PixieRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);

        state.idleAnimation.copyFrom(entity.IDLE_ANIMATION);
        state.spellCastingAnimation.copyFrom(entity.SPELL_CASTING_ANIMATION);
    }
}
