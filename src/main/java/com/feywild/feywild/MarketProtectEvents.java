package com.feywild.feywild;

import com.feywild.feywild.entity.MarketDwarf;
import com.feywild.feywild.world.FeywildDimensions;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MarketProtectEvents {

    @SubscribeEvent
    public void blockBreak(BlockEvent.BreakEvent event) {
        if (event.getPlayer().level().dimension() == FeywildDimensions.MARKETPLACE) {
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
        if (event.getEntity().level().dimension() == FeywildDimensions.MARKETPLACE) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(receiveCanceled = true)
    public void mobSpawnAttempt(MobSpawnEvent.FinalizeSpawn event) {
        Level level;
        if (event.getLevel() instanceof Level) level = (Level) event.getLevel();
        else level = event.getEntity().level();
        if (level != null && level.dimension().equals(FeywildDimensions.MARKETPLACE)) {
            event.setCanceled(true);
            event.setSpawnCancelled(true);
        }
    }

    @SubscribeEvent
    public void livingAttack(LivingAttackEvent event) {
        if (!event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY) && event.getEntity().level().dimension() == FeywildDimensions.MARKETPLACE && event.getEntity() instanceof MarketDwarf) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void livingHurt(LivingHurtEvent event) {
        if (!event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY) && event.getEntity().level().dimension() == FeywildDimensions.MARKETPLACE && event.getEntity() instanceof MarketDwarf) {
            event.setCanceled(true);
        }
    }
}
