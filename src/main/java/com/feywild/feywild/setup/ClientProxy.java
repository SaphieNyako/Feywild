package com.feywild.feywild.setup;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.block.render.ElectrifiedGroundRenderer;
import com.feywild.feywild.block.render.FeyAltarRenderer;
import com.feywild.feywild.container.ModContainers;
import com.feywild.feywild.entity.ModEntityTypes;
import com.feywild.feywild.entity.render.*;
import com.feywild.feywild.events.ClientEvents;
import com.feywild.feywild.screens.DwarvenAnvilScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static com.feywild.feywild.entity.ModEntityTypes.DWARF_BLACKSMITH;

@Mod.EventBusSubscriber(modid = FeywildMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientProxy implements IProxy {

    //@OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerRenderers(final FMLClientSetupEvent event) {
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

        ClientRegistry.bindTileEntityRenderer(ModBlocks.FEY_ALTAR_ENTITY.get(),
                FeyAltarRenderer::new);
    }

    @Override
    public void init() {
        RenderTypeLookup.setRenderLayer(ModBlocks.MANDRAKE_CROP.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.SPRING_TREE_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.SPRING_TREE_LEAVES.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.SUMMER_TREE_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.SUMMER_TREE_LEAVES.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.AUTUMN_TREE_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.AUTUMN_TREE_LEAVES.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.WINTER_TREE_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.WINTER_TREE_LEAVES.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.SUNFLOWER.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.SUNFLOWER_STEM.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.DANDELION_STEM.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.DANDELION.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.CROCUS_STEM.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.CROCUS.get(), RenderType.cutout());

        ClientRegistry.bindTileEntityRenderer(ModBlocks.FEY_ALTAR_ENTITY.get(), FeyAltarRenderer::new);

        ClientRegistry.bindTileEntityRenderer(ModBlocks.ELECTRIFIED_GROUND_ENTITY.get(), ElectrifiedGroundRenderer::new);

        MinecraftForge.EVENT_BUS.register(new ClientEvents());
        ScreenManager.register(ModContainers.DWARVEN_ANVIL_CONTAINER.get(), DwarvenAnvilScreen::new);
    }

    @Override
    public World getClientWorld() {
        return Minecraft.getInstance().level;
    }
}
