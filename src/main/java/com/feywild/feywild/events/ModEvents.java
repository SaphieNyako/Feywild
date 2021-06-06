package com.feywild.feywild.events;

import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.util.Config;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModEvents {

    @SubscribeEvent
    public void interactWithVillager(PlayerInteractEvent.EntityInteract event) {

        //Check if it's a villager
        if (event.getTarget() instanceof VillagerEntity) {
            // Store player & villager
            VillagerEntity villagerEntity = (VillagerEntity) event.getTarget();
            PlayerEntity player = event.getPlayer();
            PlayerInventory playerInventory = new PlayerInventory(player);

            if (villagerEntity.getTags().contains("spawn_librarian") && !player.getTags().contains("speakToLib")) {
                //On first interaction
                player.sendMessage(new TranslationTextComponent("librarian.feywild.initial"), event.getPlayer().getUUID());
                player.addTag("speakToLib");
                event.setCanceled(true);

            } else if (villagerEntity.getTags().contains("spawn_librarian") && player.getTags().contains("speakToLib")
                    && !player.getTags().contains("borrowLexicon") && !player.getTags().contains("foundLexicon")) {

                player.sendMessage(new TranslationTextComponent("librarian.feywild.borrow"), event.getPlayer().getUUID());
                player.addItem(new ItemStack(ModItems.FEYWILD_LEXICON.get()));
                player.addTag("borrowLexicon");
                event.setCanceled(true);

            } else if (villagerEntity.getTags().contains("spawn_librarian")
                    && (player.getTags().contains("foundLexicon"))) {
                player.sendMessage(new TranslationTextComponent("librarian.feywild.found"), event.getPlayer().getUUID());
                player.addTag("borrowLexicon");
                event.setCanceled(true);

            } else if (villagerEntity.getTags().contains("spawn_librarian")
                    && (player.getTags().contains("borrowLexicon"))) {  //&& playerInventory.contains(new ItemStack(ModItems.FEYWILD_LEXICON.get()))

                player.sendMessage(new TranslationTextComponent("librarian.feywild.final"), event.getPlayer().getUUID());
                event.setCanceled(true);

            } /* else if (villagerEntity.getTags().contains("spawn_librarian")
                    && (player.getTags().contains("borrowLexicon") && !playerInventory.contains(new ItemStack(ModItems.FEYWILD_LEXICON.get())))) {
                player.sendMessage(new TranslationTextComponent("librarian.feywild.lost"), event.getPlayer().getUUID());
                player.addItem(new ItemStack(ModItems.FEYWILD_LEXICON.get()));
                event.setCanceled(true);
            }
*/
        }
    }

    @SubscribeEvent
    public void spawnWithItem(PlayerEvent.PlayerLoggedInEvent spawnEvent) {

        PlayerEntity player = spawnEvent.getPlayer();

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
*/

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


