package com.feywild.feywild.events;

import com.feywild.feywild.item.ModItems;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;
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

            if (villagerEntity.getTags().contains("spawn_librarian") && !player.getTags().contains("speakToLib")) {
                //On first interaction
                player.sendMessage(new TranslationTextComponent("librarian.feywild.initial"), event.getPlayer().getUUID());
                player.addTag("speakToLib");
                event.setCanceled(true);

            } else if (villagerEntity.getTags().contains("spawn_librarian") && player.getTags().contains("speakToLib") && !player.getTags().contains("borrowLexicon")) {

                player.sendMessage(new TranslationTextComponent("librarian.feywild.borrow"), event.getPlayer().getUUID());
                player.addItem(new ItemStack(ModItems.FEYWILD_LEXICON.get()));
                player.addTag("borrowLexicon");
                event.setCanceled(true);
            } else if (villagerEntity.getTags().contains("spawn_librarian") && player.getTags().contains("borrowLexicon")) {
                player.sendMessage(new TranslationTextComponent("librarian.feywild.final"), event.getPlayer().getUUID());
                event.setCanceled(true);
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

}
