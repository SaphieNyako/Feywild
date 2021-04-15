package com.feywild.feywild.setup;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.block.render.FeyAltarRenderer;
import com.feywild.feywild.entity.ModEntityTypes;
import com.feywild.feywild.entity.render.*;
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

import static com.feywild.feywild.entity.ModEntityTypes.DWARF_BLACKSMITH;

@Mod.EventBusSubscriber(modid = FeywildMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientProxy implements IProxy{
    @Override
    public void init() {
        RenderTypeLookup.setRenderLayer(ModBlocks.MANDRAKE_CROP.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.SPRING_TREE_SAPLING.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.SPRING_TREE_LEAVES.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.SUMMER_TREE_SAPLING.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.SUMMER_TREE_LEAVES.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.AUTUMN_TREE_SAPLING.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.AUTUMN_TREE_LEAVES.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.WINTER_TREE_SAPLING.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.WINTER_TREE_LEAVES.get(), RenderType.getCutout());


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

        RenderingRegistry.registerEntityRenderingHandler(DWARF_BLACKSMITH.get(),
                DwarfBlacksmithRenderer::new);

        ModSpawnEggItem.initSpawnEggs();

        ClientRegistry.bindTileEntityRenderer(ModBlocks.FEY_ALTAR_ENTITY.get(),
               FeyAltarRenderer::new);



    }

}
