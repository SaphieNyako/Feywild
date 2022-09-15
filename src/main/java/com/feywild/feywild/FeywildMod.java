package com.feywild.feywild;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.compat.MineMentionCompat;
import com.feywild.feywild.entity.*;
import com.feywild.feywild.entity.model.*;
import com.feywild.feywild.entity.render.*;
import com.feywild.feywild.events.ClientEvents;
import com.feywild.feywild.network.FeywildNetwork;
import com.feywild.feywild.particles.*;
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
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.GrassColor;
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

import javax.annotation.Nonnull;

@Mod("feywild")
public final class FeywildMod extends ModXRegistration {

    public static final Logger logger = LoggerFactory.getLogger("feywild");

    private static FeywildMod instance;
    private static FeywildNetwork network;

    public FeywildMod() {
        super(new FeywildTab("feywild"));

        instance = this;
        network = new FeywildNetwork(this);

        GeckoLib.initialize();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(CapabilityQuests::register);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::entityAttributes);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerParticles);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerRenderTypes);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::blockColors);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::itemColors);

        MinecraftForge.EVENT_BUS.addListener(this::reloadData);

        MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, CapabilityQuests::attachPlayerCaps);
        MinecraftForge.EVENT_BUS.addListener(CapabilityQuests::playerCopy);

        MinecraftForge.EVENT_BUS.register(new EventListener());
        MinecraftForge.EVENT_BUS.register(new MarketProtectEvents());
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> MinecraftForge.EVENT_BUS.addListener(FeywildMenuMusic::playSound));

        // Quest task & reward types. Not in setup as they are required for datagen.
        TaskTypes.register(new ResourceLocation(this.modid, "craft"), CraftTask.INSTANCE);
        TaskTypes.register(new ResourceLocation(this.modid, "fey_gift"), FeyGiftTask.INSTANCE);
        TaskTypes.register(new ResourceLocation(this.modid, "item_stack"), ItemStackTask.INSTANCE);
        TaskTypes.register(new ResourceLocation(this.modid, "item_pickup"), ItemPickupTask.INSTANCE);
        TaskTypes.register(new ResourceLocation(this.modid, "kill"), KillTask.INSTANCE);
        TaskTypes.register(new ResourceLocation(this.modid, "pet"), AnimalPetTask.INSTANCE);
        TaskTypes.register(new ResourceLocation(this.modid, "biome"), BiomeTask.INSTANCE);
        TaskTypes.register(new ResourceLocation(this.modid, "structure"), StructureTask.INSTANCE);
        TaskTypes.register(new ResourceLocation(this.modid, "special"), SpecialTask.INSTANCE);

        RewardTypes.register(new ResourceLocation(this.modid, "command"), CommandReward.INSTANCE);
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
            SpawnPlacements.register(ModEntities.springBotaniaPixie, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpringBotaniaPixie::canSpawn);
            SpawnPlacements.register(ModEntities.autumnBotaniaPixie, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AutumnBotaniaPixie::canSpawn);
            SpawnPlacements.register(ModEntities.summerBotaniaPixie, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SummerBotaniaPixie::canSpawn);
            SpawnPlacements.register(ModEntities.winterBotaniaPixie, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WinterBotaniaPixie::canSpawn);

            MarketGenerator.registerMarketDwarf(new ResourceLocation(this.modid, "miner"), ModEntities.dwarfMiner, new BlockPos(11, 64, 20));
            MarketGenerator.registerMarketDwarf(new ResourceLocation(this.modid, "baker"), ModEntities.dwarfBaker, new BlockPos(-3, 64, 10));
            MarketGenerator.registerMarketDwarf(new ResourceLocation(this.modid, "shepherd"), ModEntities.dwarfShepherd, new BlockPos(0, 63, -3));
            MarketGenerator.registerMarketDwarf(new ResourceLocation(this.modid, "artificer"), ModEntities.dwarfArtificer, new BlockPos(7, 63, -2));
            MarketGenerator.registerMarketDwarf(new ResourceLocation(this.modid, "dragon_hunter"), ModEntities.dwarfDragonHunter, new BlockPos(21, 63, 20));
            MarketGenerator.registerMarketDwarf(new ResourceLocation(this.modid, "tool_smith"), ModEntities.dwarfToolsmith, new BlockPos(21, 63, 11));

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
        EntityRenderers.register(ModEntities.springBotaniaPixie, BotaniaPixieRenderer.create(BotaniaPixieModel::new));
        EntityRenderers.register(ModEntities.autumnBotaniaPixie, BotaniaPixieRenderer.create(BotaniaPixieModel::new));
        EntityRenderers.register(ModEntities.summerBotaniaPixie, BotaniaPixieRenderer.create(BotaniaPixieModel::new));
        EntityRenderers.register(ModEntities.winterBotaniaPixie, BotaniaPixieRenderer.create(BotaniaPixieModel::new));

        MinecraftForge.EVENT_BUS.register(ClientEvents.class);
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
        event.put(ModEntities.springBotaniaPixie, SpringBotaniaPixie.getDefaultAttributes().build());
        event.put(ModEntities.autumnBotaniaPixie, AutumnBotaniaPixie.getDefaultAttributes().build());
        event.put(ModEntities.summerBotaniaPixie, SummerBotaniaPixie.getDefaultAttributes().build());
        event.put(ModEntities.winterBotaniaPixie, WinterBotaniaPixie.getDefaultAttributes().build());
    }

    public void registerParticles(RegisterParticleProvidersEvent event) {
        event.register(ModParticles.leafParticle, LeafParticle.Factory::new);
        event.register(ModParticles.springLeafParticle, LeafParticle.Factory::new);
        event.register(ModParticles.summerLeafParticle, LeafParticle.Factory::new);
        event.register(ModParticles.winterLeafParticle, LeafParticle.Factory::new);
        event.register(ModParticles.springSparkleParticle, SpringSparkleParticle.Factory::new);
        event.register(ModParticles.autumnSparkleParticle, AutumnSparkleParticle.Factory::new);
        event.register(ModParticles.summerSparkleParticle, SummerSparkleParticle.Factory::new);
        event.register(ModParticles.winterSparkleParticle, WinterSparkleParticle.Factory::new);
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
