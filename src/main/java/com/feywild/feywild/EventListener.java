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
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ScreenOpenEvent;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.PacketDistributor;

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
                if (player.getLevel().structureFeatureManager().getStructureAt(player.blockPosition(), task.getValue()).isValid()) {
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
    public void openGui(ScreenOpenEvent event) {
        if (ClientConfig.replace_menu && event.getScreen() instanceof TitleScreen && !(event.getScreen() instanceof FeywildTitleScreen)) {
            event.setScreen(new FeywildTitleScreen());
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
    public void afterReload(OnDatapackSyncEvent event) {
        if (event.getPlayer() == null) {
            FeywildMod.getNetwork().channel.send(PacketDistributor.ALL.noArg(), new TradesSerializer.Message(TradeManager.buildRecipes()));
        } else {
            FeywildMod.getNetwork().channel.send(PacketDistributor.PLAYER.with(event::getPlayer), new TradesSerializer.Message(TradeManager.buildRecipes()));
        }
    }

}
