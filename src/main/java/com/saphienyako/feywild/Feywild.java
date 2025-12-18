package com.saphienyako.feywild;

import com.mojang.logging.LogUtils;
import com.saphienyako.feywild.block.ModBlocks;
import com.saphienyako.feywild.block.entity.ModBlockEntities;
import com.saphienyako.feywild.block.renderer.FeyAltarBlockRenderer;
import com.saphienyako.feywild.config.FeywildConfig;
import com.saphienyako.feywild.entity.*;
import com.saphienyako.feywild.entity.model.*;
import com.saphienyako.feywild.entity.renderer.AutumnPixieRenderer;
import com.saphienyako.feywild.entity.renderer.SpringPixieRenderer;
import com.saphienyako.feywild.entity.renderer.SummerPixieRenderer;
import com.saphienyako.feywild.entity.renderer.WinterPixieRenderer;
import com.saphienyako.feywild.events.ModEventListener;
import com.saphienyako.feywild.item.ModCreativeModeTab;
import com.saphienyako.feywild.item.ModItems;
import com.saphienyako.feywild.network.FeywildNetwork;
import com.saphienyako.feywild.particle.ModParticles;
import com.saphienyako.feywild.particle.SparkleParticleProvider;
import com.saphienyako.feywild.recipe.ModRecipes;
import com.saphienyako.feywild.screen.FeyAltarScreen;
import com.saphienyako.feywild.screen.ModMenuTypes;
import com.saphienyako.feywild.sound.ModSounds;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
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
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

@Mod(Feywild.MOD_ID)
public class Feywild
{
    public static final String MOD_ID = "feywild";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Feywild(IEventBus modEventBus, ModContainer modContainer) {

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

        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.register(new ModEventListener());

        modContainer.registerConfig(ModConfig.Type.COMMON, FeywildConfig.COMMON_SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("THE FEY ARE PLEASED!");
    }

    private void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.SPRING_PIXIE.get(), SpringPixieEntity.getDefaultAttributes().build());
        event.put(ModEntities.SUMMER_PIXIE.get(), SummerPixieEntity.getDefaultAttributes().build());
        event.put(ModEntities.AUTUMN_PIXIE.get(), AutumnPixieEntity.getDefaultAttributes().build());
        event.put(ModEntities.WINTER_PIXIE.get(), WinterPixieEntity.getDefaultAttributes().build());
    }
    @SuppressWarnings("unused")
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("THEY FEY ARE PLEASE the server is starting");
    }
    @SuppressWarnings("unused")
    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("AND A LITTLE BIT OF PIXIE DUST!");
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
        @SubscribeEvent
        private static void spawnPlacement(RegisterSpawnPlacementsEvent event) {
            event.register(ModEntities.SUMMER_PIXIE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SummerPixieEntity::canSpawn, RegisterSpawnPlacementsEvent.Operation.REPLACE);
            event.register(ModEntities.SPRING_PIXIE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpringPixieEntity::canSpawn, RegisterSpawnPlacementsEvent.Operation.REPLACE);
            event.register(ModEntities.AUTUMN_PIXIE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AutumnPixieEntity::canSpawn, RegisterSpawnPlacementsEvent.Operation.REPLACE);
            event.register(ModEntities.WINTER_PIXIE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WinterPixieEntity::canSpawn, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        }
    }
}
