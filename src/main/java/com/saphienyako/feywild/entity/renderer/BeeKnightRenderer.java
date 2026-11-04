package com.saphienyako.feywild.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.BeeKnightEntity;
import com.saphienyako.feywild.entity.BeeMountEntity;
import com.saphienyako.feywild.entity.model.BeeKnightModel;
import com.saphienyako.feywild.entity.model.ModModelLayers;
import com.saphienyako.feywild.entity.renderer.layer.BeeKnightArmorLayer;
import com.saphienyako.feywild.entity.renderer.layer.BeeKnightSpearLayer;
import com.saphienyako.feywild.entity.state.BeeKnightRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public class BeeKnightRenderer extends MobRenderer<@NotNull BeeKnightEntity, @NotNull BeeKnightRenderState, @NotNull BeeKnightModel> {
    private static final float MODEL_SCALE = 0.65F;

    public BeeKnightRenderer(EntityRendererProvider.Context context) {
        super(context, new BeeKnightModel(context.bakeLayer(ModModelLayers.BEE_KNIGHT_LAYER)), 0.50f);

        this.addLayer(new BeeKnightArmorLayer(this, context.getModelSet()));
        this.addLayer(new BeeKnightSpearLayer(this, context.getItemModelResolver()));
    }

    @Override
    public BeeKnightRenderState createRenderState() {
        return new BeeKnightRenderState();
    }

    @Override
    public void extractRenderState(BeeKnightEntity entity, BeeKnightRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);

        state.sitAnimation.copyFrom(entity.SIT_ANIMATION);
        state.attackAnimation.copyFrom(entity.ATTACK_ANIMATION);
        state.wingAnimation.copyFrom(entity.WING_ANIMATION);

        state.isAttacking = entity.getState() == BeeKnightEntity.State.ATTACK;

        BeeMountEntity mount = entity.getMount();

        state.armorTexture = null;

        if (mount != null) {
            if (mount.getEntityData().get(BeeMountEntity.KNIGHT_HAS_GOLD_ARMOR)) {
                state.armorTexture = Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "textures/entity/bee_knight/bee_knight_gold_armor.png");
            } else if (mount.getEntityData().get(BeeMountEntity.KNIGHT_HAS_DIAMOND_ARMOR)) {
                state.armorTexture = Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "textures/entity/bee_knight/bee_knight_diamond_armor.png");
            } else if (mount.getEntityData().get(BeeMountEntity.KNIGHT_HAS_NETHERITE_ARMOR)) {
                state.armorTexture = Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "textures/entity/bee_knight/bee_knight_netherite_armor.png");
            }
        }

        state.spear = ItemStack.EMPTY;

        if (mount != null) {
            if (mount.getEntityData().get(BeeMountEntity.KNIGHT_HAS_GOLD_SPEAR)) {
                state.spear = new ItemStack(Items.GOLDEN_SPEAR);
            } else if (mount.getEntityData().get(BeeMountEntity.KNIGHT_HAS_DIAMOND_SPEAR)) {
                state.spear = new ItemStack(Items.DIAMOND_SPEAR);
            } else if (mount.getEntityData().get(BeeMountEntity.KNIGHT_HAS_NETHERITE_SPEAR)) {
                state.spear = new ItemStack(Items.NETHERITE_SPEAR);
            }
        }
    }

    @Override
    public @NotNull Identifier getTextureLocation(BeeKnightRenderState state) {
        if (state.isAttacking) {
            return Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "textures/entity/bee_knight/bee_knight_angry.png");
        }

        return Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "textures/entity/bee_knight/bee_knight.png");
    }

    @Override
    protected void scale(BeeKnightRenderState state, PoseStack poseStack) {
        poseStack.scale(MODEL_SCALE, MODEL_SCALE, MODEL_SCALE);
    }
}