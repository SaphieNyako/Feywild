package com.feywild.feywild;

import com.feywild.feywild.entity.MarketDwarfEntity;
import com.feywild.feywild.world.dimension.ModDimensions;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MarketProtectEvents {

    //TODO: Prevent players from going to far out of the area

    @SubscribeEvent
    public void blockBreak(BlockEvent.BreakEvent event) {
        if (event.getPlayer().level.dimension() == ModDimensions.MARKET_PLACE_DIMENSION && !event.getPlayer().hasPermissions(2)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void blockPlace(BlockEvent.EntityPlaceEvent event) {
        if (event.getWorld() instanceof World && ((World) event.getWorld()).dimension() == ModDimensions.MARKET_PLACE_DIMENSION) {
            if (!(event.getEntity() instanceof PlayerEntity) || !event.getEntity().hasPermissions(2)) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void blockMultiPlace(BlockEvent.EntityMultiPlaceEvent event) {
        if (event.getWorld() instanceof World && ((World) event.getWorld()).dimension() == ModDimensions.MARKET_PLACE_DIMENSION) {
            if (!(event.getEntity() instanceof PlayerEntity) || !event.getEntity().hasPermissions(2)) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void farmlandTrample(BlockEvent.FarmlandTrampleEvent event) {
        if (event.getEntity().level.dimension() == ModDimensions.MARKET_PLACE_DIMENSION) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void mobSpawnAttempt(LivingSpawnEvent.CheckSpawn event) {
        World world;
        if (event.getWorld() instanceof World) world = (World) event.getWorld();
        else world = event.getEntity().level;
        if (world != null && world.dimension() == ModDimensions.MARKET_PLACE_DIMENSION && !(event.getEntity() instanceof PlayerEntity)) {
            event.setResult(Event.Result.DENY);
        }
    }

    @SubscribeEvent
    public void mobSpawn(LivingSpawnEvent.SpecialSpawn event) {
        World world;
        if (event.getWorld() instanceof World) world = (World) event.getWorld();
        else world = event.getEntity().level;
        if (world != null && world.dimension() == ModDimensions.MARKET_PLACE_DIMENSION && !(event.getEntity() instanceof PlayerEntity)) {
            if (event.getSpawnReason() != SpawnReason.SPAWN_EGG && event.getSpawnReason() != SpawnReason.BUCKET
                    && event.getSpawnReason() != SpawnReason.MOB_SUMMONED && event.getSpawnReason() != SpawnReason.COMMAND) {
                if (event.isCancelable()) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public void livingAttack(LivingAttackEvent event) {
        if (!event.getSource().isBypassInvul() && event.getEntity().level.dimension() == ModDimensions.MARKET_PLACE_DIMENSION && event.getEntity() instanceof MarketDwarfEntity) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void livingHurt(LivingHurtEvent event) {
        if (!event.getSource().isBypassInvul() && event.getEntity().level.dimension() == ModDimensions.MARKET_PLACE_DIMENSION && event.getEntity() instanceof MarketDwarfEntity) {
            event.setCanceled(true);
        }
    }
}
