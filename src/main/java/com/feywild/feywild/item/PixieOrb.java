package com.feywild.feywild.item;

import com.feywild.feywild.FeyPlayerData;
import com.feywild.feywild.entity.*;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
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
            Entity pixie = getPixie(player, level);
            pixie.setPos(player.getX(), player.getY(), player.getZ());
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

    public Entity getPixie(Player player, Level level) {
        return switch (FeyPlayerData.get(player).getString("capture_pixie")) {
            case "spring" -> new SpringBotaniaPixie(ModEntities.springBotaniaPixie, level);
            case "autumn" -> new AutumnBotaniaPixie(ModEntities.autumnBotaniaPixie, level);
            case "winter" -> new WinterBotaniaPixie(ModEntities.winterBotaniaPixie, level);
            default -> new SummerBotaniaPixie(ModEntities.summerBotaniaPixie, level);
        };
    }
}
