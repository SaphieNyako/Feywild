package com.feywild.feywild.entity;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModTrees;
import com.feywild.feywild.block.trees.BaseTree;
import com.feywild.feywild.entity.base.TreeEnt;
import com.feywild.feywild.network.ParticleMessage;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class HexenTreeEnt extends TreeEnt {

    public HexenTreeEnt(EntityType<? extends TreeEnt> entityType, Level level) {
        super(entityType, null, level);
    }

    @Override
    protected BaseTree getTree() {
        return ModTrees.hexenTree;
    }

    @Override
    protected void grantReward(Player player) {
        if (random.nextInt(3) < 1) {
            player.addEffect(this.randomEffect());
            FeywildMod.getNetwork().sendParticles(this.level(), ParticleMessage.Type.MAGIC_EFFECT, player.getX(), player.getY(), player.getZ());
        }
    }

    public MobEffectInstance randomEffect() {

        return switch (random.nextInt(29)) {
            case 0 -> new MobEffectInstance(MobEffects.ABSORPTION, 12000);
            case 1 -> new MobEffectInstance(MobEffects.BAD_OMEN, 600);
            case 2 -> new MobEffectInstance(MobEffects.BLINDNESS, 600);
            case 3 -> new MobEffectInstance(MobEffects.CONDUIT_POWER, 12000);
            case 4 -> new MobEffectInstance(MobEffects.CONFUSION, 600);
            case 5 -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 12000);
            case 6 -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 12000);
            case 7 -> new MobEffectInstance(MobEffects.DARKNESS, 600);
            case 8 -> new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 600);
            case 9 -> new MobEffectInstance(MobEffects.DIG_SPEED, 12000);
            case 10 -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 12000);
            case 11 -> new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 600);
            case 12 -> new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 12000);
            case 13 -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 12000);
            case 14 -> new MobEffectInstance(MobEffects.GLOWING, 600);
            case 15 -> new MobEffectInstance(MobEffects.HARM, 600);
            case 16 -> new MobEffectInstance(MobEffects.HEAL, 600);
            case 17 -> new MobEffectInstance(MobEffects.HEALTH_BOOST, 12000);
            case 18 -> new MobEffectInstance(MobEffects.INVISIBILITY, 12000);
            case 19 -> new MobEffectInstance(MobEffects.LUCK, 12000);
            case 20 -> new MobEffectInstance(MobEffects.NIGHT_VISION, 12000);
            case 21 -> new MobEffectInstance(MobEffects.POISON, 600);
            case 22 -> new MobEffectInstance(MobEffects.REGENERATION, 600);
            case 23 -> new MobEffectInstance(MobEffects.SLOW_FALLING, 12000);
            case 24 -> new MobEffectInstance(MobEffects.UNLUCK, 12000);
            case 25 -> new MobEffectInstance(MobEffects.WEAKNESS, 600);
            case 26 -> new MobEffectInstance(MobEffects.WATER_BREATHING, 12000);
            case 27 -> new MobEffectInstance(MobEffects.WITHER, 600);
            default -> new MobEffectInstance(MobEffects.LEVITATION, 600);
        };
    }
}
