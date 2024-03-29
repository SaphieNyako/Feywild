package com.feywild.feywild.block.entity;

import com.feywild.feywild.entity.DwarfBlacksmith;
import com.feywild.feywild.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.moddingx.libx.base.tile.BlockEntityBase;
import org.moddingx.libx.base.tile.TickingBlock;

import javax.annotation.Nonnull;

public class AncientRunestone extends BlockEntityBase implements TickingBlock {

    private int time;

    public AncientRunestone(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void tick() {
        if (this.time > 0) {
            this.time += 1;
            if (this.time >= 80 && this.level != null) {
                if (!this.level.isClientSide) {
                    this.level.setBlock(this.worldPosition, Blocks.AIR.defaultBlockState(), 3);
                    // Spawn a persistent dwarf
                    DwarfBlacksmith entity = new DwarfBlacksmith(ModEntities.dwarfBlacksmith, this.level);
                    entity.setPos(this.worldPosition.getX() + 0.5, this.worldPosition.getY(), this.worldPosition.getZ() + 0.5);
                    entity.setSummonPos(this.worldPosition);
                    entity.trySetTamed(false);
                    entity.setPersistenceRequired();
                    this.level.addFreshEntity(entity);
                } else {
                    this.level.addParticle(ParticleTypes.FLASH, this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 0.55, this.worldPosition.getZ() + 0.5, 0, 0, 0);
                }
            } else if (level != null && level.isClientSide) {
                for (int i = 0; i < 4; i++) {
                    this.level.addParticle(ParticleTypes.PORTAL, this.worldPosition.getX() + this.level.random.nextDouble(), this.worldPosition.getY() + 0.2 + this.level.random.nextDouble(), this.worldPosition.getZ() + this.level.random.nextDouble(), 0, 0, 0);
                }
            }
        }
    }

    public boolean start() {
        if (this.level != null && !this.level.isClientSide && this.time <= 0) {
            this.time = 1;
            this.setDispatchable();
            return true;
        } else {
            return false;
        }
    }

    public int time() {
        return time;
    }

    @Override
    public void load(@Nonnull CompoundTag nbt) {
        super.load(nbt);
        this.time = nbt.getInt("Time");
    }

    @Override
    public void saveAdditional(@Nonnull CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putInt("Time", this.time);
    }

    @Nonnull
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = super.getUpdateTag();
        if (this.level != null && !this.level.isClientSide) {
            nbt.putInt("Time", this.time);
        }
        return nbt;
    }

    @Override
    public void handleUpdateTag(CompoundTag nbt) {
        super.handleUpdateTag(nbt);
        if (this.level != null && this.level.isClientSide) {
            this.time = nbt.getInt("Time");
        }
    }
}
