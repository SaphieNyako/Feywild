package com.feywild.feywild.item;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.ModEntityTypes;
import com.feywild.feywild.entity.SummerPixieEntity;
import com.feywild.feywild.entity.WinterPixieEntity;
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

public class SummoningScrollWinterPixie extends Item {

    public SummoningScrollWinterPixie() {
        super(new Item.Properties().group(FeywildMod.FEYWILD_TAB));
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        if(!context.getWorld().isRemote){
            WinterPixieEntity entity = new WinterPixieEntity(ModEntityTypes.WINTER_PIXIE.get(),context.getWorld());
            entity.setPosition(context.getHitVec().getX(), context.getHitVec().getY(), context.getHitVec().getZ());
            context.getWorld().addEntity(entity);
            context.getPlayer().getHeldItem(context.getHand()).shrink(1);
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag){

        if(KeyboardHelper.isHoldingShift()){

            tooltip.add(new TranslationTextComponent("message.feywild.winter_pixie"));
        }
        else {
            tooltip.add(new TranslationTextComponent("message.feywild.itemmessage"));

        }

        super.addInformation(stack, world, tooltip, flag);
    }

}
