package com.feywild.feywild.world.biome;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.common.util.Lazy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.Random;
import java.util.function.Supplier;

public class LoggingSurfaceBuilder<C extends ISurfaceBuilderConfig, S extends SurfaceBuilder<C>> extends SurfaceBuilder<C> {

    /* Helper class so we can see where our biomes spawn*/

    private static final Logger LOGGER = LogManager.getLogger();
    private final Lazy<S> delegatedSurfaceBuilder;
    private boolean logged = false;

    public LoggingSurfaceBuilder(final Supplier<S> delegatedSurfaceBuilder, final Codec<C> codec) {

        super(codec);
        this.delegatedSurfaceBuilder = Lazy.of(delegatedSurfaceBuilder);
    }

    @Override
    public void apply(@Nonnull Random random, @Nonnull IChunk chunkIn, @Nonnull Biome biomeIn, int x, int z, int startHeight, double noise,
                      @Nonnull BlockState defaultBlock, @Nonnull BlockState defaultFluid, int seaLevel, long seed, @Nonnull C config
    ) {

        delegatedSurfaceBuilder.get().apply(random, chunkIn, biomeIn, x, z, startHeight,
                noise, defaultBlock, defaultFluid, seaLevel, seed, config);

        if (!logged) {
            logged = true;
            ChunkPos chunkPos = chunkIn.getPos();
            LOGGER.info("Currently Generated at {} at {}, {}", biomeIn.getRegistryName(),
                    chunkPos.getMinBlockX(), chunkPos.getMinBlockZ());
        }
    }

}
