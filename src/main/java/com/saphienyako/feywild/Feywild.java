package com.saphienyako.feywild;

import com.mojang.logging.LogUtils;
import com.saphienyako.feywild.block.ModBlocks;
import com.saphienyako.feywild.block.entity.ModBlockEntities;
import com.saphienyako.feywild.block.renderer.FeyAltarBlockRenderer;
import com.saphienyako.feywild.entity.*;
import com.saphienyako.feywild.entity.model.*;
import com.saphienyako.feywild.entity.renderer.AutumnPixieRenderer;
import com.saphienyako.feywild.entity.renderer.SpringPixieRenderer;
import com.saphienyako.feywild.entity.renderer.SummerPixieRenderer;
import com.saphienyako.feywild.entity.renderer.WinterPixieRenderer;
import com.saphienyako.feywild.item.ModCreativeModeTab;
import com.saphienyako.feywild.item.ModItems;
import com.saphienyako.feywild.network.FeywildNetwork;
import com.saphienyako.feywild.particle.ModParticles;
import com.saphienyako.feywild.particle.SparkleParticleProvider;
import com.saphienyako.feywild.recipe.ModRecipes;
import com.saphienyako.feywild.screen.FeyAltarScreen;
import com.saphienyako.feywild.screen.ModMenuTypes;
import com.saphienyako.feywild.sound.ModSounds;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

@Mod(Feywild.MOD_ID)

public class Feywild
{
    public static final String MOD_ID = "feywild";

    private static Feywild instance;
    private static final Logger LOGGER = LogUtils.getLogger();

    public Feywild(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(FeywildNetwork::register);
        modEventBus.addListener(this::entityAttributes);

        ModCreativeModeTab.register(modEventBus);

        ModParticles.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModSounds.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModEntities.register(modEventBus);
        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
        /*
        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));

        */
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        //Added ModCreativeModeTab for the mod itself
    }

    private void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.SPRING_PIXIE.get(), SpringPixieEntity.getDefaultAttributes().build());
        event.put(ModEntities.SUMMER_PIXIE.get(), SummerPixieEntity.getDefaultAttributes().build());
        event.put(ModEntities.AUTUMN_PIXIE.get(), AutumnPixieEntity.getDefaultAttributes().build());
        event.put(ModEntities.WINTER_PIXIE.get(), WinterPixieEntity.getDefaultAttributes().build());
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            // LOGGER.info("HELLO FROM CLIENT SETUP");
            // LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }

        @SubscribeEvent
        public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(ModParticles.SPRING_SPARKLE_PARTICLE.get(), spriteSet -> new SparkleParticleProvider(spriteSet, 0, 1, 0));
            event.registerSpriteSet(ModParticles.SUMMER_SPARKLE_PARTICLE.get(), spriteSet -> new SparkleParticleProvider(spriteSet, 1, 0.8f, 0));
            event.registerSpriteSet(ModParticles.WINTER_SPARKLE_PARTICLE.get(), spriteSet -> new SparkleParticleProvider(spriteSet, 0.2f, 0.8f, 0.9f));
            event.registerSpriteSet(ModParticles.AUTUMN_SPARKLE_PARTICLE.get(), spriteSet -> new SparkleParticleProvider(spriteSet, 1, 0.4f, 0));
            event.registerSpriteSet(ModParticles.FEY_SPARKLE_PARTICLE.get(), spriteSet -> new SparkleParticleProvider(spriteSet, 0.3f,0.9f,0.9f));
        }

        @SubscribeEvent
        public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(ModModelLayers.SPRING_PIXIE_LAYER, SpringPixieModel::createBodyLayer);
            event.registerLayerDefinition(ModModelLayers.SUMMER_PIXIE_LAYER, SummerPixieModel::createBodyLayer);
            event.registerLayerDefinition(ModModelLayers.AUTUMN_PIXIE_LAYER, AutumnPixieModel::createBodyLayer);
            event.registerLayerDefinition(ModModelLayers.WINTER_PIXIE_LAYER, WinterPixieModel::createBodyLayer);
        }

        @SubscribeEvent
        public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ModBlockEntities.FEY_ALTAR_BLOCK_ENTITY.get(), FeyAltarBlockRenderer::new);
            event.registerEntityRenderer(ModEntities.SPRING_PIXIE.get(), SpringPixieRenderer::new);
            event.registerEntityRenderer(ModEntities.SUMMER_PIXIE.get(), SummerPixieRenderer::new);
            event.registerEntityRenderer(ModEntities.AUTUMN_PIXIE.get(), AutumnPixieRenderer::new);
            event.registerEntityRenderer(ModEntities.WINTER_PIXIE.get(), WinterPixieRenderer::new);

        }


        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(ModMenuTypes.FEY_ALTAR_MENU.get(), FeyAltarScreen::new);
        }
    }
}
