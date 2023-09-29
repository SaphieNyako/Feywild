package com.feywild.feywild;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.block.ModTrees;
import com.feywild.feywild.compat.MineMentionCompat;
import com.feywild.feywild.data.*;
import com.feywild.feywild.data.loot.BlockLootProvider;
import com.feywild.feywild.data.loot.ChestLootProvider;
import com.feywild.feywild.data.loot.EntityLootProvider;
import com.feywild.feywild.data.loot.LootModifierProvider;
import com.feywild.feywild.data.patchouli.FeywildLexiconProvider;
import com.feywild.feywild.data.recipe.RecipeProvider;
import com.feywild.feywild.data.tags.BiomeLayerTagsProvider;
import com.feywild.feywild.data.tags.BiomeTagsProvider;
import com.feywild.feywild.data.tags.CommonTagsProvider;
import com.feywild.feywild.data.tags.EntityTagsProvider;
import com.feywild.feywild.data.worldgen.*;
import com.feywild.feywild.entity.*;
import com.feywild.feywild.entity.model.*;
import com.feywild.feywild.entity.render.BaseEntityRenderer;
import com.feywild.feywild.entity.render.BotaniaPixieRenderer;
import com.feywild.feywild.entity.render.DwarfBlacksmithRenderer;
import com.feywild.feywild.entity.render.ShroomlingRenderer;
import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.network.FeywildNetwork;
import com.feywild.feywild.particles.LeafParticle;
import com.feywild.feywild.particles.ModParticles;
import com.feywild.feywild.particles.SparkleParticle;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.quest.QuestManager;
import com.feywild.feywild.quest.player.CapabilityQuests;
import com.feywild.feywild.quest.reward.CommandReward;
import com.feywild.feywild.quest.reward.ItemReward;
import com.feywild.feywild.quest.reward.RewardTypes;
import com.feywild.feywild.quest.task.*;
import com.feywild.feywild.sound.FeywildMenuMusic;
import com.feywild.feywild.trade.TradeManager;
import com.feywild.feywild.util.LibraryBooks;
import com.feywild.feywild.world.market.MarketGenerator;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.VillagerRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeRenderTypes;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterNamedRenderTypesEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;
import org.moddingx.libx.datagen.DatagenSystem;
import org.moddingx.libx.datagen.PackTarget;
import org.moddingx.libx.datapack.DynamicPacks;
import org.moddingx.libx.mod.ModXRegistration;
import org.moddingx.libx.registration.RegistrationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.GeckoLib;

import javax.annotation.Nonnull;

@Mod("feywild")
public final class FeywildMod extends ModXRegistration {

    public static final Logger logger = LoggerFactory.getLogger("feywild");
    
    private static FeywildMod instance;
    private static FeywildNetwork network;
    private static FeywildTab tab;

