package com.feywild.feywild.item;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.entity.DwarfBlacksmith;
import com.feywild.feywild.util.TooltipHelper;
import org.moddingx.libx.mod.ModX;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.world.item.Item.Properties;

public class SummoningScrollDwarfBlacksmith extends SummoningScroll<DwarfBlacksmith> {

    public SummoningScrollDwarfBlacksmith(ModX mod, EntityType<DwarfBlacksmith> type, Properties properties) {
        super(mod, type, null, properties);
    }

    @Override
    protected boolean canSummon(Level level, Player player, BlockPos pos, @Nullable CompoundTag storedTag, DwarfBlacksmith entity) {
        return level.getBlockState(pos).getBlock() == ModBlocks.dwarvenAnvil;
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        TooltipHelper.addTooltip(tooltip, new TranslatableComponent("message.feywild.dwarf_blacksmith"));
    }
}
