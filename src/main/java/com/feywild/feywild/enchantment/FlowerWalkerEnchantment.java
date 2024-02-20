package com.feywild.feywild.enchantment;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;

import javax.annotation.Nonnull;
import java.util.Random;

public class FlowerWalkerEnchantment extends Enchantment {

    protected FlowerWalkerEnchantment(Enchantment.Rarity rarity, EquipmentSlot... applicableSlots) {
        super(rarity, EnchantmentCategory.ARMOR_FEET, applicableSlots);
    }

    @Override
    public int getMinCost(int enchantmentLevel) {
        return enchantmentLevel * 10;
    }

    @Override
    public int getMaxCost(int enchantmentLevel) {
        return this.getMinCost(enchantmentLevel) + 15;
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public boolean checkCompatibility(@Nonnull Enchantment ench) {
        return super.checkCompatibility(ench) && ench != Enchantments.DEPTH_STRIDER;
    }

    public static void onEntityMoved(LivingEntity living, Level level, BlockPos basePos, int enchantmentLevel) {
        if (living.onGround()) {
            BlockState flower = getDecorationBlock();
            int radius = Math.min(2, 2 + enchantmentLevel);
            BlockPos.MutableBlockPos mpos = new BlockPos.MutableBlockPos();
            for (BlockPos pos : BlockPos.betweenClosed(basePos.offset(-radius, -1, -radius), basePos.offset(radius, -1, radius))) {
                if (pos.closerToCenterThan(living.position(), radius)) {
                    mpos.set(pos.getX(), pos.getY() + 1, pos.getZ());
                    BlockState current = level.getBlockState(mpos);
                    if (current.isAir()) {
                        current = level.getBlockState(pos.below());
                        if (current.is(BlockTags.DIRT) && flower.canSurvive(level, pos) && level.isUnobstructed(flower, pos, CollisionContext.empty())) {
                            level.setBlockAndUpdate(pos.above(), flower);
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
}
