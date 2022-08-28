package com.feywild.feywild;

import com.feywild.feywild.entity.MarketDwarf;
import com.feywild.feywild.world.FeywildDimensions;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MarketProtectEvents {

    @SubscribeEvent
    public void blockBreak(BlockEvent.BreakEvent event) {
        if (event.getPlayer().level.dimension() == FeywildDimensions.MARKETPLACE) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void blockPlace(BlockEvent.EntityPlaceEvent event) {
        if (event.getLevel() instanceof Level && ((Level) event.getLevel()).dimension() == FeywildDimensions.MARKETPLACE) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void blockMultiPlace(BlockEvent.EntityMultiPlaceEvent event) {
        if (event.getLevel() instanceof Level && ((Level) event.getLevel()).dimension() == FeywildDimensions.MARKETPLACE) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void farmlandTrample(BlockEvent.FarmlandTrampleEvent event) {
        if (event.getEntity().level.dimension() == FeywildDimensions.MARKETPLACE) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void mobSpawnAttempt(LivingSpawnEvent.CheckSpawn event) {
        Level level;
        if (event.getLevel() instanceof Level) level = (Level) event.getLevel();
        else level = event.getEntity().level;
        if (level != null && level.dimension() == FeywildDimensions.MARKETPLACE) {
            event.setResult(Event.Result.DENY);
        }
    }

    @SubscribeEvent
    public void mobSpawn(LivingSpawnEvent.SpecialSpawn event) {
        Level level;
        if (event.getLevel() instanceof Level) level = (Level) event.getLevel();
        else level = event.getEntity().level;
        if (level != null && level.dimension() == FeywildDimensions.MARKETPLACE) {
            if (event.getSpawnReason() != MobSpawnType.SPAWN_EGG && event.getSpawnReason() != MobSpawnType.BUCKET
                    && event.getSpawnReason() != MobSpawnType.MOB_SUMMONED && event.getSpawnReason() != MobSpawnType.COMMAND) {
                if (event.isCancelable()) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public void livingAttack(LivingAttackEvent event) {
        if (!event.getSource().isBypassInvul() && event.getEntity().level.dimension() == FeywildDimensions.MARKETPLACE && event.getEntity() instanceof MarketDwarf) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void livingHurt(LivingHurtEvent event) {
        if (!event.getSource().isBypassInvul() && event.getEntity().level.dimension() == FeywildDimensions.MARKETPLACE && event.getEntity() instanceof MarketDwarf) {
            event.setCanceled(true);
        }
    }
}
