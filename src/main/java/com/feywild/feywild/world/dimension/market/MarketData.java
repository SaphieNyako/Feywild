package com.feywild.feywild.world.dimension.market;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.world.dimension.ModDimensions;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

// Holds data about the curren tstae of the dwarf market
// Gives the possibility to add new dwarves later without
// regenerating the world
public class MarketData extends WorldSavedData  {

    @Nullable
    public static MarketData get(ServerWorld world) {
        if (world.dimension() != ModDimensions.MARKET_PLACE_DIMENSION) return null;
        DimensionSavedDataManager storage = world.getDataStorage();
        return storage.computeIfAbsent(MarketData::new, FeywildMod.getInstance().modid);
    }

    private boolean open;
    private boolean generated;
    private final List<ResourceLocation> spawnedDwarves;
    
    public MarketData() {
        super(FeywildMod.getInstance().modid);
        this.generated = false;
        this.spawnedDwarves = new ArrayList<>();
    }
    
    @Override
    public void load(@Nonnull CompoundNBT nbt) {
        open = nbt.getBoolean("Open");
        generated = nbt.getBoolean("Generated");
        spawnedDwarves.clear();
        if (nbt.contains("SpawnedDwarves", Constants.NBT.TAG_LIST)) {
            ListNBT list = nbt.getList("SpawnedDwarves", Constants.NBT.TAG_STRING);
            for (int i = 0; i < list.size(); i++) {
                ResourceLocation rl = ResourceLocation.tryParse(list.getString(i));
                if (rl != null) spawnedDwarves.add(rl);
            }
        }
    }

    @Nonnull
    @Override
    public CompoundNBT save(@Nonnull CompoundNBT nbt) {
        nbt.putBoolean("Open", open);
        nbt.putBoolean("Generated", generated);
        ListNBT list = new ListNBT();
        spawnedDwarves.forEach(rl -> list.add(StringNBT.valueOf(rl.toString())));
        nbt.put("SpawnedDwarves", list);
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
    
    public boolean trySpawnDwarf(ResourceLocation id) {
        if (!spawnedDwarves.contains(id)) {
            spawnedDwarves.add(id);
            setDirty();
            return true;
        } else {
            return false;
        }
    }

    public void update(MinecraftServer server, Runnable onClose) {
        ServerWorld world = server.overworld();
        boolean shouldBeOpen = world.getDayTime() < 13000;
        if (shouldBeOpen != open) {
            if (!shouldBeOpen) {
                onClose.run();
            }
            open = shouldBeOpen;
            setDirty();
        }
    }
    
    public boolean isOpen() {
        return open;
    }
}
