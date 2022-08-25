package com.feywild.feywild.world.gen.structure;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.config.CompatConfig;
import com.feywild.feywild.entity.DwarfBlacksmith;
import com.feywild.feywild.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class FeywildDataMarkers {
    
    public static void handle(String data, StructureTemplateManager templates, StructureManager structures, WorldGenLevel level, StructureTemplate.StructureBlockInfo block, BlockPos pos, Rotation rot, RandomSource random, BoundingBox box) {
        if (data.equals("Waystone") && CompatConfig.waystones) {
            placePiece(templates, level, "waystone", pos, random);
        } else if (data.equals("Dwarf")) {
            placeDwarf(level, pos);
        }
    }

    @SuppressWarnings("SameParameterValue")
    private static void placePiece(StructureTemplateManager templates, WorldGenLevel level, String name, BlockPos pos, RandomSource random) {
        StructureTemplate template = templates.getOrCreate(new ResourceLocation(FeywildMod.getInstance().modid, "parts/" + name));
        template.placeInWorld(level, pos, pos, new StructurePlaceSettings(), random, 4);
    }

    private static void placeDwarf(WorldGenLevel level, BlockPos pos) {
        if (CompatConfig.mythic_alfheim.locked) {
            level.setBlock(pos, ModBlocks.ancientRunestone.defaultBlockState(), 2);
        } else {
            DwarfBlacksmith entity = new DwarfBlacksmith(ModEntities.dwarfBlacksmith, level.getLevel());
            entity.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            entity.trySetTamed(false);
            entity.setPersistenceRequired();
            addEntity(level, entity);
        }
    }

    private static void addEntity(WorldGenLevel level, Entity entity) {
        if (level instanceof WorldGenRegion) {
            int x = ((int) Math.floor(entity.getX())) >> 4;
            int z = ((int) Math.floor(entity.getZ())) >> 4;
            if (((WorldGenRegion) level).getCenter().x == x && ((WorldGenRegion) level).getCenter().z == z) {
                level.addFreshEntity(entity);
            }
        } else {
            level.addFreshEntity(entity);
        }
    }
}
