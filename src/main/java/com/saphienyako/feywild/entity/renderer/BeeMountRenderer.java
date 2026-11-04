package com.saphienyako.feywild.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.BeeKnightEntity;
import com.saphienyako.feywild.entity.BeeMountEntity;
import com.saphienyako.feywild.entity.model.BeeMountModel;
import com.saphienyako.feywild.entity.model.ModModelLayers;
import com.saphienyako.feywild.entity.renderer.layer.BeeMountArmorLayer;
import com.saphienyako.feywild.entity.state.BeeMountRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class BeeMountRenderer extends MobRenderer<@NotNull BeeMountEntity, @NotNull BeeMountRenderState, @NotNull BeeMountModel> {

    private static final float MODEL_SCALE = 0.85F;

    public BeeMountRenderer(EntityRendererProvider.Context context) {
        super(context, new BeeMountModel(context.bakeLayer(ModModelLayers.BEE_MOUNT_LAYER)), 0.50f);

        this.addLayer(new BeeMountArmorLayer(this, context.getModelSet()));
    }

    @Override
    public BeeMountRenderState createRenderState() {
        return new BeeMountRenderState();
    }

    /** @noinspection ConstantValue*/
    @Override
    public void extractRenderState(BeeMountEntity entity, BeeMountRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);

        state.flyAnimation.copyFrom(entity.FLY_ANIMATION);
        state.flyIdleAnimation.copyFrom(entity.FLY_IDLE_ANIMATION);

        BeeKnightEntity knight = entity.getLinkedKnight();
        state.isAngry = knight != null && knight.getState() == BeeKnightEntity.State.ATTACK;

        state.armorTexture = null;

        if (entity.getEntityData().get(BeeMountEntity.MOUNT_HAS_NETHERITE_ARMOR)) {
            state.armorTexture = Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "textures/entity/bee_knight/bee_netherite_armor.png");
        } else if (entity.getEntityData().get(BeeMountEntity.MOUNT_HAS_DIAMOND_ARMOR)) {
            state.armorTexture = Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "textures/entity/bee_knight/bee_diamond_armor.png");
        } else if (entity.getEntityData().get(BeeMountEntity.MOUNT_HAS_GOLD_ARMOR)) {
            state.armorTexture = Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "textures/entity/bee_knight/bee_gold_armor.png");
        }
    }

    @Override
    public @NotNull Identifier getTextureLocation(BeeMountRenderState state) {
        if (state.isAngry) {
            return Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "textures/entity/bee_knight/bee_mount_angry.png");
        }

        return Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "textures/entity/bee_knight/bee_mount.png");
    }

    @Override
    protected void scale(BeeMountRenderState state, PoseStack poseStack) {
        poseStack.scale(MODEL_SCALE, MODEL_SCALE, MODEL_SCALE);
    }
}