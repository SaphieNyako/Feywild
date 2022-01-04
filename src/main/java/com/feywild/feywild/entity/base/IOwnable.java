package com.feywild.feywild.entity.base;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.UUID;

public interface IOwnable extends ITameable {
    
    @Nullable
    UUID getOwner();
    void setOwner(UUID uid);
    
    default void setOwner(Player player) {
        setOwner(player.getGameProfile().getId());
    }
    
    default Player getOwningPlayer() {
        UUID id = this.getOwner();
        return id == null ? null : this.getEntityLevel().getPlayerByUUID(id);
    }
    
    // Can't use getLevel because of reobf
    Level getEntityLevel();
    
    @Override
    default boolean isTamed() {
        return this.getOwner() != null;
    }
}
