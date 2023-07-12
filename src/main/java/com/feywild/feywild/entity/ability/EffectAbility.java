package com.feywild.feywild.entity.ability;

import com.feywild.feywild.entity.base.Pixie;
import net.minecraft.util.Unit;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class EffectAbility implements Ability<Unit> {
    
    private final List<Supplier<MobEffectInstance>> effects;

    @SafeVarargs
    public EffectAbility(Supplier<MobEffectInstance>... effects) {
        this.effects = List.of(effects);
    }

    @Nullable
    @Override
    public Unit init(Level level, Pixie pixie) {
        return Unit.INSTANCE;
    }

    @Override
    public boolean stillValid(Level level, Pixie pixie, Unit data) {
        return true;
    }

    @Override
    public void perform(Level level, Pixie pixie, Unit data) {
        Player player = pixie.getOwningPlayer();
        if (player != null) {
            for (Supplier<MobEffectInstance> instance : this.effects) {
                player.addEffect(instance.get());
            }
        }
    }

    @Nullable
    @Override
    public Vec3 target(Level level, Pixie pixie, Unit data) {
        Player player = pixie.getOwningPlayer();
        return player == null ? null : player.position();
    }
}
