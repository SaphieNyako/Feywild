package com.feywild.feywild;

import com.feywild.feywild.block.ModTrees;
import com.feywild.feywild.config.ClientConfig;
import com.feywild.feywild.config.MiscConfig;
import com.feywild.feywild.config.data.ScrollSelectType;
import com.feywild.feywild.entity.BeeKnight;
import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.network.LibraryScreenMessage;
import com.feywild.feywild.network.OpeningScreenMessage;
import com.feywild.feywild.network.TradesMessage;
import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.quest.task.*;
import com.feywild.feywild.quest.util.SpecialTaskAction;
import com.feywild.feywild.screens.DisplayQuestScreen;
import com.feywild.feywild.screens.FeywildTitleScreen;
import com.feywild.feywild.screens.SelectQuestScreen;
import com.feywild.feywild.trade.TradeManager;
import com.feywild.feywild.util.LibraryBooks;
import com.feywild.feywild.world.market.MarketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.PacketDistributor;
import org.moddingx.libx.event.ConfigLoadedEvent;

public class EventListener {

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void showGui(RenderGuiOverlayEvent.Pre event) {
        if (Minecraft.getInstance().screen instanceof DisplayQuestScreen || Minecraft.getInstance().screen instanceof SelectQuestScreen) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void craftItem(PlayerEvent.ItemCraftedEvent event) {
        if (event.getEntity() instanceof ServerPlayer) {
            QuestData.get((ServerPlayer) event.getEntity()).checkComplete(CraftTask.INSTANCE, event.getCrafting());
        }
    }

    @SubscribeEvent
    public void playerKill(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player) {
            QuestData quests = QuestData.get(player);
            quests.checkComplete(KillTask.INSTANCE, event.getEntity());
        }
    }

    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event) {
        // Only check one / second
        if (event.player.tickCount % 20 == 0 && !event.player.level.isClientSide && event.player instanceof ServerPlayer player) {
            QuestData quests = QuestData.get(player);
            player.getInventory().items.forEach(stack -> quests.checkComplete(ItemStackTask.INSTANCE, stack));
            //Quest Check for Biome
            player.getLevel().getBiome(player.blockPosition()).is(biome -> quests.checkComplete(BiomeTask.INSTANCE, biome.location()));
            //Quest Check for Structure
            if (player.getLevel().structureManager().hasAnyStructureAt(player.blockPosition())) {
                RegistryAccess access = player.getLevel().registryAccess();
                Registry<Structure> structureRegistry = access.registryOrThrow(Registry.STRUCTURE_REGISTRY);
                player.getLevel().structureManager().getAllStructuresAt(player.blockPosition()).forEach((structure, set) -> {
                    ResourceLocation structureId = structureRegistry.getKey(structure);
                    if (structureId != null) {
                        quests.checkComplete(StructureTask.INSTANCE, structureId);
                    }
                });
            }
        }
    }

    @SubscribeEvent
    public void entityInteract(PlayerInteractEvent.EntityInteract event) {
        if (!event.getLevel().isClientSide && event.getEntity() instanceof ServerPlayer player) {
            QuestData.get(player).checkComplete(AnimalPetTask.INSTANCE, event.getTarget());
            if (event.getTarget() instanceof Villager && event.getTarget().getTags().contains("feywild_librarian")) {
                player.sendSystemMessage(Component.translatable("librarian.feywild.initial"));
                FeywildMod.getNetwork().channel.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.getEntity()), new LibraryScreenMessage(event.getTarget().getDisplayName(), LibraryBooks.getLibraryBooks()));
                player.swing(event.getHand(), true);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void playerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.getEntity().level.isClientSide) {
            if (event.getEntity() instanceof ServerPlayer) {
                FeywildMod.getNetwork().channel.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.getEntity()), new TradesMessage(TradeManager.buildRecipes()));
            }
            if (!FeyPlayerData.get(event.getEntity()).getBoolean("feywild_got_lexicon") && MiscConfig.initial_lexicon) {
                event.getEntity().getInventory().add(new ItemStack(ModItems.feywildLexicon));
                FeyPlayerData.get(event.getEntity()).putBoolean("feywild_got_lexicon", true);
            }
            if (!FeyPlayerData.get(event.getEntity()).getBoolean("feywild_got_scroll") && MiscConfig.initial_scroll == ScrollSelectType.LOGIN) {
                FeywildMod.getNetwork().channel.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.getEntity()), new OpeningScreenMessage());
                // feywild_got_scroll is set when the player actually retrieves a scroll
            }
        }

    }

    @SubscribeEvent
    public void playerClone(PlayerEvent.Clone event) {
        FeyPlayerData.copy(event.getOriginal(), event.getEntity());
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void openGui(ScreenEvent.Opening event) {
        if (ClientConfig.replace_menu && event.getScreen() instanceof TitleScreen && !(event.getScreen() instanceof FeywildTitleScreen)) {
            event.setNewScreen(new FeywildTitleScreen());
        }
    }

    @SubscribeEvent
    public void loadConfig(ConfigLoadedEvent event) {
        if (event.getConfigClass() == MiscConfig.class) {
            ModItems.feyDust.updateFood();
        }
    }

    @SubscribeEvent
    public void blockInteract(PlayerInteractEvent.RightClickBlock event) {
        BeeKnight.anger(event.getLevel(), event.getEntity(), event.getPos());
    }

    @SubscribeEvent
    public void BonemealEvent(BonemealEvent event) {
        Player player = event.getEntity();
        if (player instanceof ServerPlayer) {
            if (event.getBlock() == ModTrees.springTree.getSapling().defaultBlockState()) {
                QuestData.get((ServerPlayer) player).checkComplete(SpecialTask.INSTANCE, SpecialTaskAction.GROW_SPRING_TREE);
            }
            if (event.getBlock() == ModTrees.summerTree.getSapling().defaultBlockState()) {
                QuestData.get((ServerPlayer) player).checkComplete(SpecialTask.INSTANCE, SpecialTaskAction.GROW_SUMMER_TREE);
            }
            if (event.getBlock() == ModTrees.autumnTree.getSapling().defaultBlockState()) {
                QuestData.get((ServerPlayer) player).checkComplete(SpecialTask.INSTANCE, SpecialTaskAction.GROW_AUTUMN_TREE);
            }
            if (event.getBlock() == ModTrees.winterTree.getSapling().defaultBlockState()) {
                QuestData.get((ServerPlayer) player).checkComplete(SpecialTask.INSTANCE, SpecialTaskAction.GROW_WINTER_TREE);
            }
        }
    }


    @SubscribeEvent
    public void blockInteract(PlayerInteractEvent.LeftClickBlock event) {
        BeeKnight.anger(event.getLevel(), event.getEntity(), event.getPos());
    }

    @SubscribeEvent
    public void tickServer(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            MarketHandler.update(event.getServer());
        }
    }

    @SubscribeEvent
    public void afterReload(OnDatapackSyncEvent event) {
        if (event.getPlayer() == null) {
            FeywildMod.getNetwork().channel.send(PacketDistributor.ALL.noArg(), new TradesMessage(TradeManager.buildRecipes()));
        } else {
            FeywildMod.getNetwork().channel.send(PacketDistributor.PLAYER.with(event::getPlayer), new TradesMessage(TradeManager.buildRecipes()));
        }
    }
}
