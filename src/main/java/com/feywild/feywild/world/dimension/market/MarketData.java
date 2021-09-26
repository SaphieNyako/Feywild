package com.feywild.feywild.world.dimension.market;

import com.feywild.feywild.FeywildMod;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

// Holds data about the curren tstae of the dwarf market
// Gives the possibility to add new dwarves later without
// regenerating the world
public class MarketData extends WorldSavedData  {

    public static MarketData get(ServerWorld world) {
        DimensionSavedDataManager storage = world.getDataStorage();
        return storage.get(MarketData::new, FeywildMod.getInstance().modid);
    }

    private boolean generated;
    private final List<ResourceLocation> spawnedDwarves;
    
    public MarketData() {
        super(FeywildMod.getInstance().modid);
        this.generated = false;
        this.spawnedDwarves = new ArrayList<>();
    }
    
    @Override
    public void load(@Nonnull CompoundNBT nbt) {
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
}
