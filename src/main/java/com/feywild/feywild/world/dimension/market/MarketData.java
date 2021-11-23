package com.feywild.feywild.world.dimension.market;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.world.dimension.ModDimensions;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

// Holds data about the current state of the dwarf market
public class MarketData extends WorldSavedData {

    private boolean open;
    private boolean generated;
    public MarketData() {
        super(FeywildMod.getInstance().modid);
        this.generated = false;
    }

    @Nullable
    public static MarketData get(ServerWorld world) {
        if (world.dimension() != ModDimensions.MARKET_PLACE_DIMENSION) return null;
        DimensionSavedDataManager storage = world.getDataStorage();
        return storage.computeIfAbsent(MarketData::new, FeywildMod.getInstance().modid);
    }

    @Override
    public void load(@Nonnull CompoundNBT nbt) {
        open = nbt.getBoolean("Open");
        generated = nbt.getBoolean("Generated");
    }

    @Nonnull
    @Override
    public CompoundNBT save(@Nonnull CompoundNBT nbt) {
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
        ServerWorld world = server.overworld();
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
