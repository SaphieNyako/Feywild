package com.feywild.feywild.setup;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.block.render.FeyAltarRenderer;
import com.feywild.feywild.entity.ModEntityTypes;
import com.feywild.feywild.entity.render.AutumnPixieRenderer;
import com.feywild.feywild.entity.render.SpringPixieRenderer;
import com.feywild.feywild.entity.render.SummerPixieRenderer;
import com.feywild.feywild.entity.render.WinterPixieRenderer;
import com.feywild.feywild.item.ModSpawnEggItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

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


    //@OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerRenderers(final FMLClientSetupEvent event)
    {
        //Solved...still not called....
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.SPRING_PIXIE.get(),
                SpringPixieRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.AUTUMN_PIXIE.get(),
                AutumnPixieRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.SUMMER_PIXIE.get(),
               SummerPixieRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.WINTER_PIXIE.get(),
                WinterPixieRenderer::new);

        ModSpawnEggItem.initSpawnEggs();

        ClientRegistry.bindTileEntityRenderer(ModBlocks.FEY_ALTAR_ENTITY.get(),
               FeyAltarRenderer::new);

    }

}
