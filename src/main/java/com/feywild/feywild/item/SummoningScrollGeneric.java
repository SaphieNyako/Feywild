package com.feywild.feywild.item;

import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.sound.FeySound;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.moddingx.libx.mod.ModX;

import javax.annotation.Nullable;

public class SummoningScrollGeneric<T extends LivingEntity> extends SummoningScroll<T> {
    
    @Nullable
    private final Alignment requiredAlignment;
    
    public SummoningScrollGeneric(ModX mod, EntityType<T> type, @Nullable FeySound sound, @Nullable Alignment requiredAlignment, Properties properties) {
        super(mod, type, sound, properties);
        this.requiredAlignment = requiredAlignment;
    }

    @Nullable
    @Override
    protected Alignment requiredAlignment(Level level, Player player, T entity) {
        return this.requiredAlignment;
    }
}
