package com.feywild.feywild.effects;

import com.feywild.feywild.particles.ModParticles;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class WindWalkEffect extends Effect {
    protected WindWalkEffect() {
        super(EffectType.BENEFICIAL, 0x994C00);
    }

    @Override
    public boolean isDurationEffectTick(int p_76397_1_, int p_76397_2_) {
        return true;
    }

    @Override
    public void applyEffectTick(LivingEntity p_76394_1_, int p_76394_2_) {
        super.applyEffectTick(p_76394_1_, p_76394_2_);
        World world = p_76394_1_.level;
        if(!world.isClientSide) {
            AxisAlignedBB box = new AxisAlignedBB(p_76394_1_.blockPosition()).inflate(p_76394_2_);
            world.getEntities(null, box).forEach(entity -> {
                if (entity instanceof MonsterEntity || entity instanceof ProjectileEntity) {
                    entity.setDeltaMovement((entity.getX() - p_76394_1_.getX()) / 10, (entity.getY() - p_76394_1_.getY()) / 10, (entity.getZ() - p_76394_1_.getZ()) / 10);
                }
            });
        }else{
            world.addParticle(ModParticles.LEAF_PARTICLE.get(),p_76394_1_.getRandom().nextDouble() * 1.5 + p_76394_1_.getX() -1,p_76394_1_.getRandom().nextDouble() * 2 + p_76394_1_.getY() + 2,p_76394_1_.getRandom().nextDouble() * 1.5 + p_76394_1_.getZ() - 1,0,0,0);
        }
    }
}
