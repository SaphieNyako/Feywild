package com.saphienyako.feywild.block.entity;

import com.saphienyako.feywild.block.ModBlocks;
import com.saphienyako.feywild.entity.*;
import com.saphienyako.feywild.entity.base.TreeEntBase;
import com.saphienyako.feywild.network.ParticleMessage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class FeyCrackedLogBlockEntity extends BlockEntity {

    private UUID treeEntUUID;
    private int tickCounter = 0; // counts server ticks

    public FeyCrackedLogBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FEY_CRACKED_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("TreeEntUUID")) {
            treeEntUUID = tag.getUUID("TreeEntUUID");
        }
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        if (treeEntUUID != null) {
            tag.putUUID("TreeEntUUID", treeEntUUID);
        }
    }

    public void setTreeEnt(TreeEntBase ent) {
        this.treeEntUUID = ent.getUUID();
        setChanged();
    }

    public void clearTreeEnt() {
        this.treeEntUUID = null;
        setChanged();
    }

    public TreeEntBase getTreeEnt(ServerLevel level) {
        if (treeEntUUID == null) return null;
        Entity e = level.getEntity(treeEntUUID);
        return e instanceof TreeEntBase ? (TreeEntBase) e : null;
    }

    public void serverTick(ServerLevel level, BlockState state, BlockPos pos) {
        if (level.isClientSide) return;


        tickCounter++;
        if (tickCounter < 200) return;
        tickCounter = 0;

        boolean isDay = level.isDay();
        TreeEntBase ent = getTreeEnt(level);

        if (isDay) {
            if (ent == null || !ent.isAlive()) {
                BlockPos spawnPos = findNearbyGrass(level, pos, 10);
                TreeEntBase spawn = getTreeEntVariant(state.getBlock(), level);
                if (spawnPos != null && spawn != null) {
                    spawn.setPos(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5);
                    level.addFreshEntity(spawn);
                    setTreeEnt(spawn);
                    PacketDistributor.sendToPlayersTrackingEntity(
                            spawn,
                            new ParticleMessage(
                                    ParticleMessage.Particles.DANDELION_FLUFF,
                                    spawnPos
                            )
                    );
                }
            }
        } else {
            // Night
            if (ent != null && ent.isAlive()) {
                PacketDistributor.sendToPlayersTrackingEntity(
                        ent,
                        new ParticleMessage(
                                ParticleMessage.Particles.DANDELION_FLUFF,
                                ent.getOnPos()
                        )
                );
                ent.remove(Entity.RemovalReason.DISCARDED);
                clearTreeEnt();
            }
        }
    }

    private BlockPos findNearbyGrass(Level level, BlockPos origin, int radius) {
        int requiredHeight = 4;
        int halfWidth = 1;

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                for (int dy = 0; dy >= -10; dy--) {
                    BlockPos groundPos = origin.offset(dx, dy, dz);
                    BlockState ground = level.getBlockState(groundPos);

                    if (!ground.is(Blocks.GRASS_BLOCK)) continue;

                    boolean canSpawn = true;

                    for (int x = -halfWidth; x <= halfWidth && canSpawn; x++) {
                        for (int z = -halfWidth; z <= halfWidth && canSpawn; z++) {
                            for (int y = 1; y <= requiredHeight; y++) {
                                BlockPos posToCheck = groundPos.offset(x, y, z);
                                if (!level.isEmptyBlock(posToCheck)) {
                                    canSpawn = false;
                                    break;
                                }
                            }
                        }
                    }

                    if (canSpawn) {
                        return groundPos.above();
                    }
                }
            }
        }

        return null;
    }

    private static TreeEntBase getTreeEntVariant(Block block, ServerLevel level) {
        if (block == ModBlocks.AUTUMN_TREE_CRACKED_LOG.value())
            return new AutumnTreeEntEntity(ModEntities.AUTUMN_TREE_ENT.get(), level);
        if (block == ModBlocks.SPRING_TREE_CRACKED_LOG.value())
            return new SpringTreeEntEntity(ModEntities.SPRING_TREE_ENT.get(), level);
        if (block == ModBlocks.SUMMER_TREE_CRACKED_LOG.value())
            return new SummerTreeEntEntity(ModEntities.SUMMER_TREE_ENT.get(), level);
        if (block == ModBlocks.WINTER_TREE_CRACKED_LOG.value())
            return new WinterTreeEntEntity(ModEntities.WINTER_TREE_ENT.get(), level);
        return null;
    }
}
