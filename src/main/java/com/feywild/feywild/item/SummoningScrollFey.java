package com.feywild.feywild.item;

import com.feywild.feywild.entity.base.Fey;
import com.feywild.feywild.entity.base.FeyBase;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.util.TooltipHelper;
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

public class SummoningScrollFey<T extends FeyBase> extends SummoningScroll<T> implements Registerable {

    public SummoningScrollFey(ModX mod, EntityType<T> type, @Nullable SoundEvent soundEvent, Properties properties) {
        super(mod, type, soundEvent, properties);
    }

    @Override
    public void registerCommon(SetupContext ctx) {
        EmptyScroll.registerCapture(type, this);
    }

    @Override
    protected boolean canSummon(Level level, Player player, BlockPos pos, @Nullable CompoundTag storedTag, T entity) {
        if (player instanceof ServerPlayer serverPlayer) {
            Alignment alignment = QuestData.get(serverPlayer).getAlignment();
            if (alignment != entity.alignment && !(entity instanceof Fey && alignment == null)) {
                player.sendSystemMessage(Component.translatable("message.feywild.summon_fail"));
                return false;
            } else {
                return true;
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
    public void appendHoverText(@Nonnull ItemStack stack, Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        TooltipHelper.addTooltip(tooltip, level, Component.translatable("message.feywild." + Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(this.type)).getPath()));
        super.appendHoverText(stack, level, tooltip, flag);
    }
}
