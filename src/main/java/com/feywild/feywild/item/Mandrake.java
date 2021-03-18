package com.feywild.feywild.item;

import com.feywild.feywild.util.Config;
import com.feywild.feywild.util.KeyboardHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class Mandrake extends Item {

    //Constructor
    public Mandrake(Item.Properties properties) {
        super(properties);
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {

        if (KeyboardHelper.isHoldingShift()) {

            tooltip.add(new StringTextComponent("It doesn't seem very happy"));
        } else {
            tooltip.add(new StringTextComponent("Hold " + "\u00A7e" + "SHIFT" + "\u00A77" + " for more information."));
        }

        super.addInformation(stack, world, tooltip, flag);
    }
}


