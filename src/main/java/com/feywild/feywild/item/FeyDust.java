package com.feywild.feywild.item;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.util.Config;
import com.feywild.feywild.util.KeyboardHelper;
import com.mojang.brigadier.Command;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.command.FunctionObject;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class FeyDust extends Item {
    //Constructor
    public FeyDust(Properties properties) {
        super(properties);
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag){

        if(KeyboardHelper.isHoldingShift()){

            tooltip.add(new TranslationTextComponent("message.feywild.fey_dust"));

        }
        else {


            tooltip.add(new TranslationTextComponent("message.feywild.itemmessage"));
            //tooltip.add(new StringTextComponent("Hold "+ "\u00A7e" + "SHIFT" + "\u00A77" + " for more information."));
        }

        super.addInformation(stack, world, tooltip, flag);
    }


    //Test
    @Override
    public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        if (target instanceof SheepEntity) {
            //Get number of uses from item stack data
            int count = playerIn.getHeldItem(hand).getOrCreateTag().getInt("uses");

            //Check number of uses
            switch (count) {
                case 1:
                    playerIn.sendStatusMessage(new TranslationTextComponent("message.feywild.fey_dust_giggling"), true);
                    break;
                case 2:
                    //Special sheep stuff
                    playerIn.addItemStackToInventory(new ItemStack(ModItems.FEY_SHEEP_DROPPINGS.get(), 1));
                    target.addPotionEffect(new EffectInstance(Effects.LEVITATION, Config.FEY_DUST_DURATION.get(), 10));
                    playerIn.getHeldItem(hand).getOrCreateTag().putInt("uses", 0);
                    stack.shrink(1);
                    return ActionResultType.SUCCESS;
            }
            //Sheep levitation and item logic
            target.addPotionEffect(new EffectInstance(Effects.LEVITATION, Config.FEY_DUST_DURATION.get(), 2));
            playerIn.getHeldItem(hand).getOrCreateTag().putInt("uses", ++count);
            stack.shrink(1);
        } else {
            //General levitation and item logic
            target.addPotionEffect(new EffectInstance(Effects.LEVITATION, Config.FEY_DUST_DURATION.get(), 2));
            stack.shrink(1);
        }

        //Tell player to move hand while using item
        return ActionResultType.SUCCESS;
    }
}
