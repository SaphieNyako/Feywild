package com.feywild.feywild.block.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
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

public class FeyAltarRenderer extends TileEntityRenderer<FeyAltarBlockEntity> {

    private Minecraft minecraft = Minecraft.getInstance();
    //Constructor
    public FeyAltarRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    // Render extra features like items
    @Override
    public void render(FeyAltarBlockEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        if(tileEntityIn.isEmpty()){
            // Initialize
            ClientPlayerEntity playerEntity = minecraft.player;
            int lightLevel = getLightLevel(tileEntityIn.getWorld(),tileEntityIn.getPos().up());
                // Another init just so I don't have to deal with the AtomicDouble
                double shiftX , shiftZ, shiftY;

                //Loop through items and render them
                for(int i = 0; i < tileEntityIn.getSizeInventory() ; i ++){
                        //shift position for items
                        shiftX = Math.cos(((tileEntityIn.getWorld().getGameTime()) + partialTicks + (i * 10)) / 8) / 2;
                        shiftZ = Math.sin(((tileEntityIn.getWorld().getGameTime()) + partialTicks + (i * 10)) / 8) / 2;
                        //render item
                        renderItem(tileEntityIn.getStackInSlot(i), new double[]{0.5d + shiftX, 1d, 0.5d + shiftZ}, Vector3f.YP.rotation((tileEntityIn.getWorld().getGameTime() + partialTicks) / 20), matrixStackIn, bufferIn, partialTicks, combinedOverlayIn, lightLevel, 0.85f);
                }
        }
    }

    // render the item
    private void renderItem(ItemStack itemStack, double[] translation, Quaternion rotation, MatrixStack stack, IRenderTypeBuffer buf, float partialTicks, int combinedOverlay, int lightLevel, float scale){
        stack.push();

        stack.translate(translation[0], translation[1], translation[2]);

        stack.rotate(rotation);
        stack.scale(scale,scale,scale);

        //get item model
        IBakedModel model = minecraft.getItemRenderer().getItemModelWithOverrides(itemStack,null,null);
        //the line of code that actually renders the item
        minecraft.getItemRenderer().renderItem(itemStack, ItemCameraTransforms.TransformType.GROUND,true,stack,buf,lightLevel,combinedOverlay,model);

        stack.pop();
    }

    private int getLightLevel(World world, BlockPos pos){
        int bLight = world.getLightFor(LightType.BLOCK, pos);
        int sLight = world.getLightFor(LightType.SKY, pos);
        return LightTexture.packLight(bLight,sLight);
    }

    //Ancient's note : holy cow this is so much more complicated than on fabric since you have to do most of the things yourself
}
