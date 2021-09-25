package com.feywild.feywild.entity;

import com.feywild.feywild.entity.base.TraderEntity;
import com.feywild.feywild.world.dimension.ModDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtCustomerGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class MarketDwarfEntity extends DwarfBlacksmithEntity {

    public MarketDwarfEntity(EntityType<? extends TraderEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public static AttributeModifierMap.MutableAttribute getDefaultAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 36)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8)
                .add(Attributes.ARMOR_TOUGHNESS, 5)
                .add(Attributes.ARMOR, 15)
                .add(Attributes.ATTACK_DAMAGE, 4)
                .add(Attributes.MOVEMENT_SPEED, 0);
    }

    @Nonnull
    @Override
    public ActionResultType interactAt(PlayerEntity player, @Nonnull Vector3d vec, @Nonnull Hand hand) {
        if (!player.getCommandSenderWorld().isClientSide) {
            if (player.level.dimension() == ModDimensions.MARKET_PLACE_DIMENSION) {
                trade(player);
            } else {
                player.displayClientMessage(new TranslationTextComponent("dwarf.feywild.annoyed"), false);
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(2, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(3, new LookAtCustomerGoal(this));
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
