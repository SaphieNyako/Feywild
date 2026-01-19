package com.saphienyako.feywild;

import com.mojang.logging.LogUtils;
import com.saphienyako.feywild.block.ModBlocks;
import com.saphienyako.feywild.block.entity.ModBlockEntities;
import com.saphienyako.feywild.block.renderer.FeyAltarBlockRenderer;
import com.saphienyako.feywild.config.FeywildConfig;
import com.saphienyako.feywild.entity.*;
import com.saphienyako.feywild.entity.model.*;
import com.saphienyako.feywild.entity.renderer.*;
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
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
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
import net.neoforged.neoforge.client.NeoForgeRenderTypes;
import net.neoforged.neoforge.client.event.*;
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
        modContainer.registerConfig(ModConfig.Type.CLIENT, FeywildConfig.CLIENT_SPEC);
    }


    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("THE FEY ARE PLEASED!");
    }

    private void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.SPRING_PIXIE.get(), SpringPixieEntity.getDefaultAttributes().build());
        event.put(ModEntities.SUMMER_PIXIE.get(), SummerPixieEntity.getDefaultAttributes().build());
        event.put(ModEntities.AUTUMN_PIXIE.get(), AutumnPixieEntity.getDefaultAttributes().build());
        event.put(ModEntities.WINTER_PIXIE.get(), WinterPixieEntity.getDefaultAttributes().build());
        event.put(ModEntities.SHROOMLING.get(), ShroomlingEntity.getDefaultAttributes().build());
        event.put(ModEntities.MANDRAGORA.get(), MandragoraEntity.getDefaultAttributes().build());
    }
    @SuppressWarnings("unused")
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("THE FEY ARE PLEASED the server is starting");
    }
    @SuppressWarnings("unused")
    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("AND A LITTLE BIT OF PIXIE DUST!");
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.ELVEN_QUARTZ_MOSSY_BRICK.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.SPRING_ELVEN_QUARTZ_MOSSY_BRICK.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.SUMMER_ELVEN_QUARTZ_MOSSY_BRICK.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.WINTER_ELVEN_QUARTZ_MOSSY_BRICK.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.AUTUMN_ELVEN_QUARTZ_MOSSY_BRICK.get(), RenderType.cutout());
        }

        @SubscribeEvent
        public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
            event.getBlockColors().register((state, world, pos, tintIndex) -> {
                        if (tintIndex == 0) return 0xFFFFFF; // base brick, no tint

                        return switch (tintIndex) {
                            case 1 -> world != null && pos != null ? BiomeColors.getAverageFoliageColor(world, pos) : 0x91BD59; // default moss
                            case 2 -> 0xA3E48C; // spring, fresh green
                            case 3 -> 0xFFD35B; // summer, golden leaves
                            case 4 -> 0xA0D8FF; // winter, icy blue
                            case 5 -> 0xD88C3F; // autumn, orange/brown
                            default -> 0xFFFFFF;
                        };
                    },
                    ModBlocks.ELVEN_QUARTZ_MOSSY_BRICK.get(),
                    ModBlocks.SPRING_ELVEN_QUARTZ_MOSSY_BRICK.get(),
                    ModBlocks.SUMMER_ELVEN_QUARTZ_MOSSY_BRICK.get(),
                    ModBlocks.WINTER_ELVEN_QUARTZ_MOSSY_BRICK.get(),
                    ModBlocks.AUTUMN_ELVEN_QUARTZ_MOSSY_BRICK.get()
            );
        }

        @SubscribeEvent
        public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
            event.getItemColors().register((stack, tintIndex) -> {
                        if (tintIndex == 0) return 0xFFFFFF;

                        return switch (tintIndex) {
                            case 1 -> 0x91BD59; // default
                            case 2 -> 0xA3E48C; // spring, fresh green
                            case 3 -> 0xFFD35B; // summer, golden leaves
                            case 4 -> 0xA0D8FF; // winter, icy blue
                            case 5 -> 0xD88C3F; // autumn, orange/brown
                            default -> 0xFFFFFF;
                        };
                    },
                    ModBlocks.ELVEN_QUARTZ_MOSSY_BRICK.get().asItem(),
                    ModBlocks.SPRING_ELVEN_QUARTZ_MOSSY_BRICK.asItem(),
                    ModBlocks.SUMMER_ELVEN_QUARTZ_MOSSY_BRICK.get().asItem(),
                    ModBlocks.WINTER_ELVEN_QUARTZ_MOSSY_BRICK.get().asItem(),
                    ModBlocks.AUTUMN_ELVEN_QUARTZ_MOSSY_BRICK.get().asItem()
            );
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
            event.registerLayerDefinition(ModModelLayers.SHROOMLING_LAYER, ShroomlingModel::createBodyLayer);
            event.registerLayerDefinition(ModModelLayers.MANDRAGORA_LAYER, MandragoraModel::createBodyLayer);
        }

        @SubscribeEvent
        public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ModBlockEntities.FEY_ALTAR_BLOCK_ENTITY.get(), FeyAltarBlockRenderer::new);
            event.registerEntityRenderer(ModEntities.SPRING_PIXIE.get(), SpringPixieRenderer::new);
            event.registerEntityRenderer(ModEntities.SUMMER_PIXIE.get(), SummerPixieRenderer::new);
            event.registerEntityRenderer(ModEntities.AUTUMN_PIXIE.get(), AutumnPixieRenderer::new);
            event.registerEntityRenderer(ModEntities.WINTER_PIXIE.get(), WinterPixieRenderer::new);
            event.registerEntityRenderer(ModEntities.SHROOMLING.get(), ShroomlingRenderer::new);
            event.registerEntityRenderer(ModEntities.MANDRAGORA.get(), MandragoraRenderer::new);
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
            event.register(ModEntities.SHROOMLING.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ShroomlingEntity::canSpawn, RegisterSpawnPlacementsEvent.Operation.REPLACE);
            event.register(ModEntities.MANDRAGORA.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MandragoraEntity::canSpawn, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        }
    }
}
