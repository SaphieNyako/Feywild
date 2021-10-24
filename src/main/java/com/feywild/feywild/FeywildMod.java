package com.feywild.feywild;

import com.feywild.feywild.block.entity.mana.CapabilityMana;
import com.feywild.feywild.config.*;
import com.feywild.feywild.config.mapper.BiomeTypeMapper;
import com.feywild.feywild.config.validator.StructureDataValidator;
import com.feywild.feywild.entity.BeeKnight;
import com.feywild.feywild.entity.DwarfBlacksmith;
import com.feywild.feywild.entity.MarketDwarf;
import com.feywild.feywild.entity.ModEntityTypes;
import com.feywild.feywild.entity.base.Fey;
import com.feywild.feywild.entity.base.Mandragora;
import com.feywild.feywild.entity.model.*;
import com.feywild.feywild.entity.render.BasePixieRenderer;
import com.feywild.feywild.entity.render.DwarfBlacksmithRenderer;
import com.feywild.feywild.entity.render.MandragoraRenderer;
import com.feywild.feywild.entity.render.MarketDwarfRenderer;
import com.feywild.feywild.network.FeywildNetwork;
import com.feywild.feywild.particles.ModParticleFactories;
import com.feywild.feywild.quest.QuestManager;
import com.feywild.feywild.quest.player.CapabilityQuests;
import com.feywild.feywild.quest.reward.ItemReward;
import com.feywild.feywild.quest.reward.RewardTypes;
import com.feywild.feywild.quest.task.*;
import com.feywild.feywild.sound.FeywildMenuMusic;
import com.feywild.feywild.trade.TradeManager;
import com.feywild.feywild.util.LibraryBooks;
import com.feywild.feywild.world.BiomeLoader;
import com.feywild.feywild.world.StructureLoader;
import com.feywild.feywild.world.biome.ModBiomeGeneration;
import com.feywild.feywild.world.dimension.market.MarketGenerator;
import com.feywild.feywild.world.gen.OreType;
import com.feywild.feywild.world.structure.ModStructures;
import com.feywild.feywild.world.structure.load.FeywildStructurePiece;
import io.github.noeppi_noeppi.libx.config.ConfigManager;
import io.github.noeppi_noeppi.libx.mod.registration.ModXRegistration;
import io.github.noeppi_noeppi.libx.mod.registration.RegistrationBuilder;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib3.GeckoLib;

import javax.annotation.Nonnull;

// TODO structure files need updating for 1.17

@Mod("feywild")
public final class FeywildMod extends ModXRegistration {

    private static FeywildMod instance;
    private static FeywildNetwork network;

    public FeywildMod() {
        super(new FeywildTab("feywild"));

        instance = this;
        network = new FeywildNetwork(this);

        ConfigManager.registerValueMapper(this.modid, new BiomeTypeMapper());
        ConfigManager.registerConfigValidator(this.modid, new StructureDataValidator());
        ConfigManager.registerConfig(new ResourceLocation(this.modid, "misc"), MiscConfig.class, false);
        ConfigManager.registerConfig(new ResourceLocation(this.modid, "world_gen"), WorldGenConfig.class, false);
        ConfigManager.registerConfig(new ResourceLocation(this.modid, "mob_spawns"), MobConfig.class, false);
        ConfigManager.registerConfig(new ResourceLocation(this.modid, "compat"), CompatConfig.class, false);
        ConfigManager.registerConfig(new ResourceLocation(this.modid, "client"), ClientConfig.class, true);
        
        GeckoLib.initialize();
        
        // TODO mythicbotany
//        if (ModList.get().isLoaded("mythicbotany") && CompatConfig.mythic_alfheim.alfheim) {
//            this.addRegistrationHandler(ModAlfheimBiomes::register);
//            FMLJavaModLoadingContext.get().getModEventBus().addListener(ModAlfheimBiomes::setup);
//        }

        FMLJavaModLoadingContext.get().getModEventBus().addListener(CapabilityMana::register);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(CapabilityQuests::register);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::entityAttributes);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(EventPriority.LOW, ModParticleFactories::registerParticles);

