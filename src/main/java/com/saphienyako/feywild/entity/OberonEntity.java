package com.saphienyako.feywild.entity;

import com.saphienyako.feywild.entity.base.BossBase;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class OberonEntity extends BossBase {
    public OberonEntity(EntityType<? extends PathfinderMob> entity, Level level) {
        super(entity, level, (ServerBossEvent) (new ServerBossEvent(Component.translatable("entity.feywild.oberon").withStyle(ChatFormatting.GREEN),
                BossEvent.BossBarColor.GREEN, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(false).setCreateWorldFog(true));
    }

    public static AttributeSupplier.Builder getDefaultAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.5)
                .add(Attributes.FLYING_SPEED, 0.5)
                .add(Attributes.MAX_HEALTH, 300)
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.LUCK, 0.2);
    }

    public static boolean canSpawn(EntityType<? extends BossBase> entity, LevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return isBrightEnoughToSpawn(level, pos);
    }

    @Override
    public SoundEvent getSummonSound() {
        return null;
    }

    @Override
    public Component getFeySummonMessage() {
        return null;
    }

    @Override
    public SimpleParticleType getParticle() {
        return null;
    }

    @Override
    public SpriteEntity.SpriteVariant getSpriteVariant() {
        return SpriteEntity.SpriteVariant.SPRING;
    }
}
