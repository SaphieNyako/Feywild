package com.feywild.feywild;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.container.ModContainers;
import com.feywild.feywild.entity.DwarfBlacksmithEntity;
import com.feywild.feywild.entity.ModEntityTypes;
import com.feywild.feywild.entity.util.FeyEntity;
import com.feywild.feywild.entity.util.trades.TamedTradeManager;
import com.feywild.feywild.events.ModEvents;
import com.feywild.feywild.events.SpawnData;
import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.network.FeywildPacketHandler;
import com.feywild.feywild.quest.QuestManager;
import com.feywild.feywild.setup.ClientProxy;
import com.feywild.feywild.setup.IProxy;
import com.feywild.feywild.setup.ServerProxy;
import com.feywild.feywild.sound.ModSoundEvents;
import com.feywild.feywild.util.Configs.Config;
import com.feywild.feywild.util.Registration;
import com.feywild.feywild.util.serializer.UtilManager;
import com.feywild.feywild.world.biome.ModBiomes;
import com.feywild.feywild.world.biome.ModSurfaceBuilders;
import com.feywild.feywild.world.feature.ModFeatures;
import com.feywild.feywild.world.structure.ModConfiguredStructures;
import com.feywild.feywild.world.structure.ModStructures;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Mod(FeywildMod.MOD_ID)
public class FeywildMod {

    public static final String MOD_ID = "feywild";
    public static final ItemGroup FEYWILD_TAB = new ItemGroup("feywildTab") {

        @Override
        public ItemStack makeIcon() {

            return new ItemStack(ModItems.SHINY_FEY_GEM.get());
        }
    };
    public static final Logger LOGGER = LogManager.getLogger();
    public static IProxy proxy;
    private static Method GETCODEC_METHOD;

