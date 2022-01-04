package com.feywild.feywild.block;

import com.feywild.feywild.block.entity.LibraryBell;
import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.quest.task.SpecialTask;
import com.feywild.feywild.quest.util.SpecialTaskAction;
import io.github.noeppi_noeppi.libx.base.tile.BlockBE;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;
import java.util.Random;

public class LibraryBellBlock extends BlockBE<LibraryBell> {

    public static final VoxelShape SHAPE = box(5.1875, 0, 5.26563, 10.8125, 3.23438, 10.70313);

    public LibraryBellBlock(ModX mod) {
        super(mod, LibraryBell.class, Properties.of(Material.METAL)
                .strength(-1, 3600000).noDrops()
                .noOcclusion()
                .randomTicks()
                .noCollission()
                .sound(SoundType.STONE));
    }

    @Override
    public void onRemove(@Nonnull BlockState state, Level level, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean moving) {
        if (!level.isClientSide && level instanceof ServerLevel) {
            LibraryBell tile = this.getBlockEntity(level, pos);
            if (tile.getLibrarian() != null) {
                Entity librarian = ((ServerLevel) level).getEntity(tile.getLibrarian());
                if (librarian instanceof Villager) ((Villager) librarian).releaseAllPois();
                if (librarian != null) librarian.remove(Entity.RemovalReason.DISCARDED);
            }
            if (tile.getSecurity() != null) {
                Entity security = ((ServerLevel) level).getEntity(tile.getSecurity());
                if (security != null) security.remove(Entity.RemovalReason.DISCARDED);
            }
        }
        super.onRemove(state, level, pos, newState, moving);
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter levelIn, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        return SHAPE;
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(@Nonnull BlockState state, Level level, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult trace) {
        if (level.isClientSide) {
            level.playSound(player, pos, SoundEvents.NOTE_BLOCK_BELL, SoundSource.BLOCKS, 1f, 1.2f);
        } else {
            LibraryBell blockEntity = this.getBlockEntity(level, pos);
            if (player.getGameProfile().getId().equals(blockEntity.getPlayer())) {
                blockEntity.setAnnoyance(blockEntity.getAnnoyance() + 1);
            } else {
                blockEntity.setPlayer(player.getGameProfile().getId());
                blockEntity.setAnnoyance(0);
            }

            if (level instanceof ServerLevel) {
                Entity librarian = blockEntity.getLibrarian() != null ? ((ServerLevel) level).getEntity(blockEntity.getLibrarian()) : null;
                Entity security = blockEntity.getSecurity() != null ? ((ServerLevel) level).getEntity(blockEntity.getSecurity()) : null;
                if (blockEntity.getAnnoyance() >= 10 && librarian != null && librarian.isAlive()) {
                    blockEntity.setAnnoyance(0);
                    if (security == null) {
                        IronGolem golem = new IronGolem(EntityType.IRON_GOLEM, level);
                        golem.setPlayerCreated(false);
                        golem.setTarget(player);
                        player.sendMessage(new TranslatableComponent("message.feywild.bell.angry"), player.getUUID());
                        golem.setPos(librarian.getX(), librarian.getY(), librarian.getZ());
                        level.addFreshEntity(golem);
                        blockEntity.setSecurity(golem.getUUID());
                        QuestData.get((ServerPlayer) player).checkComplete(SpecialTask.INSTANCE, SpecialTaskAction.ANNOY_LIBRARIAN);
                    } else {
                        security.setPos(librarian.getX(), librarian.getY(), librarian.getZ());
                        if (security instanceof Mob) {
                            ((Mob) security).setTarget(player);
                        }
                    }
                } else if (blockEntity.getAnnoyance() > 6) {
                    player.sendMessage(new TranslatableComponent("message.feywild.bell.annoyed"), player.getUUID());
                }

                if (librarian != null && librarian.isAlive()) {
                    if (librarian instanceof Villager) ((Villager) librarian).releaseAllPois();
                    librarian.remove(Entity.RemovalReason.DISCARDED);
                }

                Villager entity = new Villager(EntityType.VILLAGER, level);
                entity.addTag("feywild_librarian");
                entity.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                for (Direction dir : Direction.values()) {
                    if (dir.getAxis() != Direction.Axis.Y) {
                        BlockPos target = pos.below().relative(dir);
                        if (level.getBlockState(target).isAir()) {
                            entity.setPos(target.getX() + 0.5, target.getY(), target.getZ() + 0.5);
                            break;
                        }
                    }
                }
                level.addFreshEntity(entity);
                blockEntity.setLibrarian(entity.getUUID());
                QuestData.get((ServerPlayer) player).checkComplete(SpecialTask.INSTANCE, SpecialTaskAction.SUMMON_LIBRARIAN);
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public boolean isRandomlyTicking(@Nonnull BlockState state) {
        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(@Nonnull BlockState state, ServerLevel level, @Nonnull BlockPos pos, @Nonnull Random random) {
        LibraryBell entity = (LibraryBell) level.getBlockEntity(pos);

        if (entity != null && entity.getSecurity() != null) {
            Entity security = level.getEntity(entity.getSecurity());
            if (security != null) {
                entity.setDespawnTimer(entity.getDespawnTimer() + 1);
                if (entity.getDespawnTimer() >= 2) {
                    entity.setDespawnTimer(0);
                    security.remove(Entity.RemovalReason.DISCARDED);
                }
            }
        }
    }
}
