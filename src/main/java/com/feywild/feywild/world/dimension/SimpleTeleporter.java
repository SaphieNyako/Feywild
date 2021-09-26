package com.feywild.feywild.world.dimension;

import net.minecraft.block.PortalInfo;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;

import javax.annotation.Nullable;
import java.util.function.Function;

public class SimpleTeleporter implements ITeleporter {
    
    private final BlockPos targetPos;

    public SimpleTeleporter(BlockPos targetPos) {
        this.targetPos = targetPos;
    }

    @Override
    public boolean isVanilla() {
        return false;
    }

    @Nullable
    @Override
    public PortalInfo getPortalInfo(Entity entity, ServerWorld destWorld, Function<ServerWorld, PortalInfo> defaultPortalInfo) {
        return new PortalInfo(new Vector3d(this.targetPos.getX() + 0.5, this.targetPos.getY(), this.targetPos.getZ() + 0.5), Vector3d.ZERO, entity.yRot, entity.xRot);
    }

    @Override
    public Entity placeEntity(Entity entity, ServerWorld source, ServerWorld target, float yaw, Function<Boolean, Entity> repositionEntity) {
        return repositionEntity.apply(false);
    }
}
