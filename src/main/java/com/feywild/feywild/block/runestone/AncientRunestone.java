package com.feywild.feywild.block.runestone;

import com.feywild.feywild.entity.DwarfBlacksmithEntity;
import com.feywild.feywild.entity.ModEntityTypes;
import io.github.noeppi_noeppi.libx.mod.registration.TileEntityBase;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;

import javax.annotation.Nonnull;

public class AncientRunestone extends TileEntityBase implements ITickableTileEntity {

    private int time;
    
    public AncientRunestone(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Override
    public void tick() {
        if (this.time > 0) {
            this.time += 1;
            if (this.time >= 80 && this.level != null) {
                if (!this.level.isClientSide) {
                    this.level.setBlock(this.worldPosition, Blocks.AIR.defaultBlockState(), 3);
                    // Spawn a persistent dwarf
                    DwarfBlacksmithEntity entity = new DwarfBlacksmithEntity(ModEntityTypes.dwarfBlacksmith, this.level);
                    entity.setPos(this.worldPosition.getX() + 0.5, this.worldPosition.getY(), this.worldPosition.getZ() + 0.5);
                    entity.setSummonPos(this.worldPosition);
                    entity.setTamed(false);
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
            this.markDispatchable();
            return true;
        } else {
            return false;
        }
    }

    public int time() {
        return time;
    }

    @Override
    public void load(@Nonnull BlockState state, @Nonnull CompoundNBT nbt) {
        super.load(state, nbt);
        this.time = nbt.getInt("Time");
    }

    @Nonnull
    @Override
    public CompoundNBT save(@Nonnull CompoundNBT nbt) {
        nbt.putInt("Time", this.time);
        return super.save(nbt);
    }

    @Nonnull
    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = super.getUpdateTag();
        if (this.level != null && !this.level.isClientSide) {
            nbt.putInt("Time", this.time);
        }
        return nbt;
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT nbt) {
        super.handleUpdateTag(state, nbt);
        if (this.level != null && this.level.isClientSide) {
            this.time = nbt.getInt("Time");
        }
    }
}
