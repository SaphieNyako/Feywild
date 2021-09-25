package com.feywild.feywild.entity;

import com.feywild.feywild.entity.base.TraderEntity;
import com.feywild.feywild.world.dimension.ModDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class MarketDwarfEntity extends DwarfBlacksmithEntity{
    public MarketDwarfEntity(EntityType<? extends TraderEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Nonnull
    @Override
    public ActionResultType interactAt(PlayerEntity player, @Nonnull Vector3d vec, @Nonnull Hand hand) {
        if(!player.getCommandSenderWorld().isClientSide){
            if(player.level.dimension() == ModDimensions.MARKET_PLACE_DIMENSION){
                trade(player);
            }else
            {
                player.displayClientMessage(new TranslationTextComponent("dwarf.feywild.annoyed"), false);
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }

    @Override
    public boolean isPersistenceRequired() {
        return true;
    }
}
