package com.saphienyako.feywild;

import com.mojang.logging.LogUtils;
import com.saphienyako.feywild.block.ModBlocks;
import com.saphienyako.feywild.block.entity.ModBlockEntities;
import com.saphienyako.feywild.block.renderer.FeyAltarBlockRenderer;
import com.saphienyako.feywild.data.BeeKnightItems;
import com.saphienyako.feywild.data.BellsnickelItems;
import com.saphienyako.feywild.data.MandragoraItems;
import com.saphienyako.feywild.data.ShroomlingItems;
import com.saphienyako.feywild.effect.ModEffects;
import com.saphienyako.feywild.entity.*;
import com.saphienyako.feywild.entity.model.*;
import com.saphienyako.feywild.entity.renderer.*;
import com.saphienyako.feywild.entity.renderer.layer.FeyWingsPlayerLayer;
import com.saphienyako.feywild.events.EventListener;
import com.saphienyako.feywild.item.ModItems;
import com.saphienyako.feywild.network.FeywildNetwork;
import com.saphienyako.feywild.particle.LeafParticle;
import com.saphienyako.feywild.particle.ModParticles;
import com.saphienyako.feywild.particle.SparkleParticle;
import com.saphienyako.feywild.recipe.ModRecipes;
import com.saphienyako.feywild.screen.BeeKnightScreen;
import com.saphienyako.feywild.screen.BellsnickelScreen;
import com.saphienyako.feywild.screen.FeyAltarScreen;
import com.saphienyako.feywild.screen.ModMenuTypes;
import com.saphienyako.feywild.sound.ModSounds;
import com.saphienyako.feywild.worldgen.ModConfiguredFeatures;
import com.saphienyako.feywild.worldgen.ModPlacedFeatures;
import com.saphienyako.feywild.worldgen.features.ModFeatures;
import com.saphienyako.feywild.worldgen.processor.FeywildProcessors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;


@Mod(Feywild.MOD_ID)
public class Feywild
{
    public static final String MOD_ID = "feywild";

    private static Feywild instance;
    public static final Logger LOGGER = LogUtils.getLogger();

