package com.feywild.feywild.events;

import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.network.FeywildPacketHandler;
import com.feywild.feywild.network.ItemEntityMessage;
import com.feywild.feywild.network.LibrarianScreenMessage;
import com.feywild.feywild.network.QuestMessage;
import com.feywild.feywild.quest.QuestMap;
import com.feywild.feywild.util.ClientUtil;
import com.feywild.feywild.util.MenuScreen;
import com.feywild.feywild.util.ModUtil;
import com.feywild.feywild.util.configs.Config;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Score;
import net.minecraft.util.Hand;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class ModEvents {

    public static boolean genericInteract(PlayerEntity playerEntity, Hand hand, LivingEntity entity, boolean shrink) {
        AtomicBoolean ret = new AtomicBoolean(false);

        if (!playerEntity.level.isClientSide) {
            String[][] tokenArr = ModUtil.getTokens(playerEntity,"use");

            String stack = Objects.requireNonNull(playerEntity.getItemInHand(hand).getItem().getRegistryName()).toString();

            for (String[] tokens : tokenArr) {
                if (Registry.ENTITY_TYPE.getKey(entity.getType()).toString().equalsIgnoreCase(tokens[0]))
                    ModUtil.getTagTokens(tokens[1]).forEach(s -> {
                        if ((stack.equalsIgnoreCase(s) || s.equalsIgnoreCase("empty"))) {

                            int interact = playerEntity.getPersistentData().getInt(tokens[3]+ "Progress") + 1;

                            if (Integer.parseInt(tokens[2]) <= interact) {
                                playerEntity.getPersistentData().remove(tokens[3]+ "Progress");
                                QuestMap.completeQuest(playerEntity, Objects.requireNonNull(QuestMap.getQuest(tokens[3])));
                            } else {
                                playerEntity.displayClientMessage(new StringTextComponent(interact + "/" + tokens[2]), true);
                                playerEntity.getPersistentData().putInt(tokens[3]+ "Progress", interact);
                            }

                            if (shrink)
                                playerEntity.getItemInHand(hand).shrink(1);
                            ret.set(true);
                        }
                    });
            }
        }

        return ret.get();
    }


    @SubscribeEvent
    public void interactWithEntity(PlayerInteractEvent.EntityInteract event) {
        villagerInteract(event);
        genericInteract(event);
    }

    private void genericInteract(PlayerInteractEvent.EntityInteract event) {
        if (!event.getPlayer().level.isClientSide) {
            String[][] tokenArr = ModUtil.getTokens(event.getPlayer(),"use");

            String stack = Objects.requireNonNull(event.getItemStack().getItem().getRegistryName()).toString();

            for (String[] tokens : tokenArr) {
                if (Registry.ENTITY_TYPE.getKey(event.getTarget().getType()).toString().equalsIgnoreCase(tokens[0]))
                    ModUtil.getTagTokens(tokens[1]).forEach(s -> {

                        if ((stack.equalsIgnoreCase(s) || s.equalsIgnoreCase("empty"))) {

                            int interact = event.getPlayer().getPersistentData().getInt(tokens[3]+ "Progress") + 1;
                            if (Integer.parseInt(tokens[2]) <= interact) {
                                event.getPlayer().getPersistentData().remove(tokens[3]+ "Progress");
                                QuestMap.completeQuest(event.getPlayer(), Objects.requireNonNull(QuestMap.getQuest(tokens[3])));
                            } else {
                                event.getPlayer().displayClientMessage(new StringTextComponent(interact + "/" + tokens[2]), true);
                                event.getPlayer().getPersistentData().putInt(tokens[3]+ "Progress", interact);
                            }

                        }

                    });
            }
        }
    }

    private void villagerInteract(PlayerInteractEvent.EntityInteract event) {
        //Check if it's a villager
        if (event.getTarget() instanceof VillagerEntity) {
            // Store player & villager
            VillagerEntity villagerEntity = (VillagerEntity) event.getTarget();
            PlayerEntity player = event.getPlayer();
            PlayerInventory playerInventory = player.inventory;

            if (villagerEntity.getTags().contains("spawn_librarian") && !event.getTarget().level.isClientSide) {
                player.sendMessage(new TranslationTextComponent("librarian.feywild.initial"), player.getUUID());
                for (int i = 0; i < ModUtil.getLibrarianBooks().size(); i++) {
                    FeywildPacketHandler.sendToPlayer(new ItemEntityMessage(ModUtil.getLibrarianBooks().get(i)), player);
                }
                FeywildPacketHandler.sendToPlayer(new LibrarianScreenMessage(),player);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerExit(PlayerEvent.PlayerLoggedOutEvent leaveEvent) {
        ModUtil.killOnExit.keySet().forEach(entity -> {
            if (leaveEvent.getPlayer().equals(ModUtil.killOnExit.get(entity))) {
                entity.remove();
            }
        });
    }

    @SubscribeEvent
    public void craftItem(PlayerEvent.ItemCraftedEvent event) {

        PlayerEntity playerEntity = event.getPlayer();
        if (!playerEntity.level.isClientSide) {
            String[][] tokenArr = ModUtil.getTokens(event.getPlayer(), "craft");
            for (String[] tokens : tokenArr) {
                    ModUtil.getTagTokens(tokens[0]).forEach(s -> {
                        if (Objects.requireNonNull(event.getCrafting().getItem().getRegistryName()).toString().equalsIgnoreCase(s)) {

                                int interact = event.getPlayer().getPersistentData().getInt(tokens[3]+ "Progress") + event.getCrafting().getCount();

                                if (Integer.parseInt(tokens[2]) <= interact) {
                                    event.getPlayer().getPersistentData().remove(tokens[3]+ "Progress");
                                    QuestMap.completeQuest(event.getPlayer(), Objects.requireNonNull(QuestMap.getQuest(tokens[3])));
                                } else {
                                    event.getPlayer().displayClientMessage(new StringTextComponent(interact + "/" + tokens[2]), true);
                                    event.getPlayer().getPersistentData().putInt(tokens[3]+ "Progress", interact);
                                }
                        }
                    });
            }
        }
    }

    @SubscribeEvent
    public void genericAttackEvent(LivingDeathEvent event) {
        if (!event.getEntityLiving().level.isClientSide && event.getSource().getEntity() instanceof PlayerEntity) {

            PlayerEntity playerEntity = (PlayerEntity) event.getSource().getEntity();
            String[][] tokenArr = ModUtil.getTokens(playerEntity, "kill");
            String stack = Objects.requireNonNull(playerEntity.getMainHandItem().getItem().getRegistryName()).toString();

            for (String[] tokens : tokenArr) {
                if (Registry.ENTITY_TYPE.getKey(event.getEntityLiving().getType()).toString().equalsIgnoreCase(tokens[0]))
                    ModUtil.getTagTokens(tokens[1]).forEach(s -> {
                        if ((stack.equalsIgnoreCase(s) || s.equalsIgnoreCase("empty"))) {

                            int interact = playerEntity.getPersistentData().getInt(tokens[3]+ "Progress") + 1;

                            if (Integer.parseInt(tokens[2]) <= interact) {
                                playerEntity.getPersistentData().remove(tokens[3]+ "Progress");
                                QuestMap.completeQuest(playerEntity,Objects.requireNonNull(QuestMap.getQuest(tokens[3])));
                            } else {
                                playerEntity.displayClientMessage(new StringTextComponent(interact + "/" + tokens[2]), true);
                                playerEntity.getPersistentData().putInt(tokens[3]+ "Progress", interact);
                            }
                        }
                    });
            }
        }
    }

    @SubscribeEvent
    public void genericPickUp(PlayerEvent.ItemPickupEvent event) {
        if (!event.getPlayer().level.isClientSide) {
            String[][] tokenArr = ModUtil.getTokens(event.getPlayer(), "get");

            ItemStack stack = event.getStack();
            for (String[] tokens : tokenArr) {
                    ModUtil.getTagTokens(tokens[0]).forEach(s -> {
                        if ((stack.getItem().getRegistryName().toString().equalsIgnoreCase(s))) {

                            int interact = event.getPlayer().getPersistentData().getInt(tokens[3]+ "Progress") + event.getStack().getCount();

                            if (Integer.parseInt(tokens[2]) <= interact) {
                                event.getPlayer().getPersistentData().remove(tokens[3]+ "Progress");
                                QuestMap.completeQuest(event.getPlayer(),Objects.requireNonNull(QuestMap.getQuest(tokens[3])));
                            } else {
                                event.getPlayer().displayClientMessage(new StringTextComponent(interact + "/" + tokens[2]), true);
                                event.getPlayer().getPersistentData().putInt(tokens[3]+ "Progress", interact);
                            }
                        }
                    });

            }
        }
    }

    @SubscribeEvent
    public void spawnWithItem(PlayerEvent.PlayerLoggedInEvent spawnEvent) {

        PlayerEntity player = spawnEvent.getPlayer();

        if (!spawnEvent.getEntityLiving().getCommandSenderWorld().isClientSide && !player.getTags().contains("initQuests")) {
            player.getPersistentData().putString("FWQuest", "/");
            player.addTag("initQuests");
        }



        if (!spawnEvent.getEntityLiving().getCommandSenderWorld().isClientSide && !player.getTags().contains("foundLexicon")
                && Config.SPAWN_LEXICON.get()) {

            spawnEvent.getEntityLiving().getCommandSenderWorld()
                    .addFreshEntity(new ItemEntity(player.level, player.getX(), player.getY(), player.getZ(), new ItemStack(ModItems.FEYWILD_LEXICON.get())));
            player.addTag("foundLexicon");

            //This shows a screen when logging in:
/*
            BlockPos pos = player.blockPosition();
            SpringPixieEntity entity = new SpringPixieEntity(player.level, true, pos);

            INamedContainerProvider containerProvider = new INamedContainerProvider() {
                @Override
                public ITextComponent getDisplayName() {
                    return new TranslationTextComponent("screen.feywild.pixie");
                }

                @Nullable
                @Override
                public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {

                    return new PixieContainer(i, playerInventory, playerEntity, entity);
                }
            };

            NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider);
            entity.remove();

        } */
        }
    }
}





/* EXAMPLE: An event handler is a class that contains one or more public void member methods that are marked with the @SubscribeEvent annotation. */

    /*   @SubscribeEvent
    public void feyDustOnAnimal(PlayerInteractEvent.EntityInteract event) {

        //When player attacks with feydust in mainhand do this:
        if (event.getPlayer().getHeldItemMainhand().getItem() == ModItems.FEY_DUST.get()) {

            //Get current player starting this event
            PlayerEntity player = event.getPlayer();

            EffectInstance levitation = new EffectInstance(Effects.LEVITATION, Config.FEY_DUST_DURATION.get(), 1);

            //Get Entity of living target
            LivingEntity target = (LivingEntity) event.getTarget();
            if (target instanceof SheepEntity) {

                if (count == 3) {

                    //Apply effect on Sheep
                    target.addPotionEffect(new EffectInstance(Effects.LEVITATION, 180, 1));
                    //send message
                    player.sendStatusMessage(new StringTextComponent("You hear giggling."), true);
                    target.entityDropItem(ModItems.FEY_SHEEP_DROPPINGS.get());

                    count = 0;


                }
                else if(count < 3 ){
                    target.addPotionEffect(levitation);
                    count++;
                    //player.sendStatusMessage(new StringTextComponent(" " + count), true);
                }

            }

            else { target.addPotionEffect(levitation); }

            //Delete one of the held items
            player.getHeldItemMainhand().shrink(1);

        }

    }
 */


