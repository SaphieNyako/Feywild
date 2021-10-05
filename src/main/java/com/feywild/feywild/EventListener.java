package com.feywild.feywild;

import com.feywild.feywild.config.ClientConfig;
import com.feywild.feywild.config.MiscConfig;
import com.feywild.feywild.entity.BeeKnight;
import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.network.FeywildNetwork;
import com.feywild.feywild.network.OpenLibraryScreenSerializer;
import com.feywild.feywild.network.OpeningScreenSerializer;
import com.feywild.feywild.network.TradesSerializer;
import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.quest.task.CraftTask;
import com.feywild.feywild.quest.task.ItemTask;
import com.feywild.feywild.quest.task.KillTask;
import com.feywild.feywild.trade.TradeManager;
import com.feywild.feywild.util.LibraryBooks;
import com.feywild.feywild.util.MenuScreen;
import com.feywild.feywild.world.dimension.ModDimensions;
import com.feywild.feywild.world.dimension.market.MarketGenerator;
import com.feywild.feywild.world.dimension.market.MarketHandler;
import io.github.noeppi_noeppi.libx.event.ConfigLoadedEvent;
import io.github.noeppi_noeppi.libx.event.DatapacksReloadedEvent;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.List;

public class EventListener {

    @SubscribeEvent
    public void craftItem(PlayerEvent.ItemCraftedEvent event) {
        if (event.getPlayer() instanceof ServerPlayerEntity) {
            QuestData.get((ServerPlayerEntity) event.getPlayer()).checkComplete(CraftTask.INSTANCE, event.getCrafting());
        }
    }

    @SubscribeEvent
    public void playerKill(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) event.getSource().getEntity();
            QuestData quests = QuestData.get(player);
            quests.checkComplete(KillTask.INSTANCE, event.getEntityLiving());
        }
    }

    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event) {
        // Only check one / second
        if (event.player.tickCount % 20 == 0 && !event.player.level.isClientSide && event.player instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) event.player;
            QuestData quests = QuestData.get(player);
            player.inventory.items.forEach(stack -> quests.checkComplete(ItemTask.INSTANCE, stack));
        }
    }

    @SubscribeEvent
    public void entityInteract(PlayerInteractEvent.EntityInteract event) {
        if (!event.getWorld().isClientSide && event.getPlayer() instanceof ServerPlayerEntity) {
            if (event.getTarget() instanceof VillagerEntity && event.getTarget().getTags().contains("feywild_librarian")) {
                event.getPlayer().sendMessage(new TranslationTextComponent("librarian.feywild.initial"), event.getPlayer().getUUID());
                FeywildMod.getNetwork().instance.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()), new OpenLibraryScreenSerializer.Message(event.getTarget().getDisplayName(), LibraryBooks.getLibraryBooks()));
                event.getPlayer().swing(event.getHand(), true);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void playerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.getPlayer().level.isClientSide) {
            FeywildMod.getNetwork().instance.send(PacketDistributor.ALL.noArg(), new TradesSerializer.Message(TradeManager.buildRecipes()));
            if (!FeyPlayerData.get(event.getPlayer()).getBoolean("feywild_got_lexicon") && MiscConfig.initial_lexicon) {
                event.getPlayer().inventory.add(new ItemStack(ModItems.feywildLexicon));
                FeyPlayerData.get(event.getPlayer()).putBoolean("feywild_got_lexicon", true);
            }
            if (!FeyPlayerData.get(event.getPlayer()).getBoolean("feywild_got_scroll") && MiscConfig.initial_scroll) {
                FeywildMod.getNetwork().instance.send(PacketDistributor.PLAYER.with( () -> (ServerPlayerEntity) event.getPlayer()), new OpeningScreenSerializer.Message(LibraryBooks.getLibraryBooks().size()));
                FeyPlayerData.get(event.getPlayer()).putBoolean("feywild_got_scroll", true);
            }
            // Move player back if still in market and the market is closed
            if(event.getPlayer().level.getDayTime() < 13000 && event.getPlayer().level.dimension() == ModDimensions.MARKET_PLACE_DIMENSION && event.getPlayer() instanceof ServerPlayerEntity){
                MarketHandler.teleportToOverworld((ServerPlayerEntity) event.getPlayer());
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
        if (ClientConfig.replace_menu && event.getGui() instanceof MainMenuScreen && !(event.getGui() instanceof MenuScreen)) {
            event.setGui(new MenuScreen());
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
        if (event.world instanceof ServerWorld && event.world.dimension() == World.OVERWORLD) {
            MarketHandler.update(((ServerWorld) event.world).getServer());
        }
    }
    
    @SubscribeEvent
    public void afterReload(DatapacksReloadedEvent event) {
        FeywildMod.getNetwork().instance.send(PacketDistributor.ALL.noArg(), new TradesSerializer.Message(TradeManager.buildRecipes()));
    }

    /* LOOTTABLES */

    @SubscribeEvent
    public void lootTableLoad(LootTableLoadEvent event) {
        if (event.getName().equals(LootTables.ABANDONED_MINESHAFT)) {
            @Nullable
            LootPool pool = event.getTable().getPool("main");
            //noinspection ConstantConditions
            if (pool != null) {
                addEntry(pool, ItemLootEntry.lootTableItem(ModItems.schematicsFeyAltar).setWeight(5).build());
                addEntry(pool, ItemLootEntry.lootTableItem(ModItems.schematicsGemTransmutation).setWeight(5).build());
                addEntry(pool, ItemLootEntry.lootTableItem(ModItems.inactiveMarketRuneStone).setWeight(5).build());
                addEntry(pool, ItemLootEntry.lootTableItem(ModItems.lesserFeyGem).setWeight(30).build());
                addEntry(pool, ItemLootEntry.lootTableItem(ModItems.feywildMusicDisc).setWeight(1).build());
            }
        }
    }

    private void addEntry(LootPool pool, LootEntry entry) {
        try {
            //noinspection unchecked
            List<LootEntry> lootEntries = (List<LootEntry>) ObfuscationReflectionHelper.findField(LootPool.class, "field_186453_a").get(pool);
            if (lootEntries.stream().noneMatch(e -> e == entry)) {
                lootEntries.add(entry);
            }
        } catch (ReflectiveOperationException e) {
            //
        }
    }
}
