package com.feywild.feywild.block.entity;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.block.entity.mana.CapabilityMana;
import com.feywild.feywild.block.entity.mana.CustomManaStorage;
import com.feywild.feywild.block.entity.mana.IManaStorage;
import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.item.Schematics;
import com.feywild.feywild.recipes.DwarvenAnvilRecipe;
import com.feywild.feywild.recipes.ModRecipeTypes;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.Inventory;
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
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class DwarvenAnvilEntity extends InventoryTile implements ITickableTileEntity {

    private final ItemStackHandler itemHandler = createHandler();
    private final CustomManaStorage manaStorage = createManaStorage();

    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    private final LazyOptional<IManaStorage> manaHandler = LazyOptional.of(() -> manaStorage);

    private final int MAX_MANA = 1000; //TODO: Add to Config
    private final int FEY_DUST_MANA_COST = 50; //TODO: Add to Config
    private int tick = 0;

    private boolean dwarfPresent;

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
            updateInventory(-1, true);
            setChanged(); //markDirty(); Might not be necessary here

        }

        if (!itemHandler.getStackInSlot(0).isEmpty()) {

        }

    }



    /* DATA */

    @Override
    public void load(BlockState state, CompoundNBT tag) {

        dwarfPresent = tag.getBoolean("dwarf_present");
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
        tag.putBoolean("dwarf_present", dwarfPresent);

        return super.save(tag);
    }

    /* STORE ITEMS */

    private ItemStackHandler createHandler() {

        return new ItemStackHandler(8) {
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
                    case 1:
                        return stack.getItem() instanceof Schematics;

                    case 7:
                        return stack.isEmpty();

                    default:
                        return true;
                }

            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                /* Insert Item into a specific slot */

                if (slot == -1) {
                    int count = Math.min(this.stacks.get(7).getMaxStackSize(), this.stacks.get(7).getCount() + stack.getCount());
                    stack.setCount(count);
                    this.stacks.set(7, stack.copy());
                    return stack;
                } else {

                    if (!isItemValid(slot, stack)) {
                        return stack;
                    }

                    return super.insertItem(slot, stack, simulate);
                }
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

    @Override
    public void updateInventory(int flags, boolean shouldCraft) {
        if (shouldCraft) {
            craft();
        } else {
            super.updateInventory(flags, false);
        }
    }

    public void craft() {
        Inventory inv = new Inventory(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inv.setItem(i, itemHandler.getStackInSlot(i));
        }

        Optional<DwarvenAnvilRecipe> recipe = level.getRecipeManager().getRecipeFor(ModRecipeTypes.DWARVEN_ANVIL_RECIPE, inv, level);

        recipe.ifPresent(iRecipe -> {
            ItemStack output = iRecipe.getResultItem();

            int manaUsage = iRecipe.getManaUsage();

            if ((inv.getItem(7).isEmpty() || inv.getItem(7).getItem() == output.copy().getItem())
                    && inv.getItem(7).getCount() < inv.getItem(7).getMaxStackSize() && dwarfPresent) {

                level.playSound(null, this.getBlockPos(), SoundEvents.ANVIL_USE, SoundCategory.BLOCKS, 1.0f, 1.0f);

                itemHandler.insertItem(-1, output, false);
                itemHandler.extractItem(2, 1, false);
                itemHandler.extractItem(3, 1, false);
                itemHandler.extractItem(4, 1, false);
                itemHandler.extractItem(5, 1, false);
                itemHandler.extractItem(6, 1, false);

                manaStorage.consumeMana(manaUsage);

            }
            //  clearContent();

        });

    }

    public void setDwarfPresent(boolean dwarfPresent) {
        this.dwarfPresent = dwarfPresent;
    }
}
