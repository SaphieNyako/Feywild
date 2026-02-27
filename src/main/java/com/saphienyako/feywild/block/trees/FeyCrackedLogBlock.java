package com.saphienyako.feywild.block.trees;

import com.saphienyako.feywild.block.entity.FeyCrackedLogBlockEntity;
import com.saphienyako.feywild.entity.base.TreeEntBase;
import com.saphienyako.feywild.network.ParticleMessage;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class FeyCrackedLogBlock extends FeyFlammableRotatedPillarBlock implements EntityBlock {


    public FeyCrackedLogBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!level.isClientSide) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof FeyCrackedLogBlockEntity feyBlock) {
                TreeEntBase ent = feyBlock.getTreeEnt((ServerLevel) level);
                if (ent != null && ent.isAlive()) {
                    PacketDistributor.sendToPlayersTrackingEntity(
                            ent,
                            new ParticleMessage(
                                    ParticleMessage.Particles.DANDELION_FLUFF,
                                    ent.getOnPos()
                            )
                    );
                    ent.remove(Entity.RemovalReason.DISCARDED);
                }
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos,@Nonnull BlockState state) {
        return new FeyCrackedLogBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level,@Nonnull BlockState state,@Nonnull BlockEntityType<T> type) {
        if (level.isClientSide) return null;

        return (tickLevel, pos, blockState, be) -> {
            if (be instanceof FeyCrackedLogBlockEntity blockEntity && tickLevel instanceof ServerLevel serverLevel) {
                blockEntity.serverTick(serverLevel, blockState, pos);
            }
        };
    }
}
