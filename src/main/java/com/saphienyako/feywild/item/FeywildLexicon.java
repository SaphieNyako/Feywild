package com.saphienyako.feywild.item;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.Objects;

public class FeywildLexicon extends Item {
    public FeywildLexicon(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player instanceof ServerPlayer) {

                PatchouliAPI.get().openBookGUI((ServerPlayer) player, Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(this)));
        }
        return new InteractionResultHolder<>(InteractionResult.FAIL, stack);
    }



}
