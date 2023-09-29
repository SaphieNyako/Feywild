package com.feywild.feywild.world.gen.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TreeProcessor extends StructureProcessor {
    
    public static final Codec<TreeProcessor> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            WeightedRandomList.codec(WeightedEntry.Wrapper.codec(ForgeRegistries.BLOCKS.getCodec())).fieldOf("log").forGetter(TreeProcessor::getLog),
            WeightedRandomList.codec(WeightedEntry.Wrapper.codec(ForgeRegistries.BLOCKS.getCodec())).fieldOf("wood").forGetter(TreeProcessor::getWood),
            WeightedRandomList.codec(WeightedEntry.Wrapper.codec(ForgeRegistries.BLOCKS.getCodec())).fieldOf("leaves").forGetter(TreeProcessor::getLeaves)
    ).apply(instance, TreeProcessor::new));
    
    public static final StructureProcessorType<TreeProcessor> TYPE = () -> CODEC;
    
    private final WeightedRandomList<WeightedEntry.Wrapper<Block>> log;
    private final WeightedRandomList<WeightedEntry.Wrapper<Block>> wood;
    private final WeightedRandomList<WeightedEntry.Wrapper<Block>> leaves;

    public TreeProcessor(WeightedRandomList<WeightedEntry.Wrapper<Block>> log, WeightedRandomList<WeightedEntry.Wrapper<Block>> wood, WeightedRandomList<WeightedEntry.Wrapper<Block>> leaves) {
        this.log = log;
        this.wood = wood;
        this.leaves = leaves;
    }

    @Nonnull
    @Override
    protected StructureProcessorType<?> getType() {
        return TYPE;
    }

    public WeightedRandomList<WeightedEntry.Wrapper<Block>> getLog() {
        return log;
    }

    public WeightedRandomList<WeightedEntry.Wrapper<Block>> getWood() {
        return wood;
    }

    public WeightedRandomList<WeightedEntry.Wrapper<Block>> getLeaves() {
        return leaves;
    }

    @Nullable
    @Override
    @SuppressWarnings({"deprecation", "unchecked", "rawtypes"})
    public StructureTemplate.StructureBlockInfo processBlock(@Nonnull LevelReader level, @Nonnull BlockPos pos0, @Nonnull BlockPos pos, @Nonnull StructureTemplate.StructureBlockInfo blockInfo, @Nonnull StructureTemplate.StructureBlockInfo relativeBlockInfo, @Nonnull StructurePlaceSettings settings) {
        Block newBlock = null;
        if (relativeBlockInfo.state().getBlock() == Blocks.OAK_LOG) {
            newBlock = this.log.getRandom(settings.getRandom(relativeBlockInfo.pos())).map(WeightedEntry.Wrapper::getData).orElse(null);
        } else if (relativeBlockInfo.state().getBlock() == Blocks.OAK_WOOD) {
            newBlock = this.wood.getRandom(settings.getRandom(relativeBlockInfo.pos())).map(WeightedEntry.Wrapper::getData).orElse(null);
        } else if (relativeBlockInfo.state().is(BlockTags.LEAVES)) {
            newBlock = this.leaves.getRandom(settings.getRandom(relativeBlockInfo.pos())).map(WeightedEntry.Wrapper::getData).orElse(null);
        }
        
        if (newBlock != null) {
            BlockState newState = newBlock.defaultBlockState();
            for (Property<?> prop : relativeBlockInfo.state().getProperties()) {
                if (newState.hasProperty(prop)) {
                    newState = newState.setValue(((Property) prop), relativeBlockInfo.state().getValue(prop));
                }
            }
            return new StructureTemplate.StructureBlockInfo(relativeBlockInfo.pos(), newState, relativeBlockInfo.nbt());
        } else {
            return relativeBlockInfo;
        }
    }
}
