package com.feywild.feywild.block.entity;

import com.feywild.feywild.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import software.bernie.geckolib3.core.snapshot.DirtyTracker;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DwarvenAnvilEntity extends TileEntity {

    private final ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(()-> itemHandler);

    public DwarvenAnvilEntity(TileEntityType<?> tileEntityType) {

        super(tileEntityType);
    }

    public DwarvenAnvilEntity() {

        this(ModBlocks.DWARVEN_ANVIL_ENTITY.get());
    }

    /* DATA */

    @Override
    public void load(BlockState state, CompoundNBT tag) {

        itemHandler.deserializeNBT(tag.getCompound("inventory"));
        super.load(state, tag);
    }

    @Override
    public CompoundNBT save(CompoundNBT tag) {
        tag.put("inventory", itemHandler.serializeNBT());

        return super.save(tag);
    }

    /* STORE ITEMS */

    private ItemStackHandler createHandler(){

        return new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {

              /*  It tells Minecraft that the data in your TileEntity has changed and so it must be written to disk on the next write cycle
              (note that it doesn't change anything about when data is written).
              If you change data in your TileEntity and not call markDirty that data might be lost when the chunk is unloaded. */

                setChanged(); //markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                /* Can we put in an item in a specific slot */

                return true;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate){
                /* Insert Item into a specific slot */

                return super.insertItem(slot, stack, simulate);

            }
        };

    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {

        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return handler.cast();
        }

        return super.getCapability(capability,side);

    }
}
