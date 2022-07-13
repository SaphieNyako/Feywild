package com.feywild.feywild.entity;

import com.feywild.feywild.entity.base.Trader;
import com.feywild.feywild.world.dimension.market.MarketPlaceDimension;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtTradingPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;

public class MarketDwarf extends DwarfBlacksmith {

    public MarketDwarf(EntityType<? extends Trader> type, Level levelIn) {
        super(type, levelIn);
    }

    public static AttributeSupplier.Builder getDefaultAttributes() {
        return DwarfBlacksmith.getDefaultAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0);
    }

    @Override
    public String getTradeCategory() {
        return "trades";
    }

    @Nonnull
    @Override
    public InteractionResult interactAt(Player player, @Nonnull Vec3 vec, @Nonnull InteractionHand hand) {
        if (!player.getCommandSenderWorld().isClientSide) {
            if (player.level.dimension() == MarketPlaceDimension.MARKET_PLACE_DIMENSION) {
                trade(player);
            } else {
                player.displayClientMessage(new TranslatableComponent("dwarf.feywild.annoyed"), false);
            }
        }
        return InteractionResult.SUCCESS;
    }

    protected void trade(Player player) {
        this.setTradingPlayer(player);
        this.openTradingScreen(player, new TranslatableComponent("Dwarven Trader"), 1);
        player.displayClientMessage(new TranslatableComponent("dwarf.feywild.dialogue"), false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(3, new LookAtTradingPlayerGoal(this));
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
