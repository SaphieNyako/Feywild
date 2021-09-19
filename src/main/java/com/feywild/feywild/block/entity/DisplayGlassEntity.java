package com.feywild.feywild.block.entity;

import com.feywild.feywild.FeywildMod;
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
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DisplayGlassEntity extends TileEntityBase implements ITickableTileEntity {

    private final BaseItemStackHandler inv;
    private final LazyOptional<IItemHandlerModifiable> itemHandler;
    private int cooldown = 0, hits =0;
    private boolean canTick = false;

    public DisplayGlassEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
        this.inv = new BaseItemStackHandler(1, slot -> {
            this.setChanged();
            this.markDispatchable();
        });
        this.inv.setDefaultSlotLimit(1);
        this.itemHandler = ItemStackHandlerWrapper.createLazy(() -> this.inv);
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

    @Nonnull
    @Override
    public CompoundNBT save(@Nonnull CompoundNBT nbt) {
        nbt.put("inventory", this.inv.serializeNBT());
        nbt.putInt("cooldown", this.cooldown);
        nbt.putInt("hits", this.hits);
        nbt.putBoolean("tick",this.canTick);
        return super.save(nbt);
    }

    @Override
    public void load(@Nonnull BlockState state, @Nonnull CompoundNBT nbt) {
        super.load(state, nbt);
        this.inv.deserializeNBT(nbt.getCompound("inventory"));
        this.cooldown = nbt.getInt("cooldown");
        this.hits = nbt.getInt("hits");
        this.canTick = nbt.getBoolean("tick");
    }

    @Nonnull
    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = super.getUpdateTag();
        if (this.level != null && !this.level.isClientSide) {
            nbt.put("inventory", this.inv.serializeNBT());
        }
        return nbt;
    }

    public void hitGlass(World world){
        hits++;
        world.playSound(null,worldPosition, SoundEvents.GLASS_HIT, SoundCategory.BLOCKS,1,1);
        if(hits > 3 && world.getBlockState(worldPosition).getBlock() instanceof DisplayGlassBlock){
            if(world.getBlockState(worldPosition).getValue(DisplayGlassBlock.STATE) == 3)
                canTick = true;
            if(world.getBlockState(worldPosition).getValue(DisplayGlassBlock.STATE) < 4) {
                world.setBlock(worldPosition, world.getBlockState(worldPosition).setValue(DisplayGlassBlock.STATE, world.getBlockState(worldPosition).getValue(DisplayGlassBlock.STATE) + 1), 2);
                world.playSound(null, worldPosition, SoundEvents.GLASS_BREAK, SoundCategory.BLOCKS, 1, 1);
                hits = 0;
            }
        }
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT nbt) {
        super.handleUpdateTag(state, nbt);
        if (this.level != null && this.level.isClientSide) {
            this.inv.deserializeNBT(nbt.getCompound("inventory"));
        }
    }

    public BaseItemStackHandler getInventory() {
        return this.inv;
    }

    @Override
    public void tick() {
        if(!level.isClientSide){
            if(cooldown <= 0 && getInventory().getStackInSlot(0).isEmpty() && level.getBlockState(worldPosition).getBlock() instanceof DisplayGlassBlock && level.getBlockState(worldPosition).getValue(DisplayGlassBlock.GENERATOR) ){
                level.setBlock(worldPosition, level.getBlockState(worldPosition).setValue(DisplayGlassBlock.STATE, 0), 2);
                getInventory().insertItem(0, new ItemStack(ModItems.honeycomb).copy(),false);
                cooldown = WorldGenConfig.structures.bee_keep.honey_timer;
                canTick = false;
            }else if(canTick)
                cooldown--;
        }
    }
}
