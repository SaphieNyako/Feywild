package com.feywild.feywild.block.render;

import com.feywild.feywild.block.ClientDataBlock;
import com.feywild.feywild.block.entity.FeyAltarBlockEntity;
import com.feywild.feywild.block.model.FeyAltarModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class FeyAltarRenderer extends GeoBlockRenderer<FeyAltarBlockEntity> {

    double lerp = 1;

    public FeyAltarRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {

        super(rendererDispatcherIn, new FeyAltarModel());
    }

    // TODO code needs changing after other tODO comments got implemented.
    @Override
    public void render(FeyAltarBlockEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(tileEntityIn, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        if (tileEntityIn.isEmpty() && tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getBlock() instanceof ClientDataBlock) {
            // Initialize
            int data = ((ClientDataBlock) tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getBlock()).getData();
            int lightLevel = getLightLevel(tileEntityIn.getLevel(), tileEntityIn.getBlockPos().above());
            // Another init just so I don't have to deal with the AtomicDouble
            double shiftX, shiftZ;

            lerp = data == 1 ? lerp - 0.01d : 1;

            //Loop through items and render them
            for (int i = 0; i < tileEntityIn.getContainerSize(); i++) {
                //shift position for items
                shiftX = Math.cos(((tileEntityIn.getLevel().getGameTime()) + partialTicks + (i * 10)) / 8) / 2 * lerp;
                shiftZ = Math.sin(((tileEntityIn.getLevel().getGameTime()) + partialTicks + (i * 10)) / 8) / 2 * lerp;
                //render item
                if (lerp < 1)
                    tileEntityIn.getLevel().addParticle(ParticleTypes.END_ROD, true, tileEntityIn.getBlockPos().getX() + 0.5d + shiftX, tileEntityIn.getBlockPos().getY() + 1d + 1 - lerp, tileEntityIn.getBlockPos().getZ() + 0.5d + shiftZ, 0, 0, 0);
                renderItem(tileEntityIn.getItem(i), new double[]{0.5d + shiftX, 1d + 1 - lerp, 0.5d + shiftZ}, Vector3f.YP.rotation((tileEntityIn.getLevel().getGameTime() + partialTicks) / 20), matrixStackIn, bufferIn, partialTicks, 999999999, lightLevel, 0.85f);
            }
            if (lerp <= 0.07) {
                ((ClientDataBlock) tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getBlock()).setData(0);
                lerp = 1;
            }
        }
    }

    // render the item
    private void renderItem(ItemStack itemStack, double[] translation, Quaternion rotation, MatrixStack stack, IRenderTypeBuffer buf, float partialTicks, int combinedOverlay, int lightLevel, float scale) {
        stack.pushPose();

        stack.translate(translation[0], translation[1], translation[2]);

        stack.mulPose(rotation);
        stack.scale(scale, scale, scale);

        //get item model
        IBakedModel model = Minecraft.getInstance().getItemRenderer().getModel(itemStack, null, null);
        //the line of code that actually renders the item
        Minecraft.getInstance().getItemRenderer().render(itemStack, ItemCameraTransforms.TransformType.GROUND, true, stack, buf, lightLevel, combinedOverlay, model);

        stack.popPose();
    }

    private int getLightLevel(World world, BlockPos pos) {
        int bLight = world.getBrightness(LightType.BLOCK, pos);
        int sLight = world.getBrightness(LightType.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }

    //Ancient's note : holy cow this is so much more complicated than on fabric since you have to do most of the things yourself
    //noeppi's note: holy cow you're doing it more complicated than needed :P
}
