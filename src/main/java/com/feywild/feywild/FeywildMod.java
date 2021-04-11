package com.feywild.feywild;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.entity.FeyEntity;
import com.feywild.feywild.entity.ModEntityTypes;
import com.feywild.feywild.entity.SpringPixieEntity;
import com.feywild.feywild.events.ModEvents;
import com.feywild.feywild.events.ModRecipes;
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
import com.sun.jna.platform.win32.WinNT;
import net.minecraft.block.Block;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
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

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(FeywildMod.MOD_ID)
public class FeywildMod
{
    //Variables
    //Sets Mod_ID
    public static final String MOD_ID = "feywild";

    //Creates custom Create Tab
    public static final ItemGroup FEYWILD_TAB = new ItemGroup("feywildTab")
    {

        @Override
        public ItemStack createIcon(){
            //Shows this item's icon
            return new ItemStack(ModItems.SHINY_FEY_GEM.get());
        }
    };

    public static IProxy proxy;

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    //Constructor
    public FeywildMod() {

        //prio 1
        GeckoLib.initialize();

        proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);

        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

        //prio 2
        registerModAdditions();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }


    //Methodes
    private void setup(final FMLCommonSetupEvent event)
    {
        DwarfTrades.registerTrades();
        registerConfigs();

        proxy.init();

        entityQueue(event);

        loadConfigs();

        FeywildPacketHandler.register();

        //register entities that can spawn
        SpawnData.registerSpawn();
    }

    private void registerConfigs(){

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_CONFIG);
    }

    private void loadConfigs(){

        Config.loadConfigFile(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve("feywild-client.toml").toString());
        Config.loadConfigFile(Config.SERVER_CONFIG, FMLPaths.CONFIGDIR.get().resolve("feywild-server.toml").toString());
    }

    private void registerModAdditions(){

        //Inits the registration of our additions
        Registration.init();

        //registers items, blocks ect. added by our mod
        ModItems.register();
        ModBlocks.register();

        //register Mod event!
        MinecraftForge.EVENT_BUS.register(new ModEvents());

        ModSoundEvents.register();

        // Entities register
        ModEntityTypes.register();

    }



    //Communication with other mods.
    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }


    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
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
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            LOGGER.info("HELLO from Register Block");
        }

        @SubscribeEvent
        public static void register(final RegistryEvent.Register<Item> event) {
            ModRecipes.register();
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
}
