package com.feywild.feywild.events;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.block.entity.FeyAltarBlockEntity;
import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.util.Config;
import com.mojang.brigadier.Message;
import net.minecraft.client.resources.Language;
import net.minecraft.command.arguments.MessageArgument;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.loading.progress.StartupMessageManager;
import net.minecraftforge.server.command.TextComponentHelper;
import org.apache.logging.log4j.LogManager;

import java.util.Collection;

public class ModEvents {


    //TODO: Change Text Librarian
    //TODO: Add Summoning Text for Pixies
    @SubscribeEvent
    public void interactWithVillager(PlayerInteractEvent.EntityInteract event){
        //Check if it's a villager
        if(event.getTarget() instanceof VillagerEntity){
            // Store player & villager
            VillagerEntity villagerEntity = (VillagerEntity) event.getTarget();
            PlayerEntity player = event.getPlayer();
            // Check if it's the spawn librarian
            if(villagerEntity.getTags().contains("spawn_librarian") && !player.getTags().contains("speakToLib")){
                //On first interaction
                player.sendMessage(new TranslationTextComponent("librarian.feywild.initial"), event.getPlayer().getUUID());
                player.addTag("speakToLib");
                event.setCanceled(true);
            }else if(villagerEntity.getTags().contains("spawn_librarian") && player.getTags().contains("speakToLib")){
                // On second+ interaction
                player.sendMessage(new TranslationTextComponent("librarian.feywild.final"), event.getPlayer().getUUID());
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
