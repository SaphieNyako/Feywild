package com.feywild.feywild;

import com.feywild.feywild.config.ClientConfig;
import com.feywild.feywild.config.MiscConfig;
import com.feywild.feywild.config.data.ScrollSelectType;
import com.feywild.feywild.entity.BeeKnight;
import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.quest.task.*;
import com.feywild.feywild.trade.TradeManager;
import com.feywild.feywild.util.FeywildTitleScreen;
import com.feywild.feywild.util.LibraryBooks;
import com.feywild.feywild.world.dimension.market.setup.MarketHandler;
import org.moddingx.libx.event.ConfigLoadedEvent;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ScreenEvent.Open;
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
            player.getInventory().items.forEach(stack -> quests.checkComplete(ItemTask.INSTANCE, stack));
            //Quest Check for Biome
            player.getLevel().getBiome(player.blockPosition()).is(biome -> quests.checkComplete(BiomeTask.INSTANCE, biome.location()));
            //Quest Check for Structure
            if (player.getLevel().structureFeatureManager().hasAnyStructureAt(player.blockPosition())) {
                player.getLevel().structureFeatureManager().getAllStructuresAt(player.blockPosition()).forEach((structure, set) -> quests.checkComplete(StructureTask.INSTANCE, structure));

                //TODO TELEPORTING PLAYER TO OTHER DIMENSION WHEN NEAR A STRUCTURE

                //   if (player.getLevel().structureFeatureManager().getAllStructuresAt(player.blockPosition()).containsKey(FeywildDimensionConfiguredFeatures.feyCircle.value())) {
                //      FeywildDimensionHandler.teleportToFeywild(player);
                //   }

                //  player.getLevel().structureFeatureManager().getStructureAt(player.blockPosition(), FeywildDimensionConfiguredFeatures.feyCircle.value()) != null);

                //   if (player.getLevel().findNearestMapFeature(ModStructureTags.ConfiguredStructureFeatures.PORTAL_STRUCTURE, player.blockPosition(), 0, true) != null) {
                //       FeywildDimensionHandler.teleportToFeywild(player);
                //   }
            }
        }
    }

    @SubscribeEvent
    public void entityInteract(PlayerInteractEvent.EntityInteract event) {
        if (!event.getLevel().isClientSide && event.getEntity() instanceof ServerPlayer) {
            if (event.getTarget() instanceof Villager && event.getTarget().getTags().contains("feywild_librarian")) {
                event.getEntity().sendMessage(new TranslatableComponent("librarian.feywild.initial"), event.getEntity().getUUID());
                FeywildMod.getNetwork().channel.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.getEntity()), new OpenLibraryScreenSerializer.Message(event.getTarget().getDisplayName(), LibraryBooks.getLibraryBooks()));
                event.getEntity().swing(event.getHand(), true);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void playerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.getEntity().level.isClientSide) {
            if (event.getEntity() instanceof ServerPlayer) {
                FeywildMod.getNetwork().channel.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.getEntity()), new TradesSerializer.Message(TradeManager.buildRecipes()));
            }
            if (!FeyPlayerData.get(event.getEntity()).getBoolean("feywild_got_lexicon") && MiscConfig.initial_lexicon) {
                event.getEntity().getInventory().add(new ItemStack(ModItems.feywildLexicon));
                FeyPlayerData.get(event.getEntity()).putBoolean("feywild_got_lexicon", true);
            }
            if (!FeyPlayerData.get(event.getEntity()).getBoolean("feywild_got_scroll") && MiscConfig.initial_scroll == ScrollSelectType.LOGIN) {
                FeywildMod.getNetwork().channel.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.getEntity()), new OpeningScreenSerializer.Message());
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
    public void openGui(Open event) {
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
        BeeKnight.anger(event.getLevel(), event.getEntity(), event.getPos());
    }

    @SubscribeEvent
    public void blockInteract(PlayerInteractEvent.LeftClickBlock event) {
        BeeKnight.anger(event.getLevel(), event.getEntity(), event.getPos());
    }

    @SubscribeEvent
    public void tickWorld(TickEvent.LevelTickEvent event) {
        if (event.level instanceof ServerLevel && event.level.dimension() == Level.OVERWORLD) {
            MarketHandler.update(((ServerLevel) event.level).getServer());
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
