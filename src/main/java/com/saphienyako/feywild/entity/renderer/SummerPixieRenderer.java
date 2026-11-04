package com.saphienyako.feywild.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.SummerPixieEntity;
import com.saphienyako.feywild.entity.model.ModModelLayers;
import com.saphienyako.feywild.entity.model.SummerPixieModel;
import com.saphienyako.feywild.entity.state.PixieRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class SummerPixieRenderer extends MobRenderer<@NotNull SummerPixieEntity, @NotNull PixieRenderState, @NotNull SummerPixieModel> {

    private static final float MODEL_SCALE = 0.65F;

    public SummerPixieRenderer(EntityRendererProvider.Context context) {
        super(context, new SummerPixieModel(context.bakeLayer(ModModelLayers.SUMMER_PIXIE_LAYER)), 0.50f);
    }

    @Override
    public PixieRenderState createRenderState() {
        return new PixieRenderState();
    }

    @Override
    public void extractRenderState(SummerPixieEntity entity, PixieRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);

        state.idleAnimation.copyFrom(entity.IDLE_ANIMATION);
        state.spellCastingAnimation.copyFrom(entity.SPELL_CASTING_ANIMATION);
    }

    @Override
    public @NotNull Identifier getTextureLocation(PixieRenderState state) {
        return Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "textures/entity/summer_pixie.png");
    }

    @Override
    protected void scale(PixieRenderState state, PoseStack poseStack) {
        poseStack.scale(MODEL_SCALE, MODEL_SCALE, MODEL_SCALE);
    }
}