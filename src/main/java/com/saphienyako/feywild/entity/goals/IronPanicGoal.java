package com.saphienyako.feywild.entity.goals;

import com.saphienyako.feywild.entity.base.PixieBase;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;

import javax.annotation.Nullable;
import java.util.List;

public class IronPanicGoal extends Goal {

    private static final TargetingConditions TARGETING = TargetingConditions.forNonCombat().range(8).ignoreLineOfSight();

    private final LivingEntity entity;
    private final double speed;
    private final double range;

    private Level level;

    public IronPanicGoal(PixieBase entity, Level level, double speed, double range) {
        this.entity = entity;
        this.level = level;
        this.speed = speed;
        this.range = range;
    }

    @Override
    public boolean canUse() {
        List<Player> players = entity.level().getEntitiesOfClass(Player.class,
                entity.getBoundingBox().inflate(range),
                player -> !player.isCreative() && isHoldingIron(player));

        return !players.isEmpty();
    }

    @Override
    public void start() {
        Player targetPlayer = findPlayer();

        if (targetPlayer == null) return;
        Vec3 dir = this.entity.position().subtract(targetPlayer.position()).normalize();

        double intensity = 0.4;
        this.entity.setDeltaMovement(dir.scale(intensity));

        entity.lookAt(EntityAnchorArgument.Anchor.EYES, targetPlayer.position());
    }

    private boolean isHoldingIron(Player player) {
        ItemStack main = player.getMainHandItem();
        ItemStack off = player.getOffhandItem();

        return isIron(main) || isIron(off);
    }

    private boolean isIron(ItemStack stack) {
        return !stack.isEmpty() && stack.is(Items.IRON_INGOT);
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Nullable
    private Player findPlayer() {
        double distance = Double.MAX_VALUE;
        Player current = null;
        for (Player player : this.level.getNearbyEntities(Player.class, TARGETING, this.entity, this.entity.getBoundingBox().inflate(8))) {
            if (!player.isCreative() && isHoldingIron(player) && this.entity.distanceToSqr(player) < distance) {
                current = player;
                distance = this.entity.distanceToSqr(player);
            }
        }
        return current;
    }
}
