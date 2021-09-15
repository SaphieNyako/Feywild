package com.feywild.feywild.block;

import com.feywild.feywild.block.entity.DisplayGlassEntity;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.BlockTE;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.material.Material;

public class DisplayGlassBlock extends BlockTE<DisplayGlassEntity> {
    public DisplayGlassBlock(ModX mod) {
        super(mod, DisplayGlassEntity.class, AbstractBlock.Properties.of(Material.GLASS).strength(2f).noOcclusion());
    }
}