        MinecraftForge.EVENT_BUS.addListener(this::reloadData);

        MinecraftForge.EVENT_BUS.addListener(BiomeLoader::loadBiome);
        MinecraftForge.EVENT_BUS.addListener(StructureLoader::addStructureSettings);

        MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, CapabilityQuests::attachPlayerCaps);
        MinecraftForge.EVENT_BUS.addListener(CapabilityQuests::playerCopy);

        MinecraftForge.EVENT_BUS.register(new EventListener());
        MinecraftForge.EVENT_BUS.register(new MarketProtectEvents());
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> MinecraftForge.EVENT_BUS.addListener(FeywildMenuMusic::playSound));

        // Quest task & reward types. Not in setup as they are required for datagen.
        TaskTypes.register(new ResourceLocation(this.modid, "craft"), CraftTask.INSTANCE);
        TaskTypes.register(new ResourceLocation(this.modid, "fey_gift"), FeyGiftTask.INSTANCE);
        TaskTypes.register(new ResourceLocation(this.modid, "item"), ItemTask.INSTANCE);
        TaskTypes.register(new ResourceLocation(this.modid, "kill"), KillTask.INSTANCE);
        TaskTypes.register(new ResourceLocation(this.modid, "special"), SpecialTask.INSTANCE);
        TaskTypes.register(new ResourceLocation(this.modid, "biome"), BiomeTask.INSTANCE);
        TaskTypes.register(new ResourceLocation(this.modid, "structure"), StructureTask.INSTANCE);
        RewardTypes.register(new ResourceLocation(this.modid, "item"), ItemReward.INSTANCE);
    }

    @Nonnull
    public static FeywildMod getInstance() {
        return instance;
    }

    @Nonnull
    public static FeywildNetwork getNetwork() {
        return network;
    }

    @Override
    protected void initRegistration(RegistrationBuilder builder) {
        builder.setVersion(1);
    }

    @Override
    protected void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            Registry.register(Registry.STRUCTURE_POOL_ELEMENT, FeywildStructurePiece.ID, FeywildStructurePiece.TYPE);
            ModBiomeGeneration.setupBiomes();
            OreType.setupOres();
            ModStructures.setupStructures();

            SpawnPlacements.register(ModEntityTypes.springPixie, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Fey::canSpawn);
            SpawnPlacements.register(ModEntityTypes.autumnPixie, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Fey::canSpawn);
            SpawnPlacements.register(ModEntityTypes.summerPixie, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Fey::canSpawn);
            SpawnPlacements.register(ModEntityTypes.winterPixie, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Fey::canSpawn);
            SpawnPlacements.register(ModEntityTypes.dwarfBlacksmith, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DwarfBlacksmith::canSpawn);
            SpawnPlacements.register(ModEntityTypes.beeKnight, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BeeKnight::canSpawn);

            MarketGenerator.registerMarketDwarf(new ResourceLocation(this.modid, "miner"), ModEntityTypes.dwarfMiner, new BlockPos(11, 64, 20));
            MarketGenerator.registerMarketDwarf(new ResourceLocation(this.modid, "baker"), ModEntityTypes.dwarfBaker, new BlockPos(-3, 64, 10));
            MarketGenerator.registerMarketDwarf(new ResourceLocation(this.modid, "shepherd"), ModEntityTypes.dwarfShepherd, new BlockPos(0, 63, -3));
            MarketGenerator.registerMarketDwarf(new ResourceLocation(this.modid, "artificer"), ModEntityTypes.dwarfArtificer, new BlockPos(7, 63, -2));
            MarketGenerator.registerMarketDwarf(new ResourceLocation(this.modid, "dragon_hunter"), ModEntityTypes.dwarfDragonHunter, new BlockPos(21, 63, 20));
            MarketGenerator.registerMarketDwarf(new ResourceLocation(this.modid, "tool_smith"), ModEntityTypes.dwarfToolsmith, new BlockPos(21, 63, 11));
        });
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void clientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(ModEntityTypes.beeKnight, BasePixieRenderer.create(BeeKnightModel::new));
        EntityRenderers.register(ModEntityTypes.dwarfToolsmith, MarketDwarfRenderer::new);
        EntityRenderers.register(ModEntityTypes.dwarfArtificer, MarketDwarfRenderer::new);
        EntityRenderers.register(ModEntityTypes.dwarfDragonHunter, MarketDwarfRenderer::new);
        EntityRenderers.register(ModEntityTypes.dwarfBaker, MarketDwarfRenderer::new);
        EntityRenderers.register(ModEntityTypes.dwarfMiner, MarketDwarfRenderer::new);
        EntityRenderers.register(ModEntityTypes.dwarfBlacksmith, DwarfBlacksmithRenderer::new);
        EntityRenderers.register(ModEntityTypes.dwarfShepherd, MarketDwarfRenderer::new);
        EntityRenderers.register(ModEntityTypes.springPixie, BasePixieRenderer.create(SpringPixieModel::new));
        EntityRenderers.register(ModEntityTypes.summerPixie, BasePixieRenderer.create(SummerPixieModel::new));
        EntityRenderers.register(ModEntityTypes.autumnPixie, BasePixieRenderer.create(AutumnPixieModel::new));
        EntityRenderers.register(ModEntityTypes.winterPixie, BasePixieRenderer.create(WinterPixieModel::new));
        EntityRenderers.register(ModEntityTypes.melonMandragora, MandragoraRenderer.create(MandragoraModel::new));
        EntityRenderers.register(ModEntityTypes.onionMandragora, MandragoraRenderer.create(MandragoraModel::new));
        EntityRenderers.register(ModEntityTypes.potatoMandragora, MandragoraRenderer.create(MandragoraModel::new));
        EntityRenderers.register(ModEntityTypes.pumpkinMandragora, MandragoraRenderer.create(MandragoraModel::new));
        EntityRenderers.register(ModEntityTypes.tomatoMandragora, MandragoraRenderer.create(MandragoraModel::new));
    }

    private void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.springPixie, Fey.getDefaultAttributes().build());
        event.put(ModEntityTypes.winterPixie, Fey.getDefaultAttributes().build());
        event.put(ModEntityTypes.summerPixie, Fey.getDefaultAttributes().build());
        event.put(ModEntityTypes.autumnPixie, Fey.getDefaultAttributes().build());
        event.put(ModEntityTypes.dwarfBlacksmith, DwarfBlacksmith.getDefaultAttributes().build());
        event.put(ModEntityTypes.dwarfArtificer, MarketDwarf.getDefaultAttributes().build());
        event.put(ModEntityTypes.dwarfBaker, MarketDwarf.getDefaultAttributes().build());
        event.put(ModEntityTypes.dwarfMiner, MarketDwarf.getDefaultAttributes().build());
        event.put(ModEntityTypes.dwarfDragonHunter, MarketDwarf.getDefaultAttributes().build());
        event.put(ModEntityTypes.dwarfShepherd, MarketDwarf.getDefaultAttributes().build());
        event.put(ModEntityTypes.dwarfToolsmith, MarketDwarf.getDefaultAttributes().build());
        event.put(ModEntityTypes.melonMandragora, Mandragora.getDefaultAttributes().build());
        event.put(ModEntityTypes.onionMandragora, Mandragora.getDefaultAttributes().build());
        event.put(ModEntityTypes.potatoMandragora, Mandragora.getDefaultAttributes().build());
        event.put(ModEntityTypes.pumpkinMandragora, Mandragora.getDefaultAttributes().build());
        event.put(ModEntityTypes.tomatoMandragora, Mandragora.getDefaultAttributes().build());
        event.put(ModEntityTypes.beeKnight, BeeKnight.getDefaultAttributes().build());
    }

    @SubscribeEvent
    public void reloadData(AddReloadListenerEvent event) {
        event.addListener(LibraryBooks.createReloadListener());
        event.addListener(TradeManager.createReloadListener());
        event.addListener(QuestManager.createReloadListener());
    }
}
