package com.feywild.feywild.world.dimension.market.setup;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.world.dimension.market.MarketPlaceDimensions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

// Holds data about the current state of the dwarf market
public class MarketData extends SavedData {

    private boolean open;
    private boolean generated;

    public MarketData() {
        open = false;
        generated = false;
    }

    public MarketData(@Nonnull CompoundTag nbt) {
        open = nbt.getBoolean("Open");
        generated = nbt.getBoolean("Generated");
    }

    @Nullable
    public static MarketData get(ServerLevel level) {
        if (level.dimension() != MarketPlaceDimensions.MARKET_PLACE_DIMENSION) return null;
        DimensionDataStorage storage = level.getDataStorage();
        return storage.computeIfAbsent(MarketData::new, MarketData::new, FeywildMod.getInstance().modid + "_market");
    }

    @Nonnull
    @Override
    public CompoundTag save(@Nonnull CompoundTag nbt) {
        nbt.putBoolean("Open", open);
        nbt.putBoolean("Generated", generated);
        return nbt;
    }

    public boolean tryGenerate() {
        if (!generated) {
            generated = true;
            setDirty();
            return true;
        } else {
            return false;
        }
    }

    public void update(MinecraftServer server, Runnable onClose) {
        ServerLevel world = server.overworld();
        if (world.isDay() != open) {
            if (!world.isDay()) {
                onClose.run();
                generated = false;
            }
            open = world.isDay();
            setDirty();
        }
    }

    public boolean isOpen() {
        return open;
    }
}
