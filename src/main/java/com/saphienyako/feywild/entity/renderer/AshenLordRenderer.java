package com.saphienyako.feywild.entity.renderer;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.AshenLordEntity;
import com.saphienyako.feywild.entity.model.AshenLordModel;
import com.saphienyako.feywild.entity.model.ModModelLayers;
import com.saphienyako.feywild.entity.state.AshenLordRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class AshenLordRenderer extends MobRenderer<@NotNull AshenLordEntity, @NotNull AshenLordRenderState, @NotNull AshenLordModel> {



    public AshenLordRenderer(EntityRendererProvider.Context context) {
        super(context, new AshenLordModel(context.bakeLayer(ModModelLayers.ASHEN_LORD_LAYER)), 0.50f);
    }

    @Override
    public AshenLordRenderState createRenderState() {
        return new AshenLordRenderState();
    }

    @Override
    public @NotNull Identifier getTextureLocation(AshenLordRenderState ashenLordRenderState) {
        return Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "textures/entity/ashen_lord.png");
    }

    @Override
    public void extractRenderState(@NotNull AshenLordEntity entity, @NotNull AshenLordRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.idleAnimation.copyFrom(entity.IDLE_ANIMATION);
        state.walkingAnimation.copyFrom(entity.WALKING_ANIMATION);
        state.channelAnimation.copyFrom(entity.CHANNEL_ANIMATION);
    }
}
