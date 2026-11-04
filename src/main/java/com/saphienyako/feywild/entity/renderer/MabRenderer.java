package com.saphienyako.feywild.entity.renderer;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.MabEntity;
import com.saphienyako.feywild.entity.model.MabModel;
import com.saphienyako.feywild.entity.model.ModModelLayers;
import com.saphienyako.feywild.entity.state.MabRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class MabRenderer extends MobRenderer<@NotNull MabEntity, @NotNull MabRenderState, @NotNull MabModel> {

    public MabRenderer(EntityRendererProvider.Context context) {
        super(context, new MabModel(context.bakeLayer(ModModelLayers.MAB_LAYER)), 0.50f);
    }

    @Override
    public MabRenderState createRenderState() {
        return new MabRenderState();
    }

    @Override
    public void extractRenderState(MabEntity entity, MabRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);

        state.flyingIdleAnimation.copyFrom(entity.FLYING_IDLE_ANIMATION);
        state.flyingAnimation.copyFrom(entity.FLYING_ANIMATION);
        state.channelAnimation.copyFrom(entity.CHANNEL_ANIMATION);
        state.intimidationAnimation.copyFrom(entity.INTIMIDATION_ANIMATION);
        state.attackingAnimation.copyFrom(entity.ATTACKING_ANIMATION);
    }

    @Override
    public @NotNull Identifier getTextureLocation(MabRenderState state) {
        return Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "textures/entity/mab.png");
    }
}