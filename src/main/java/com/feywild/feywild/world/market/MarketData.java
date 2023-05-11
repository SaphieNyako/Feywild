package com.feywild.feywild.world.market;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.util.BoundingBoxUtil;
import com.feywild.feywild.world.FeywildDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

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
        if (level.dimension() != FeywildDimensions.MARKETPLACE) return null;
        DimensionDataStorage storage = level.getDataStorage();
        return storage.computeIfAbsent(MarketData::new, MarketData::new, FeywildMod.getInstance().modid + "_market");
    }

    private static void removeAllEntities(ServerLevel level) {
        // Remove all entities from the world
        // Must use version with bounding box to load the chunks
        StructureTemplate template = level.getStructureManager().get(FeywildMod.getInstance().resource("market")).orElse(null);

        if (template != null) {
            AABB aabb = BoundingBoxUtil.get(template.getBoundingBox(new StructurePlaceSettings(), new BlockPos(-10, 57, -10))).inflate(10);
            level.getEntities(null, aabb).stream()
                    .filter(e -> !(e instanceof Player))
                    .forEach(e -> e.remove(Entity.RemovalReason.DISCARDED));
        }
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
                removeAllEntities(Objects.requireNonNull(server.getLevel(FeywildDimensions.MARKETPLACE)));
            }
            open = world.isDay();
            setDirty();
        }
    }

    public boolean isOpen() {
        return open;
    }
}
