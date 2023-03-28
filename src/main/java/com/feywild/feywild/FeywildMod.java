package com.feywild.feywild;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.compat.MineMentionCompat;
import com.feywild.feywild.entity.*;
import com.feywild.feywild.entity.base.BotaniaPixie;
import com.feywild.feywild.entity.model.*;
import com.feywild.feywild.entity.render.*;
import com.feywild.feywild.item.FeyWing;
import com.feywild.feywild.item.render.FeyWingsRenderer;
import com.feywild.feywild.network.FeywildNetwork;
import com.feywild.feywild.particles.LeafParticle;
import com.feywild.feywild.particles.ModParticles;
import com.feywild.feywild.particles.SparkleParticle;
import com.feywild.feywild.quest.QuestManager;
import com.feywild.feywild.quest.player.CapabilityQuests;
import com.feywild.feywild.quest.reward.CommandReward;
import com.feywild.feywild.quest.reward.ItemReward;
import com.feywild.feywild.quest.reward.RewardTypes;
import com.feywild.feywild.quest.task.*;
import com.feywild.feywild.sound.FeywildMenuMusic;
import com.feywild.feywild.trade.TradeManager;
import com.feywild.feywild.util.FeywildJigsawHelper;
import com.feywild.feywild.util.LibraryBooks;
import com.feywild.feywild.world.gen.processor.*;
import com.feywild.feywild.world.market.MarketGenerator;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeRenderTypes;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterNamedRenderTypesEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.moddingx.libx.mod.ModXRegistration;
import org.moddingx.libx.registration.RegistrationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib3.GeckoLib;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

import javax.annotation.Nonnull;

@Mod("feywild")
public final class FeywildMod extends ModXRegistration {

    public static final Logger logger = LoggerFactory.getLogger("feywild");
    public static StructureProcessorType<SpringTreeProcessor> SPRING_TREE_PROCESSOR = () -> SpringTreeProcessor.CODEC;
    public static StructureProcessorType<SummerTreeProcessor> SUMMER_TREE_PROCESSOR = () -> SummerTreeProcessor.CODEC;
    public static StructureProcessorType<AutumnTreeProcessor> AUTUMN_TREE_PROCESSOR = () -> AutumnTreeProcessor.CODEC;
    public static StructureProcessorType<WinterTreeProcessor> WINTER_TREE_PROCESSOR = () -> WinterTreeProcessor.CODEC;
    public static StructureProcessorType<BlossomTreeProcessor> BLOSSOM_TREE_PROCESSOR = () -> BlossomTreeProcessor.CODEC;
    public static StructureProcessorType<HexenTreeProcessor> HEXEN_TREE_PROCESSOR = () -> HexenTreeProcessor.CODEC;
    private static FeywildMod instance;
    private static FeywildNetwork network;

