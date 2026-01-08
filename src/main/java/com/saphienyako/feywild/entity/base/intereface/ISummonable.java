package com.saphienyako.feywild.entity.base.intereface;

import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

public interface ISummonable {

    @Nullable
    BlockPos getSummonPos();

    void setSummonPos(@Nullable BlockPos pos);
}
