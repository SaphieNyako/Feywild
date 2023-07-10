package com.feywild.feywild.item;

import com.feywild.feywild.block.ModTrees;
import com.feywild.feywild.entity.ModEntities;
import com.feywild.feywild.entity.base.TreeEnt;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.sound.FeySound;
import com.feywild.feywild.util.TooltipHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.moddingx.libx.mod.ModX;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class SummoningScrollTreeEnt<T extends TreeEnt> extends SummoningScroll<T> {

    public SummoningScrollTreeEnt(ModX mod, EntityType<T> type, @Nullable FeySound sound, Properties properties, @Nullable Alignment alignment) {
        super(mod, type, sound, properties);
    }
    
    @Override
    protected boolean canSummon(Level level, Player player, BlockPos pos, @Nullable CompoundTag storedTag, T entity) {
        if (level.getBlockState(pos).getBlock() == entity.getTree().getSapling()) {
            return true;
        } else {
            player.sendSystemMessage(Component.translatable("message.feywild.summon_fail"));
            return false;
        }
    }

    @Nullable
    @Override
    protected Alignment requiredAlignment(Level level, Player player, T entity) {
        return entity.alignment();
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        if (level != null) {
            TooltipHelper.addTooltip(tooltip, level, Component.translatable("message.feywild." + Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(this.type)).getPath()));
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }
}
