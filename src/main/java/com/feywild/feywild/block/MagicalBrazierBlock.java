package com.feywild.feywild.block;

import com.feywild.feywild.block.entity.MagicalBrazier;
import com.feywild.feywild.block.render.MagicalBrazierRenderer;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.BlockTE;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class MagicalBrazierBlock extends BlockTE<MagicalBrazier> {

    public MagicalBrazierBlock(ModX mod) {
        super(mod, MagicalBrazier.class, AbstractBlock.Properties.of(Material.PISTON).strength(2f).noOcclusion());
        //.lightLevel(blockState -> 8)
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void registerClient(ResourceLocation id, Consumer<Runnable> defer) {
        defer.accept(() -> ClientRegistry.bindTileEntityRenderer(this.getTileType(), MagicalBrazierRenderer::new));
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public BlockRenderType getRenderShape(@Nonnull BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }
}
