package com.feywild.feywild.item;

import com.feywild.feywild.quest.QuestMap;
import com.feywild.feywild.util.TooltipHelper;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.ItemBase;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.util.ITooltipFlag;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class MandrakePotion extends ItemBase {

    public MandrakePotion(ModX mod, Item.Properties properties) {
        super(mod, properties);
    }
    
    @Nonnull
    @Override
    public UseAction getUseAnimation(@Nonnull ItemStack stack) {
        return UseAction.DRINK;
    }
    
    @Nonnull
    @Override
    public ActionResult<ItemStack> use(@Nonnull World world, @Nonnull PlayerEntity player, @Nonnull Hand hand) {
        return DrinkHelper.useDrink(world, player, hand);
    }
    
    @Nonnull
    @Override
    public ItemStack finishUsingItem(@Nonnull ItemStack stack, @Nonnull World world, @Nonnull LivingEntity living) {
        PlayerEntity player = living instanceof PlayerEntity ? (PlayerEntity) living : null;
        if (player instanceof ServerPlayerEntity) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity) player, stack);
            player.awardStat(Stats.ITEM_USED.get(this));
            String[] data = player.getPersistentData().getString("FWQuest").split("/");
            if(data.length > 0 && data[0].split("-").length > 0)
            for(String string : data[0].split("-")){
                player.getPersistentData().remove(string+"Progress");
            }
            player.getPersistentData().putInt("FWRep", 0);
            player.getPersistentData().putString("FWQuest", "/");
            player.removeTag(QuestMap.Courts.AutumnAligned.toString());
            player.removeTag(QuestMap.Courts.SpringAligned.toString());
            player.removeTag(QuestMap.Courts.SummerAligned.toString());
            player.removeTag(QuestMap.Courts.WinterAligned.toString());

            if (!player.isCreative()) {
                stack.shrink(1);
                player.inventory.add(new ItemStack(Items.GLASS_BOTTLE));
            }
        }
        return stack;
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable World world, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flag) {
        TooltipHelper.addTooltip(tooltip, new TranslationTextComponent("message.feywild.mandrake_potion"));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
