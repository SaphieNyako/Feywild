package com.feywild.feywild.item;

import com.feywild.feywild.quest.QuestMap;
import com.google.common.collect.ImmutableList;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DrinkHelper;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class MandrakePotion extends TooltipItem {

    public MandrakePotion(Item.Properties properties) {
        super(properties);
    }

    @Override
    public List<ITextComponent> getTooltip(ItemStack stack, World world) {
        return ImmutableList.of(new TranslationTextComponent("message.feywild.mandrake_potion"));
    }

    public UseAction getUseAnimation(ItemStack p_77661_1_) {
        return UseAction.DRINK;
    } //this works

    public ActionResult<ItemStack> use(World p_77659_1_, PlayerEntity p_77659_2_, Hand p_77659_3_) {
        return DrinkHelper.useDrink(p_77659_1_, p_77659_2_, p_77659_3_);
    }

    public ItemStack finishUsingItem(ItemStack p_77654_1_, World p_77654_2_, LivingEntity p_77654_3_) {
        PlayerEntity playerentity = p_77654_3_ instanceof PlayerEntity ? (PlayerEntity) p_77654_3_ : null;
        if (playerentity != null && playerentity instanceof ServerPlayerEntity) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity) playerentity, p_77654_1_);

            playerentity.awardStat(Stats.ITEM_USED.get(this));

            playerentity.getPersistentData().putInt("FWRep", 0);
            playerentity.getPersistentData().putString("FWQuest", "/");
            playerentity.removeTag(QuestMap.Courts.AutumnAligned.toString());
            playerentity.removeTag(QuestMap.Courts.SpringAligned.toString());
            playerentity.removeTag(QuestMap.Courts.SummerAligned.toString());
            playerentity.removeTag(QuestMap.Courts.WinterAligned.toString());

            if (!playerentity.abilities.instabuild) {
                p_77654_1_.shrink(1);
                playerentity.inventory.add(new ItemStack(Items.GLASS_BOTTLE));
            }
        }

        return p_77654_1_;
    }

}
