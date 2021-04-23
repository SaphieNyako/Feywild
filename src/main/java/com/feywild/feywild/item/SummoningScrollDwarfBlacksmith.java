package com.feywild.feywild.item;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.DwarvenAnvil;
import com.feywild.feywild.entity.DwarfBlacksmithEntity;
import com.feywild.feywild.entity.ModEntityTypes;
import com.feywild.feywild.entity.SpringPixieEntity;
import com.feywild.feywild.util.KeyboardHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
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
    public ActionResultType onItemUse(ItemUseContext context) {
        if(!context.getWorld().isRemote && context.getWorld().getBlockState(context.getPos()).getBlock() instanceof DwarvenAnvil){
            DwarfBlacksmithEntity entity = new DwarfBlacksmithEntity(ModEntityTypes.DWARF_BLACKSMITH.get(),context.getWorld());
            entity.setPosition(context.getHitVec().getX(), context.getHitVec().getY(), context.getHitVec().getZ());
            entity.setTamed(true);
            context.getWorld().addEntity(entity);
            context.getPlayer().getHeldItem(context.getHand()).shrink(1);
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag){

        if(KeyboardHelper.isHoldingShift()){

            tooltip.add(new TranslationTextComponent( "message.feywild.dwarf_blacksmith"));
        }
        else {
            tooltip.add(new TranslationTextComponent("message.feywild.itemmessage"));

        }

        super.addInformation(stack, world, tooltip, flag);
    }
}
