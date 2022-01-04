package com.feywild.feywild.entity.base;

import net.minecraft.core.BlockPos;

public interface ISummonable {
    
    BlockPos getSummonPos();
    void setSummonPos(BlockPos pos);
}
