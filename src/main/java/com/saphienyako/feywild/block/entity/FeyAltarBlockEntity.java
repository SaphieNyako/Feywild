package com.saphienyako.feywild.block.entity;

import com.saphienyako.feywild.network.AltarParticleMessage;
import com.saphienyako.feywild.network.FeywildNetwork;
import com.saphienyako.feywild.recipe.FeyAltarRecipe;
import com.saphienyako.feywild.screen.FeyAltarMenu;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FeyAltarBlockEntity extends TileEntity implements INamedContainerProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(6) {

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            if (slot == OUTPUT_SLOT) {
                return stack;
            }
            return super.insertItem(slot, stack, simulate);
        }
    };
    private static final int INPUT_SLOT_00 = 0;
    private static final int INPUT_SLOT_01 = 1;
    private static final int INPUT_SLOT_02 = 2;
    private static final int INPUT_SLOT_03 = 3;
    private static final int INPUT_SLOT_04 = 4;
    private static final int OUTPUT_SLOT = 5;
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    //ContainerData
    protected final IIntArray data;
    private int progress = 0;
    private int maxProgress = 40;


    public FeyAltarBlockEntity() {
        super(ModTileEntities.FEY_ALTAR_BLOCK_ENTITY.get());

        this.data = new IIntArray() {
            @Override
            public int get(int index) {
                switch (index) {
                    case 0:
                        return FeyAltarBlockEntity.this.progress;
                    case 1:
                        return FeyAltarBlockEntity.this.maxProgress;
                    default:
                        return 0;
                }
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0:
                        FeyAltarBlockEntity.this.progress = value;
                        break;
                    case 1:
                        FeyAltarBlockEntity.this.maxProgress = value;
                        break;
                }
            }

            public int getCount() {
                return 2;
            }
        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }
    public ItemStackHandler getInventory() {
        return itemHandler;
    }

    public int getProgress() {return progress;}

    public int getMaxProgress() {return maxProgress;}

    public void drops() {
      Inventory inventory = new Inventory(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        InventoryHelper.dropContents(this.level, this.worldPosition, inventory);
    }

    @Nonnull
    @Override
    public CompoundNBT save(@Nonnull CompoundNBT nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());
        nbt.putInt("progress", this.progress);
        return super.save(nbt);
    }

    @Override
    public void load(@Nonnull BlockState state,@Nonnull CompoundNBT nbt) {
        super.load(state, nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        this.progress = nbt.getInt("progress");
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Nonnull
    @Override
    public ITextComponent getDisplayName() {
        return  new TranslationTextComponent("block.feywild.fey_altar");
    }

    @Nonnull
    @Override
    public Container createMenu(int id,@Nonnull PlayerInventory inventory,@Nonnull PlayerEntity player) {
        return new FeyAltarMenu(id, inventory,this,this.data);
    }

    public void tick(World level, BlockPos pos, BlockState state) {
        if (this.level == null) return;
        if (!this.level.isClientSide) {
            if (hasRecipe()) {
                increaseCraftingProgress();
                setChanged();
                level.sendBlockUpdated(worldPosition, state, state, 2);

                if (hasProgressFinished()) {
                    craftItem();
                    resetProgress();
                    setChanged();
                    level.sendBlockUpdated(worldPosition, state, state, 2);
                }
            } else {
                resetProgress();
                setChanged();
                level.sendBlockUpdated(worldPosition, state, state, 2);
            }
            addParticles();
        }
    }

    private void addParticles() {
        if (this.progress > 0) {
            if (this.progress >= (maxProgress - 1)) {
                //Particles after item has been crafted
                FeywildNetwork.sendParticles(level, AltarParticleMessage.Type.ALTAR_01,this.worldPosition, progress, maxProgress);
            } else {
                List<ItemStack> stacks = new ArrayList<>();
                for (int slot = 0; slot < itemHandler.getSlots(); slot++) {
                    ItemStack stack = itemHandler.getStackInSlot(slot);
                    if (!stack.isEmpty()) stacks.add(stack);
                }
                if (!stacks.isEmpty()) {
                    //Particles moving up while being crafted
                    FeywildNetwork.sendParticles(level, AltarParticleMessage.Type.ALTAR_02,this.worldPosition, progress, maxProgress);
                }
            }
        } else {
            //Particles on Model
            FeywildNetwork.sendParticles(level, AltarParticleMessage.Type.ALTAR_03,this.worldPosition, progress, maxProgress);
        }
    }

    private void resetProgress() {
        progress = 0;
    }

    private boolean hasRecipe() {
        Optional<FeyAltarRecipe> recipe = getCurrentRecipe();

        if(!recipe.isPresent()) {
            return false;
        }
        ItemStack result = recipe.get().getResultItem();

        return canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private Optional<FeyAltarRecipe> getCurrentRecipe() {
        Inventory inventory = new Inventory(this.itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }

        assert this.level != null;
        return this.level.getRecipeManager().getRecipeFor(FeyAltarRecipe.Type.INSTANCE, inventory, level);
    }

    private void craftItem() {
        Optional<FeyAltarRecipe> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().getResultItem();

        this.itemHandler.extractItem(INPUT_SLOT_00, 1, false);
        this.itemHandler.extractItem(INPUT_SLOT_01, 1, false);
        this.itemHandler.extractItem(INPUT_SLOT_02, 1, false);
        this.itemHandler.extractItem(INPUT_SLOT_03, 1, false);
        this.itemHandler.extractItem(INPUT_SLOT_04, 1, false);

        this.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(result.getItem(),
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()));
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT).getItem().equals(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count <= this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }
    private void increaseCraftingProgress() {
        progress++;
    }

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }


    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(this.worldPosition); // render particles outside of block
    }

    @Nonnull
    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = super.getUpdateTag();
        if (this.level != null && !this.level.isClientSide) {
            nbt.put("inventory", itemHandler.serializeNBT());
            nbt.putInt("progress", this.progress);
        }
        return nbt;
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT nbt) {
        super.handleUpdateTag(state, nbt);
        if (this.level != null && this.level.isClientSide) {
            itemHandler.deserializeNBT(nbt.getCompound("inventory"));
            this.progress = nbt.getInt("progress");
        }
    }
}
