package com.feywild.feywild.block.entity;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.block.entity.mana.CapabilityMana;
import com.feywild.feywild.block.entity.mana.CustomManaStorage;
import com.feywild.feywild.block.entity.mana.IManaStorage;
import com.feywild.feywild.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DwarvenAnvilEntity extends TileEntity implements ITickableTileEntity {

    private final ItemStackHandler itemHandler = createHandler();
    private final CustomManaStorage manaStorage = createManaStorage();

    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    private final LazyOptional<IManaStorage> manaHandler = LazyOptional.of(() -> manaStorage);

    private final int MAX_MANA = 1000; //TODO: Add to Config
    private final int FEY_DUST_MANA_COST = 50; //TODO: Add to Config
    private int tick = 0;

    public DwarvenAnvilEntity(TileEntityType<?> tileEntityType) {

        super(tileEntityType);
    }

    public DwarvenAnvilEntity() {

        this(ModBlocks.DWARVEN_ANVIL_ENTITY.get());
    }

    private CustomManaStorage createManaStorage() {
        return new CustomManaStorage(1000, 0) {
            @Override
            protected void onManaChanged() {
                setChanged(); //markDirty();
            }
        };
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        handler.invalidate();
        manaHandler.invalidate();
    }

    public void tick() {

        if (level.isClientSide) return;

        tick++;
        //check every 20 ticks
        if (tick == 20) { //TODO: Add to Config
            //  if there is feydust in slot 0 && // if mana is still below 1000
            if (this.itemHandler.getStackInSlot(0).getItem() == ModItems.FEY_DUST.get() && manaStorage.getManaStored() < MAX_MANA) {
                // remove a feydust and add 50 mana
                itemHandler.extractItem(0, 1, false);
                manaStorage.generateMana(FEY_DUST_MANA_COST);

                //reset tick
                // tick = 0;
            }

            tick = 0;
            setChanged(); //markDirty(); Might not be necessary here
        }

    }

    /* DATA */

    @Override
    public void load(BlockState state, CompoundNBT tag) {

        itemHandler.deserializeNBT(tag.getCompound("inventory"));
        manaStorage.deserializeNBT(tag.getCompound("mana"));
        tick = tag.getInt("counter");
        super.load(state, tag);
    }

    @Override
    public CompoundNBT save(CompoundNBT tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        tag.put("mana", manaStorage.serializeNBT());
        tag.putInt("counter", tick);
        return super.save(tag);
    }

    /* STORE ITEMS */

    private ItemStackHandler createHandler() {

        return new ItemStackHandler(7) {
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

                switch (slot) {

                    case 0:
                        return stack.getItem() == ModItems.FEY_DUST.get();
                    default:
                        return true;
                }

            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                /* Insert Item into a specific slot */

                if (!isItemValid(slot, stack)) {
                    return stack;
                }

                return super.insertItem(slot, stack, simulate);

            }
        };

    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {

        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        } else if (capability == CapabilityMana.MANA) {
            return manaHandler.cast();
        }

        return super.getCapability(capability, side);

    }

}
