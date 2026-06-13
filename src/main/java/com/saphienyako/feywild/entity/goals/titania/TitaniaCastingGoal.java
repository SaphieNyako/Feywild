package com.saphienyako.feywild.entity.goals.titania;

import com.saphienyako.feywild.entity.ModEntities;
import com.saphienyako.feywild.entity.SpriteEntity;
import com.saphienyako.feywild.entity.TitaniaEntity;
import com.saphienyako.feywild.sound.ModSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class TitaniaCastingGoal extends Goal {

    protected final Level level;
    private final TitaniaEntity entity;
    private int ticksLeft = 0;

    public TitaniaCastingGoal(TitaniaEntity entity, Level level) {
        this.entity = entity;
        this.level = level;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return this.level.random.nextFloat() < 0.1f
                && entity.getTarget() != null
                && entity.getTarget().isAlive();
    }

    @Override
    public void start() {
        this.ticksLeft = 32;
        entity.setState(TitaniaEntity.State.CASTING);
    }

    @Override
    public void tick() {

        if (this.ticksLeft > 0) {
            this.ticksLeft--;
            if (this.ticksLeft <= 0) {
                if (this.level instanceof ServerLevel) {
                    LivingEntity target = entity.getTarget();
                    if(target != null) {
                        this.castSpriteProjectiles(target);
                    }
                }
                this.reset();
            } else if (this.ticksLeft == 15) {
                this.spellCasting();
            } else if (this.ticksLeft <= 31) {
                LivingEntity target = entity.getTarget();
                if(target != null) {
                    this.entity.getLookControl().setLookAt(target);
                }
            }
        }
    }

    private void spellCasting() {
        this.entity.setState(TitaniaEntity.State.CASTING);
        this.entity.playSound(ModSounds.PIXIE_SPELL_CASTING_SHORT.get(), 0.7f, 1);
    }

    private void castSpriteProjectiles(LivingEntity target) {

        Vec3 targetPos = target.position();

        for (int i = -1; i <= 1; i++) {

            SpriteEntity sprite = new SpriteEntity(ModEntities.SPRITE.get(), entity.level());
            sprite.moveTo(entity.getX(), entity.getY() + 2, entity.getZ());
           // sprite.moveTo(entity.getX() + (i * 0.6), entity.getY() + 2, entity.getZ() + (i * 0.6));

            SpriteEntity.SpriteVariant variant = SpriteEntity.SpriteVariant.values()[entity.getRandom().nextInt(SpriteEntity.SpriteVariant.values().length)];
            sprite.setVariant(variant);
            sprite.setMode(SpriteEntity.Mode.PROJECTILE);


            Vec3 direction = targetPos.subtract(sprite.position()).normalize();

            direction = direction.add(i * 0.12, 0, i * 0.12).normalize();

            sprite.setDeltaMovement(direction.scale(0.6));

            entity.level().addFreshEntity(sprite);
        }
    }

    protected void reset() {
        this.entity.setState(TitaniaEntity.State.IDLE_FLYING);
        this.ticksLeft = -1;
    }

    @Override
    public boolean canContinueToUse() {
        return entity.getTarget() != null && entity.getTarget().isAlive() && this.ticksLeft > 0;
    }
}
