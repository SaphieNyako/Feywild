package com.feywild.feywild.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import vazkii.patchouli.api.PatchouliAPI;

public class FeywildLexicon extends Item {


    public FeywildLexicon(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }


    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);

        if(playerIn instanceof ServerPlayerEntity) {
            ServerPlayerEntity player=  (ServerPlayerEntity) playerIn;

            PatchouliAPI.instance.openBookGUI((ServerPlayerEntity) playerIn, Registry.ITEM.getKey(this));
        }

        return new ActionResult<>(ActionResultType.PASS, stack);
    }



}
