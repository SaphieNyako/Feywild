package com.feywild.feywild.tag;

import com.feywild.feywild.FeywildMod;
import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;

public class ModBlockTags {

    public static final ITag.INamedTag<Block> FEY_LOGS = BlockTags.bind(new ResourceLocation(FeywildMod.getInstance().modid, "fey_logs").toString());

}
