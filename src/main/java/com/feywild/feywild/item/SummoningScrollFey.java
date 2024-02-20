package com.feywild.feywild.item;

import com.feywild.feywild.entity.base.FeyBase;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.sound.FeySound;
import com.feywild.feywild.util.TooltipHelper;
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

public class SummoningScrollFey<T extends FeyBase> extends SummoningScroll<T> {

    public SummoningScrollFey(ModX mod, EntityType<T> type, @Nullable FeySound sound, Properties properties) {
        super(mod, type, sound, properties);
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
