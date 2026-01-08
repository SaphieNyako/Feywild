package com.saphienyako.feywild;


import com.saphienyako.feywild.block.ModBlocks;
import com.saphienyako.feywild.block.entity.ModTileEntities;
import com.saphienyako.feywild.block.renderer.FeyAltarBlockRenderer;
import com.saphienyako.feywild.entity.*;
import com.saphienyako.feywild.entity.renderer.AutumnPixieRenderer;
import com.saphienyako.feywild.entity.renderer.SpringPixieRenderer;
import com.saphienyako.feywild.entity.renderer.SummerPixieRenderer;
import com.saphienyako.feywild.entity.renderer.WinterPixieRenderer;
import com.saphienyako.feywild.events.EventListener;
import com.saphienyako.feywild.item.ModItems;
import com.saphienyako.feywild.network.FeywildNetwork;
import com.saphienyako.feywild.particle.LeafParticle;
import com.saphienyako.feywild.particle.ModParticles;
import com.saphienyako.feywild.particle.SparkleParticle;
import com.saphienyako.feywild.recipe.ModRecipes;
import com.saphienyako.feywild.screen.FeyAltarScreen;
import com.saphienyako.feywild.screen.ModMenuTypes;
import com.saphienyako.feywild.sound.ModSounds;
import net.minecraft.block.ComposterBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


@Mod(Feywild.MOD_ID)
public class Feywild
{
    public static final String MOD_ID = "feywild";

    private static Feywild instance;

    public Feywild() {

        instance = this;


        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::entityAttributes);

        ModEntities.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModSounds.register(modEventBus);

        ModParticles.register(modEventBus);
        ModTileEntities.register(modEventBus);
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


    private void commonSetup(final FMLCommonSetupEvent event) {

        event.enqueueWork(FeywildNetwork::register);
        event.enqueueWork(() -> {
            ComposterBlock.COMPOSTABLES.put(ModItems.MANDRAKE.get(), 2.0F);
            ComposterBlock.COMPOSTABLES.put(ModItems.MANDRAKE_ROOT.get(), 0.3F);


            EntitySpawnPlacementRegistry.register(ModEntities.SPRING_PIXIE.get(),
                    EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    SpringPixieEntity::canSpawn);

            EntitySpawnPlacementRegistry.register(ModEntities.SUMMER_PIXIE.get(),
                    EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    SummerPixieEntity::canSpawn);

            EntitySpawnPlacementRegistry.register(ModEntities.AUTUMN_PIXIE.get(),
                    EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    AutumnPixieEntity::canSpawn);

            EntitySpawnPlacementRegistry.register(ModEntities.WINTER_PIXIE.get(),
                    EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    WinterPixieEntity::canSpawn);

        });
    }

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
    public void onServerStarting(FMLServerStartingEvent event) {

    }



    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            RenderingRegistry.registerEntityRenderingHandler(ModEntities.SPRING_PIXIE.get(), SpringPixieRenderer::new);
            RenderingRegistry.registerEntityRenderingHandler(ModEntities.AUTUMN_PIXIE.get(), AutumnPixieRenderer::new);
            RenderingRegistry.registerEntityRenderingHandler(ModEntities.SUMMER_PIXIE.get(), SummerPixieRenderer::new);
            RenderingRegistry.registerEntityRenderingHandler(ModEntities.WINTER_PIXIE.get(), WinterPixieRenderer::new);
            ClientRegistry.bindTileEntityRenderer(ModTileEntities.FEY_ALTAR_BLOCK_ENTITY.get(), FeyAltarBlockRenderer::new);

            ParticleManager manager = Minecraft.getInstance().particleEngine;
            manager.register(ModParticles.AUTUMN_LEAF_PARTICLE.get(), LeafParticle.Factory::new);
                   manager.register(
                    ModParticles.SPRING_SPARKLE_PARTICLE.get(),
                    sprites -> new SparkleParticle.Factory(sprites,0, 1, 0)
            );

            manager.register(
                    ModParticles.SUMMER_SPARKLE_PARTICLE.get(),
                    sprites -> new SparkleParticle.Factory(sprites,1, 0.8f, 0)
            );

            manager.register(
                    ModParticles.AUTUMN_SPARKLE_PARTICLE.get(),
                    sprites -> new SparkleParticle.Factory(sprites,1, 0.4f, 0)
            );

            manager.register(
                    ModParticles.WINTER_SPARKLE_PARTICLE.get(),
                    sprites -> new SparkleParticle.Factory(sprites,0.2f, 0.8f, 0.9f)
            );

            manager.register(
                    ModParticles.FEY_SPARKLE_PARTICLE.get(),
                    sprites -> new SparkleParticle.Factory(sprites,0.3f, 0.9f, 0.9f)
            );

            ScreenManager.register(ModMenuTypes.FEY_ALTAR_MENU.get(),FeyAltarScreen::new);

            RenderTypeLookup.setRenderLayer(ModBlocks.MANDRAKE_CROP.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(ModBlocks.FEY_ALTAR.get(), RenderType.cutout());
        }
    }
}
