package com.saphienyako.feywild.entity.renderer;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.BellsnickelEntity;
import com.saphienyako.feywild.entity.model.BellsnickelModel;
import com.saphienyako.feywild.entity.model.ModModelLayers;
import com.saphienyako.feywild.entity.renderer.layer.BellsnickelLanternLayer;
import com.saphienyako.feywild.entity.state.BellsnickelRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class BellsnickelRenderer extends MobRenderer<@NotNull BellsnickelEntity, @NotNull BellsnickelRenderState, BellsnickelModel> {

    public BellsnickelRenderer(EntityRendererProvider.Context context) {
        super(context, new BellsnickelModel(context.bakeLayer(ModModelLayers.BELLSNICKEL_LAYER)), 0.50f);

        this.addLayer(new BellsnickelLanternLayer(this));
    }

    @Override
    public BellsnickelRenderState createRenderState() {
        return new BellsnickelRenderState();
    }

    @Override
    public void extractRenderState(BellsnickelEntity entity, BellsnickelRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);

        state.idleAnimation.copyFrom(entity.IDLE_ANIMATION);
        state.tradeAnimation.copyFrom(entity.TRADE_ANIMATION);
        state.poseAnimation.copyFrom(entity.POSE_ANIMATION);
        state.walkAnimation.copyFrom(entity.WALK_ANIMATION);

        state.variantName = entity.getVariant().name().toLowerCase(Locale.ROOT);
    }

    @Override
    public @NotNull Identifier getTextureLocation(BellsnickelRenderState state) {
        return Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "textures/entity/bellsnickel/" + state.variantName + ".png");
    }
}
