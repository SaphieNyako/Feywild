package com.saphienyako.feywild.entity.renderer;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.SpriteEntity;
import com.saphienyako.feywild.entity.model.ModModelLayers;
import com.saphienyako.feywild.entity.model.SpriteModel;
import com.saphienyako.feywild.entity.state.SpriteRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class SpriteRenderer extends MobRenderer<@NotNull SpriteEntity, @NotNull SpriteRenderState, @NotNull SpriteModel> {

    public SpriteRenderer(EntityRendererProvider.Context context) {
        super(context, new SpriteModel(context.bakeLayer(ModModelLayers.SPRITE_LAYER)), 0.50f);
    }

    @Override
    public SpriteRenderState createRenderState() {
        return new SpriteRenderState();
    }

    @Override
    public void extractRenderState(SpriteEntity entity, SpriteRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);

        state.flyAnimation.copyFrom(entity.FLY_ANIMATION);
        state.flyIdleAnimation.copyFrom(entity.FLY_IDLE_ANIMATION);
        state.happyAnimation.copyFrom(entity.HAPPY_ANIMATION);
        state.angryAnimation.copyFrom(entity.ANGRY_ANIMATION);

        state.variantName = entity.getVariant().name().toLowerCase(Locale.ROOT);
    }

    @Override
    public @NotNull Identifier getTextureLocation(SpriteRenderState state) {
        return Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "textures/entity/sprite/" + state.variantName + ".png");
    }
}
