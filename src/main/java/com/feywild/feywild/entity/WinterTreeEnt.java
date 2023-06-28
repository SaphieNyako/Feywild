package com.feywild.feywild.entity;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.trees.BaseTree;
import com.feywild.feywild.entity.base.TreeEnt;
import com.feywild.feywild.network.ParticleMessage;
import com.feywild.feywild.quest.Alignment;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class WinterTreeEnt extends TreeEnt {

    public WinterTreeEnt(EntityType<? extends TreeEnt> entityType, Level level) {
        super(entityType, Alignment.WINTER, level);
    }

    @Override
    protected BaseTree getTree() {
        return null;
    }

    @Override
    protected void grantReward(Player player) {
        if (random.nextInt(5) == 0) {
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 12000));
            FeywildMod.getNetwork().sendParticles(this.level(), ParticleMessage.Type.MAGIC_EFFECT, player.getX(), player.getY(), player.getZ());
        }
    }
}