    public Feywild() {

        instance = this;

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::entityAttributes);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            modEventBus.addListener(this::registerLayer);
            modEventBus.addListener(this::registerParticles);
        });
        modEventBus.addListener(this::enqueueIMC);

        FMLJavaModLoadingContext.get().getModEventBus().addListener((FMLCommonSetupEvent e) -> {
            FeywildProcessors.register();
        });

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModSounds.register(modEventBus);
        ModEntities.register(modEventBus);
        ModEffects.register(modEventBus);
        ModParticles.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModRecipes.register(modEventBus);

        //Needs register
        ModConfiguredFeatures.register(modEventBus);
        ModPlacedFeatures.register(modEventBus);

        ModFeatures.FEATURES.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        addConfig();

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new EventListener());
        MinecraftForge.EVENT_BUS.addListener(this::reloadData);
    }

    //Creative Tab in 1.19.2 is done in the Items Properties.

    public static Feywild getInstance() {
        return instance;
    }

    public void reloadData(AddReloadListenerEvent event) {
        event.addListener(ShroomlingItems.createReloadListener());
        event.addListener(MandragoraItems.createReloadListener());
        event.addListener(BellsnickelItems.createReloadListener());
        event.addListener(BeeKnightItems.createReloadListener());
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        InterModComms.sendTo(
                CuriosApi.MODID,
                SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.HEAD.getMessageBuilder()
                        .size(1)
                        .build()
        );
    }

    private void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.SPRING_PIXIE.get(), SpringPixieEntity.getDefaultAttributes().build());
        event.put(ModEntities.AUTUMN_PIXIE.get(), AutumnPixieEntity.getDefaultAttributes().build());
        event.put(ModEntities.SUMMER_PIXIE.get(), SummerPixieEntity.getDefaultAttributes().build());
        event.put(ModEntities.WINTER_PIXIE.get(), WinterPixieEntity.getDefaultAttributes().build());
        event.put(ModEntities.SHROOMLING.get(), ShroomlingEntity.getDefaultAttributes().build());
        event.put(ModEntities.MANDRAGORA.get(), MandragoraEntity.getDefaultAttributes().build());
        event.put(ModEntities.MOO_SHROOM_COW.get(), MooShroomCowEntity.createAttributes().build());
        event.put(ModEntities.BELLSNICKEL.get(), BellsnickelEntity.getDefaultAttributes().build());
        event.put(ModEntities.BEE_MOUNT.get(), BeeMountEntity.getDefaultAttributes().build());
        event.put(ModEntities.BEE_KNIGHT.get(), BeeKnightEntity.getDefaultAttributes().build());
        event.put(ModEntities.SPRING_TREE_ENT.get(), SpringTreeEntEntity.getDefaultAttributes().build());
        event.put(ModEntities.SUMMER_TREE_ENT.get(), SummerTreeEntEntity.getDefaultAttributes().build());
        event.put(ModEntities.AUTUMN_TREE_ENT.get(), AutumnTreeEntEntity.getDefaultAttributes().build());
        event.put(ModEntities.WINTER_TREE_ENT.get(), WinterTreeEntEntity.getDefaultAttributes().build());
        event.put(ModEntities.SPRITE.get(), SpriteEntity.getDefaultAttributes().build());
        event.put(ModEntities.TITANIA.get(), TitaniaEntity.getDefaultAttributes().build());
        event.put(ModEntities.MAB.get(), MabEntity.getDefaultAttributes().build());
        event.put(ModEntities.OBERON.get(), OberonEntity.getDefaultAttributes().build());
        event.put(ModEntities.ASHEN_LORD.get(), AshenLordEntity.getDefaultAttributes().build());
        event.put(ModEntities.FEY_WINGS.get(), FeyWingsEntity.getDefaultAttributes().build());
    }

    @OnlyIn(Dist.CLIENT)
    private void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.SPRING_PIXIE_LAYER, SpringPixieModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.AUTUMN_PIXIE_LAYER, AutumnPixieModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.SUMMER_PIXIE_LAYER, SummerPixieModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.WINTER_PIXIE_LAYER, WinterPixieModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.SHROOMLING_LAYER, ShroomlingModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.MANDRAGORA_LAYER, MandragoraModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.MOO_SHROOM_LAYER, CowModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.BELLSNICKEL_LAYER, BellsnickelModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.BEE_KNIGHT_LAYER, BeeKnightModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.BEE_MOUNT_LAYER, BeeMountModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.TREE_ENT_LAYER, TreeEntModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.SPRITE_LAYER, SpriteModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.TITANIA_LAYER, TitaniaModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.MAB_LAYER, MabModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.OBERON_LAYER, OberonModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.ASHEN_LORD_LAYER, AshenLordModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.FEY_WINGS_LAYER, FeyWingsModel::createBodyLayer);
    }

    @SuppressWarnings("deprecated")
    private void commonSetup(final FMLCommonSetupEvent event) {

        event.enqueueWork(FeywildNetwork::register);
        event.enqueueWork(ShroomlingEntity::initVariants);
        event.enqueueWork(() -> {
            ComposterBlock.COMPOSTABLES.put(ModItems.MANDRAKE.get(), 2.0F);
            ComposterBlock.COMPOSTABLES.put(ModItems.MANDRAKE_ROOT.get(), 0.3F);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.ORANGE_MUSHROOM.get(), 0.65f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.ORANGE_MUSHROOM_BLOCK.get(), 0.85f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.YELLOW_MUSHROOM.get(), 0.65f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.YELLOW_MUSHROOM_BLOCK.get(), 0.85f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.GREEN_MUSHROOM.get(), 0.65f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.GREEN_MUSHROOM_BLOCK.get(), 0.85f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.LIGHT_BLUE_MUSHROOM.get(), 0.65f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.LIGHT_BLUE_MUSHROOM_BLOCK.get(), 0.85f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.BLUE_MUSHROOM.get(), 0.65f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.BLUE_MUSHROOM_BLOCK.get(), 0.85f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.PURPLE_MUSHROOM.get(), 0.65f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.PURPLE_MUSHROOM_BLOCK.get(), 0.85f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.PINK_MUSHROOM.get(), 0.65f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.PINK_MUSHROOM_BLOCK.get(), 0.85f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.AUTUMN_TREE_SAPLING.get(), 0.3f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.SPRING_TREE_SAPLING.get(), 0.3f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.SUMMER_TREE_SAPLING.get(), 0.3f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.WINTER_TREE_SAPLING.get(), 0.3f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.AUTUMN_TREE_LEAVES_DARK_GRAY.get(), 0.3f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.AUTUMN_TREE_LEAVES_LIGHT_GRAY.get(), 0.3f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.AUTUMN_TREE_LEAVES_RED.get(), 0.3f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.AUTUMN_TREE_LEAVES_BROWN.get(), 0.3f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.SPRING_TREE_LEAVES_LIME.get(), 0.3f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.SPRING_TREE_LEAVES_GREEN.get(), 0.3f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.SPRING_TREE_LEAVES_CYAN.get(), 0.3f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.SUMMER_TREE_LEAVES_ORANGE.get(), 0.3f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.SUMMER_TREE_LEAVES_YELLOW.get(), 0.3f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.WINTER_TREE_LEAVES_BLUE.get(), 0.3f);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.WINTER_TREE_LEAVES_LIGHT_BLUE.get(), 0.3f);

            SpawnPlacements.register(ModEntities.SPRING_PIXIE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpringPixieEntity::canSpawn);
            SpawnPlacements.register(ModEntities.SUMMER_PIXIE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SummerPixieEntity::canSpawn);
            SpawnPlacements.register(ModEntities.AUTUMN_PIXIE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AutumnPixieEntity::canSpawn);
            SpawnPlacements.register(ModEntities.WINTER_PIXIE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WinterPixieEntity::canSpawn);
            SpawnPlacements.register(ModEntities.SHROOMLING.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ShroomlingEntity::canShroomlingSpawn);
            SpawnPlacements.register(ModEntities.MANDRAGORA.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MandragoraEntity::canSpawn);
            SpawnPlacements.register(ModEntities.MOO_SHROOM_COW.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MooShroomCowEntity::canSpawn);
            SpawnPlacements.register(ModEntities.BELLSNICKEL.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BellsnickelEntity::canSpawn);
            SpawnPlacements.register(ModEntities.BEE_MOUNT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BeeMountEntity::canSpawn);
            SpawnPlacements.register(ModEntities.BEE_KNIGHT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BeeKnightEntity::canSpawn);
            SpawnPlacements.register(ModEntities.SPRING_TREE_ENT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpringTreeEntEntity::canSpawn);
            SpawnPlacements.register(ModEntities.WINTER_TREE_ENT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WinterTreeEntEntity::canSpawn);
            SpawnPlacements.register(ModEntities.AUTUMN_TREE_ENT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AutumnTreeEntEntity::canSpawn);
            SpawnPlacements.register(ModEntities.SUMMER_TREE_ENT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SummerTreeEntEntity::canSpawn);
            SpawnPlacements.register(ModEntities.SPRITE.get(),SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpriteEntity::canSpawn);
            SpawnPlacements.register(ModEntities.TITANIA.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TitaniaEntity::canSpawn);
            SpawnPlacements.register(ModEntities.MAB.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MabEntity::canSpawn);
            SpawnPlacements.register(ModEntities.OBERON.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, OberonEntity::canSpawn);
            SpawnPlacements.register(ModEntities.ASHEN_LORD.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AshenLordEntity::canSpawn);
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
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("THE FEY ARE PLEASED the server is starting");
    }


    @SuppressWarnings("removal")
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onAddLayers(EntityRenderersEvent.AddLayers event) {
            for (var skin : event.getSkins()) {
                PlayerRenderer renderer = event.getSkin(skin);
                renderer.addLayer(new FeyWingsPlayerLayer<>(renderer, Minecraft.getInstance().getEntityRenderDispatcher()));
            }
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("AND A LITTLE BIT OF PIXIE DUST!");

            ItemBlockRenderTypes.setRenderLayer(ModBlocks.ELVEN_QUARTZ_MOSSY_BRICK.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.SPRING_ELVEN_QUARTZ_MOSSY_BRICK.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.SUMMER_ELVEN_QUARTZ_MOSSY_BRICK.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.WINTER_ELVEN_QUARTZ_MOSSY_BRICK.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.AUTUMN_ELVEN_QUARTZ_MOSSY_BRICK.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.ORANGE_MUSHROOM.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.YELLOW_MUSHROOM.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.GREEN_MUSHROOM.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.LIGHT_BLUE_MUSHROOM.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.BLUE_MUSHROOM.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.PURPLE_MUSHROOM.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.PINK_MUSHROOM.get(), RenderType.cutout());

            EntityRenderers.register(ModEntities.SPRING_PIXIE.get(), SpringPixieRenderer::new);
            EntityRenderers.register(ModEntities.AUTUMN_PIXIE.get(), AutumnPixieRenderer::new);
            EntityRenderers.register(ModEntities.SUMMER_PIXIE.get(), SummerPixieRenderer::new);
            EntityRenderers.register(ModEntities.WINTER_PIXIE.get(), WinterPixieRenderer::new);
            EntityRenderers.register(ModEntities.SHROOMLING.get(), ShroomlingRenderer::new);
            EntityRenderers.register(ModEntities.MANDRAGORA.get(), MandragoraRenderer::new);
            EntityRenderers.register(ModEntities.MOO_SHROOM_COW.get(), MooShroomCowRenderer::new);
            EntityRenderers.register(ModEntities.BELLSNICKEL.get(), BellsnickelRenderer::new);
            EntityRenderers.register(ModEntities.BELLSNICKEL.get(), BellsnickelRenderer::new);
            EntityRenderers.register(ModEntities.BEE_MOUNT.get(), BeeMountRenderer::new);
            EntityRenderers.register(ModEntities.BEE_KNIGHT.get(), BeeKnightRenderer::new);
            EntityRenderers.register(ModEntities.AUTUMN_TREE_ENT.get(), TreeEntRenderer::new);
            EntityRenderers.register(ModEntities.SPRING_TREE_ENT.get(), TreeEntRenderer::new);
            EntityRenderers.register(ModEntities.SUMMER_TREE_ENT.get(), TreeEntRenderer::new);
            EntityRenderers.register(ModEntities.WINTER_TREE_ENT.get(), TreeEntRenderer::new);
            EntityRenderers.register(ModEntities.SPRITE.get(), SpriteRenderer::new);
            EntityRenderers.register(ModEntities.TITANIA.get(), TitaniaRenderer::new);
            EntityRenderers.register(ModEntities.MAB.get(), MabRenderer::new);
            EntityRenderers.register(ModEntities.OBERON.get(), OberonRenderer::new);
            EntityRenderers.register(ModEntities.ASHEN_LORD.get(), AshenLordRenderer::new);
            EntityRenderers.register(ModEntities.FEY_WINGS.get(), FeyWingsRenderer::new);
            EntityRenderers.register(ModEntities.LEAF_PROJECTILE.get(), LeafProjectileRenderer::new);

            BlockEntityRenderers.register(ModBlockEntities.FEY_ALTAR_BLOCK_ENTITY.get(), FeyAltarBlockRenderer::new);
            MenuScreens.register(ModMenuTypes.FEY_ALTAR_MENU.get(), FeyAltarScreen::new);
            MenuScreens.register(ModMenuTypes.BELLSNICKEL_MENU.get(), BellsnickelScreen::new);
            MenuScreens.register(ModMenuTypes.BEE_KNIGHT_MENU.get(), BeeKnightScreen::new);
        }
    }
    @SuppressWarnings("removal")
    public void registerParticles(RegisterParticleProvidersEvent event) {

        Minecraft.getInstance().particleEngine.register(ModParticles.AUTUMN_LEAF_PARTICLE.get(),
                LeafParticle.Factory::new);

        Minecraft.getInstance().particleEngine.register(ModParticles.SPRING_LEAF_PARTICLE.get(),
                LeafParticle.Factory::new);

        Minecraft.getInstance().particleEngine.register(ModParticles.SUMMER_LEAF_PARTICLE.get(),
                LeafParticle.Factory::new);

        Minecraft.getInstance().particleEngine.register(ModParticles.WINTER_LEAF_PARTICLE.get(),
                LeafParticle.Factory::new);

        Minecraft.getInstance().particleEngine.register(ModParticles.SPRING_SPARKLE_PARTICLE.get(),
                SparkleParticle.provider(0, 1, 0));

        Minecraft.getInstance().particleEngine.register(ModParticles.SUMMER_SPARKLE_PARTICLE.get(),
                SparkleParticle.provider(1, 0.8f, 0));

        Minecraft.getInstance().particleEngine.register(ModParticles.AUTUMN_SPARKLE_PARTICLE.get(),
                SparkleParticle.provider(1, 0.4f, 0));

        Minecraft.getInstance().particleEngine.register(ModParticles.WINTER_SPARKLE_PARTICLE.get(),
                SparkleParticle.provider(0.2f, 0.8f, 0.9f));

        Minecraft.getInstance().particleEngine.register(ModParticles.HEXEN_SPARKLE_PARTICLE.get(),
                SparkleParticle.provider(0.5f, 0.2f, 0.8f));

        Minecraft.getInstance().particleEngine.register(ModParticles.FEY_SPARKLE_PARTICLE.get(),
                SparkleParticle.provider(0.3f, 0.9f, 0.9f));
    }
}