    public FeywildMod() {

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        GeckoLib.initialize();

        registerConfigs();
        loadConfigs();
        proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);
        eventBus.addListener(this::setup); // Register the setup method for modloading
        eventBus.addListener(this::enqueueIMC);  // Register the enqueueIMC method for modloading
        eventBus.addListener(this::processIMC); // Register the processIMC method for modloading
        MinecraftForge.EVENT_BUS.addListener(this::reloadStuff);
        registerModAdditions();
        MinecraftForge.EVENT_BUS.register(this); // Register ourselves for server and other game events we are interested in

    }

    //This might have a conflict when merging with the quests
    public void reloadStuff(AddReloadListenerEvent event) {
        event.addListener(TamedTradeManager.instance());
        event.addListener(QuestManager.instance());
        event.addListener(UtilManager.instance());
    }

    private void setup(final FMLCommonSetupEvent event) {

        // DwarfTrades.registerTrades();
        proxy.init();
        entityQueue(event);
        structureQueue(event);
        FeywildPacketHandler.register();
        SpawnData.registerSpawn();
        TamedTradeManager.instance();
        QuestManager.instance();
        UtilManager.instance();

    }

    private void registerConfigs() {

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_CONFIG);
    }

    private void loadConfigs() {

        Config.loadConfigFile(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve("feywild-client.toml").toString());
        Config.loadConfigFile(Config.SERVER_CONFIG, FMLPaths.CONFIGDIR.get().resolve("feywild-server.toml").toString());
    }

    private void registerModAdditions() {

        Registration.init();
        ModSoundEvents.register();
        ModItems.register();
        ModBlocks.register();
        ModContainers.register();
        modStructuresRegister();
        ModFeatures.register();
        ModBiomes.register();
        ModSurfaceBuilders.register();
        MinecraftForge.EVENT_BUS.register(new ModEvents());
        ModEntityTypes.register();

    }

    //Communication with other mods.
    private void enqueueIMC(final InterModEnqueueEvent event) {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("examplemod", "helloworld", () -> {
            LOGGER.info("Hello world from the MDK");
            return "Hello world";
        });
    }

    private void processIMC(final InterModProcessEvent event) {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m -> m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    private void entityQueue(final FMLCommonSetupEvent event) {
        // Ancient's note : switched this to event.enqueueWork to avoid a potential bug
        event.enqueueWork(() -> {
            GlobalEntityTypeAttributes.put(ModEntityTypes.SPRING_PIXIE.get(), FeyEntity.setCustomAttributes().build());
            GlobalEntityTypeAttributes.put(ModEntityTypes.WINTER_PIXIE.get(), FeyEntity.setCustomAttributes().build());
            GlobalEntityTypeAttributes.put(ModEntityTypes.SUMMER_PIXIE.get(), FeyEntity.setCustomAttributes().build());
            GlobalEntityTypeAttributes.put(ModEntityTypes.AUTUMN_PIXIE.get(), FeyEntity.setCustomAttributes().build());
            GlobalEntityTypeAttributes.put(ModEntityTypes.DWARF_BLACKSMITH.get(), DwarfBlacksmithEntity.setCustomAttributes().build());
        });
    }


    /*STRUCTURES*/

    private void structureQueue(final FMLCommonSetupEvent event) {

        event.enqueueWork(() -> {
            ModStructures.setupStructures();
            ModConfiguredStructures.registerConfiguredStructures();
        });
    }

    private void modStructuresRegister() {

        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addListener(EventPriority.NORMAL, this::addDimensionalSpacing);
        forgeBus.addListener(EventPriority.HIGH, this::biomeModification);

    }

    public void biomeModification(final BiomeLoadingEvent event) {

        String SpringBiome = "blossoming_wealds";
        String SummerBiome = "golden_seelie_fields";
        String AutumnBiome = "eternal_fall";
        String WinterBiome = "frozen_retreat";
        String AlfHeimPlains = "alfheim_plains";
        String GoldenFields = "golden_fields";
        String AlfHeimHills = "alfheim_hills";
        String AlfHeimForest = "dreamwood_forest";
        String biomeName = event.getName().toString();

        RegistryKey<Biome> key = RegistryKey.create(Registry.BIOME_REGISTRY, event.getName());
        Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(key);

        if (!types.contains(BiomeDictionary.Type.NETHER) && !types.contains(BiomeDictionary.Type.END)
                && !types.contains(BiomeDictionary.Type.OCEAN)) {

            if (types.contains(BiomeDictionary.Type.PLAINS)) {

                event.getGeneration().getStructures().add(() -> ModConfiguredStructures.CONFIGURED_BLACKSMITH);
                event.getGeneration().getStructures().add(() -> ModConfiguredStructures.CONFIGURED_LIBRARY);
            }

            if (types.contains(BiomeDictionary.Type.FOREST) && !types.contains(BiomeDictionary.Type.HILLS)
                    && !types.contains(BiomeDictionary.Type.MOUNTAIN) && !types.contains(BiomeDictionary.Type.COLD)) {

                event.getGeneration().getStructures().add(() -> ModConfiguredStructures.CONFIGURED_LIBRARY);

            }

            if (biomeName.contains(SpringBiome) || (biomeName.contains(AlfHeimPlains) && Config.MYTHIC.get() != 0)) {

                event.getGeneration().getStructures().add(() -> ModConfiguredStructures.CONFIGURED_SPRING_WORLD_TREE);

            }

            if (biomeName.contains(SummerBiome) || (biomeName.contains(GoldenFields) && Config.MYTHIC.get() != 0)) {

                event.getGeneration().getStructures().add(() -> ModConfiguredStructures.CONFIGURED_SUMMER_WORLD_TREE);

            }

            if (biomeName.contains(AutumnBiome) || (biomeName.contains(AlfHeimHills) && Config.MYTHIC.get() != 0)) {

                event.getGeneration().getStructures().add(() -> ModConfiguredStructures.CONFIGURED_AUTUMN_WORLD_TREE);

            }

            if (biomeName.contains(WinterBiome) || (biomeName.contains(AlfHeimForest) && Config.MYTHIC.get() != 0)) {

                event.getGeneration().getStructures().add(() -> ModConfiguredStructures.CONFIGURED_WINTER_WORLD_TREE);

            }
        }
    }

    public void addDimensionalSpacing(final WorldEvent.Load event) {
        if (event.getWorld() instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) event.getWorld();

            try {
                if (GETCODEC_METHOD == null)
                    GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "codec");
                ResourceLocation cgRL = Registry.CHUNK_GENERATOR.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invoke(serverWorld.getChunkSource().generator));

                if (cgRL != null && cgRL.getNamespace().equals("terraforged")) return;
            } catch (Exception e) {
                FeywildMod.LOGGER.error("Was unable to check if " + serverWorld.dimension().location() + " is using Terraforged's ChunkGenerator.");
            }

            // Prevent spawning our structure in Vanilla's superflat world as
            if (serverWorld.getChunkSource().generator instanceof FlatChunkGenerator &&
                    serverWorld.dimension().equals(World.OVERWORLD)) {
                return;
            }

            /*
             * putIfAbsent so people can override the spacing with dimension datapacks themselves if they wish to customize spacing more precisely per dimension.
             * Requires AccessTransformer  (see resources/META-INF/accesstransformer.cfg)
             *
             * NOTE: if you add per-dimension spacing configs, you can't use putIfAbsent as WorldGenRegistries.NOISE_GENERATOR_SETTINGS in FMLCommonSetupEvent
             * already added your default structure spacing to some dimensions. You would need to override the spacing with .put(...)
             * And if you want to do dimension blacklisting, you need to remove the spacing entry entirely from the map below to prevent generation safely.
             */

            Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(serverWorld.getChunkSource().generator.getSettings().structureConfig());
            tempMap.putIfAbsent(ModStructures.SPRING_WORLD_TREE.get(), DimensionStructuresSettings.DEFAULTS.get(ModStructures.SPRING_WORLD_TREE.get()));
            tempMap.putIfAbsent(ModStructures.SUMMER_WORLD_TREE.get(), DimensionStructuresSettings.DEFAULTS.get(ModStructures.SUMMER_WORLD_TREE.get()));
            tempMap.putIfAbsent(ModStructures.AUTUMN_WORLD_TREE.get(), DimensionStructuresSettings.DEFAULTS.get(ModStructures.AUTUMN_WORLD_TREE.get()));
            tempMap.putIfAbsent(ModStructures.WINTER_WORLD_TREE.get(), DimensionStructuresSettings.DEFAULTS.get(ModStructures.AUTUMN_WORLD_TREE.get()));
            tempMap.putIfAbsent(ModStructures.BLACKSMITH.get(), DimensionStructuresSettings.DEFAULTS.get(ModStructures.BLACKSMITH.get()));
            tempMap.putIfAbsent(ModStructures.LIBRARY.get(), DimensionStructuresSettings.DEFAULTS.get(ModStructures.LIBRARY.get()));
            serverWorld.getChunkSource().generator.getSettings().structureConfig = tempMap;

        }
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }
}
