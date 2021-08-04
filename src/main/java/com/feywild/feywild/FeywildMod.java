package com.feywild.feywild;

import com.feywild.feywild.data.DataGenerators;
import com.feywild.feywild.entity.DwarfBlacksmithEntity;
import com.feywild.feywild.entity.ModEntityTypes;
import com.feywild.feywild.entity.render.*;
import com.feywild.feywild.entity.util.FeyEntity;
import com.feywild.feywild.events.ClientEvents;
import com.feywild.feywild.events.ModEvents;
import com.feywild.feywild.events.SpawnData;
import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.network.FeywildNetwork;
import com.feywild.feywild.quest.old.QuestManager;
import com.feywild.feywild.quest.reward.ItemReward;
import com.feywild.feywild.quest.reward.RewardTypes;
import com.feywild.feywild.quest.task.*;
import com.feywild.feywild.trade.TradeManager;
import com.feywild.feywild.util.LibraryBooks;
import com.feywild.feywild.util.Registration;
import com.feywild.feywild.util.configs.Config;
import com.feywild.feywild.util.serializer.UtilManager;
import com.feywild.feywild.world.structure.ModConfiguredStructures;
import com.feywild.feywild.world.structure.ModStructures;
import io.github.noeppi_noeppi.libx.mod.registration.ModXRegistration;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import software.bernie.geckolib3.GeckoLib;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

// General TODOs that affect so much code that I wrote them here
// TODO: many entities and tile/block entities don't serialise all the fields they should.
// TODO: Remove the many System.out.println s

@Mod("feywild")
public class FeywildMod extends ModXRegistration {
    
    private static FeywildMod instance;
    private static FeywildNetwork network;
    
    public FeywildMod() {
        super("feywild", new ItemGroup("feywild") {

            @Nonnull
            @Override
            public ItemStack makeIcon() {
                return new ItemStack(ModItems.shinyFeyGem);
            }
        });
        
        instance = this;
        network = new FeywildNetwork(this);
        
        GeckoLib.initialize();

        // TODO
        registerConfigs();
        loadConfigs();
        
        FMLJavaModLoadingContext.get().getModEventBus().addListener(DataGenerators::gatherData);
        
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        
        MinecraftForge.EVENT_BUS.addListener(this::reloadData);
        
        registerModAdditions();
        
        
        // TODO move
        TaskTypes.register(new ResourceLocation(modid, "craft"), CraftTask.INSTANCE);
        TaskTypes.register(new ResourceLocation(modid, "fey_gift"), FeyGiftTask.INSTANCE);
        TaskTypes.register(new ResourceLocation(modid, "item"), ItemTask.INSTANCE);
        TaskTypes.register(new ResourceLocation(modid, "kill"), KillTask.INSTANCE);
        TaskTypes.register(new ResourceLocation(modid, "special"), SpecialTask.INSTANCE);
        RewardTypes.register(new ResourceLocation(modid, "item"), ItemReward.INSTANCE);
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
    protected void setup(final FMLCommonSetupEvent event) {
        SpawnData.registerSpawn();
        QuestManager.instance();
        UtilManager.instance();

        event.enqueueWork(() -> {
            // TODO use event (see javadoc of put)
            GlobalEntityTypeAttributes.put(ModEntityTypes.springPixie, FeyEntity.setCustomAttributes().build());
            GlobalEntityTypeAttributes.put(ModEntityTypes.winterPixie, FeyEntity.setCustomAttributes().build());
            GlobalEntityTypeAttributes.put(ModEntityTypes.summerPixie, FeyEntity.setCustomAttributes().build());
            GlobalEntityTypeAttributes.put(ModEntityTypes.autumnPixie, FeyEntity.setCustomAttributes().build());
            GlobalEntityTypeAttributes.put(ModEntityTypes.dwarfBlacksmith, DwarfBlacksmithEntity.getDefaultAttributes().build());
            
            ModStructures.setupStructures();
            ModConfiguredStructures.registerConfiguredStructures();
        });
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void clientSetup(FMLClientSetupEvent fmlClientSetupEvent) {
        MinecraftForge.EVENT_BUS.register(new ClientEvents());
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.dwarfBlacksmith, DwarfBlacksmithRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.springPixie, SpringPixieRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.summerPixie, SummerPixieRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.autumnPixie, AutumnPixieRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.winterPixie, WinterPixieRenderer::new);
    }

    //This might have a conflict when merging with the quests
    @SubscribeEvent
    public void reloadData(AddReloadListenerEvent event) {
        event.addListener(LibraryBooks.createReloadListener());
        event.addListener(TradeManager.createReloadListener());
//        event.addListener(QuestManager.instance());
//        event.addListener(UtilManager.instance());
    }

    private void registerConfigs() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_CONFIG);
    }

    private void loadConfigs() {
        Config.loadConfigFile(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve("feywild-client.toml").toString());
        Config.loadConfigFile(Config.SERVER_CONFIG, FMLPaths.CONFIGDIR.get().resolve("feywild-server.toml").toString());
    }

    // TODO
    private void registerModAdditions() {
        Registration.init();
        modStructuresRegister();
        MinecraftForge.EVENT_BUS.register(new ModEvents());
    }

    //Communication with other mods.
    private void enqueueIMC(final InterModEnqueueEvent event) {
//        // some example code to dispatch IMC to another mod
//        InterModComms.sendTo("examplemod", "helloworld", () -> {
//            LOGGER.info("Hello world from the MDK");
//            return "Hello world";
//        });
    }

    private void processIMC(final InterModProcessEvent event) {
//        // some example code to receive and process InterModComms from other mods
//        LOGGER.info("Got IMC {}", event.getIMCStream().
//                map(m -> m.getMessageSupplier().get()).
//                collect(Collectors.toList()));
    }

    // TODO put in separate class
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
//                if (GETCODEC_METHOD == null)
//                    GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "codec");
//                ResourceLocation cgRL = Registry.CHUNK_GENERATOR.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invoke(serverWorld.getChunkSource().generator));

//                if (cgRL != null && cgRL.getNamespace().equals("terraforged")) return;
            } catch (Exception e) {
                FeywildMod.getInstance().logger.error("Was unable to check if " + serverWorld.dimension().location() + " is using Terraforged's ChunkGenerator.");
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
            tempMap.putIfAbsent(ModStructures.springWorldTree, DimensionStructuresSettings.DEFAULTS.get(ModStructures.springWorldTree));
            tempMap.putIfAbsent(ModStructures.summerWorldTree, DimensionStructuresSettings.DEFAULTS.get(ModStructures.summerWorldTree));
            tempMap.putIfAbsent(ModStructures.autumnWorldTree, DimensionStructuresSettings.DEFAULTS.get(ModStructures.autumnWorldTree));
            tempMap.putIfAbsent(ModStructures.winterWorldTree, DimensionStructuresSettings.DEFAULTS.get(ModStructures.autumnWorldTree));
            tempMap.putIfAbsent(ModStructures.blacksmith, DimensionStructuresSettings.DEFAULTS.get(ModStructures.blacksmith));
            tempMap.putIfAbsent(ModStructures.library, DimensionStructuresSettings.DEFAULTS.get(ModStructures.library));
            serverWorld.getChunkSource().generator.getSettings().structureConfig = tempMap;

        }
    }
}
