package com.saphienyako.feywild.entity.renderer;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.base.TreeEntBase;
import com.saphienyako.feywild.entity.model.ModModelLayers;
import com.saphienyako.feywild.entity.model.TreeEntModel;
import com.saphienyako.feywild.entity.state.TreeEntRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class TreeEntRenderer extends MobRenderer<@NotNull TreeEntBase, @NotNull TreeEntRenderState, @NotNull TreeEntModel> {

    public TreeEntRenderer(EntityRendererProvider.Context context) {
        super(context, new TreeEntModel(context.bakeLayer(ModModelLayers.TREE_ENT_LAYER)), 0.50f);
    }

    @Override
    public TreeEntRenderState createRenderState() {
        return new TreeEntRenderState();
    }

    @Override
    public void extractRenderState(TreeEntBase entity, TreeEntRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);

        state.idleAnimation.copyFrom(entity.IDLE_ANIMATION);
        state.walkQuickAnimation.copyFrom(entity.WALK_QUICK_ANIMATION);
        state.attackAnimation.copyFrom(entity.ATTACK_ANIMATION);
        state.walkAnimation.copyFrom(entity.WALK_ANIMATION);

        state.alignmentName = entity.getAlignment().name().toLowerCase(Locale.ROOT);
    }

    @Override
    public @NotNull Identifier getTextureLocation(TreeEntRenderState state) {
        return Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "textures/entity/tree_ent/" + state.alignmentName + ".png");
    }
}
