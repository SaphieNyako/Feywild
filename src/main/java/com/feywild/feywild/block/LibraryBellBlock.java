package com.feywild.feywild.block;

import com.feywild.feywild.block.entity.LibraryBell;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.BlockTE;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;

public class LibraryBellBlock extends BlockTE<LibraryBell> {

    public static final VoxelShape SHAPE = box(5.1875, 0, 5.26563, 10.8125, 3.23438, 10.70313);

    public LibraryBellBlock(ModX mod) {
        super(mod, LibraryBell.class, Properties.of(Material.METAL)
                .strength(-1, 3600000).noDrops()
                .noOcclusion()
                .sound(SoundType.STONE));
    }

    @Override
    public void onRemove(@Nonnull BlockState state, World world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean moving) {
        if (!world.isClientSide && world instanceof ServerWorld) {
            LibraryBell tile = this.getTile(world, pos);
            if (tile.getLibrarian() != null) {
                Entity librarian = ((ServerWorld) world).getEntity(tile.getLibrarian());
                if (librarian instanceof VillagerEntity) ((VillagerEntity) librarian).releaseAllPois();
                if (librarian != null) librarian.remove();
            }
            if (tile.getSecurity() != null) {
                Entity security = ((ServerWorld) world).getEntity(tile.getSecurity());
                if (security != null) security.remove();
            }
        }
        super.onRemove(state, world, pos, newState, moving);
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        return SHAPE;
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public ActionResultType use(@Nonnull BlockState state, World world, @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand hand, @Nonnull BlockRayTraceResult trace) {
        if (world.isClientSide) {
            world.playSound(player, pos, SoundEvents.NOTE_BLOCK_BELL, SoundCategory.BLOCKS, 1f, 1.2f);
        } else {
            LibraryBell blockEntity = this.getTile(world, pos);
            if (player.getGameProfile().getId().equals(blockEntity.getPlayer())) {
                blockEntity.setAnnoyance(blockEntity.getAnnoyance() + 1);
            } else {
                blockEntity.setPlayer(player.getGameProfile().getId());
                blockEntity.setAnnoyance(0);
            }

            if (world instanceof ServerWorld) {
                Entity librarian = blockEntity.getLibrarian() != null ? ((ServerWorld) world).getEntity(blockEntity.getLibrarian()) : null;
                Entity security = blockEntity.getSecurity() != null ? ((ServerWorld) world).getEntity(blockEntity.getSecurity()) : null;
                if (blockEntity.getAnnoyance() >= 10 && librarian != null && librarian.isAlive()) {
                    blockEntity.setAnnoyance(0);
                    if (security == null) {
                        IronGolemEntity golem = new IronGolemEntity(EntityType.IRON_GOLEM, world);
                        golem.setPlayerCreated(false);
                        golem.setTarget(player);
                        player.sendMessage(new TranslationTextComponent("message.feywild.bell.angry"), player.getUUID());
                        golem.setPos(librarian.getX(), librarian.getY(), librarian.getZ());
                        world.addFreshEntity(golem);
                        blockEntity.setSecurity(golem.getUUID());
                    } else {
                        security.setPos(librarian.getX(), librarian.getY(), librarian.getZ());
                        if (security instanceof MobEntity) {
                            ((MobEntity) security).setTarget(player);
                        }
                    }
                } else if (blockEntity.getAnnoyance() > 6) {
                    player.sendMessage(new TranslationTextComponent("message.feywild.bell.annoyed"), player.getUUID());
                }

                if (librarian != null && librarian.isAlive()) {
                    if (librarian instanceof VillagerEntity) ((VillagerEntity) librarian).releaseAllPois();
                    librarian.remove();
                }

                VillagerEntity entity = new VillagerEntity(EntityType.VILLAGER, world);
                entity.addTag("feywild_librarian");
                entity.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                for (Direction dir : Direction.values()) {
                    if (dir.getAxis() != Direction.Axis.Y) {
                        BlockPos target = pos.below().relative(dir);
                        if (world.getBlockState(target).isAir()) {
                            entity.setPos(target.getX() + 0.5, target.getY(), target.getZ() + 0.5);
                            break;
                        }
                    }
                }
                world.addFreshEntity(entity);
                blockEntity.setLibrarian(entity.getUUID());
            }
        }
        return ActionResultType.sidedSuccess(world.isClientSide);
    }
}
