package com.feywild.feywild.block.decoration;

import net.minecraft.world.level.block.Block;
import org.moddingx.libx.base.BlockBase;
import org.moddingx.libx.mod.ModX;

public class MossyBlock extends BlockBase {

    private final Block baseBlock;
    
    public MossyBlock(ModX mod, Block baseBlock, Properties properties) {
        super(mod, properties);
        this.baseBlock = baseBlock;
    }

    public Block getBaseBlock() {
        return baseBlock;
    }
}
