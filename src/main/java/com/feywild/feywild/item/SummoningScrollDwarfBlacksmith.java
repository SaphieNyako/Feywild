package com.feywild.feywild.item;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.util.KeyboardHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class SummoningScrollDwarfBlacksmith extends Item {

    public SummoningScrollDwarfBlacksmith() {
                super(new Item.Properties().group(FeywildMod.FEYWILD_TAB));
    }

    // On Item use summons a different version of the DwarfBlacksmith
    // Linked to position of Anvil, will move away and at times comes back to Anvil
    // Dwarf has recipes that he can craft into items. Not sure how we want to do this yet.
    // Maybe items given are circled around the anvil? When given he comes to anvil and starts crafting...


    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag){

        if(KeyboardHelper.isHoldingShift()){

            tooltip.add(new StringTextComponent("Use this scroll on a Dwarven Anvil to summon your Dwarven friend."));
        }
        else {
            tooltip.add(new StringTextComponent("Hold "+ "\u00A7e" + "SHIFT" + "\u00A77" + " for more information."));
        }

        super.addInformation(stack, world, tooltip, flag);
    }
}
