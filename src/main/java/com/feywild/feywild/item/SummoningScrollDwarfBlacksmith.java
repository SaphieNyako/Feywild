package com.feywild.feywild.item;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.DwarvenAnvil;
import com.feywild.feywild.block.entity.DwarvenAnvilEntity;
import com.feywild.feywild.entity.DwarfBlacksmithEntity;
import com.feywild.feywild.util.KeyboardHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class SummoningScrollDwarfBlacksmith extends Item {

    public SummoningScrollDwarfBlacksmith() {
        super(new Item.Properties().tab(FeywildMod.FEYWILD_TAB));
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        if (!context.getLevel().isClientSide && context.getLevel().getBlockState(context.getClickedPos()).getBlock() instanceof DwarvenAnvil) {

            DwarfBlacksmithEntity entity = new DwarfBlacksmithEntity(context.getLevel(), true, context.getClickedPos());

            entity.setPos(context.getClickLocation().x(), context.getClickLocation().y(), context.getClickLocation().z());

            context.getLevel().addFreshEntity(entity);
            context.getPlayer().getItemInHand(context.getHand()).shrink(1);

            if (context.getLevel().getBlockEntity(context.getClickedPos()) instanceof DwarvenAnvilEntity) {
                ((DwarvenAnvilEntity) context.getLevel().getBlockEntity(context.getClickedPos())).setDwarfPresent(true);
            }

        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {

        if (KeyboardHelper.isHoldingShift()) {

            tooltip.add(new TranslationTextComponent("message.feywild.dwarf_blacksmith"));
        } else {
            tooltip.add(new TranslationTextComponent("message.feywild.itemmessage"));

        }

        super.appendHoverText(stack, world, tooltip, flag);
    }
}
