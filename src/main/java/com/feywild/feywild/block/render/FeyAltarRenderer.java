package com.feywild.feywild.block.render;

import com.feywild.feywild.block.entity.FeyAltarBlockEntity;
import com.feywild.feywild.block.model.FeyAltarModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

import javax.annotation.Nullable;

public class FeyAltarRenderer extends GeoBlockRenderer<FeyAltarBlockEntity> {

    private Minecraft minecraft = Minecraft.getInstance();
    //Constructor
    public FeyAltarRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {

        super(rendererDispatcherIn, new FeyAltarModel());
    }

    @Override
    public void render(FeyAltarBlockEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(tileEntityIn, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        if(tileEntityIn.isEmpty()){
            // Initialize
            ClientPlayerEntity playerEntity = minecraft.player;
            int lightLevel = getLightLevel(tileEntityIn.getLevel(),tileEntityIn.getBlockPos().above());
            // Another init just so I don't have to deal with the AtomicDouble
            double shiftX , shiftZ, shiftY;

            //Loop through items and render them
            for(int i = 0; i < tileEntityIn.getContainerSize() ; i ++){
                //shift position for items
                shiftX = Math.cos(((tileEntityIn.getLevel().getGameTime()) + partialTicks + (i * 10)) / 8) / 2;
                shiftZ = Math.sin(((tileEntityIn.getLevel().getGameTime()) + partialTicks + (i * 10)) / 8) / 2;
                //render item
                renderItem(tileEntityIn.getItem(i), new double[]{0.5d + shiftX, 1d, 0.5d + shiftZ}, Vector3f.YP.rotation((tileEntityIn.getLevel().getGameTime() + partialTicks) / 20), matrixStackIn, bufferIn, partialTicks,999999999, lightLevel, 0.85f);
            }
        }
    }

    // render the item
    private void renderItem(ItemStack itemStack, double[] translation, Quaternion rotation, MatrixStack stack, IRenderTypeBuffer buf,float partialTicks, int combinedOverlay, int lightLevel, float scale){
        stack.pushPose();

        stack.translate(translation[0], translation[1], translation[2]);

        stack.mulPose(rotation);
        stack.scale(scale,scale,scale);

        //get item model
        IBakedModel model = minecraft.getItemRenderer().getModel(itemStack,null,null);
        //the line of code that actually renders the item
        minecraft.getItemRenderer().render(itemStack, ItemCameraTransforms.TransformType.GROUND,true,stack,buf,lightLevel,combinedOverlay,model);

        stack.popPose();
    }

    private int getLightLevel(World world, BlockPos pos){
        int bLight = world.getBrightness(LightType.BLOCK, pos);
        int sLight = world.getBrightness(LightType.SKY, pos);
        return LightTexture.pack(bLight,sLight);
    }

    //Ancient's note : holy cow this is so much more complicated than on fabric since you have to do most of the things yourself
}
