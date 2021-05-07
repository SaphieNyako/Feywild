package com.feywild.feywild;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.entity.FeyEntity;
import com.feywild.feywild.entity.ModEntityTypes;
import com.feywild.feywild.entity.SpringPixieEntity;
import com.feywild.feywild.events.ModEvents;
import com.feywild.feywild.events.SpawnData;
import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.misc.DwarfTrades;
import com.feywild.feywild.network.FeywildPacketHandler;
import com.feywild.feywild.recipes.ModRecipeTypes;
import com.feywild.feywild.setup.ClientProxy;
import com.feywild.feywild.setup.IProxy;
import com.feywild.feywild.setup.ServerProxy;
import com.feywild.feywild.sound.ModSoundEvents;
import com.feywild.feywild.util.Config;
import com.feywild.feywild.util.Registration;
import com.feywild.feywild.world.biome.ModBiomes;
import com.feywild.feywild.world.biome.ModSurfaceBuilders;
import com.feywild.feywild.world.feature.ModFeatures;
import com.feywild.feywild.world.structure.ModConfiguredStructures;
import com.feywild.feywild.world.structure.ModStructures;
import com.mojang.serialization.Codec;
import com.sun.jna.platform.win32.WinNT;
import net.minecraft.block.Block;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.common.MinecraftForge;
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
import software.bernie.geckolib3.compat.PatchouliCompat;
import vazkii.patchouli.api.PatchouliAPI;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(FeywildMod.MOD_ID)
public class FeywildMod {
    //Variables
    //Sets Mod_ID
    public static final String MOD_ID = "feywild";

    //Creates custom Create Tab
    public static final ItemGroup FEYWILD_TAB = new ItemGroup("feywildTab") {

        @Override
        public ItemStack createIcon() {
            //Shows this item's icon
            return new ItemStack(ModItems.SHINY_FEY_GEM.get());
        }
    };

    public static IProxy proxy;

    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    //Constructor
    public FeywildMod() {

        GeckoLib.initialize();

        proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);

        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

        registerModAdditions();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

    }


    //Methodes
    private void setup(final FMLCommonSetupEvent event) {

        DwarfTrades.registerTrades();
        registerConfigs();

        proxy.init();

        entityQueue(event);
        structureQueue(event);

        loadConfigs();

        FeywildPacketHandler.register();

        SpawnData.registerSpawn();
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
        ModItems.register();
        ModBlocks.register();

        modStructuresRegister();

        ModBiomes.register();
        ModSurfaceBuilders.register();
        MinecraftForge.EVENT_BUS.register(new ModEvents());
        ModSoundEvents.register();
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

    private void entityQueue(final FMLCommonSetupEvent event) {
        // Ancient's note : switched this to event.enqueueWork to avoid a potential bug
        event.enqueueWork(() -> {
            GlobalEntityTypeAttributes.put(ModEntityTypes.SPRING_PIXIE.get(), FeyEntity.setCustomAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntityTypes.WINTER_PIXIE.get(), FeyEntity.setCustomAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntityTypes.SUMMER_PIXIE.get(), FeyEntity.setCustomAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntityTypes.AUTUMN_PIXIE.get(), FeyEntity.setCustomAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntityTypes.DWARF_BLACKSMITH.get(), FeyEntity.setCustomAttributes().create());
        });
    }


    /*STRUCTURES*/

    private void structureQueue(final FMLCommonSetupEvent event) {

        event.enqueueWork(() -> {
            ModStructures.setupStructures();
            ModConfiguredStructures.registerConfiguredStructures();
        });
    }

    private void modStructuresRegister(){

        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addListener(EventPriority.NORMAL, this::addDimensionalSpacing);
        forgeBus.addListener(EventPriority.HIGH, this::biomeModification);

    }


    public void biomeModification(final BiomeLoadingEvent event) {
        /*
         * Add our structure to all biomes including other modded biomes.
         * You can skip or add only to certain biomes based on stuff like biome category,
         * temperature, scale, precipitation, mod id, etc. All kinds of options!
         *
         * You can even use the BiomeDictionary as well! To use BiomeDictionary, do
         * RegistryKey.getOrCreateKey(Registry.BIOME_KEY, event.getName()) to get the biome's
         * registrykey. Then that can be fed into the dictionary to get the biome's types.
         */
        event.getGeneration().getStructures().add(() -> ModConfiguredStructures.CONFIGURED_SPRING_WORLD_TREE);
    }

    private static Method GETCODEC_METHOD;

    public void addDimensionalSpacing(final WorldEvent.Load event) {
        if (event.getWorld() instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) event.getWorld();

            try {
                if (GETCODEC_METHOD == null)
                    GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "func_230347_a_");
                ResourceLocation cgRL = Registry.CHUNK_GENERATOR_CODEC.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invoke(serverWorld.getChunkProvider().generator));

                if (cgRL != null && cgRL.getNamespace().equals("terraforged")) return;
            } catch (Exception e) {
                FeywildMod.LOGGER.error("Was unable to check if " + serverWorld.getDimensionKey().getLocation() + " is using Terraforged's ChunkGenerator.");
            }

            // Prevent spawning our structure in Vanilla's superflat world as
            if (serverWorld.getChunkProvider().generator instanceof FlatChunkGenerator &&
                    serverWorld.getDimensionKey().equals(World.OVERWORLD)) {
                return;
            }

            /*
             * putIfAbsent so people can override the spacing with dimension datapacks themselves if they wish to customize spacing more precisely per dimension.
             * Requires AccessTransformer  (see resources/META-INF/accesstransformer.cfg)
             *
             * NOTE: if you add per-dimension spacing configs, you can't use putIfAbsent as WorldGenRegistries.NOISE_GENERATOR_SETTINGS in FMLCommonSetupEvent
             * already added your default structure spacing to some dimensions. You would need to override the spacing with .put(...)
             * And if you want to do dimension blacklisting, you need to remove the spacing entry entirely from the map below to prevent generation safely.

           Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(serverWorld.getChunkProvider().generator.func_235957_b_().func_236199_b_());


            tempMap.putIfAbsent(ModStructures.SPRING_WORLD_TREE.get(), DimensionStructuresSettings.field_236191_b_.get(ModStructures.SPRING_WORLD_TREE.get()));
            serverWorld.getChunkProvider().generator.func_235957_b_().func_236199_b_();
                   // .getSettings().structureConfig = tempMap;
            }*/

        }
    }
}
