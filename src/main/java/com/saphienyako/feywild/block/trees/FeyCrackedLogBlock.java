package com.saphienyako.feywild.block.trees;

import com.saphienyako.feywild.block.entity.FeyCrackedLogBlockEntity;
import com.saphienyako.feywild.entity.base.TreeEntBase;
import com.saphienyako.feywild.network.ParticleMessage;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class FeyCrackedLogBlock extends FeyFlammableRotatedPillarBlock implements EntityBlock {


    public FeyCrackedLogBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onBlockStateChange(LevelReader level, BlockPos pos, BlockState oldState, BlockState newState) {

        if (oldState.is(this) && !newState.is(this) && level instanceof ServerLevel serverLevel) {

            BlockEntity be = serverLevel.getBlockEntity(pos);

            if (be instanceof FeyCrackedLogBlockEntity feyBlock) {
                TreeEntBase ent = feyBlock.getTreeEnt(serverLevel);

                if (ent != null && ent.isAlive()) {
                    PacketDistributor.sendToPlayersTrackingEntity(ent, new ParticleMessage(ParticleMessage.Particles.DANDELION_FLUFF, ent.getOnPos()));
                    ent.remove(Entity.RemovalReason.DISCARDED);
                }
            }
        }

        super.onBlockStateChange(level, pos, oldState, newState);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos,@Nonnull BlockState state) {
        return new FeyCrackedLogBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level,@Nonnull BlockState state,@Nonnull BlockEntityType<T> type) {
        if (level.isClientSide()) return null;

        return (tickLevel, pos, blockState, be) -> {
            if (be instanceof FeyCrackedLogBlockEntity blockEntity && tickLevel instanceof ServerLevel serverLevel) {
                blockEntity.serverTick(serverLevel, blockState, pos);
            }
        };
    }
}
