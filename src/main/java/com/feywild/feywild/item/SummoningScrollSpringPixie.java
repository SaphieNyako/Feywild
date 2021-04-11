package com.feywild.feywild.item;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.entity.AutumnPixieEntity;
import com.feywild.feywild.entity.ModEntityTypes;
import com.feywild.feywild.entity.SpringPixieEntity;
import com.feywild.feywild.util.Config;
import com.feywild.feywild.util.KeyboardHelper;
import jdk.nashorn.internal.ir.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.client.model.pipeline.BlockInfo;

import java.util.List;

public class SummoningScrollSpringPixie extends Item {

    public SummoningScrollSpringPixie() {
        super(new Item.Properties().group(FeywildMod.FEYWILD_TAB));
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        if(!context.getWorld().isRemote){
            SpringPixieEntity entity = new SpringPixieEntity(ModEntityTypes.SPRING_PIXIE.get(),context.getWorld());
            entity.setPosition(context.getHitVec().getX(), context.getHitVec().getY(), context.getHitVec().getZ());
            context.getWorld().addEntity(entity);
            context.getPlayer().getHeldItem(context.getHand()).shrink(1);
        }
        return ActionResultType.SUCCESS;
    }


    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag){

        if(KeyboardHelper.isHoldingShift()){

            tooltip.add(new TranslationTextComponent("message.feywild.spring_pixie"));
        }
        else {
            tooltip.add(new TranslationTextComponent("message.feywild.itemmessage"));

        }

        super.addInformation(stack, world, tooltip, flag);
    }
}
