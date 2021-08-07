package com.feywild.feywild.item;

import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.ItemBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SummoningScroll<T extends LivingEntity> extends ItemBase {
    
    protected final EntityType<T> type;
    
    @Nullable
    private final SoundEvent soundEvent;

    public SummoningScroll(ModX mod, EntityType<T> type, @Nullable SoundEvent soundEvent, Properties properties) {
        super(mod, properties);
        this.type = type;
        this.soundEvent = soundEvent;
    }

    protected boolean canSummon(World world, PlayerEntity player, BlockPos pos) {
        return true;
    }
    
    protected void prepareEntity(World world, PlayerEntity player, BlockPos pos, T entity) {
        
    }
    
    @Nonnull
    @Override
    public ActionResultType useOn(@Nonnull ItemUseContext context) {
        if (context.getPlayer() != null && this.canSummon(context.getLevel(), context.getPlayer(), context.getClickedPos().immutable())) {
            if (!context.getLevel().isClientSide) {
                T entity = this.type.create(context.getLevel());
                if (entity != null) {
                    BlockPos offsetPos = context.getClickedPos().relative(context.getClickedFace());
                    entity.setPos(offsetPos.getX() + 0.5, offsetPos.getY(), offsetPos.getZ() + 0.5);
                    this.prepareEntity(context.getLevel(), context.getPlayer(), context.getClickedPos().immutable(), entity);
                    context.getLevel().addFreshEntity(entity);
                    if (this.soundEvent != null) entity.playSound(this.soundEvent, 1, 1);
                    if (!context.getPlayer().isCreative()) context.getItemInHand().shrink(1);
                }
            }
            return ActionResultType.sidedSuccess(context.getLevel().isClientSide);
        }
        return ActionResultType.PASS;
    }
}
