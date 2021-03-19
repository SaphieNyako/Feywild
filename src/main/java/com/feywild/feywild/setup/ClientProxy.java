package com.feywild.feywild.setup;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.block.entity.FeyAltarRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FeywildMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientProxy implements IProxy{
    @Override
    public void init() {
        RenderTypeLookup.setRenderLayer(ModBlocks.MANDRAKE_CROP.get(), RenderType.getCutout());
        ClientRegistry.bindTileEntityRenderer(ModBlocks.FEY_ALTAR_ENTITY.get(), FeyAltarRenderer::new);
    }

    @Override
    public World getClientWorld() {
        return Minecraft.getInstance().world;
    }
}
