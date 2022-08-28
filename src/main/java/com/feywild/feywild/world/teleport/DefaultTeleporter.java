package com.feywild.feywild.world.teleport;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.PlayerRespawnLogic;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Function;

public class DefaultTeleporter implements ITeleporter {

    private static final List<Direction.Axis> HORIZONTAL_AXES = List.of(Direction.Axis.X, Direction.Axis.Z);
    
    @Nullable
    @Override
    public PortalInfo getPortalInfo(Entity entity, ServerLevel dest, Function<ServerLevel, PortalInfo> defaultPortalInfo) {
        if (!(entity.level instanceof ServerLevel level)) return null;
        double scaleFactor = level.dimensionType().coordinateScale() / dest.dimensionType().coordinateScale();
        BlockPos calculatedTarget = dest.getWorldBorder().clampToBounds(entity.getX() * scaleFactor, 0, entity.getZ() * scaleFactor);
        ChunkPos chunkPos = new ChunkPos(calculatedTarget);
        for (int i = 0; i < 5; i++) {
            for (Direction.Axis axis : HORIZONTAL_AXES) {
                BlockPos validSpawn = PlayerRespawnLogic.getSpawnPosInChunk(dest, chunkPos);
                if (validSpawn != null) {
                    return new PortalInfo(new Vec3(validSpawn.getX() + 0.5, validSpawn.getY(), validSpawn.getZ() + 0.5), Vec3.ZERO, entity.getXRot(), entity.getYRot());
                }
                Direction dir = Direction.get(i % 2 == 0 ? Direction.AxisDirection.POSITIVE : Direction.AxisDirection.NEGATIVE, axis);
                chunkPos = new ChunkPos(dir.getStepX() * i, dir.getStepZ() * i);
            }
        }
        // No valid spawn found in spiral, use highest free block at calcuated pos
        BlockPos.MutableBlockPos mpos = calculatedTarget.mutable();
        mpos.setY(dest.getMaxBuildHeight() - 1);
        while (mpos.getY() - 1 > dest.getMinBuildHeight() && dest.getBlockState(mpos).isAir()) {
            mpos.move(Direction.DOWN);
        }
        return new PortalInfo(new Vec3(mpos.getX() + 0.5, mpos.getY() + 1, mpos.getZ() + 0.5), Vec3.ZERO, entity.getXRot(), entity.getYRot());
    }

    @Override
    public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
        return repositionEntity.apply(false);
    }
}
