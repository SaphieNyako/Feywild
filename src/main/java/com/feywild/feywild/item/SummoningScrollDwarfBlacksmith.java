package com.feywild.feywild.item;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.entity.DwarfBlacksmithEntity;
import com.feywild.feywild.util.TooltipHelper;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class SummoningScrollDwarfBlacksmith extends SummoningScroll<DwarfBlacksmithEntity> {
    
    public SummoningScrollDwarfBlacksmith(ModX mod, EntityType<DwarfBlacksmithEntity> type, Properties properties) {
        super(mod, type, null, properties);
    }
    
    @Override
    protected boolean canSummon(World world, PlayerEntity player, BlockPos pos, @Nullable CompoundNBT storedTag) {
        return world.getBlockState(pos).getBlock() == ModBlocks.dwarvenAnvil;
    }

    @Override
    protected void prepareEntity(World world, PlayerEntity player, BlockPos pos, DwarfBlacksmithEntity entity) {
        entity.setTamed(true);
        entity.setSummonPos(pos);
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable World world, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flag) {
        TooltipHelper.addTooltip(tooltip, new TranslationTextComponent("message.feywild.dwarf_blacksmith"));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
