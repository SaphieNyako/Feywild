package com.feywild.feywild.world.dimension.market;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.world.dimension.ModDimensions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.level.saveddata.SavedData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

// Holds data about the current state of the dwarf market
public class MarketData extends SavedData  {

    @Nullable
    public static MarketData get(ServerLevel level) {
        if (level.dimension() != ModDimensions.MARKET_PLACE_DIMENSION) return null;
        DimensionDataStorage storage = level.getDataStorage();
        return storage.computeIfAbsent(MarketData::new, FeywildMod.getInstance().modid);
    }

    private boolean open;
    private boolean generated;
    
    public MarketData() {
        super(FeywildMod.getInstance().modid);
        this.generated = false;
    }
    
    @Override
    public void load(@Nonnull CompoundTag nbt) {
        open = nbt.getBoolean("Open");
        generated = nbt.getBoolean("Generated");
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
        ServerLevel level = server.overworld();
        boolean shouldBeOpen = world.getDayTime() < 13000;
        if (shouldBeOpen != open) {
            if (!shouldBeOpen) {
                onClose.run();
                generated = false;
            }
            open = shouldBeOpen;
            setDirty();
        }
    }
    
    public boolean isOpen() {
        return open;
    }
}
