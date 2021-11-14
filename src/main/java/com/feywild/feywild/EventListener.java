package com.feywild.feywild;

import com.feywild.feywild.config.ClientConfig;
import com.feywild.feywild.config.MiscConfig;
import com.feywild.feywild.config.data.ScrollSelectType;
import com.feywild.feywild.entity.BeeKnight;
import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.network.OpenLibraryScreenSerializer;
import com.feywild.feywild.network.OpeningScreenSerializer;
import com.feywild.feywild.network.TradesSerializer;
import com.feywild.feywild.quest.player.CompletableTaskInfo;
import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.quest.task.*;
import com.feywild.feywild.trade.TradeManager;
import com.feywild.feywild.util.FeywildTitleScreen;
import com.feywild.feywild.util.LibraryBooks;
import com.feywild.feywild.world.dimension.market.MarketHandler;
import io.github.noeppi_noeppi.libx.event.ConfigLoadedEvent;
import io.github.noeppi_noeppi.libx.event.DataPacksReloadedEvent;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.List;

public class EventListener {

    @SubscribeEvent
    public void craftItem(PlayerEvent.ItemCraftedEvent event) {
        if (event.getPlayer() instanceof ServerPlayer) {
            QuestData.get((ServerPlayer) event.getPlayer()).checkComplete(CraftTask.INSTANCE, event.getCrafting());
        }
    }

    @SubscribeEvent
    public void playerKill(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player) {
            QuestData quests = QuestData.get(player);
            quests.checkComplete(KillTask.INSTANCE, event.getEntityLiving());
        }
    }

    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event) {
        // Only check one / second
        if (event.player.tickCount % 20 == 0 && !event.player.level.isClientSide && event.player instanceof ServerPlayer player) {
            QuestData quests = QuestData.get(player);
            player.getInventory().items.forEach(stack -> quests.checkComplete(ItemTask.INSTANCE, stack));
            //Quest Check for Biome
            player.getLevel().getBiomeName(player.blockPosition()).ifPresent(biome -> quests.checkComplete(BiomeTask.INSTANCE, biome.location()));
            //Quest Check for Structure
            for (CompletableTaskInfo<StructureFeature<?>, StructureFeature<?>> task : quests.getAllCurrentTasks(StructureTask.INSTANCE)) {
                if (player.getLevel().structureFeatureManager().getStructureAt(player.blockPosition(), true, task.getValue()).isValid()) {
                    task.checkComplete(task.getValue());
                }
            }
        }
    }

    @SubscribeEvent
    public void entityInteract(PlayerInteractEvent.EntityInteract event) {
        if (!event.getWorld().isClientSide && event.getPlayer() instanceof ServerPlayer) {
            if (event.getTarget() instanceof Villager && event.getTarget().getTags().contains("feywild_librarian")) {
                event.getPlayer().sendMessage(new TranslatableComponent("librarian.feywild.initial"), event.getPlayer().getUUID());
                FeywildMod.getNetwork().channel.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.getPlayer()), new OpenLibraryScreenSerializer.Message(event.getTarget().getDisplayName(), LibraryBooks.getLibraryBooks()));
                event.getPlayer().swing(event.getHand(), true);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void playerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.getPlayer().level.isClientSide) {
            if (event.getPlayer() instanceof ServerPlayer) {
                FeywildMod.getNetwork().channel.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.getPlayer()), new TradesSerializer.Message(TradeManager.buildRecipes()));
            }
            if (!FeyPlayerData.get(event.getPlayer()).getBoolean("feywild_got_lexicon") && MiscConfig.initial_lexicon) {
                event.getPlayer().getInventory().add(new ItemStack(ModItems.feywildLexicon));
                FeyPlayerData.get(event.getPlayer()).putBoolean("feywild_got_lexicon", true);
            }
            if (!FeyPlayerData.get(event.getPlayer()).getBoolean("feywild_got_scroll") && MiscConfig.initial_scroll == ScrollSelectType.LOGIN) {
                FeywildMod.getNetwork().channel.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.getPlayer()), new OpeningScreenSerializer.Message());
                // feywild_got_scroll is set when the player actually retrieves a scroll
            }
        }

    }

    @SubscribeEvent
    public void playerClone(PlayerEvent.Clone event) {
        FeyPlayerData.copy(event.getOriginal(), event.getPlayer());
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void openGui(GuiOpenEvent event) {
        if (ClientConfig.replace_menu && event.getGui() instanceof TitleScreen && !(event.getGui() instanceof FeywildTitleScreen)) {
            event.setGui(new FeywildTitleScreen());
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
        BeeKnight.anger(event.getWorld(), event.getPlayer(), event.getPos());
    }

    @SubscribeEvent
    public void blockInteract(PlayerInteractEvent.LeftClickBlock event) {
        BeeKnight.anger(event.getWorld(), event.getPlayer(), event.getPos());
    }

    @SubscribeEvent
    public void tickWorld(TickEvent.WorldTickEvent event) {
        if (event.world instanceof ServerLevel && event.world.dimension() == Level.OVERWORLD) {
            MarketHandler.update(((ServerLevel) event.world).getServer());
        }
    }

    @SubscribeEvent
    public void afterReload(DataPacksReloadedEvent event) {
        FeywildMod.getNetwork().channel.send(PacketDistributor.ALL.noArg(), new TradesSerializer.Message(TradeManager.buildRecipes()));
    }

    /* LOOTTABLES */

    @SubscribeEvent
    public void lootTableLoad(LootTableLoadEvent event) {
        if (event.getName().equals(BuiltInLootTables.ABANDONED_MINESHAFT)) {
            @Nullable
            LootPool pool = event.getTable().getPool("main");
            //noinspection ConstantConditions
            if (pool != null) {
                addEntry(pool, LootItem.lootTableItem(ModItems.schematicsGemTransmutation).setWeight(8).build());
                addEntry(pool, LootItem.lootTableItem(ModItems.inactiveMarketRuneStone).setWeight(MiscConfig.rune_stone_weight).build());
                addEntry(pool, LootItem.lootTableItem(ModItems.lesserFeyGem).setWeight(30).build());
                addEntry(pool, LootItem.lootTableItem(ModItems.greaterFeyGem).setWeight(15).build());
                addEntry(pool, LootItem.lootTableItem(ModItems.shinyFeyGem).setWeight(8).build());
                addEntry(pool, LootItem.lootTableItem(ModItems.brilliantFeyGem).setWeight(4).build());
                addEntry(pool, LootItem.lootTableItem(ModItems.feywildMusicDisc).setWeight(2).build());
            }
        }
    }

    private void addEntry(LootPool pool, LootPoolEntryContainer entry) {
        try {
            //noinspection unchecked
            List<LootPoolEntryContainer> lootEntries = (List<LootPoolEntryContainer>) ObfuscationReflectionHelper.findField(LootPool.class, "f_79023_").get(pool);
            if (lootEntries.stream().noneMatch(e -> e == entry)) {
                lootEntries.add(entry);
            }
        } catch (ReflectiveOperationException e) {
            //
        }
    }
}
