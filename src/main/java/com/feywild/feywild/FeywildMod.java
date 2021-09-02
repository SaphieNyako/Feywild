package com.feywild.feywild;

import com.feywild.feywild.block.entity.mana.CapabilityMana;
import com.feywild.feywild.config.*;
import com.feywild.feywild.config.mapper.BiomeTypesMapper;
import com.feywild.feywild.data.DataGenerators;
import com.feywild.feywild.entity.DwarfBlacksmithEntity;
import com.feywild.feywild.entity.MandragoraEntity;
import com.feywild.feywild.entity.MarketDwarfEntity;
import com.feywild.feywild.entity.ModEntityTypes;
import com.feywild.feywild.entity.base.FeyEntity;
import com.feywild.feywild.entity.render.*;
import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.network.FeywildNetwork;
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
import com.feywild.feywild.world.dimension.ModDimensions;
import com.feywild.feywild.world.gen.OreType;
import com.feywild.feywild.world.structure.ModStructures;
import com.feywild.feywild.world.structure.load.FeywildStructurePiece;
import io.github.noeppi_noeppi.libx.config.ConfigManager;
import io.github.noeppi_noeppi.libx.mod.registration.ModXRegistration;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib3.GeckoLib;

import javax.annotation.Nonnull;

@Mod("feywild")
public class FeywildMod extends ModXRegistration {

    private static FeywildMod instance;
    private static FeywildNetwork network;

    public FeywildMod() {
        super("feywild", new ItemGroup("feywild") {
            @Nonnull
            @Override
            public ItemStack makeIcon() {
                return new ItemStack(ModItems.summoningScrollSpringPixie);
            }
        });

        instance = this;
        network = new FeywildNetwork(this);

        ConfigManager.registerValueMapper(new ResourceLocation(this.modid, "biome_types"), new BiomeTypesMapper());
        ConfigManager.registerConfig(new ResourceLocation(this.modid, "misc"), MiscConfig.class, false);
        ConfigManager.registerConfig(new ResourceLocation(this.modid, "world_gen"), WorldGenConfig.class, false);
        ConfigManager.registerConfig(new ResourceLocation(this.modid, "mob_spawns"), MobConfig.class, false);
        ConfigManager.registerConfig(new ResourceLocation(this.modid, "compat"), CompatConfig.class, false);
        ConfigManager.registerConfig(new ResourceLocation(this.modid, "client"), ClientConfig.class, true);

        GeckoLib.initialize();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(DataGenerators::gatherData);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::entityAttributes);

        MinecraftForge.EVENT_BUS.addListener(this::reloadData);

        MinecraftForge.EVENT_BUS.addListener(BiomeLoader::loadBiome);
        MinecraftForge.EVENT_BUS.addListener(StructureLoader::addStructureSettings);

        MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, CapabilityQuests::attachPlayerCaps);
        MinecraftForge.EVENT_BUS.addListener(CapabilityQuests::playerCopy);

        MinecraftForge.EVENT_BUS.register(new EventListener());
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> MinecraftForge.EVENT_BUS.addListener(FeywildMenuMusic::playSound));

        // Quest task & reward types. Not in setup as they are required for datagen.
        TaskTypes.register(new ResourceLocation(this.modid, "craft"), CraftTask.INSTANCE);
        TaskTypes.register(new ResourceLocation(this.modid, "fey_gift"), FeyGiftTask.INSTANCE);
        TaskTypes.register(new ResourceLocation(this.modid, "item"), ItemTask.INSTANCE);
        TaskTypes.register(new ResourceLocation(this.modid, "kill"), KillTask.INSTANCE);
        TaskTypes.register(new ResourceLocation(this.modid, "special"), SpecialTask.INSTANCE);
        RewardTypes.register(new ResourceLocation(this.modid, "item"), ItemReward.INSTANCE);
        ModDimensions.register();
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
    protected void setup(FMLCommonSetupEvent event) {
        CapabilityMana.register();
        CapabilityQuests.register();
        event.enqueueWork(() -> {
            Registry.register(Registry.STRUCTURE_POOL_ELEMENT, FeywildStructurePiece.ID, FeywildStructurePiece.TYPE);
            ModBiomeGeneration.setupBiomes();
            OreType.setupOres();
            ModStructures.setupStructures();
            EntitySpawnPlacementRegistry.register(ModEntityTypes.springPixie, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FeyEntity::canSpawn);
            EntitySpawnPlacementRegistry.register(ModEntityTypes.autumnPixie, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FeyEntity::canSpawn);
            EntitySpawnPlacementRegistry.register(ModEntityTypes.summerPixie, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FeyEntity::canSpawn);
            EntitySpawnPlacementRegistry.register(ModEntityTypes.winterPixie, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FeyEntity::canSpawn);
            EntitySpawnPlacementRegistry.register(ModEntityTypes.dwarfBlacksmith, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DwarfBlacksmithEntity::canSpawn);
        });
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void clientSetup(FMLClientSetupEvent fmlClientSetupEvent) {

        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.dwarfArtificer, DwarfBlacksmithRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.dwarfDragonHunter, DwarfBlacksmithRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.dwarfBaker, DwarfBlacksmithRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.dwarfBlacksmithMarket, DwarfBlacksmithRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.dwarfBlacksmith, DwarfBlacksmithRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.dwarfShepherd, DwarfBlacksmithRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.springPixie, SpringPixieRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.summerPixie, SummerPixieRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.autumnPixie, AutumnPixieRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.winterPixie, WinterPixieRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.mandragora, MandragoraRenderer::new);
    }

    private void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.springPixie, FeyEntity.getDefaultAttributes().build());
        event.put(ModEntityTypes.winterPixie, FeyEntity.getDefaultAttributes().build());
        event.put(ModEntityTypes.summerPixie, FeyEntity.getDefaultAttributes().build());
        event.put(ModEntityTypes.autumnPixie, FeyEntity.getDefaultAttributes().build());
        event.put(ModEntityTypes.dwarfBlacksmith, DwarfBlacksmithEntity.getDefaultAttributes().build());
        event.put(ModEntityTypes.dwarfArtificer, MarketDwarfEntity.getDefaultAttributes().build());
        event.put(ModEntityTypes.dwarfBaker, MarketDwarfEntity.getDefaultAttributes().build());
        event.put(ModEntityTypes.dwarfBlacksmithMarket, MarketDwarfEntity.getDefaultAttributes().build());
        event.put(ModEntityTypes.dwarfDragonHunter, MarketDwarfEntity.getDefaultAttributes().build());
        event.put(ModEntityTypes.dwarfShepherd, MarketDwarfEntity.getDefaultAttributes().build());
        event.put(ModEntityTypes.mandragora, MandragoraEntity.getDefaultAttributes().build());
    }

    //This might have a conflict when merging with the quests
    @SubscribeEvent
    public void reloadData(AddReloadListenerEvent event) {
        event.addListener(LibraryBooks.createReloadListener());
        event.addListener(TradeManager.createReloadListener());
        event.addListener(QuestManager.createReloadListener());
    }
}
