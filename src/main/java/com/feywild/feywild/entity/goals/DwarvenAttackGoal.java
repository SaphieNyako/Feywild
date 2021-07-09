package com.feywild.feywild.entity.goals;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.entity.DwarfBlacksmithEntity;
import com.feywild.feywild.entity.util.LightningAttack;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class DwarvenAttackGoal extends Goal {

    // private static final EntityPredicate TARGETING = (new EntityPredicate()).range(8.0D).allowInvulnerable().allowSameTeam().allowUnseeable();
    protected final World worldLevel;
    protected DwarfBlacksmithEntity entity;
    protected LivingEntity targetMonster;
    protected boolean enchantMonstersNearby = false;
    protected int count = 0;
    private Vector3d targetPos;

    public DwarvenAttackGoal(DwarfBlacksmithEntity entity) {
        this.entity = entity;
        this.worldLevel = entity.level;

    }

    @Override
    public void tick() {
        if (entity.getLastHurtByMob() != null) {
            targetMonster = entity.getLastHurtByMob();
            enchantMonstersNearby = true;
        }

        if (enchantMonstersNearby && targetMonster instanceof MonsterEntity) {
            count--;

            if (count == 0) {
                reset();
            } else if (count == 10) {
                attackTarget();

            } else if (count == 80) {
                entity.setState(1);
                entity.getNavigation().moveTo(targetMonster.getX(), targetMonster.getY(), targetMonster.getZ(), 0.5);

            } else if (count <= 80) {
                entity.lookAt(EntityAnchorArgument.Type.EYES, targetMonster.position());

            }
        }
    }

    @Override
    public void start() {
        count = 120;
    }

    protected void attackTarget() {
        if(entity.getRandom().nextDouble() > 0.6){
            targetMonster.hurt(DamageSource.GENERIC, 15.0f);
        }else{
            targetMonster.hurt(DamageSource.GENERIC, 20.0f);
            summonLightning();
        }
    }


    private void summonLightning(){
        worldLevel.setBlock(targetMonster.blockPosition(), ModBlocks.ELECTRIFIED_GROUND.get().defaultBlockState(), 2);
        targetMonster.hurt(DamageSource.LIGHTNING_BOLT, 20);
    }

    protected void reset() {
        enchantMonstersNearby = false;
        entity.setState(0);
        targetMonster = null;
    }

    @Override
    public boolean canContinueToUse() {
        return enchantMonstersNearby;
    }

    @Override
    public boolean canUse() {
        return entity.level.random.nextFloat() < 0.3f;
    }
}