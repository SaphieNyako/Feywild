package com.saphienyako.feywild.entity.renderer;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.TitaniaEntity;
import com.saphienyako.feywild.entity.model.ModModelLayers;
import com.saphienyako.feywild.entity.model.TitaniaModel;
import com.saphienyako.feywild.entity.state.TitaniaRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class TitaniaRenderer extends MobRenderer<@NotNull TitaniaEntity, @NotNull TitaniaRenderState, @NotNull TitaniaModel> {

    public TitaniaRenderer(EntityRendererProvider.Context context) {
        super(context, new TitaniaModel(context.bakeLayer(ModModelLayers.TITANIA_LAYER)), 0.50f);
    }

    @Override
    public TitaniaRenderState createRenderState() {
        return new TitaniaRenderState();
    }

    @Override
    public void extractRenderState(TitaniaEntity entity, TitaniaRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);

        state.idleAnimation.copyFrom(entity.IDLE_ANIMATION);
        state.flyingAnimation.copyFrom(entity.FLYING_ANIMATION);
        state.castingAnimation.copyFrom(entity.CASTING_ANIMATION);
        state.enchantingAnimation.copyFrom(entity.ENCHANTING_ANIMATION);
    }

    @Override
    public @NotNull Identifier getTextureLocation(TitaniaRenderState state) {
        return Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "textures/entity/titania.png");
    }
}

