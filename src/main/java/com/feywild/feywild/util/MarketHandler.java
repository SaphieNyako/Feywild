package com.feywild.feywild.util;

import com.feywild.feywild.world.dimension.ModDimensions;
import com.feywild.feywild.world.dimension.teleporters.MarketPlaceTeleporter;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Objects;

public class MarketHandler {

    private static MarketHandler instance;
    long date;

    private MarketHandler(){
        date = 0;
    }

    public static MarketHandler getInstance() {
        if(instance == null){
            instance = new MarketHandler();
        }
        return instance;
    }

    public long getDate() {
        return date;
    }

    public void updateMarket(MinecraftServer server, long date){
            ServerWorld marketWorld = server.getLevel(ModDimensions.MARKET_PLACE_DIMENSION);


            assert marketWorld != null;
            // Mark dimension for reconstruction
            marketWorld.setBlock(new BlockPos(0, 0, 0), Blocks.AIR.defaultBlockState(), 2);

            // Move all players back to the overworld
            marketWorld.getPlayers(LivingEntity::isAlive).forEach(player -> {
                MarketPlaceTeleporter.teleportToDimension(player, Objects.requireNonNull(server.getLevel(World.OVERWORLD)));
                player.sendMessage(new TranslationTextComponent("message.feywild.market_closed"), player.getUUID());
            });
            //Clean Up
            marketWorld.getEntities().forEach(Entity::remove);

            //Set date
            this.date = date;
    }
}
