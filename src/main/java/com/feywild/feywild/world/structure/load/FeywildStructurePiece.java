package com.feywild.feywild.world.structure.load;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.config.CompatConfig;
import com.feywild.feywild.entity.DwarfBlacksmith;
import com.feywild.feywild.entity.ModEntityTypes;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.structures.SinglePoolElement;
import net.minecraft.world.level.levelgen.feature.structures.StructurePoolElementType;
import net.minecraft.world.level.levelgen.feature.structures.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;

import javax.annotation.Nonnull;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

public class FeywildStructurePiece extends SinglePoolElement {

    public static final ResourceLocation ID = new ResourceLocation(FeywildMod.getInstance().modid, "structure_piece");

    public static final Codec<FeywildStructurePiece> CODEC = RecordCodecBuilder.create((builder) -> builder.group(
            templateCodec(),
            processorsCodec(),
            projectionCodec()
    ).apply(builder, FeywildStructurePiece::new));

    public static final StructurePoolElementType<FeywildStructurePiece> TYPE = () -> CODEC;

    protected FeywildStructurePiece(Either<ResourceLocation, StructureTemplate> template, Supplier<StructureProcessorList> processors, StructureTemplatePool.Projection projection) {
        super(template, processors, projection);
    }

    @Override
    public boolean place(@Nonnull StructureManager templates, @Nonnull WorldGenLevel level, @Nonnull StructureFeatureManager structures, @Nonnull ChunkGenerator generator, @Nonnull BlockPos fromPos, @Nonnull BlockPos toPos, @Nonnull Rotation rot, @Nonnull BoundingBox box, @Nonnull Random random, boolean jigsaw) {
        StructureTemplate template = this.template.map(templates::getOrCreate, Function.identity());
        StructurePlaceSettings settings = this.getSettings(rot, box, jigsaw);
        if (!template.placeInWorld(level, fromPos, toPos, settings, random, 18)) {
            return false;
        } else {
            for (StructureTemplate.StructureBlockInfo info : StructureTemplate.processBlockInfos(level, fromPos, toPos, settings, this.getDataMarkers(templates, fromPos, rot, false), template)) {
                this.handleCustomDataMarker(templates, structures, level, info, info.pos, rot, random, box);
            }
            return true;
        }
    }

    @Nonnull
    @Override
    protected StructurePlaceSettings getSettings(@Nonnull Rotation rot, @Nonnull BoundingBox box, boolean jigsaw) {
        StructurePlaceSettings settings = super.getSettings(rot, box, jigsaw);
        // Don't ignore structure blocks
        settings.popProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);
        return settings;
    }

    // Same as handleDataMarker but will get the template manager
    public void handleCustomDataMarker(StructureManager templates, StructureFeatureManager structures, WorldGenLevel level, StructureTemplate.StructureBlockInfo block, BlockPos pos, Rotation rot, Random random, BoundingBox box) {
        String data = block.nbt == null ? "" : block.nbt.getString("metadata");
        // Replace structure block in all cases
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
        if (data.equals("Waystone") && CompatConfig.waystones) {
            placePiece(templates, level, "waystone", pos, random);
        } else if (data.equals("Dwarf")) {
            placeDwarf(level, pos);
        }
        super.handleDataMarker(level, block, pos, rot, random, box);
    }

    @Nonnull
    @Override
    public StructurePoolElementType<?> getType() {
        return TYPE;
    }

    @SuppressWarnings("SameParameterValue")
    private void placePiece(StructureManager templates, WorldGenLevel level, String name, BlockPos pos, Random random) {
        StructureTemplate template = templates.getOrCreate(new ResourceLocation(FeywildMod.getInstance().modid, "parts/" + name));
        template.placeInWorld(level, pos, pos, new StructurePlaceSettings(), random, 4);
    }

    private void placeDwarf(WorldGenLevel level, BlockPos pos) {
        if (CompatConfig.mythic_alfheim.locked) {
            //  level.setBlock(pos, ModBlocks.ancientRunestone.defaultBlockState(), 2);
        } else {
            DwarfBlacksmith entity = new DwarfBlacksmith(ModEntityTypes.dwarfBlacksmith, level.getLevel());
            entity.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            entity.setTamed(false);
            entity.setPersistenceRequired();
            addEntity(level, entity);
        }
    }

    private void addEntity(WorldGenLevel level, Entity entity) {
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
