package com.saphienyako.feywild;

import com.mojang.logging.LogUtils;
import com.saphienyako.feywild.block.ModBlocks;
import com.saphienyako.feywild.block.entity.ModBlockEntities;
import com.saphienyako.feywild.block.renderer.FairyAltarBlockRenderer;
import com.saphienyako.feywild.effect.ModEffects;
import com.saphienyako.feywild.entity.*;
import com.saphienyako.feywild.entity.model.*;
import com.saphienyako.feywild.entity.renderer.AutumnPixieRenderer;
import com.saphienyako.feywild.entity.renderer.SpringPixieRenderer;
import com.saphienyako.feywild.entity.renderer.SummerPixieRenderer;
import com.saphienyako.feywild.entity.renderer.WinterPixieRenderer;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Feywild.MOD_ID)
public class Feywild
{
    public static final String MOD_ID = "feywild";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Feywild() {

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::entityAttributes);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerLayer);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerParticles));

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

        MinecraftForge.EVENT_BUS.register(this);
        //modEventBus.addListener(this::addCreative);
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
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        //Added ModCreativeModeTab for the mod itself
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.SPRING_PIXIE.get(), SpringPixieRenderer::new);
            EntityRenderers.register(ModEntities.AUTUMN_PIXIE.get(), AutumnPixieRenderer::new);
            EntityRenderers.register(ModEntities.SUMMER_PIXIE.get(), SummerPixieRenderer::new);
            EntityRenderers.register(ModEntities.WINTER_PIXIE.get(), WinterPixieRenderer::new);
            BlockEntityRenderers.register(ModBlockEntities.FEY_ALTAR_BLOCK_ENTITY.get(), FairyAltarBlockRenderer::new);
            MenuScreens.register(ModMenuTypes.FAIRY_ALTAR_MENU.get(), FeyAltarScreen::new);
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
}
