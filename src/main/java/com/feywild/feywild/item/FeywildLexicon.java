package com.feywild.feywild.item;

import com.feywild.feywild.FeyPlayerData;
import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.config.MiscConfig;
import com.feywild.feywild.config.ScrollConfig;
import com.feywild.feywild.network.OpeningScreenSerializer;
import com.feywild.feywild.util.LibraryBooks;
import com.feywild.feywild.util.TooltipHelper;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.ItemBase;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;
import vazkii.patchouli.api.PatchouliAPI;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class FeywildLexicon extends ItemBase {

    public FeywildLexicon(ModX mod, Properties properties) {
        super(mod, properties);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> use(@Nonnull World worldIn, @Nonnull PlayerEntity player, @Nonnull Hand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player instanceof ServerPlayerEntity) {
            if (!FeyPlayerData.get(player).getBoolean("feywild_got_scroll") && MiscConfig.initial_scroll == ScrollConfig.BOOK) {
                FeywildMod.getNetwork().instance.send(PacketDistributor.PLAYER.with( () -> (ServerPlayerEntity) player), new OpeningScreenSerializer.Message(LibraryBooks.getLibraryBooks().size()));
                FeyPlayerData.get(player).putBoolean("feywild_got_scroll", true);
            }else
                PatchouliAPI.get().openBookGUI((ServerPlayerEntity) player, Objects.requireNonNull(this.getRegistryName()));
        }
        return new ActionResult<>(ActionResultType.FAIL, stack);
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable World world, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flag) {
        TooltipHelper.addTooltip(tooltip, new TranslationTextComponent("message.feywild.feywild_lexicon"));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
