package com.saphienyako.feywild.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.WinterPixieEntity;
import com.saphienyako.feywild.entity.model.ModModelLayers;
import com.saphienyako.feywild.entity.model.WinterPixieModel;
import com.saphienyako.feywild.entity.state.PixieRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class WinterPixieRenderer extends MobRenderer<@NotNull WinterPixieEntity, @NotNull PixieRenderState, @NotNull WinterPixieModel> {

    private static final float MODEL_SCALE = 0.65F;

    public WinterPixieRenderer(EntityRendererProvider.Context context) {
        super(context, new WinterPixieModel(context.bakeLayer(ModModelLayers.WINTER_PIXIE_LAYER)), 0.50f);
    }

    @Override
    public PixieRenderState createRenderState() {
        return new PixieRenderState();
    }

    @Override
    public void extractRenderState(WinterPixieEntity entity, PixieRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);

        state.idleAnimation.copyFrom(entity.IDLE_ANIMATION);
        state.spellCastingAnimation.copyFrom(entity.SPELL_CASTING_ANIMATION);
    }

    @Override
    public @NotNull Identifier getTextureLocation(PixieRenderState state) {
        return Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "textures/entity/winter_pixie.png");
    }

    @Override
    protected void scale(PixieRenderState state, PoseStack poseStack) {
        poseStack.scale(MODEL_SCALE, MODEL_SCALE, MODEL_SCALE);
    }
}
