package com.feywild.feywild.world.gen.tree;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class LeafSkipper extends StructureProcessor {

    public static final LeafSkipper INSTANCE = new LeafSkipper();
    public static final Codec<LeafSkipper> CODEC = Codec.unit(INSTANCE);
    public static final StructureProcessorType<LeafSkipper> TYPE = () -> CODEC;
    
    private LeafSkipper() {
        
    }

    @Nonnull
    @Override
    protected StructureProcessorType<?> getType() {
        return TYPE;
    }

    @Nullable
    @Override
    @SuppressWarnings("deprecation")
    public StructureTemplate.StructureBlockInfo processBlock(@Nonnull LevelReader level, @Nonnull BlockPos pos0, @Nonnull BlockPos pos, @Nonnull StructureTemplate.StructureBlockInfo blockInfo, @Nonnull StructureTemplate.StructureBlockInfo relativeBlockInfo, @Nonnull StructurePlaceSettings settings) {
        if (relativeBlockInfo.state().is(BlockTags.LEAVES)) {
            BlockState old = level.getBlockState(relativeBlockInfo.pos());
            if (old.getBlock() != relativeBlockInfo.state().getBlock() && !old.isAir() && !old.is(BlockTags.REPLACEABLE)) {
                return null;
            }
        }
        return relativeBlockInfo;
    }
}
