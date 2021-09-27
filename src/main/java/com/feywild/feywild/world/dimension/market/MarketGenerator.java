package com.feywild.feywild.world.dimension.market;

import com.feywild.feywild.FeywildMod;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.server.ServerWorld;

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

    public static void generate(ServerWorld world) {
        MarketData data = MarketData.get(world);
        if (data != null) {
            if (data.tryGenerate()) {
                //   purge(world);
                generateBase(world);
            }
            for (DwarfEntry entry : DWARVES.values()) {
                Entity entity = entry.type.create(world);
                if (entity != null) {
                    entity.setPos(
                            entry.position.getX() + 0.5,
                            entry.position.getY(),
                            entry.position.getZ() + 0.5
                    );
                    world.addFreshEntity(entity);
                }
            }
        }
    }

    private static void generateBase(ServerWorld world) {
        Template template = world.getStructureManager().get(new ResourceLocation(FeywildMod.getInstance().modid, "market"));
        if (template != null) {
            template.placeInWorld(world, BASE_POS, new PlacementSettings(), world.random);
        }
        for (int i = 0; i < 4; i++) {
            addLivestock(world, -3.5, 63, 1.5);
        }
    }

    @SuppressWarnings("SameParameterValue")
    private static void addLivestock(ServerWorld world, double x, double y, double z) {
        EntityType<?> type = LIVESTOCK.get(world.random.nextInt(LIVESTOCK.size()));
        Entity entity = type.create(world);
        if (entity != null) {
            entity.setPos(x, y, z);
            world.addFreshEntity(entity);
        }
    }

    private static class DwarfEntry {

        public final EntityType<?> type;
        public final BlockPos position;

        public DwarfEntry(EntityType<?> type, BlockPos position) {
            this.type = type;
            this.position = position;
        }
    }
}