    public FeywildMod() {
        super(new FeywildTab("feywild"));

        instance = this;
        network = new FeywildNetwork(this);

        GeckoLib.initialize();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(CapabilityQuests::register);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::entityAttributes);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerParticles));
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerRenderTypes);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> FMLJavaModLoadingContext.get().getModEventBus().addListener(this::blockColors));
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> FMLJavaModLoadingContext.get().getModEventBus().addListener(this::itemColors));
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerArmorRenderers));

        MinecraftForge.EVENT_BUS.addListener(this::reloadData);
        MinecraftForge.EVENT_BUS.addListener(this::serverAboutToStart);

        MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, CapabilityQuests::attachPlayerCaps);
        MinecraftForge.EVENT_BUS.addListener(CapabilityQuests::playerCopy);

        MinecraftForge.EVENT_BUS.register(new EventListener());
        MinecraftForge.EVENT_BUS.register(new MarketProtectEvents());
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> MinecraftForge.EVENT_BUS.addListener(FeywildMenuMusic::playSound));

        // Quest task & reward types. Not in setup as they are required for datagen.
        TaskTypes.register(new ResourceLocation(this.modid, "craft"), CraftTask.INSTANCE);
        TaskTypes.register(new ResourceLocation(this.modid, "fey_gift"), FeyGiftTask.INSTANCE);
        TaskTypes.register(new ResourceLocation(this.modid, "item_stack"), ItemStackTask.INSTANCE);
        TaskTypes.register(new ResourceLocation(this.modid, "kill"), KillTask.INSTANCE);
        TaskTypes.register(new ResourceLocation(this.modid, "pet"), AnimalPetTask.INSTANCE);
        TaskTypes.register(new ResourceLocation(this.modid, "tame"), AnimalTameTask.INSTANCE);
        TaskTypes.register(new ResourceLocation(this.modid, "biome"), BiomeTask.INSTANCE);
        TaskTypes.register(new ResourceLocation(this.modid, "structure"), StructureTask.INSTANCE);
        TaskTypes.register(new ResourceLocation(this.modid, "special"), SpecialTask.INSTANCE);

        RewardTypes.register(new ResourceLocation(this.modid, "item"), ItemReward.INSTANCE);
        RewardTypes.register(new ResourceLocation(this.modid, "command"), CommandReward.INSTANCE);
    }

    @Nonnull
    public static FeywildMod getInstance() {
        return instance;
    }

    @Nonnull
    public static FeywildNetwork getNetwork() {
        return network;
    }

    private void serverAboutToStart(final ServerAboutToStartEvent event) {
        FeywildJigsawHelper.registerJigsaw(event.getServer(), new ResourceLocation("minecraft:village/plains/houses"),
                new ResourceLocation("feywild:village/plains/houses/fountain"), 5);
        FeywildJigsawHelper.registerJigsaw(event.getServer(), new ResourceLocation("minecraft:village/plains/houses"),
                new ResourceLocation("feywild:village/plains/houses/pond"), 10);

        FeywildJigsawHelper.registerJigsaw(event.getServer(), new ResourceLocation("minecraft:village/desert/houses"),
                new ResourceLocation("feywild:village/desert/houses/temple"), 10);

        FeywildJigsawHelper.registerJigsaw(event.getServer(), new ResourceLocation("minecraft:village/savanna/houses"),
                new ResourceLocation("feywild:village/plains/houses/fountain"), 5);
        FeywildJigsawHelper.registerJigsaw(event.getServer(), new ResourceLocation("minecraft:village/savanna/houses"),
                new ResourceLocation("feywild:village/plains/houses/pond"), 10);

        FeywildJigsawHelper.registerJigsaw(event.getServer(), new ResourceLocation("minecraft:village/snowy/houses"),
                new ResourceLocation("feywild:village/desert/houses/temple"), 10);

        FeywildJigsawHelper.registerJigsaw(event.getServer(), new ResourceLocation("minecraft:village/taiga/houses"),
                new ResourceLocation("feywild:village/plains/houses/fountain"), 5);
        FeywildJigsawHelper.registerJigsaw(event.getServer(), new ResourceLocation("minecraft:village/taiga/houses"),
                new ResourceLocation("feywild:village/plains/houses/pond"), 10);

    }

    @Override
    protected void initRegistration(RegistrationBuilder builder) {
        //
    }

    @Override
    protected void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            SpawnPlacements.register(ModEntities.springPixie, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpringPixie::canSpawn);
            SpawnPlacements.register(ModEntities.summerPixie, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SummerPixie::canSpawn);
            SpawnPlacements.register(ModEntities.autumnPixie, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AutumnPixie::canSpawn);
            SpawnPlacements.register(ModEntities.winterPixie, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WinterPixie::canSpawn);
            SpawnPlacements.register(ModEntities.dwarfBlacksmith, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DwarfBlacksmith::canSpawn);
            SpawnPlacements.register(ModEntities.beeKnight, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BeeKnight::canSpawn);
            SpawnPlacements.register(ModEntities.shroomling, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Shroomling::canSpawn);
            SpawnPlacements.register(ModEntities.botaniaPixie, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BotaniaPixie::canSpawn);

            SpawnPlacements.register(ModEntities.titania, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Titania::canSpawn);
            SpawnPlacements.register(ModEntities.mab, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mab::canSpawn);

            SpawnPlacements.register(ModEntities.springTreeEnt, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpringTreeEnt::canSpawn);
            SpawnPlacements.register(ModEntities.summerTreeEnt, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SummerTreeEnt::canSpawn);
            SpawnPlacements.register(ModEntities.winterTreeEnt, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WinterTreeEnt::canSpawn);
            SpawnPlacements.register(ModEntities.blossomTreeEnt, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BlossomTreeEnt::canSpawn);
            SpawnPlacements.register(ModEntities.hexenTreeEnt, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, HexenTreeEnt::canSpawn);

            MarketGenerator.registerMarketDwarf(new ResourceLocation(this.modid, "miner"), ModEntities.dwarfMiner, new BlockPos(11, 64, 20));
            MarketGenerator.registerMarketDwarf(new ResourceLocation(this.modid, "baker"), ModEntities.dwarfBaker, new BlockPos(-3, 64, 10));
            MarketGenerator.registerMarketDwarf(new ResourceLocation(this.modid, "shepherd"), ModEntities.dwarfShepherd, new BlockPos(0, 63, -3));
            MarketGenerator.registerMarketDwarf(new ResourceLocation(this.modid, "artificer"), ModEntities.dwarfArtificer, new BlockPos(7, 63, -2));
            MarketGenerator.registerMarketDwarf(new ResourceLocation(this.modid, "dragon_hunter"), ModEntities.dwarfDragonHunter, new BlockPos(21, 63, 20));
            MarketGenerator.registerMarketDwarf(new ResourceLocation(this.modid, "tool_smith"), ModEntities.dwarfToolsmith, new BlockPos(21, 63, 11));

            Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(FeywildMod.getInstance().modid, "spring_tree_processor"), SPRING_TREE_PROCESSOR);
            Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(FeywildMod.getInstance().modid, "summer_tree_processor"), SUMMER_TREE_PROCESSOR);
            Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(FeywildMod.getInstance().modid, "autumn_tree_processor"), AUTUMN_TREE_PROCESSOR);
            Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(FeywildMod.getInstance().modid, "winter_tree_processor"), WINTER_TREE_PROCESSOR);
            Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(FeywildMod.getInstance().modid, "blossom_tree_processor"), BLOSSOM_TREE_PROCESSOR);
            Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(FeywildMod.getInstance().modid, "hexen_tree_processor"), HEXEN_TREE_PROCESSOR);

            if (ModList.get().isLoaded("minemention")) {
                MineMentionCompat.setup();
            }
        });
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void clientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(ModEntities.beeKnight, BasePixieRenderer.create(BeeKnightModel::new));
        EntityRenderers.register(ModEntities.dwarfToolsmith, MarketDwarfRenderer::new);
        EntityRenderers.register(ModEntities.dwarfArtificer, MarketDwarfRenderer::new);
        EntityRenderers.register(ModEntities.dwarfDragonHunter, MarketDwarfRenderer::new);
        EntityRenderers.register(ModEntities.dwarfBaker, MarketDwarfRenderer::new);
        EntityRenderers.register(ModEntities.dwarfMiner, MarketDwarfRenderer::new);
        EntityRenderers.register(ModEntities.dwarfBlacksmith, DwarfBlacksmithRenderer.create(DwarfBlacksmithModel::new));
        EntityRenderers.register(ModEntities.dwarfShepherd, MarketDwarfRenderer::new);
        EntityRenderers.register(ModEntities.springPixie, BasePixieRenderer.create(SpringPixieModel::new));
        EntityRenderers.register(ModEntities.summerPixie, BasePixieRenderer.create(SummerPixieModel::new));
        EntityRenderers.register(ModEntities.autumnPixie, BasePixieRenderer.create(AutumnPixieModel::new));
        EntityRenderers.register(ModEntities.winterPixie, BasePixieRenderer.create(WinterPixieModel::new));
        EntityRenderers.register(ModEntities.mandragora, MandragoraRenderer.create(MandragoraModel::new));
        EntityRenderers.register(ModEntities.shroomling, ShroomlingRenderer.create(ShroomlingModel::new));
        EntityRenderers.register(ModEntities.botaniaPixie, BotaniaPixieRenderer.create(BotaniaPixieModel::new));

        EntityRenderers.register(ModEntities.titania, TitaniaRenderer.create(TitaniaModel::new));
        EntityRenderers.register(ModEntities.mab, MabRenderer.create(MabModel::new));

        EntityRenderers.register(ModEntities.springTreeEnt, BaseTreeEntRenderer.create(SpringTreeEntModel::new));
        EntityRenderers.register(ModEntities.summerTreeEnt, BaseTreeEntRenderer.create(SummerTreeEntModel::new));
        EntityRenderers.register(ModEntities.winterTreeEnt, BaseTreeEntRenderer.create(WinterTreeEntModel::new));
        EntityRenderers.register(ModEntities.blossomTreeEnt, BaseTreeEntRenderer.create(BlossomTreeEntModel::new));
        EntityRenderers.register(ModEntities.hexenTreeEnt, BaseTreeEntRenderer.create(HexenTreeEntModel::new));

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
        event.put(ModEntities.blossomTreeEnt, BlossomTreeEnt.getDefaultAttributes().build());
        event.put(ModEntities.hexenTreeEnt, HexenTreeEnt.getDefaultAttributes().build());
    }

    public void registerParticles(RegisterParticleProvidersEvent event) {

        event.register(ModParticles.autumnLeafParticle, LeafParticle.Factory::new);
        event.register(ModParticles.springLeafParticle, LeafParticle.Factory::new);
        event.register(ModParticles.summerLeafParticle, LeafParticle.Factory::new);
        event.register(ModParticles.winterLeafParticle, LeafParticle.Factory::new);
        event.register(ModParticles.hexenLeafParticle, LeafParticle.Factory::new);
        event.register(ModParticles.blossomLeafParticle, LeafParticle.Factory::new);
        event.register(ModParticles.springSparkleParticle, SparkleParticle.provider(0, 1, 0));
        event.register(ModParticles.summerSparkleParticle, SparkleParticle.provider(1, 0.8f, 0));
        event.register(ModParticles.autumnSparkleParticle, SparkleParticle.provider(1, 0.4f, 1));
        event.register(ModParticles.winterSparkleParticle, SparkleParticle.provider(0.2f, 0.8f, 0.9f));
    }

    public void registerRenderTypes(RegisterNamedRenderTypesEvent event) {
        event.register("semi_solid", RenderType.solid(), ForgeRenderTypes.ITEM_LAYERED_TRANSLUCENT.get());
        event.register("semi_cutout", RenderType.cutout(), ForgeRenderTypes.ITEM_LAYERED_TRANSLUCENT.get());
    }

    public void registerArmorRenderers(final EntityRenderersEvent.AddLayers event) {
        GeoArmorRenderer.registerArmorRenderer(FeyWing.class, new FeyWingsRenderer());
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
