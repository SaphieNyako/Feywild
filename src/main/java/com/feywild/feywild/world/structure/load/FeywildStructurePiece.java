package com.feywild.feywild.world.structure.load;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.config.CompatConfig;
import com.feywild.feywild.entity.DwarfBlacksmithEntity;
import com.feywild.feywild.entity.ModEntityTypes;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.jigsaw.IJigsawDeserializer;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.SingleJigsawPiece;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.template.*;

import javax.annotation.Nonnull;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

public class FeywildStructurePiece extends SingleJigsawPiece {
    
    public static final ResourceLocation ID = new ResourceLocation(FeywildMod.getInstance().modid, "structure_piece");
    
    public static final Codec<FeywildStructurePiece> CODEC = RecordCodecBuilder.create((builder) -> builder.group(
            templateCodec(),
            processorsCodec(),
            projectionCodec()
    ).apply(builder, FeywildStructurePiece::new));
    
    public static final IJigsawDeserializer<FeywildStructurePiece> TYPE = () -> CODEC;

    protected FeywildStructurePiece(Either<ResourceLocation, Template> template, Supplier<StructureProcessorList> processors, JigsawPattern.PlacementBehaviour projection) {
        super(template, processors, projection);
    }
    
    @Override
    public boolean place(@Nonnull TemplateManager templates, @Nonnull ISeedReader world, @Nonnull StructureManager structures, @Nonnull ChunkGenerator generator, @Nonnull BlockPos fromPos, @Nonnull BlockPos toPos, @Nonnull Rotation rot, @Nonnull MutableBoundingBox box, @Nonnull Random random, boolean jigsaw) {
        Template template = this.template.map(templates::getOrCreate, Function.identity());
        PlacementSettings settings = this.getSettings(rot, box, jigsaw);
        if (!template.placeInWorld(world, fromPos, toPos, settings, random, 18)) {
            return false;
        } else {
            for(Template.BlockInfo info : Template.processBlockInfos(world, fromPos, toPos, settings, this.getDataMarkers(templates, fromPos, rot, false), template)) {
                this.handleCustomDataMarker(templates, structures, world, info, info.pos, rot, random, box);
            }
            return true;
        }
    }

    @Nonnull
    @Override
    protected PlacementSettings getSettings(@Nonnull Rotation rot, @Nonnull MutableBoundingBox box, boolean jigsaw) {
        PlacementSettings settings = super.getSettings(rot, box, jigsaw);
        // Don't ignore structure blocks
        settings.popProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK);
        return settings;
    }

    // Same as handleDataMarker but will get the template manager
    public void handleCustomDataMarker(TemplateManager templates, StructureManager structures, ISeedReader world, Template.BlockInfo block, BlockPos pos, Rotation rot, Random random, MutableBoundingBox box) {
        String data = block.nbt == null ? "" : block.nbt.getString("metadata");
        // Replace structure block in all cases
        world.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
        if (data.equals("Waystone") && CompatConfig.waystones) {
            placePiece(templates, world, "waystone", pos, random);
        } else if (data.equals("Dwarf")) {
            placeDwarf(world, pos);
        }
        super.handleDataMarker(world, block, pos, rot, random, box);
    }
    
    @Nonnull
    @Override
    public IJigsawDeserializer<?> getType() {
        return TYPE;
    }
    
    private void placePiece(TemplateManager templates, ISeedReader world, String name, BlockPos pos, Random random) {
        Template template = templates.getOrCreate(new ResourceLocation(FeywildMod.getInstance().modid, "parts/" + name));
        template.placeInWorld(world, pos, new PlacementSettings(), random);
    }
    
    private void placeDwarf(ISeedReader world, BlockPos pos) {
        if (CompatConfig.mythic_alfheim.locked) {
            world.setBlock(pos, ModBlocks.ancientRunestone.defaultBlockState(), 2);
        } else {
            DwarfBlacksmithEntity entity = new DwarfBlacksmithEntity(ModEntityTypes.dwarfBlacksmith, world.getLevel());
            entity.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            entity.setTamed(false);
            world.addFreshEntity(entity);
        }
    }
}
