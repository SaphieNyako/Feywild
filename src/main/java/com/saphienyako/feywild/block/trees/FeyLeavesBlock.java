package com.saphienyako.feywild.block.trees;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class FeyLeavesBlock extends LeavesBlock {

    //TODO does this work? Maybe seperate Leave classes?
    private static final MapCodec<FeyLeavesBlock> CODEC =
            RecordCodecBuilder.mapCodec(instance -> instance.group(BlockBehaviour.propertiesCodec(),
                    BuiltInRegistries.PARTICLE_TYPE.byNameCodec()
                            .fieldOf("particle")
                            .forGetter(block -> block.particle.get()),
                    Codec.INT.fieldOf("particle_chance")
                            .forGetter(block -> block.particleChance))
                    .apply(instance, (properties, particle, chance) -> new FeyLeavesBlock(properties, () -> (SimpleParticleType) particle, chance)));
    private final Supplier<SimpleParticleType> particle;
    private final int particleChance;
    public FeyLeavesBlock(Properties properties, Supplier<SimpleParticleType> particle, int particleChance) {
        super(0.0F, properties);
        this.particle = particle;
        this.particleChance = particleChance;
    }

    @Override
    public boolean isFlammable(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull Direction direction) {
        return 5;
    }

    @Override
    public int getFireSpreadSpeed(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull Direction direction) {
        return 5;
    }

    @Override
    protected void spawnFallingLeavesParticle(@NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (level.isEmptyBlock(pos.below())) {
            level.addParticle(particle.get(), pos.getX() + random.nextDouble(), pos.getY(), pos.getZ() + random.nextDouble(), 1, -0.1, 0);
        }
    }

    @Override
    public @NotNull MapCodec<? extends LeavesBlock> codec() {
        return CODEC;
    }
}
