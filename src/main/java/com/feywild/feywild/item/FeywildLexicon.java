package com.feywild.feywild.item;

import com.feywild.feywild.util.TooltipHelper;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.base.ItemBase;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
//import vazkii.patchouli.api.PatchouliAPI;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

// TODO patchouli
public class FeywildLexicon extends ItemBase {

    public FeywildLexicon(ModX mod, Properties properties) {
        super(mod, properties);
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull Level levelIn, @Nonnull Player player, @Nonnull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
//        if (player instanceof ServerPlayer) {
//            if (!FeyPlayerData.get(player).getBoolean("feywild_got_scroll") && MiscConfig.initial_scroll == ScrollConfig.BOOK) {
//                FeywildMod.getNetwork().instance.send(PacketDistributor.PLAYER.with( () -> (ServerPlayer) player), new OpeningScreenSerializer.Message(LibraryBooks.getLibraryBooks().size()));
//                FeyPlayerData.get(player).putBoolean("feywild_got_scroll", true);
//            }else
//                PatchouliAPI.get().openBookGUI((ServerPlayer) player, Objects.requireNonNull(this.getRegistryName()));
//        }
        return new InteractionResultHolder<>(InteractionResult.FAIL, stack);
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        TooltipHelper.addTooltip(tooltip, new TranslatableComponent("message.feywild.feywild_lexicon"));
        super.appendHoverText(stack, level, tooltip, flag);
    }
}
