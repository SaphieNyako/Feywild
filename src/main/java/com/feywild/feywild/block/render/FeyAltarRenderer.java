package com.feywild.feywild.block.render;

import com.feywild.feywild.block.FeyAltarBlock;
import com.feywild.feywild.block.entity.FeyAltar;
import com.feywild.feywild.block.model.FeyAltarModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.moddingx.libx.render.ClientTickHandler;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class FeyAltarRenderer<T extends BlockEntity & GeoAnimatable & FeyAltarBlock.FeyAltarModelProperties> extends GeoBlockRenderer<T> {

    public static FeyAltarRenderer<FeyAltar> create() {
        return new FeyAltarRenderer<>();
    }
    
    public FeyAltarRenderer() {
        super(new FeyAltarModel<>());
    }
    
    @Override
    public void render(@Nonnull BlockEntity tile, float partialTick, @Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int light, int overlay) {
        super.render(tile, partialTick, poseStack, buffer, light, overlay);
        if (!(tile instanceof FeyAltar altar)) return;

        double progressScaled = altar.getProgress() / (double) FeyAltar.MAX_PROGRESS;

        List<ItemStack> stacks = new ArrayList<>();
        for (int slot = 0; slot < altar.getInventory().getSlots(); slot++) {
            ItemStack stack = altar.getInventory().getStackInSlot(slot);
            if (!stack.isEmpty()) stacks.add(stack);
        }

        if (!stacks.isEmpty()) {
            double anglePerStack = (2 * Math.PI) / stacks.size();
            for (int idx = 0; idx < stacks.size(); idx++) {
                //noinspection ConstantConditions
                double shiftX = Math.cos((((double) altar.getLevel().getGameTime() + partialTick) / 8) + (idx * anglePerStack)) * (1 - progressScaled);
                double shiftZ = Math.sin((((double) altar.getLevel().getGameTime() + partialTick) / 8) + (idx * anglePerStack)) * (1 - progressScaled);
                poseStack.pushPose();
                poseStack.translate(0.5 + shiftX, 1 + progressScaled, 0.5 + shiftZ);
                poseStack.mulPose(Axis.YP.rotation((ClientTickHandler.ticksInGame() + partialTick) / 20));
                poseStack.scale(0.85f, 0.85f, 0.85f);
                Minecraft.getInstance().getItemRenderer().renderStatic(stacks.get(idx), ItemDisplayContext.GROUND, light, OverlayTexture.NO_OVERLAY, poseStack, buffer, altar.getLevel(), 0);
                poseStack.popPose();
            }
        }
    }
}
