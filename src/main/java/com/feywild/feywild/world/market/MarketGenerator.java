package com.feywild.feywild.world.market;

import com.feywild.feywild.FeywildMod;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarketGenerator {

    public static final BlockPos BASE_POS = new BlockPos(-10, 57, -10);
    public static final List<EntityType<?>> LIVESTOCK = ImmutableList.of(
            EntityType.COW, EntityType.PIG, EntityType.SHEEP, EntityType.HORSE, EntityType.CHICKEN, EntityType.LLAMA
    );

    private static final Map<ResourceLocation, DwarfEntry> DWARVES = new HashMap<>();

    public static void registerMarketDwarf(ResourceLocation id, EntityType<?> type, BlockPos position) {
        synchronized (DWARVES) {
            if (DWARVES.containsKey(id)) throw new IllegalStateException("Dwarf entry registered twice: " + id);
            DWARVES.put(id, new DwarfEntry(type, position.immutable()));
        }
    }

    public static void generate(ServerLevel level) {
        MarketData data = MarketData.get(level);
        if (data != null) {
            if (data.tryGenerate()) {
                generateBase(level);
                addEntities(level);
            }
        }
    }

    private static void addEntities(ServerLevel level) {
        for (int i = 0; i < 4; i++) {
            addLivestock(level, -3.5, 63, 1.5);
        }

        for (DwarfEntry entry : DWARVES.values()) {
            Entity entity = entry.type.create(level);
            if (entity != null) {
                entity.setPos(
                        entry.position.getX() + 0.5,
                        entry.position.getY(),
                        entry.position.getZ() + 0.5
                );
                level.addFreshEntity(entity);
            }
        }
    }

    private static void generateBase(ServerLevel level) {
        StructureTemplate template = level.getStructureManager().get(FeywildMod.getInstance().resource("market")).orElse(null);
        if (template != null) {
            template.placeInWorld(level, BASE_POS, BASE_POS, new StructurePlaceSettings(), level.random, 0);
        }
    }

    @SuppressWarnings("SameParameterValue")
    private static void addLivestock(ServerLevel level, double x, double y, double z) {
        EntityType<?> type = LIVESTOCK.get(level.random.nextInt(LIVESTOCK.size()));
        Entity entity = type.create(level);
        if (entity != null) {
            entity.setPos(x, y, z);
            level.addFreshEntity(entity);
        }
    }

    private record DwarfEntry(EntityType<?> type, BlockPos position) {
    }
}
