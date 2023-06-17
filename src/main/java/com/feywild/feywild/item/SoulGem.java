package com.feywild.feywild.item;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.network.ParticleMessage;
import com.feywild.feywild.util.TooltipHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.moddingx.libx.base.ItemBase;
import org.moddingx.libx.mod.ModX;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import net.minecraft.world.item.Item.Properties;

public class SoulGem extends ItemBase {

    public SoulGem(ModX mod, Properties properties) {
        super(mod, properties);
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        if (level != null) {
            TooltipHelper.addTooltip(tooltip, level, Component.translatable("message.feywild.soul_gem"));
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }

    @Nonnull
    @Override
    public InteractionResult useOn(@Nonnull UseOnContext context) {

        Level level = context.getLevel();
        Player player = context.getPlayer();
        InteractionHand hand = context.getHand();
        BlockPos clickedPos = context.getClickedPos();
        if (player != null && player.getItemInHand(hand).getItem() == this) {
            if (!Objects.requireNonNull(player).isCreative()) {
                player.getItemInHand(hand).shrink(1);
            }
            player.swing(hand, true);
            getEntity(level, clickedPos);
            FeywildMod.getNetwork().sendParticles(level, ParticleMessage.Type.MAGIC_EFFECT, player.getX(), player.getY(), player.getZ());
            return InteractionResult.sidedSuccess(Objects.requireNonNull(player).level.isClientSide);
        }
        return super.useOn(context);
    }

    private void getEntity(Level level, BlockPos pos) {
        Random random = new Random();
        switch (random.nextInt(8)) {
            case 0 -> {
                Skeleton skeleton = new Skeleton(EntityType.SKELETON, level);
                skeleton.setPos(pos.getX(), pos.getY() + 1, pos.getZ());
                level.addFreshEntity(skeleton);
            }
            case 1 -> {
                ZombieVillager zombie = new ZombieVillager(EntityType.ZOMBIE_VILLAGER, level);
                zombie.setPos(pos.getX(), pos.getY() + 1, pos.getZ());
                level.addFreshEntity(zombie);
            }
            case 2 -> {
                Husk husk = new Husk(EntityType.HUSK, level);
                husk.setPos(pos.getX(), pos.getY() + 1, pos.getZ());
                level.addFreshEntity(husk);
            }
            case 3 -> {
                WitherSkeleton skeleton = new WitherSkeleton(EntityType.WITHER_SKELETON, level);
                skeleton.setPos(pos.getX(), pos.getY() + 1, pos.getZ());
                level.addFreshEntity(skeleton);
            }
            case 4 -> {
                Villager villager = new Villager(EntityType.VILLAGER, level);
                villager.setPos(pos.getX(), pos.getY() + 1, pos.getZ());
                level.addFreshEntity(villager);
            }
            case 5 -> {
                Pillager pillager = new Pillager(EntityType.PILLAGER, level);
                pillager.setPos(pos.getX(), pos.getY() + 1, pos.getZ());
                level.addFreshEntity(pillager);
            }
            case 6 -> {
                Vindicator pillager = new Vindicator(EntityType.VINDICATOR, level);
                pillager.setPos(pos.getX(), pos.getY() + 1, pos.getZ());
                level.addFreshEntity(pillager);
            }
            case 7 -> {
                Evoker pillager = new Evoker(EntityType.EVOKER, level);
                pillager.setPos(pos.getX(), pos.getY() + 1, pos.getZ());
                level.addFreshEntity(pillager);
            }

            default -> {
                Zombie zombie = new Zombie(EntityType.ZOMBIE, level);
                zombie.setPos(pos.getX(), pos.getY() + 1, pos.getZ());
                level.addFreshEntity(zombie);
            }

        }
    }
}
