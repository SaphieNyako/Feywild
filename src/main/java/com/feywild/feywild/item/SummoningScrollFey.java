package com.feywild.feywild.item;

import com.feywild.feywild.config.MiscConfig;
import com.feywild.feywild.entity.base.Pixie;
import com.feywild.feywild.entity.base.FeyBase;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.sound.FeySound;
import com.feywild.feywild.util.TooltipHelper;
import com.feywild.feywild.world.FeywildDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.moddingx.libx.mod.ModX;
import org.moddingx.libx.registration.Registerable;
import org.moddingx.libx.registration.SetupContext;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

import net.minecraft.world.item.Item.Properties;

public class SummoningScrollFey<T extends FeyBase> extends SummoningScroll<T> implements Registerable {

    public SummoningScrollFey(ModX mod, EntityType<T> type, @Nullable FeySound sound, Properties properties) {
        super(mod, type, sound, properties);
    }

    @Override
    public void registerCommon(SetupContext ctx) {
        EmptyScroll.registerCapture(type, this);
    }

    @Override
    protected boolean canSummon(Level level, Player player, BlockPos pos, @Nullable CompoundTag storedTag, T entity) {
        if (player instanceof ServerPlayer serverPlayer) {
            if (MiscConfig.summon_all_fey && serverPlayer.level().dimension() != FeywildDimensions.MARKETPLACE) {
                return true;
            } else {
                Alignment alignment = QuestData.get(serverPlayer).getAlignment();
                if (serverPlayer.level().dimension() == FeywildDimensions.MARKETPLACE) {
                    player.sendSystemMessage(Component.translatable("message.feywild.summon_market"));
                    return false;
                } else if (entity.alignment != null && alignment != entity.alignment && !(entity instanceof Pixie && alignment == null)) {
                    player.sendSystemMessage(Component.translatable("message.feywild.summon_fail"));
                    return false;
                } else {
                    return true;
                }
            }
        }
        player.sendSystemMessage(Component.translatable("message.feywild.summon_fail"));
        return false;
    }

    @Override
    protected boolean canCapture(Level level, Player player, T entity) {
        return player.getUUID().equals(entity.getOwner());
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        if (level != null) {
            TooltipHelper.addTooltip(tooltip, level, Component.translatable("message.feywild." + Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(this.type)).getPath()));
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }
}
