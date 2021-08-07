package com.feywild.feywild.data;

import com.feywild.feywild.block.flower.GiantFlowerBlock;
import com.feywild.feywild.block.trees.BaseSaplingBlock;
import com.feywild.feywild.block.trees.FeyLeavesBlock;
import com.feywild.feywild.block.trees.FeyLogBlock;
import com.feywild.feywild.block.trees.FeyWoodBlock;
import com.feywild.feywild.tag.ModBlockTags;
import io.github.noeppi_noeppi.libx.data.provider.BlockTagProviderBase;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockTagProvider extends BlockTagProviderBase {

    public BlockTagProvider(ModX mod, DataGenerator generator, ExistingFileHelper fileHelper) {
        super(mod, generator, fileHelper);
    }

    @Override
    protected void setup() {
        this.tag(BlockTags.LOGS).addTag(ModBlockTags.FEY_LOGS);
        this.tag(BlockTags.LOGS_THAT_BURN).addTag(ModBlockTags.FEY_LOGS);
    }

    @Override
    public void defaultBlockTags(Block block) {
        if (block instanceof FeyLogBlock || block instanceof FeyWoodBlock) {
            this.tag(ModBlockTags.FEY_LOGS).add(block);
        } else if (block instanceof FeyLeavesBlock) {
            this.tag(BlockTags.LEAVES).add(block);
        } else if (block instanceof BaseSaplingBlock) {
            this.tag(BlockTags.SAPLINGS).add(block);
        } else if (block instanceof GiantFlowerBlock) {
            this.tag(BlockTags.TALL_FLOWERS).add(block);
        }
    }
}
