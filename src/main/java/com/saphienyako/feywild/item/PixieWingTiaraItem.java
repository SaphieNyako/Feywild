package com.saphienyako.feywild.item;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.effect.ModEffects;
import com.saphienyako.feywild.sound.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PixieWingTiaraItem extends Item {

    private final ResourceLocation wingTexture;

    public PixieWingTiaraItem(Properties properties, ResourceLocation wingTexture) {
        super(properties);
        this.wingTexture = wingTexture;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        if(Screen.hasShiftDown()){
            tooltip.add(Component.translatable("message.feywild.pixie_wing_tiara").withStyle(ChatFormatting.BLUE));
        }

        else {
            tooltip.add(Component.translatable("message.feywild.shift_down").withStyle(ChatFormatting.GREEN));
        }
        super.appendHoverText(stack, context, tooltip, flag);
    }

    public ResourceLocation getWingTexture(@NotNull ItemStack stack) {

        String name = stack.getHoverName().getString();
        System.out.println("Wing name: " + name);

        switch (name) {
            case "lesbian wings", "pride 01" -> {
                return ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID,
                        "textures/entity/fey_wings/fey_wings_pride_01");
            }

            case "gay wings", "pride 02" -> {
                return ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID,
                        "textures/entity/fey_wings/fey_wings_pride_02");
            }

            case "bi wings", "pride 03" -> {
                return ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID,
                        "textures/entity/fey_wings/fey_wings_pride_03");
            }

            case "pan wings", "pride 04" -> {
                return ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID,
                        "textures/entity/fey_wings/fey_wings_pride_04");
            }

            case "ace wings", "pride 05" -> {
                return ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID,
                        "textures/entity/fey_wings/fey_wings_pride_05");
            }

            case "aro wings", "pride 06" -> {
                return ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID,
                        "textures/entity/fey_wings/fey_wings_pride_06");
            }

            case "pride 07" -> {
                return ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID,
                        "textures/entity/fey_wings/fey_wings_pride_07");
            }

            case "pride 08" -> {
                return ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID,
                        "textures/entity/fey_wings/fey_wings_pride_08");
            }

            case "pride 09" -> {
                return ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID,
                        "textures/entity/fey_wings/fey_wings_pride_09");
            }

            case "trans wings", "pride 10" -> {
                return ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID,
                        "textures/entity/fey_wings/fey_wings_pride_10");
            }

            default -> { return wingTexture; }
        }
    }


    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);

        if (!(entity instanceof Player player) || level.isClientSide) return;

        CompoundTag data = player.getPersistentData();

        boolean wasHolding = data.getBoolean("tiara_was_offhand");
        boolean isHolding = player.getOffhandItem().getItem() instanceof PixieWingTiaraItem;

        if (!wasHolding && isHolding) {
            player.level().playSound(null, player.blockPosition(), ModSounds.PIXIE_SPELL_CASTING_SHORT.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        }

        data.putBoolean("tiara_was_offhand", isHolding);

        if (isHolding) {
            MobEffectInstance current = player.getEffect(ModEffects.FEY_FLYING);

            if (current == null || current.getDuration() < 210) {
                player.addEffect(new MobEffectInstance(ModEffects.FEY_FLYING, 220, 0, false, false, true));
            }
        }
    }
}
