package com.saphienyako.feywild.item;

import com.saphienyako.feywild.entity.ModEntities;
import com.saphienyako.feywild.entity.SpriteEntity;
import com.saphienyako.feywild.network.FeywildNetwork;
import com.saphienyako.feywild.network.OpenLexiconMenuMessage;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.List;
import java.util.Objects;

public class FeywildLexicon extends Item {
    public FeywildLexicon(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player instanceof ServerPlayer serverPlayer) {
            SpriteEntity entity = ModEntities.SPRITE.get().create(level);

            if (entity != null) {
                entity.setPos(player.getEyePosition());
                level.addFreshEntity(entity);
                FeywildNetwork.sendToPlayer(new OpenLexiconMenuMessage(entity.getId()), (ServerPlayer)player);
            }
        }
        return new InteractionResultHolder<>(InteractionResult.FAIL, stack);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        if (level != null) {
            tooltip.add(Component.translatable("message.feywild.feywild_lexicon").withStyle(ChatFormatting.BLUE));
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }



}
