package com.saphienyako.feywild.item;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.compat.ModCompat;
import com.saphienyako.feywild.effect.ModEffects;
import com.saphienyako.feywild.item.base.ToolTipBaseItem;
import com.saphienyako.feywild.sound.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
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

public class PixieWingTiaraItem extends ToolTipBaseItem {

    private final Identifier wingTexture;

    public PixieWingTiaraItem(Properties properties, Identifier wingTexture) {
        super(properties,Component.translatable("message.feywild.pixie_wing_tiara"));
        this.wingTexture = wingTexture;
    }
    public Identifier getWingTexture(@NotNull ItemStack stack) {

        String name = stack.getHoverName().getString();

        switch (name) {
            case "lesbian wings", "pride 01" -> {
                return Identifier.fromNamespaceAndPath(Feywild.MOD_ID,
                        "textures/entity/fey_wings/fey_wings_pride_01");
            }

            case "gay wings", "pride 02" -> {
                return Identifier.fromNamespaceAndPath(Feywild.MOD_ID,
                        "textures/entity/fey_wings/fey_wings_pride_02");
            }

            case "bi wings", "pride 03" -> {
                return Identifier.fromNamespaceAndPath(Feywild.MOD_ID,
                        "textures/entity/fey_wings/fey_wings_pride_03");
            }

            case "pan wings", "pride 04" -> {
                return Identifier.fromNamespaceAndPath(Feywild.MOD_ID,
                        "textures/entity/fey_wings/fey_wings_pride_04");
            }

            case "ace wings", "pride 05" -> {
                return Identifier.fromNamespaceAndPath(Feywild.MOD_ID,
                        "textures/entity/fey_wings/fey_wings_pride_05");
            }

            case "aro wings", "pride 06" -> {
                return Identifier.fromNamespaceAndPath(Feywild.MOD_ID,
                        "textures/entity/fey_wings/fey_wings_pride_06");
            }

            case "pride 07" -> {
                return Identifier.fromNamespaceAndPath(Feywild.MOD_ID,
                        "textures/entity/fey_wings/fey_wings_pride_07");
            }

            case "pride 08" -> {
                return Identifier.fromNamespaceAndPath(Feywild.MOD_ID,
                        "textures/entity/fey_wings/fey_wings_pride_08");
            }

            case "pride 09" -> {
                return Identifier.fromNamespaceAndPath(Feywild.MOD_ID,
                        "textures/entity/fey_wings/fey_wings_pride_09");
            }

            case "trans wings", "pride 10" -> {
                return Identifier.fromNamespaceAndPath(Feywild.MOD_ID,
                        "textures/entity/fey_wings/fey_wings_pride_10");
            }

            default -> { return wingTexture; }
        }
    }

}