    public FeywildMod() {
        instance = this;
        network = new FeywildNetwork(this);
        tab = new FeywildTab(this);
        
        GeckoLib.initialize();

        DynamicPacks.RESOURCE_PACKS.enablePack(this.modid, "redux");
        
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::createRegistries);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(CapabilityQuests::register);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::entityAttributes);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::spawnPlacement);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerParticles));
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerRenderTypes);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> FMLJavaModLoadingContext.get().getModEventBus().addListener(this::blockColors));
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> FMLJavaModLoadingContext.get().getModEventBus().addListener(this::itemColors));

        MinecraftForge.EVENT_BUS.addListener(this::reloadData);

        MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, CapabilityQuests::attachPlayerCaps);
        MinecraftForge.EVENT_BUS.addListener(CapabilityQuests::playerCopy);

        MinecraftForge.EVENT_BUS.register(new EventListener());
        MinecraftForge.EVENT_BUS.register(new MarketProtectEvents());
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> MinecraftForge.EVENT_BUS.addListener(FeywildMenuMusic::playSound));

        // Quest task & reward types. Not in setup as they are required for datagen.
        TaskTypes.register(this.resource("craft"), CraftTask.INSTANCE);
        TaskTypes.register(this.resource("fey_gift"), FeyGiftTask.INSTANCE);
        TaskTypes.register(this.resource("item_stack"), ItemStackTask.INSTANCE);
        TaskTypes.register(this.resource("kill"), KillTask.INSTANCE);
        TaskTypes.register(this.resource("pet"), AnimalPetTask.INSTANCE);
        TaskTypes.register(this.resource("tame"), AnimalTameTask.INSTANCE);
        TaskTypes.register(this.resource("biome"), BiomeTask.INSTANCE);
        TaskTypes.register(this.resource("structure"), StructureTask.INSTANCE);
        TaskTypes.register(this.resource("tree"), GrowTreeTask.INSTANCE);
        TaskTypes.register(this.resource("special"), SpecialTask.INSTANCE);

        RewardTypes.register(new ResourceLocation(this.modid, "item"), ItemReward.INSTANCE);
        RewardTypes.register(new ResourceLocation(this.modid, "command"), CommandReward.INSTANCE);

        DatagenSystem.create(this, system -> {
            system.setResourceRoot("../src/main/resources");
            PackTarget redux = system.dynamic("redux", PackType.CLIENT_RESOURCES);

            system.addRegistryProvider(FeatureProvider::new);
            system.addRegistryProvider(TreeProvider::new);
            system.addRegistryProvider(PlacementProvider::new);
            system.addRegistryProvider(TemplateProvider::new);
            system.addRegistryProvider(StructureProvider::new);
            system.addRegistryProvider(StructureSetProvider::new);
            system.addRegistryProvider(BiomeProvider::new);
            system.addRegistryProvider(BiomeLayerProvider::new);
            system.addRegistryProvider(NoiseProvider::new);
            system.addRegistryProvider(DimensionTypeProvider::new);

            system.addExtensionProvider(BiomeModifierProvider::new);
            system.addExtensionProvider(SurfaceProvider::new);
            system.addExtensionProvider(TemplateExtensionProvider::new);
            system.addExtensionProvider(DimensionProvider::new);

            system.addDataProvider(TextureProvider::new);
            system.addDataProvider(redux, TextureProvider::new);
            system.addDataProvider(AdvancementProvider::new);
            system.addDataProvider(BlockStateProvider::new);
            system.addDataProvider(ItemModelProvider::new);
            system.addDataProvider(QuestProvider::new);
            system.addDataProvider(SoundProvider::new);
            system.addDataProvider(CommonTagsProvider::new);
            system.addDataProvider(EntityTagsProvider::new);
            system.addDataProvider(BiomeTagsProvider::new);
            system.addDataProvider(BiomeLayerTagsProvider::new);
            system.addDataProvider(BlockLootProvider::new);
            system.addDataProvider(ChestLootProvider::new);
            system.addDataProvider(EntityLootProvider::new);
            system.addDataProvider(LootModifierProvider::new);
            system.addDataProvider(FeywildLexiconProvider::new);
            system.addDataProvider(RecipeProvider::new);
        });
    }

    @Nonnull
    public static FeywildMod getInstance() {
        return instance;
    }

    @Nonnull
    public static FeywildNetwork getNetwork() {
        return network;
    }

    @Nonnull
    public static FeywildTab getCreativeTab() {
        return tab;
    }

    @Override
    protected void initRegistration(RegistrationBuilder builder) {
        builder.disableRegistryTracking();
    }

    private void createRegistries(NewRegistryEvent event) {
        event.create(new RegistryBuilder<>().setName(FeyRegistries.TREES.location()).hasTags());
        event.create(new RegistryBuilder<>().setName(FeyRegistries.ABILITIES.location()).hasTags());
        event.create(new RegistryBuilder<>().setName(FeyRegistries.TEMPLATE_ACTIONS.location()).hasTags());
    }
    
    @Override
    protected void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            MarketGenerator.registerMarketDwarf(new ResourceLocation(this.modid, "miner"), ModEntities.dwarfMiner, new BlockPos(11, 64, 20));
            MarketGenerator.registerMarketDwarf(new ResourceLocation(this.modid, "baker"), ModEntities.dwarfBaker, new BlockPos(-3, 64, 10));
            MarketGenerator.registerMarketDwarf(new ResourceLocation(this.modid, "shepherd"), ModEntities.dwarfShepherd, new BlockPos(0, 63, -3));
            MarketGenerator.registerMarketDwarf(new ResourceLocation(this.modid, "artificer"), ModEntities.dwarfArtificer, new BlockPos(7, 63, -2));
            MarketGenerator.registerMarketDwarf(new ResourceLocation(this.modid, "dragon_hunter"), ModEntities.dwarfDragonHunter, new BlockPos(21, 63, 20));
            MarketGenerator.registerMarketDwarf(new ResourceLocation(this.modid, "tool_smith"), ModEntities.dwarfToolsmith, new BlockPos(21, 63, 11));

            if (ModList.get().isLoaded("minemention")) {
                MineMentionCompat.setup();
            }

            ComposterBlock.COMPOSTABLES.put(ModTrees.autumnTree.getSapling().asItem(), 0.3F);
            ComposterBlock.COMPOSTABLES.put(ModTrees.springTree.getSapling().asItem(), 0.3F);
            ComposterBlock.COMPOSTABLES.put(ModTrees.summerTree.getSapling().asItem(), 0.3F);
            ComposterBlock.COMPOSTABLES.put(ModTrees.winterTree.getSapling().asItem(), 0.3F);
            ComposterBlock.COMPOSTABLES.put(ModTrees.hexenTree.getSapling().asItem(), 0.3F);
            ComposterBlock.COMPOSTABLES.put(ModTrees.blossomTree.getSapling().asItem(), 0.3F);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.treeMushroom.asItem(), 0.5F);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.feyMushroom.asItem(), 0.2F);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.mandrakeCrop.asItem(), 0.3F);
            ComposterBlock.COMPOSTABLES.put(ModItems.mandrake, 2.0F);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.sunflower.getSeed(), 0.5F);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.crocus.getSeed(), 0.5F);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.dandelion.getSeed(), 0.5F);
        });
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void clientSetup(FMLClientSetupEvent event) {
        BaseEntityRenderer.register(ModEntities.beeKnight, new BeeKnightModel());
        
        BaseEntityRenderer.register(ModEntities.dwarfToolsmith, DwarfBlacksmithRenderer::new, new DwarfModel("toolsmith"));
        BaseEntityRenderer.register(ModEntities.dwarfArtificer, DwarfBlacksmithRenderer::new, new DwarfModel("artificer"));
        BaseEntityRenderer.register(ModEntities.dwarfDragonHunter, DwarfBlacksmithRenderer::new, new DwarfModel("dragon_hunter"));
        BaseEntityRenderer.register(ModEntities.dwarfBaker, DwarfBlacksmithRenderer::new, new DwarfModel("baker"));
        BaseEntityRenderer.register(ModEntities.dwarfMiner, DwarfBlacksmithRenderer::new, new DwarfModel("miner"));
        BaseEntityRenderer.register(ModEntities.dwarfBlacksmith, DwarfBlacksmithRenderer::new, new DwarfModel("blacksmith"));
        BaseEntityRenderer.register(ModEntities.dwarfShepherd, DwarfBlacksmithRenderer::new, new DwarfModel("shepherd"));
        
        BaseEntityRenderer.register(ModEntities.springPixie, new PixieModel(Alignment.SPRING));
        BaseEntityRenderer.register(ModEntities.summerPixie, new PixieModel(Alignment.SUMMER));
        BaseEntityRenderer.register(ModEntities.autumnPixie, new PixieModel(Alignment.AUTUMN));
        BaseEntityRenderer.register(ModEntities.winterPixie, new PixieModel(Alignment.WINTER));
        
        BaseEntityRenderer.register(ModEntities.beeKnight, new BeeKnightModel());
        BaseEntityRenderer.register(ModEntities.mandragora, new MandragoraModel());
        BaseEntityRenderer.register(ModEntities.shroomling, ShroomlingRenderer::new, new ShroomlingModel());
        BaseEntityRenderer.register(ModEntities.botaniaPixie, BotaniaPixieRenderer::new, new BotaniaPixieModel());
        
        BaseEntityRenderer.register(ModEntities.titania, new TitaniaModel());
        BaseEntityRenderer.register(ModEntities.mab, new MabModel());
        
        BaseEntityRenderer.register(ModEntities.springTreeEnt, new TreeEntModel(Alignment.SPRING));
        BaseEntityRenderer.register(ModEntities.summerTreeEnt, new TreeEntModel(Alignment.SUMMER));
        BaseEntityRenderer.register(ModEntities.autumnTreeEnt, new TreeEntModel(Alignment.AUTUMN));
        BaseEntityRenderer.register(ModEntities.winterTreeEnt, new TreeEntModel(Alignment.WINTER));
        BaseEntityRenderer.register(ModEntities.blossomTreeEnt, new TreeEntModel("blossom"));
        BaseEntityRenderer.register(ModEntities.hexenTreeEnt, new TreeEntModel("hexen"));
        
        EntityRenderers.register(ModEntities.loreMaster, VillagerRenderer::new);
    }

    private void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.springPixie, SpringPixie.getDefaultAttributes().build());
        event.put(ModEntities.summerPixie, SummerPixie.getDefaultAttributes().build());
        event.put(ModEntities.autumnPixie, AutumnPixie.getDefaultAttributes().build());
        event.put(ModEntities.winterPixie, WinterPixie.getDefaultAttributes().build());
        event.put(ModEntities.dwarfBlacksmith, DwarfBlacksmith.getDefaultAttributes().build());
        event.put(ModEntities.dwarfArtificer, MarketDwarf.getDefaultAttributes().build());
        event.put(ModEntities.dwarfBaker, MarketDwarf.getDefaultAttributes().build());
        event.put(ModEntities.dwarfMiner, MarketDwarf.getDefaultAttributes().build());
        event.put(ModEntities.dwarfDragonHunter, MarketDwarf.getDefaultAttributes().build());
        event.put(ModEntities.dwarfShepherd, MarketDwarf.getDefaultAttributes().build());
        event.put(ModEntities.dwarfToolsmith, MarketDwarf.getDefaultAttributes().build());
        event.put(ModEntities.mandragora, Mandragora.getDefaultAttributes().build());
        event.put(ModEntities.beeKnight, BeeKnight.getDefaultAttributes().build());
        event.put(ModEntities.shroomling, Shroomling.getDefaultAttributes().build());
        event.put(ModEntities.botaniaPixie, BotaniaPixie.getDefaultAttributes().build());

        event.put(ModEntities.titania, Titania.getDefaultAttributes().build());
        event.put(ModEntities.mab, Mab.getDefaultAttributes().build());

        event.put(ModEntities.springTreeEnt, SpringTreeEnt.getDefaultAttributes().build());
        event.put(ModEntities.summerTreeEnt, SummerTreeEnt.getDefaultAttributes().build());
        event.put(ModEntities.winterTreeEnt, WinterTreeEnt.getDefaultAttributes().build());
        event.put(ModEntities.autumnTreeEnt, AutumnTreeEnt.getDefaultAttributes().build());
        event.put(ModEntities.blossomTreeEnt, BlossomTreeEnt.getDefaultAttributes().build());
        event.put(ModEntities.hexenTreeEnt, HexenTreeEnt.getDefaultAttributes().build());
        event.put(ModEntities.loreMaster, LoreMaster.createAttributes().build());
    }
    
    private void spawnPlacement(SpawnPlacementRegisterEvent event) {
        event.register(ModEntities.springPixie, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpringPixie::canSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.summerPixie, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SummerPixie::canSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.autumnPixie, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AutumnPixie::canSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.winterPixie, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WinterPixie::canSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.dwarfBlacksmith, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DwarfBlacksmith::canSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.beeKnight, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BeeKnight::canSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.shroomling, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Shroomling::canSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.botaniaPixie, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BotaniaPixie::canSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);

        event.register(ModEntities.titania, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Titania::canSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.mab, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mab::canSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);

        event.register(ModEntities.springTreeEnt, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpringTreeEnt::canSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.summerTreeEnt, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SummerTreeEnt::canSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.winterTreeEnt, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WinterTreeEnt::canSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.autumnTreeEnt, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AutumnTreeEnt::canSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.blossomTreeEnt, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BlossomTreeEnt::canSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.hexenTreeEnt, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, HexenTreeEnt::canSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);

        event.register(ModEntities.loreMaster, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, LoreMaster::canSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
    }

    public void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.autumnLeafParticle, LeafParticle.Factory::new);
        event.registerSpriteSet(ModParticles.springLeafParticle, LeafParticle.Factory::new);
        event.registerSpriteSet(ModParticles.summerLeafParticle, LeafParticle.Factory::new);
        event.registerSpriteSet(ModParticles.winterLeafParticle, LeafParticle.Factory::new);
        event.registerSpriteSet(ModParticles.hexenLeafParticle, LeafParticle.Factory::new);
        event.registerSpriteSet(ModParticles.blossomLeafParticle, LeafParticle.Factory::new);
        event.registerSpriteSet(ModParticles.frostWalkParticle, LeafParticle.Factory::new);
        event.registerSpriteSet(ModParticles.springSparkleParticle, SparkleParticle.provider(0, 1, 0));
        event.registerSpriteSet(ModParticles.summerSparkleParticle, SparkleParticle.provider(1, 0.8f, 0));
        event.registerSpriteSet(ModParticles.autumnSparkleParticle, SparkleParticle.provider(1, 0.4f, 1));
        event.registerSpriteSet(ModParticles.winterSparkleParticle, SparkleParticle.provider(0.2f, 0.8f, 0.9f));
    }

    public void registerRenderTypes(RegisterNamedRenderTypesEvent event) {
        event.register("semi_solid", RenderType.solid(), ForgeRenderTypes.ITEM_LAYERED_TRANSLUCENT.get());
        event.register("semi_cutout", RenderType.cutout(), ForgeRenderTypes.ITEM_LAYERED_TRANSLUCENT.get());
    }

    private void blockColors(RegisterColorHandlersEvent.Block event) {
        //noinspection SwitchStatementWithTooFewBranches
        BlockColor mossyColor = (state, level, pos, tintIndex) -> switch (tintIndex) {
            case 1 -> level != null && pos != null ? BiomeColors.getAverageGrassColor(level, pos) : GrassColor.get(0.5, 0.5);
            default -> 0xFFFFFF;
        };
        event.register(mossyColor,
                ModBlocks.elvenQuartz.getMossyBrickBlock(), ModBlocks.elvenSpringQuartz.getMossyBrickBlock(),
                ModBlocks.elvenSummerQuartz.getMossyBrickBlock(), ModBlocks.elvenAutumnQuartz.getMossyBrickBlock(),
                ModBlocks.elvenWinterQuartz.getMossyBrickBlock()
        );
    }

    public void itemColors(RegisterColorHandlersEvent.Item event) {
        //noinspection SwitchStatementWithTooFewBranches
        ItemColor mossyColor = (stack, tintIndex) -> switch (tintIndex) {
            case 1 -> GrassColor.get(0.5, 0.5);
            default -> 0xFFFFFF;
        };
        event.register(mossyColor,
                ModBlocks.elvenQuartz.getMossyBrickBlock(), ModBlocks.elvenSpringQuartz.getMossyBrickBlock(),
                ModBlocks.elvenSummerQuartz.getMossyBrickBlock(), ModBlocks.elvenAutumnQuartz.getMossyBrickBlock(),
                ModBlocks.elvenWinterQuartz.getMossyBrickBlock()
        );
    }

    public void reloadData(AddReloadListenerEvent event) {
        event.addListener(LibraryBooks.createReloadListener());
        event.addListener(TradeManager.createReloadListener());
        event.addListener(QuestManager.createReloadListener());
    }
}
