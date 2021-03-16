package com.feywild.feywild.events;

import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.util.Config;
import com.mojang.brigadier.Message;
import net.minecraft.command.arguments.MessageArgument;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.loading.progress.StartupMessageManager;
import org.apache.logging.log4j.LogManager;

import java.util.Collection;

public class ModEvents {

    @SubscribeEvent
    public void interactWithVillager(PlayerInteractEvent.EntityInteract event){
        if(event.getTarget() instanceof VillagerEntity){
            VillagerEntity villagerEntity = (VillagerEntity) event.getTarget();
            if(villagerEntity.getTags().contains("spawn_librarian") && !event.getPlayer().getTags().contains("speakToLib")){
                event.getPlayer().sendMessage(TextComponentUtils.toTextComponent(() -> "<Old and underpaid librarian> Welcome to the Feywild, the wonderful land of magic and fey.\n" +
                        "What's that now? You say you havent seen a single fairy? Oh, that's not so strange at all, they are a bit shy after all', especially around newcomers. But... I have to admit I havent been pranked by them in a while now.\n" +
                        "Maybe something is the matter after all?\n" +
                        "\n" +
                        "Stranger, would you be so kind to travel to the Great Tree and see if there is anything going on there?\n"), event.getPlayer().getUniqueID());
                event.getPlayer().addTag("speakToLib");
                event.setCanceled(true);
            }else if(villagerEntity.getTags().contains("spawn_librarian") && event.getPlayer().getTags().contains("speakToLib")){
                event.getPlayer().sendMessage(TextComponentUtils.toTextComponent(() -> "<Old and underpaid librarian> Thank you! Oh, you can grab one of these books of the shelf. They contain valuable information on the Fairy Courts as well as the location of the Great Tree. Fair tidings!\n"), event.getPlayer().getUniqueID());
                event.setCanceled(true);
            }
        }
    }

/*    //An event handler is a class that contains one or more public void member methods that are marked with the @SubscribeEvent annotation.
    @SubscribeEvent
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
