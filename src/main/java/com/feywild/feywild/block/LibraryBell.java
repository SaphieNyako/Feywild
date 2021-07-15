package com.feywild.feywild.block;

import com.feywild.feywild.block.entity.LibraryBellEntity;
import com.feywild.feywild.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class LibraryBell extends Block {

    public LibraryBell() {
        super(Properties.of(Material.METAL)
                .strength(99999999f, 99999999f).noDrops()
                .noCollission()
                .sound(SoundType.STONE));

    }


    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new LibraryBellEntity();
    }

    @Override
    public void onRemove(@Nonnull BlockState p_196243_1_, World world, @Nonnull BlockPos pos, @Nonnull BlockState p_196243_4_, boolean p_196243_5_) {
    if(!world.isClientSide) {
        LibraryBellEntity blockEntity = (LibraryBellEntity) world.getBlockEntity(pos);
        assert blockEntity != null;
        if (blockEntity.getLibrarian() != null && blockEntity.getLibrarian().isAlive()) {
            blockEntity.getLibrarian().setPos(blockEntity.getLibrarian().getX(), blockEntity.getLibrarian().getY() - 4, blockEntity.getLibrarian().getZ());
            blockEntity.getLibrarian().kill();
        }

        if (blockEntity.getSecurity() != null && blockEntity.getSecurity().isAlive())
            blockEntity.getSecurity().remove();
    }
        super.onRemove(p_196243_1_, world, pos, p_196243_4_, p_196243_5_);
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        return VoxelShapes.box(0.35, 0.01, 0.34, 0.65, 0.25, 0.65);

    }

    @Nonnull
    @Override
    public ActionResultType use(@Nonnull BlockState state, World world, @Nonnull BlockPos pos, @Nonnull PlayerEntity playerEntity, @Nonnull Hand hand, @Nonnull BlockRayTraceResult trace) {
        if (world.isClientSide) {
            world.playSound(playerEntity, pos, SoundEvents.NOTE_BLOCK_BELL, SoundCategory.BLOCKS, 1f, 1.2f);
        } else {

            LibraryBellEntity blockEntity = (LibraryBellEntity) world.getBlockEntity(pos);
            assert blockEntity != null;

            if (playerEntity == blockEntity.getPlayerEntity()) {
                blockEntity.setAnnoyance(blockEntity.getAnnoyance() + 1);
            } else {
                blockEntity.setPlayerEntity(playerEntity);
                blockEntity.setAnnoyance(0);
            }

            // Only display annoyed message and call security if the librarian is alive
            // because it can get killed and if then called again might instantly be annoyed and call security
            if (blockEntity.getLibrarian() != null && blockEntity.getLibrarian().isAlive()) {
                if (blockEntity.getAnnoyance() >= 10) {
                    blockEntity.setAnnoyance(0);

                    if (blockEntity.getSecurity() == null || !blockEntity.getSecurity().isAlive()) {
                        IronGolemEntity iron = new IronGolemEntity(EntityType.IRON_GOLEM, world);
                        iron.setPlayerCreated(false);
                        iron.setTarget(playerEntity);
                        playerEntity.sendMessage(new TranslationTextComponent("message.feywild.bell.angry"), playerEntity.getUUID());
                        iron.setPos(pos.getX(), pos.getY() - 1, pos.getZ());
                        world.addFreshEntity(iron);
                        blockEntity.setSecurity(iron);
                        ModUtil.killOnExit.put(iron,playerEntity);
                    } else {
                        blockEntity.getSecurity().setPos(pos.getX(), pos.getY() - 1, pos.getZ());
                        if (blockEntity.getSecurity() instanceof MobEntity) {
                            ((MobEntity) blockEntity.getSecurity()).setTarget(playerEntity);
                        }
                    }

                } else if (blockEntity.getAnnoyance() > 6) {
                    playerEntity.sendMessage(new TranslationTextComponent("message.feywild.bell.annoyed"), playerEntity.getUUID());
                }

                ModUtil.killOnExit.remove((LivingEntity) blockEntity.getLibrarian(),playerEntity);
                blockEntity.getLibrarian().setPos(blockEntity.getLibrarian() .getX(), blockEntity.getLibrarian().getY() - 4, blockEntity.getLibrarian().getZ());
                blockEntity.getLibrarian().remove(); // .kill()

            }

            VillagerEntity entity = new VillagerEntity(EntityType.VILLAGER, world);
            entity.addTag("spawn_librarian");
            // Coordinates here need fixing but it would probably be better to solve this as a loop
            // TODO change when doing the big refactor
            if (world.getBlockState(pos.below().north()).isAir()) {
                entity.setPos(pos.getX() + 0.5, pos.getY() - 1, pos.getZ() - 1);
            } else if (world.getBlockState(pos.below().south()).isAir()) {
                entity.setPos(pos.getX() + 0.5, pos.getY() - 1, pos.getZ() + 2);
            } else if (world.getBlockState(pos.below().east()).isAir()) {
                entity.setPos(pos.getX() + 2, pos.getY() - 1, pos.getZ() + 0.5);
            } else if (world.getBlockState(pos.below().west()).isAir()) {
                entity.setPos(pos.getX() - 1, pos.getY() - 1, pos.getZ() + 0.5);
            } else {
                // If the bell is obstructed just spawn the librarian inside the bell
                // This is not perfect but better that not setting a osition and thus spawning it at 0 0 0 
                entity.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            }
            world.addFreshEntity(entity);
            blockEntity.setLibrarian(entity);
            ModUtil.killOnExit.put(entity,playerEntity);
        }
        return ActionResultType.SUCCESS;

    }
}
