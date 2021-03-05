package com.feywild.feywild.item;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.util.KeyboardHelper;
import com.mojang.brigadier.Command;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.command.FunctionObject;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
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

            tooltip.add(new StringTextComponent("Think happy thoughts!"));
        }
        else {
            tooltip.add(new StringTextComponent("Hold "+ "\u00A7e" + "SHIFT" + "\u00A77" + " for more information."));
        }

        super.addInformation(stack, world, tooltip, flag);
    }


}
