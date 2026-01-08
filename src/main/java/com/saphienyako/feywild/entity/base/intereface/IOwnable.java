package com.saphienyako.feywild.entity.base.intereface;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.UUID;

public interface IOwnable extends ITameable {

    @Nullable
    UUID getOwner();

    void setOwner(@Nullable UUID uid);

    default void setOwner(PlayerEntity player) {
        setOwner(player.getGameProfile().getId());
    }

    default PlayerEntity getOwningPlayer() {
        UUID id = this.getOwner();
        if (id == null) return null;

        if (this.getEntityLevel() instanceof World) {
            World level = (World) this.getEntityLevel();
            for (PlayerEntity player : level.players()) {
                if (id.equals(player.getUUID())) {
                    return player;
                }
            }
        }

        return null;
    }

    // Can't use getLevel because of reobf
    World getEntityLevel();

    @Override
    default boolean isTamed() {
        return this.getOwner() != null;
    }

    @Override
    default boolean trySetTamed(boolean tamed) {
        if (!tamed) {
            this.setOwner((UUID) null);
            return true;
        } else {
            return false;
        }
    }
}
