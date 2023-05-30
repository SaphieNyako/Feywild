package com.feywild.feywild.effects.enchantment;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;

import javax.annotation.Nonnull;
import java.util.Random;

public class FlowerWalkerEnchantment extends Enchantment {

    protected FlowerWalkerEnchantment(Enchantment.Rarity rarity, EquipmentSlot... applicableSlots) {
        super(rarity, EnchantmentCategory.ARMOR_FEET, applicableSlots);
    }

    public static void onEntityMoved(LivingEntity living, Level level, BlockPos pos, int levelConflicting) {
        if (living.isOnGround()) {
            BlockState blockstate = getDecorationBlock();
            float f = (float) Math.min(2, 2 + levelConflicting);
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

            for (BlockPos blockpos : BlockPos.betweenClosed(pos.offset((double) (-f), -1.0D, (double) (-f)), pos.offset((double) f, -1.0D, (double) f))) {
                if (blockpos.closerToCenterThan(living.position(), (double) f)) {
                    blockpos$mutableblockpos.set(blockpos.getX(), blockpos.getY() + 1, blockpos.getZ());
                    BlockState blockstate1 = level.getBlockState(blockpos$mutableblockpos);
                    if (blockstate1.isAir()) {
                        BlockState blockstate2 = level.getBlockState(blockpos.below());

                        if (blockstate2.getMaterial() == Material.DIRT && blockstate.canSurvive(level, blockpos) && level.isUnobstructed(blockstate, blockpos, CollisionContext.empty()) && !net.minecraftforge.event.ForgeEventFactory.onBlockPlace(living, net.minecraftforge.common.util.BlockSnapshot.create(level.dimension(), level, blockpos), net.minecraft.core.Direction.UP)) {
                            level.setBlockAndUpdate(blockpos.above(), blockstate);
                        }
                    }
                }
            }
        }
    }

    private static BlockState getDecorationBlock() {
        Random random = new Random();
        return switch (random.nextInt(20)) {
            case 0 -> Blocks.RED_TULIP.defaultBlockState();
            case 1 -> Blocks.DANDELION.defaultBlockState();
            case 2 -> Blocks.ORANGE_TULIP.defaultBlockState();
            case 3 -> Blocks.BLUE_ORCHID.defaultBlockState();
            case 4 -> Blocks.ALLIUM.defaultBlockState();
            case 5 -> Blocks.AZURE_BLUET.defaultBlockState();
            case 6 -> Blocks.WHITE_TULIP.defaultBlockState();
            case 7 -> Blocks.LILY_OF_THE_VALLEY.defaultBlockState();
            case 8 -> Blocks.GRASS.defaultBlockState();
            default -> Blocks.AIR.defaultBlockState();
        };
    }

    public int getMinCost(int enchantmentLevel) {
        return enchantmentLevel * 10;
    }

    public int getMaxCost(int enchantmentLevel) {
        return this.getMinCost(enchantmentLevel) + 15;
    }

    public boolean isTreasureOnly() {
        return true;
    }

    public int getMaxLevel() {
        return 2;
    }

    public boolean checkCompatibility(@Nonnull Enchantment ench) {
        return super.checkCompatibility(ench) && ench != Enchantments.DEPTH_STRIDER;
    }

}
