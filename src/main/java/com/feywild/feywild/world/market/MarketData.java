package com.feywild.feywild.world.market;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.util.BoundingBoxUtil;
import com.feywild.feywild.world.FeywildDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// Holds data about the current state of the dwarf market
public class MarketData extends SavedData {

    private boolean open;
    private boolean generated;
    private final List<UUID> allowedEntities;

    public MarketData() {
        open = false;
        generated = false;
        allowedEntities = new ArrayList<>();
    }

    public MarketData(@Nonnull CompoundTag nbt) {
        open = nbt.getBoolean("Open");
        generated = nbt.getBoolean("Generated");
        allowedEntities = new ArrayList<>();
        ListTag list = nbt.getList("AllowedEntities", Tag.TAG_INT_ARRAY);
        for (Tag tag : list) {
            allowedEntities.add(NbtUtils.loadUUID(tag));
        }
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
        ListTag list = new ListTag();
        for (UUID uid : this.allowedEntities) {
            list.add(NbtUtils.createUUID(uid));
        }
        nbt.put("AllowedEntities", list);
        return nbt;
    }

    public boolean tryGenerate() {
        if (!generated) {
            generated = true;
            this.allowedEntities.clear();
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
                this.allowedEntities.clear();
            }
            open = world.isDay();
            setDirty();
        }
    }
    
    public void addAllowedEntity(Entity entity) {
        if (open && entity instanceof LivingEntity && !(entity instanceof Player) && !(entity instanceof ArmorStand)) {
            this.allowedEntities.add(entity.getUUID());
        }
    }
    
    public boolean isAllowedEntity(Entity entity) {
        if (entity instanceof LivingEntity && !(entity instanceof Player) && !(entity instanceof ArmorStand)) {
            return this.allowedEntities.contains(entity.getUUID());
        } else {
            return true;
        }
    }

    public boolean isOpen() {
        return open;
    }
}
