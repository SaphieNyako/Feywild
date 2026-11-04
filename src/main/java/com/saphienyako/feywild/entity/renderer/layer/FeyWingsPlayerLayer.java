package com.saphienyako.feywild.entity.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.compat.ModCompat;
import com.saphienyako.feywild.entity.model.FeyWingsModel;
import com.saphienyako.feywild.entity.model.ModModelLayers;
import com.saphienyako.feywild.item.PixieWingTiaraItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class FeyWingsPlayerLayer extends RenderLayer<@NotNull AvatarRenderState, @NotNull PlayerModel> {

    private final FeyWingsModel wingsModel;

    public FeyWingsPlayerLayer(RenderLayerParent<@NotNull AvatarRenderState, @NotNull PlayerModel> parent, EntityModelSet modelSet) {
        super(parent);
        this.wingsModel = new FeyWingsModel(modelSet.bakeLayer(ModModelLayers.FEY_WINGS_LAYER));
    }

    @Override
    public void submit(@NotNull PoseStack poseStack, @NotNull SubmitNodeCollector collector, int packedLight, AvatarRenderState state, float limbSwing, float limbSwingAmount) {
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.level == null) {
            return;
        }

        Entity entity = minecraft.level.getEntity(state.id);

        if (!(entity instanceof Player player)) {
            return;
        }

        ItemStack stack = ModCompat.findWingTiara(player);

        if (!(stack.getItem() instanceof PixieWingTiaraItem wingItem)) {
            return;
        }

        Identifier wingTexture = wingItem.getWingTexture(stack);

        poseStack.pushPose();

        this.getParentModel().body.translateAndRotate(poseStack);

        poseStack.translate(0.0D, 1.1D, 0.1D);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
        poseStack.scale(1.0F, 1.0F, 1.0F);

        this.wingsModel.setupAnim(state);

        renderColoredCutoutModel(this.wingsModel, wingTexture, poseStack, collector, packedLight, state, -1, 0);

        poseStack.popPose();

    }
}