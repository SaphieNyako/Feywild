package com.feywild.feywild.world.gen.structure;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;

import javax.annotation.Nonnull;
import java.util.function.Function;

public class FeywildStructurePoolElement extends SinglePoolElement {

    public static final Codec<FeywildStructurePoolElement> CODEC = RecordCodecBuilder.create((builder) -> builder.group(
                templateCodec(),
                processorsCodec(),
                projectionCodec()
        ).apply(builder, FeywildStructurePoolElement::new));
    
    public FeywildStructurePoolElement(Either<ResourceLocation, StructureTemplate> template, Holder<StructureProcessorList> processors, StructureTemplatePool.Projection projection) {
        super(template, processors, projection);
    }

    @Nonnull
    @Override
    public StructurePoolElementType<?> getType() {
        return FeywildStructureElements.elementType;
    }

    @Override
    public boolean place(@Nonnull StructureTemplateManager templates, @Nonnull WorldGenLevel level, @Nonnull StructureManager structures, @Nonnull ChunkGenerator generator, @Nonnull BlockPos pos, @Nonnull BlockPos processorPos, @Nonnull Rotation rotation, @Nonnull BoundingBox box, @Nonnull RandomSource random, boolean jigsaw) {
        StructureTemplate template = this.template.map(templates::getOrCreate, Function.identity());
        StructurePlaceSettings settings = this.getSettings(rotation, box, jigsaw);
        if (!template.placeInWorld(level, pos, processorPos, settings, random, 18)) {
            return false;
        } else {
            for (StructureTemplate.StructureBlockInfo info : StructureTemplate.processBlockInfos(level, pos, processorPos, settings, this.getDataMarkers(templates, pos, rotation, false), template)) {
                this.handleCustomDataMarker(templates, structures, level, info, info.pos, rotation, random, box);
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
    public void handleCustomDataMarker(StructureTemplateManager templates, StructureManager structures, WorldGenLevel level, StructureTemplate.StructureBlockInfo block, BlockPos pos, Rotation rot, RandomSource random, BoundingBox box) {
        //noinspection ConstantConditions
        String data = block.nbt == null ? "" : block.nbt.getString("metadata");
        // Replace structure block in all cases
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
        FeywildDataMarkers.handle(data, templates, structures, level, block, pos, rot, random, box);
        super.handleDataMarker(level, block, pos, rot, random, box);
    }
}
