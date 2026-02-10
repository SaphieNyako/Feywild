package com.saphienyako.feywild.entity.goals;

import com.saphienyako.feywild.config.FeywildConfig;
import com.saphienyako.feywild.entity.base.intereface.ITradeable;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.config.ModConfig;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

public class TradeForGemsGoal extends Goal {

    PathfinderMob entity;
    private ItemEntity target;

    private static final double SPEED = 1.2;
    private static final double TRADE_DISTANCE_SQR = 4.0;

    private int pathfindingResetCooldown = 0;

    private int tradeTimeout;

    public TradeForGemsGoal(PathfinderMob entity) {
        this.entity = entity;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!entity.isAlive()) return false;
        //Look for Entity
        List<ItemEntity> items = entity.level().getEntitiesOfClass(
                ItemEntity.class,
                entity.getBoundingBox().inflate(4.0),
                item -> item.isAlive() && ((ITradeable)entity).isTradeItem(item.getItem())
        );

        if (items.isEmpty()) return false;

        target = items.stream()
                .min(Comparator.comparingDouble(entity::distanceToSqr))
                .orElse(null);
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return target != null && target.isAlive();
        //other conditions?
    }

    @Override
    public void start() {

        pathfindingResetCooldown = 0;
        tradeTimeout = 40 + entity.level().random.nextInt(40);
        entity.getNavigation().moveTo(target, SPEED);
        if(entity.getRandom().nextInt(10) <= 1 && FeywildConfig.voicesActive) {
            this.entity.playSound(((ITradeable)entity).getTradeSound(), 0.7f, 1);
        }
    }

    @Override
    public void stop() {
        target = null;
        tradeTimeout = 0;
        pathfindingResetCooldown = 0;
        entity.getNavigation().stop();
    }

    @Override
    public void tick() {
        tradeTimeout--;
        if (tradeTimeout <= 0) {
            stop();
            return;
        }
        if (target == null) return;

        entity.getLookControl().setLookAt(target, 30.0F, 30.0F);

        if (entity.distanceToSqr(target) > TRADE_DISTANCE_SQR) {

            if (--pathfindingResetCooldown <= 0) {
                pathfindingResetCooldown = 10;

                if (!entity.getNavigation().moveTo(target, SPEED)) {
                    stop();
                }
            }
            return;
        }

        trade();
        stop();
    }

    private void trade() {
        if (target == null) return;

        ItemStack stack = target.getItem();

        stack.shrink(1);
        if (stack.isEmpty()) {
            target.discard();
        }

        ItemEntity reward = new ItemEntity(
                entity.level(),
                entity.getX(), entity.getY(), entity.getZ(), ((ITradeable) entity).getTradeResult() // getTradeResult()
        );
        this.entity.playSound((SoundEvents.COMPOSTER_EMPTY), 0.7f, 1);
        entity.level().addFreshEntity(reward);
    }
}
