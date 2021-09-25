package com.feywild.feywild.block.entity;

import com.feywild.feywild.block.DisplayGlassBlock;
import com.feywild.feywild.config.WorldGenConfig;
import com.feywild.feywild.item.ModItems;
import io.github.noeppi_noeppi.libx.inventory.BaseItemStackHandler;
import io.github.noeppi_noeppi.libx.inventory.ItemStackHandlerWrapper;
import io.github.noeppi_noeppi.libx.mod.registration.TileEntityBase;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DisplayGlass extends TileEntityBase implements ITickableTileEntity {

    private final BaseItemStackHandler inventory;
    private final LazyOptional<IItemHandlerModifiable> itemHandler;

    private int generationCoolDown = 0;
    private int hitCounter = 0;

    public DisplayGlass(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
        this.inventory = new BaseItemStackHandler(1, slot -> {
            this.setChanged();
            this.markDispatchable();
        });
        this.inventory.setDefaultSlotLimit(1);
        this.itemHandler = ItemStackHandlerWrapper.createLazy(() -> this.inventory);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return this.itemHandler.cast();
        } else {
            return super.getCapability(capability, side);
        }
    }

    @Override
    public void tick() {
        if (level != null && !level.isClientSide) {
            if (generationCoolDown == 0 && getInventory().getStackInSlot(0).isEmpty() && getBlockState().getValue(DisplayGlassBlock.CAN_GENERATE)) {
                level.setBlock(worldPosition, getBlockState().setValue(DisplayGlassBlock.BREAKAGE, 0), 3);
                this.clearCache();
                this.inventory.getUnrestricted().insertItem(0, new ItemStack(ModItems.honeycomb), false);
                generationCoolDown = -1;
            } else if (generationCoolDown > 0) {
                generationCoolDown -= 1;
            }
            this.setChanged();
        }
    }

    public void hitGlass() {
        if (level != null) {
            hitCounter += 1;
            level.playSound(null, worldPosition, SoundEvents.GLASS_HIT, SoundCategory.BLOCKS, 1, 1);
            if (hitCounter > 3) {
                if (getBlockState().getValue(DisplayGlassBlock.BREAKAGE) == 3) {
                    generationCoolDown = WorldGenConfig.structures.bee_keep.honey_timer;
                }
                if (getBlockState().getValue(DisplayGlassBlock.BREAKAGE) < 4) {
                    level.setBlock(worldPosition, getBlockState().setValue(DisplayGlassBlock.BREAKAGE, getBlockState().getValue(DisplayGlassBlock.BREAKAGE) + 1), 3);
                    level.playSound(null, worldPosition, SoundEvents.GLASS_BREAK, SoundCategory.BLOCKS, 1, 1);
                    hitCounter = 0;
                    this.clearCache();
                }
            }
            this.setChanged();
        }
    }

    @Nonnull
    @Override
    public CompoundNBT save(@Nonnull CompoundNBT nbt) {
        nbt.put("Inventory", this.inventory.serializeNBT());
        nbt.putInt("GenerationCoolDown", this.generationCoolDown);
        nbt.putInt("Hits", this.hitCounter);
        return super.save(nbt);
    }

    @Override
    public void load(@Nonnull BlockState state, @Nonnull CompoundNBT nbt) {
        super.load(state, nbt);
        this.inventory.deserializeNBT(nbt.getCompound("Inventory"));
        this.generationCoolDown = nbt.getInt("GenerationCoolDown");
        this.hitCounter = nbt.getInt("Hits");
    }

    @Nonnull
    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = super.getUpdateTag();
        if (this.level != null && !this.level.isClientSide) {
            nbt.put("Inventory", this.inventory.serializeNBT());
        }
        return nbt;
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT nbt) {
        super.handleUpdateTag(state, nbt);
        if (this.level != null && this.level.isClientSide) {
            this.inventory.deserializeNBT(nbt.getCompound("Inventory"));
        }
    }

    public BaseItemStackHandler getInventory() {
        return this.inventory;
    }
}
