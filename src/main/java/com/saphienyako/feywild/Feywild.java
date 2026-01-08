package com.saphienyako.feywild;

import com.mojang.logging.LogUtils;
import com.saphienyako.feywild.block.ModBlocks;
import com.saphienyako.feywild.block.entity.ModBlockEntities;
import com.saphienyako.feywild.block.renderer.FeyAltarBlockRenderer;
import com.saphienyako.feywild.effect.ModEffects;
import com.saphienyako.feywild.entity.*;
import com.saphienyako.feywild.entity.model.*;
import com.saphienyako.feywild.entity.renderer.AutumnPixieRenderer;
import com.saphienyako.feywild.entity.renderer.SpringPixieRenderer;
import com.saphienyako.feywild.entity.renderer.SummerPixieRenderer;
import com.saphienyako.feywild.entity.renderer.WinterPixieRenderer;
import com.saphienyako.feywild.events.EventListener;
import com.saphienyako.feywild.item.ModCreativeModeTab;
import com.saphienyako.feywild.item.ModItems;
import com.saphienyako.feywild.network.FeywildNetwork;
import com.saphienyako.feywild.particle.LeafParticle;
import com.saphienyako.feywild.particle.ModParticles;
import com.saphienyako.feywild.particle.SparkleParticle;
import com.saphienyako.feywild.recipe.ModRecipes;
import com.saphienyako.feywild.screen.FeyAltarScreen;
import com.saphienyako.feywild.screen.ModMenuTypes;
import com.saphienyako.feywild.sound.ModSounds;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;


@Mod(Feywild.MOD_ID)
public class Feywild
{
    public static final String MOD_ID = "feywild";

    private static Feywild instance;
    private static final Logger LOGGER = LogUtils.getLogger();

    @SuppressWarnings("removal")
    public Feywild() {

        instance = this;

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::entityAttributes);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            modEventBus.addListener(this::registerLayer);
            modEventBus.addListener(this::registerParticles);
        });

        ModCreativeModeTab.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModSounds.register(modEventBus);
        ModEntities.register(modEventBus);
        ModEffects.register(modEventBus);
        ModParticles.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModRecipes.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        addConfig();

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new EventListener());
    }


    public static Feywild getInstance() {
        return instance;
    }


    private void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.SPRING_PIXIE.get(), SpringPixieEntity.getDefaultAttributes().build());
        event.put(ModEntities.AUTUMN_PIXIE.get(), AutumnPixieEntity.getDefaultAttributes().build());
        event.put(ModEntities.SUMMER_PIXIE.get(), SummerPixieEntity.getDefaultAttributes().build());
        event.put(ModEntities.WINTER_PIXIE.get(), WinterPixieEntity.getDefaultAttributes().build());
    }

    @OnlyIn(Dist.CLIENT)
    private void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.SPRING_PIXIE_LAYER, SpringPixieModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.AUTUMN_PIXIE_LAYER, AutumnPixieModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.SUMMER_PIXIE_LAYER, SummerPixieModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.WINTER_PIXIE_LAYER, WinterPixieModel::createBodyLayer);
    }


    private void commonSetup(final FMLCommonSetupEvent event) {

        event.enqueueWork(FeywildNetwork::register);
        event.enqueueWork(() -> {
            ComposterBlock.COMPOSTABLES.put(ModItems.MANDRAKE.get(), 2.0F);
            ComposterBlock.COMPOSTABLES.put(ModItems.MANDRAKE_ROOT.get(), 0.3F);

        });
    }

    @SuppressWarnings("removal")
    private void addConfig(){
        ModLoadingContext.get().registerConfig(
                ModConfig.Type.COMMON,
                com.saphienyako.feywild.config.ModConfig.COMMON_SPEC,
                "feywild-common.toml"
        );

        ModLoadingContext.get().registerConfig(
                ModConfig.Type.CLIENT,
                com.saphienyako.feywild.config.ModConfig.CLIENT_SPEC,
                "feywild-client.toml"
        );
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("THE FEY ARE PLEASE the server is starting");
    }



    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
                @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
                    LOGGER.info("AND A LITTLE BIT OF PIXIE DUST!");

            EntityRenderers.register(ModEntities.SPRING_PIXIE.get(), SpringPixieRenderer::new);
            EntityRenderers.register(ModEntities.AUTUMN_PIXIE.get(), AutumnPixieRenderer::new);
            EntityRenderers.register(ModEntities.SUMMER_PIXIE.get(), SummerPixieRenderer::new);
            EntityRenderers.register(ModEntities.WINTER_PIXIE.get(), WinterPixieRenderer::new);
            BlockEntityRenderers.register(ModBlockEntities.FEY_ALTAR_BLOCK_ENTITY.get(), FeyAltarBlockRenderer::new);
            MenuScreens.register(ModMenuTypes.FEY_ALTAR_MENU.get(), FeyAltarScreen::new);
        }
    }

    public void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.AUTUMN_LEAF_PARTICLE.get(), LeafParticle.Factory::new);
        event.registerSpriteSet(ModParticles.SPRING_SPARKLE_PARTICLE.get(), SparkleParticle.provider(0, 1, 0));
        event.registerSpriteSet(ModParticles.SUMMER_SPARKLE_PARTICLE.get(), SparkleParticle.provider(1, 0.8f, 0));
        event.registerSpriteSet(ModParticles.AUTUMN_SPARKLE_PARTICLE.get(), SparkleParticle.provider(1, 0.4f, 0));
        event.registerSpriteSet(ModParticles.WINTER_SPARKLE_PARTICLE.get(), SparkleParticle.provider(0.2f, 0.8f, 0.9f));
        event.registerSpriteSet(ModParticles.FEY_SPARKLE_PARTICLE.get(), SparkleParticle.provider(0.3f,0.9f,0.9f));
    }

    private void spawnPlacement(SpawnPlacementRegisterEvent event) {
        event.register(ModEntities.SPRING_PIXIE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpringPixieEntity::canSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.SUMMER_PIXIE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SummerPixieEntity::canSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.AUTUMN_PIXIE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AutumnPixieEntity::canSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.WINTER_PIXIE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WinterPixieEntity::canSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);

    }
}
