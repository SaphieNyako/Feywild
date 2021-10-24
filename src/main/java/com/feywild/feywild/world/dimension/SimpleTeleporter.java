package com.feywild.feywild.world.dimension;

import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.server.level.ServerLevel;
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
    public PortalInfo getPortalInfo(Entity entity, ServerLevel destLevel, Function<ServerLevel, PortalInfo> defaultPortalInfo) {
        return new PortalInfo(new Vec3(this.targetPos.getX() + 0.5, this.targetPos.getY(), this.targetPos.getZ() + 0.5), Vec3.ZERO, entity.getYRot(), entity.getXRot());
    }

    @Override
    public Entity placeEntity(Entity entity, ServerLevel source, ServerLevel target, float yRot, Function<Boolean, Entity> repositionEntity) {
        return repositionEntity.apply(false);
    }
}
