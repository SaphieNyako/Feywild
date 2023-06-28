package com.feywild.feywild.entity.ability;

import com.feywild.feywild.FeyRegistries;
import com.feywild.feywild.entity.base.Pixie;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryManager;

import javax.annotation.Nullable;
import java.util.Objects;

public interface Ability<T> {
    
    default ResourceLocation id() {
        //noinspection UnstableApiUsage
        IForgeRegistry<Ability<?>> registry = RegistryManager.ACTIVE.getRegistry(FeyRegistries.ABILITIES);
        return Objects.requireNonNull(registry.getKey(this), "Ability not registered");
    }
    
    @Nullable
    T init(Level level, Pixie pixie);
    boolean stillValid(Level level, Pixie pixie, T data);
    void perform(Level level, Pixie pixie, T data);
    
    @Nullable
    default Vec3 target(Level level, Pixie pixie, T data) {
        return null;
    }
    
    static Ability<?> get(@Nullable ResourceLocation id, Ability<?> defaultValue) {
        if (id == null) return defaultValue;
        //noinspection UnstableApiUsage
        IForgeRegistry<Ability<?>> registry = RegistryManager.ACTIVE.getRegistry(FeyRegistries.ABILITIES);
        Ability<?> value = registry.getValue(id);
        if (value == null) return defaultValue;
        return value;
    }
}
