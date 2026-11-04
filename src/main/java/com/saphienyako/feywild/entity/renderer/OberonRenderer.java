package com.saphienyako.feywild.entity.renderer;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.OberonEntity;
import com.saphienyako.feywild.entity.model.ModModelLayers;
import com.saphienyako.feywild.entity.model.OberonModel;
import com.saphienyako.feywild.entity.state.OberonRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class OberonRenderer extends MobRenderer<@NotNull OberonEntity, @NotNull OberonRenderState, @NotNull OberonModel> {

    public OberonRenderer(EntityRendererProvider.Context context) {
        super(context, new OberonModel(context.bakeLayer(ModModelLayers.OBERON_LAYER)), 0.50f);
    }

    @Override
    public OberonRenderState createRenderState() {
        return new OberonRenderState();
    }

    @Override
    public void extractRenderState(OberonEntity entity, OberonRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);

        state.idleAnimation.copyFrom(entity.IDLE_ANIMATION);
        state.walkingAnimation.copyFrom(entity.WALKING_ANIMATION);
        state.chargingAnimation.copyFrom(entity.CHARGING_ANIMATION);
        state.kickingAnimation.copyFrom(entity.KICKING_ANIMATION);
        state.rearingAnimation.copyFrom(entity.REARING_ANIMATION);
    }

    @Override
    public @NotNull Identifier getTextureLocation(OberonRenderState state) {
        return Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "textures/entity/oberon.png");
    }
}
