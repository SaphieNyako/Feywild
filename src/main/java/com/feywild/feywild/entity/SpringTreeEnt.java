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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class SpringTreeEnt extends TreeEnt {

    public SpringTreeEnt(EntityType<? extends TreeEnt> entityType, Level level) {
        super(entityType, Alignment.SPRING, level);
    }

    @Override
    public BaseTree getTree() {
        return null;
    }

    @Override
    protected ItemStack getRewardItem() {
        return switch (this.random.nextInt(10)) {
            case 0 -> new ItemStack(Items.RED_TULIP);
            case 1 -> new ItemStack(Items.DANDELION);
            case 2 -> new ItemStack(Items.ORANGE_TULIP);
            case 3 -> new ItemStack(Items.BLUE_ORCHID);
            case 4 -> new ItemStack(Items.ALLIUM);
            case 5 -> new ItemStack(Items.AZURE_BLUET);
            case 6 -> new ItemStack(Items.WHITE_TULIP);
            case 7 -> new ItemStack(Items.LILY_OF_THE_VALLEY);
            case 8 -> new ItemStack(Items.POPPY);
            default -> new ItemStack(Items.PINK_TULIP);
        };
    }

    @Override
    protected void grantReward(Player player) {
        if (random.nextInt(5) == 1) {
            player.addEffect(new MobEffectInstance(MobEffects.LUCK, 12000));
            FeywildMod.getNetwork().sendParticles(this.level(), ParticleMessage.Type.MAGIC_EFFECT, player.getX(), player.getY(), player.getZ());
        }
    }
}
