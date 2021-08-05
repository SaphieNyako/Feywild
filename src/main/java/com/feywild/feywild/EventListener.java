package com.feywild.feywild;

import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.network.OpenLibraryScreenSerializer;
import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.quest.task.CraftTask;
import com.feywild.feywild.quest.task.EmptyHandKillTask;
import com.feywild.feywild.quest.task.ItemTask;
import com.feywild.feywild.quest.task.KillTask;
import com.feywild.feywild.util.LibraryBooks;
import com.feywild.feywild.util.MenuScreen;
import com.feywild.feywild.util.configs.Config;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.PacketDistributor;

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
            if (player.getItemInHand(Hand.MAIN_HAND).isEmpty()) {
                quests.checkComplete(EmptyHandKillTask.INSTANCE, event.getEntityLiving());
            }
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
        if (!event.getPlayer().level.isClientSide && !event.getPlayer().getPersistentData().getBoolean("feywild_got_lexicon") && Config.SPAWN_LEXICON.get()) {
            event.getPlayer().inventory.add(new ItemStack(ModItems.feywildLexicon));
            event.getPlayer().getPersistentData().putBoolean("feywild_got_lexicon", true);
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void openGui(GuiOpenEvent event) {
        if (Config.MENU_SCREEN.get() && event.getGui() instanceof MainMenuScreen && !(event.getGui() instanceof MenuScreen)) {
            event.setGui(new MenuScreen());
        }
    }
}
