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
import net.minecraft.util.NonNullList;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class DwarvenAnvilEntity extends InventoryTile implements ITickableTileEntity {

    private static final int MAX_MANA = 1000;
    private static final int FEY_DUST_MANA_COST = 50;

    private final ItemStackHandler itemHandler = createHandler();
    private final CustomManaStorage manaStorage = createManaStorage();

    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    private final LazyOptional<IManaStorage> manaHandler = LazyOptional.of(() -> manaStorage);

    private boolean canCraft;
    ;

    public DwarvenAnvilEntity(TileEntityType<?> tileEntityType) {
        super(tileEntityType);
    }

    public DwarvenAnvilEntity() {
        this(ModBlocks.DWARVEN_ANVIL_ENTITY.get());
    }

    @Override
    public List<ItemStack> getItems() {
        List<ItemStack> list = NonNullList.withSize(itemHandler.getSlots(), ItemStack.EMPTY);
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            list.set(i, itemHandler.getStackInSlot(i));
        }
        return list;
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

    @Override
    public void tick() {
        if (level != null && level instanceof ServerWorld) {
            if (((ServerWorld) level).getServer().getTickCount() % 20 == 0) {
                //  if there is feydust in slot 0 && // if mana is still below 1000
                if (this.itemHandler.getStackInSlot(0).getItem() == ModItems.FEY_DUST.get() && manaStorage.getManaStored() < MAX_MANA) {
                    // remove a feydust and add 50 mana
                    itemHandler.extractItem(0, 1, false);
                    manaStorage.generateMana(FEY_DUST_MANA_COST);
                    setChanged();
                }
            }
        }
    }



    /* DATA */

    @Override
    public void load(@Nonnull BlockState state, CompoundNBT tag) {
        //    dwarfPresent = tag.getBoolean("dwarf_present");
        itemHandler.deserializeNBT(tag.getCompound("inventory"));
        manaStorage.deserializeNBT(tag.getCompound("mana"));
        super.load(state, tag);
    }

    @Nonnull
    @Override
    public CompoundNBT save(CompoundNBT tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        tag.put("mana", manaStorage.serializeNBT());
        //   tag.putBoolean("dwarf_present", dwarfPresent);
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
                        return stack.getItem() instanceof Schematics; // TODO should use item tag
                    case 7:
                        return false;
                    default:
                        return true;
                }

            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                /* Insert Item into a specific slot */

                // TODO Don't use this here
                // maybe an abstract base class for inventories to handle stuff like this.
                // this could then also handle getting a stack list
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
        } else {
            return super.getCapability(capability, side);
        }
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

        Optional<DwarvenAnvilRecipe> recipe = level == null ? Optional.empty() : level.getRecipeManager().getRecipeFor(ModRecipeTypes.DWARVEN_ANVIL_RECIPE, inv, level);

        recipe.ifPresent(iRecipe -> {
            ItemStack output = iRecipe.getResultItem();

            int manaUsage = iRecipe.getManaUsage();

            //check if there is enough mana

            if ((inv.getItem(7).isEmpty() || inv.getItem(7).getItem() == output.copy().getItem())
                    && inv.getItem(7).getCount() < inv.getItem(7).getMaxStackSize() // && dwarfPresent
                    && manaStorage.getManaStored() > 0 && manaStorage.getManaStored() >= manaUsage) {

                //  level.playSound(null, this.getBlockPos(), SoundEvents.ANVIL_USE, SoundCategory.BLOCKS, 1.0f, 1.0f);

                itemHandler.insertItem(-1, output, false);

                for (int k = 2; k < 7; k++) {
                    itemHandler.extractItem(k, 1, false);
                }

                manaStorage.consumeMana(manaUsage);

            }
            //  clearContent();

        });

    }

    public void checkForViableRecipe() {
        Inventory inv = new Inventory(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inv.setItem(i, itemHandler.getStackInSlot(i));
        }

        Optional<DwarvenAnvilRecipe> recipe = level == null ? Optional.empty() : level.getRecipeManager().getRecipeFor(ModRecipeTypes.DWARVEN_ANVIL_RECIPE, inv, level);

        recipe.ifPresent(iRecipe -> {
            ItemStack output = iRecipe.getResultItem();

            int manaUsage = iRecipe.getManaUsage();

            setCanCraft((inv.getItem(7).isEmpty() || inv.getItem(7).getItem() == output.copy().getItem())
                    && inv.getItem(7).getCount() < inv.getItem(7).getMaxStackSize() //&& dwarfPresent
                    && manaStorage.getManaStored() > 0 && manaStorage.getManaStored() >= manaUsage);
        });

    }

    public boolean getCanCraft() {
        return canCraft;
    }

    public void setCanCraft(boolean canCraft) {
        this.canCraft = canCraft;
    }
/*
    public void setDwarfPresent(boolean dwarfPresent) {
        if (dwarfPresent == false) {
            dwarvesPresent--;
            if (dwarvesPresent <= 0) {
                this.dwarfPresent = false;
            } else this.dwarfPresent = true;
        }

        if (dwarfPresent == true) {
            dwarvesPresent++;
            this.dwarfPresent = true;
        }

    } */

    public int getMana() {
        return manaStorage.getManaStored();
    }
}
