package com.feywild.feywild.item;

import com.feywild.feywild.entity.BotaniaPixie;
import com.feywild.feywild.entity.ModEntities;
import com.feywild.feywild.quest.player.QuestData;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.moddingx.libx.mod.ModX;

import javax.annotation.Nonnull;
import java.util.Objects;

public class PixieOrb extends TooltipItem {

    public PixieOrb(ModX mod, Properties properties) {
        super(mod, properties, Component.translatable("message.feywild.pixie_orb"));
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull Level level, @Nonnull Player player, @Nonnull InteractionHand hand) {
        if (player instanceof ServerPlayer) {
            BotaniaPixie pixie = new BotaniaPixie(ModEntities.botaniaPixie, level);
            pixie.setPos(player.getX(), player.getY(), player.getZ());
            pixie.setVariant(getVariantPlayer(player));
            level.addFreshEntity(pixie);
            player.swing(hand, true);
            if (!Objects.requireNonNull(player).isCreative()) {
                player.getItemInHand(hand).shrink(1);
                player.addItem(new ItemStack(Items.ENDER_EYE));
            }
            return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), false);
        }
        return InteractionResultHolder.pass(player.getItemInHand(hand));
    }

    private BotaniaPixie.BotaniaPixieVariant getVariantPlayer(Player player) {
        QuestData data = QuestData.get((ServerPlayer) player);
        if (data.getAlignment() == null) {
            return BotaniaPixie.BotaniaPixieVariant.DEFAULT;
        } else {
            return switch (data.getAlignment()) {
                case SPRING -> BotaniaPixie.BotaniaPixieVariant.SPRING;
                case SUMMER -> BotaniaPixie.BotaniaPixieVariant.SUMMER;
                case WINTER -> BotaniaPixie.BotaniaPixieVariant.WINTER;
                case AUTUMN -> BotaniaPixie.BotaniaPixieVariant.AUTUMN;
            };
        }
    }
}
