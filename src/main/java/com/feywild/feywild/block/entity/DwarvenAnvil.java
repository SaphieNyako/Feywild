package com.feywild.feywild.block.entity;

import com.feywild.feywild.block.entity.mana.CapabilityMana;
import com.feywild.feywild.block.entity.mana.IManaStorage;
import com.feywild.feywild.block.entity.mana.ManaStorage;
import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.recipes.IDwarvenAnvilRecipe;
import com.feywild.feywild.recipes.ModRecipeTypes;
import com.feywild.feywild.tag.ModItemTags;
import com.feywild.feywild.util.StreamUtil;
import io.github.noeppi_noeppi.libx.crafting.recipe.RecipeHelper;
import io.github.noeppi_noeppi.libx.inventory.BaseItemStackHandler;
import io.github.noeppi_noeppi.libx.inventory.ItemStackHandlerWrapper;
import io.github.noeppi_noeppi.libx.mod.registration.TileEntityBase;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.LazyValue;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DwarvenAnvil extends TileEntityBase implements ITickableTileEntity {

    private static final int MAX_MANA = 1000;
    private static final int FEY_DUST_MANA_COST = 50;

    private final BaseItemStackHandler inventory = new BaseItemStackHandler(8, slot -> {
        this.setChanged();
        this.updateRecipe();
    }, this::isItemValid);
    private final ManaStorage manaStorage = new ManaStorage(MAX_MANA, () -> {
        this.setChanged();
        this.updateRecipe();
    });

    // Top for main inputs, side for fey dust and bottom to extract result.
    private final LazyOptional<IItemHandlerModifiable> itemHandlerGeneric = ItemStackHandlerWrapper.createLazy(() -> this.inventory);
    private final LazyOptional<IItemHandlerModifiable> itemHandlerTop = ItemStackHandlerWrapper.createLazy(() -> this.inventory, slot -> false, (slot, stack) -> slot >= 2 && slot < 7);
    private final LazyOptional<IItemHandlerModifiable> itemHandlerSide = ItemStackHandlerWrapper.createLazy(() -> this.inventory, slot -> false, (slot, stack) -> slot < 2);
    private final LazyOptional<IItemHandlerModifiable> itemHandlerBottom = ItemStackHandlerWrapper.createLazy(() -> this.inventory, slot -> slot == 7, (slot, stack) -> false);
    private final LazyOptional<IManaStorage> manaHandler = LazyOptional.of(() -> manaStorage);

    // As `load` is often called without a level, we need to store that a recipe update
    // is required, so it can be updated next tick.
    private boolean needsUpdate = false;
    // Whenever the inventory or mana changes, this is reset to a new lazy value holding
    // the current result item. It's lazy, so the result item is not computed on every
    // inventory or mana change but also not computed multiple times without a change.
    // Hold a pair of the recipe that is used and the result value.
    private LazyValue<Optional<Pair<ItemStack, IDwarvenAnvilRecipe>>> recipe = new LazyValue<>(Optional::empty);

    public DwarvenAnvil(TileEntityType<?> tileEntityType) {
        super(tileEntityType);
    }
    
    private boolean isItemValid(int slot, ItemStack stack) {
        switch (slot) {
            case 0: return stack.getItem() == ModItems.feyDust;
            case 1: return ModItemTags.SCHEMATICS.contains(stack.getItem());
            case 7: return false;
            default: return this.level == null || RecipeHelper.isItemValidInput(this.level.getRecipeManager(), ModRecipeTypes.DWARVEN_ANVIL, stack);
        }
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        itemHandlerTop.invalidate();
        itemHandlerSide.invalidate();
        itemHandlerBottom.invalidate();
        manaHandler.invalidate();
    }

    @Override
    public void tick() {
        if (level != null && level instanceof ServerWorld) {
            if (needsUpdate) {
                this.updateRecipe();
                needsUpdate = false;
            }
            if (((ServerWorld) level).getServer().getTickCount() % 20 == 0 && manaStorage.getMana() + FEY_DUST_MANA_COST <= manaStorage.getMaxMana()) {
                // Simulate extraction first
                ItemStack extracted = inventory.extractItem(0, 1, true);
                if (!extracted.isEmpty() && extracted.getItem() == ModItems.feyDust) {
                    inventory.extractItem(0, 1, false);
                    manaStorage.receiveMana(FEY_DUST_MANA_COST, false);
                    setChanged();
                }
            }
        }
    }

    @Nonnull
    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        nbt.put("inventory", inventory.serializeNBT());
        nbt.put("mana", manaStorage.serializeNBT());
        return super.save(nbt);
    }

    @Override
    public void load(@Nonnull BlockState state, @Nonnull CompoundNBT nbt) {
        super.load(state, nbt);
        inventory.deserializeNBT(nbt.getCompound("inventory"));
        manaStorage.deserializeNBT(nbt.getCompound("mana"));
        needsUpdate = true;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side == null) return itemHandlerGeneric.cast();
            switch (side) {
                case UP: return itemHandlerTop.cast();
                case DOWN: return itemHandlerBottom.cast();
                default: return itemHandlerSide.cast();
            }
        } else if (capability == CapabilityMana.MANA) {
            return manaHandler.cast();
        } else {
            return super.getCapability(capability, side);
        }
    }

    public void craft() {
        this.recipe.get().ifPresent(pair -> {
            ItemStack result = pair.getLeft();
            IDwarvenAnvilRecipe recipe = pair.getRight();
            // Consume the mana
            this.manaStorage.extractMana(recipe.getMana(), false);
            // Remove one item from all inputs.
            IntStream.range(2, 7).forEach(slot -> this.inventory.extractItem(slot, 1, false));
            // Insert result item into the output slot
            this.inventory.getUnrestricted().insertItem(7, result.copy(), false);
        });
    }

    private void updateRecipe() {
        if (level == null || level.isClientSide) {
            this.recipe = new LazyValue<>(Optional::empty);
        } else {
            this.recipe = new LazyValue<>(() -> {
                ItemStack schematics = inventory.getStackInSlot(1);
                List<ItemStack> inputs = IntStream.range(2, 7).mapToObj(inventory::getStackInSlot).filter(stack -> !stack.isEmpty()).collect(Collectors.toList());
                return level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.DWARVEN_ANVIL).stream()
                        .flatMap(r -> StreamUtil.zipOption(r.getResult(schematics, inputs), r)) // Get a stream of all result items that match the current inputs.
                        .findFirst() // The stream should normally only contain one entry but with conflicting recipes it could contain more, so we only take the first
                        .filter(p -> p.getRight().getMana() <= this.manaStorage.getMana()) // Check that we have enough mana for the recipe
                        .filter(p -> this.inventory.getUnrestricted().insertItem(7, p.getLeft(), true).isEmpty()); // Check that the resulting item stack can completely be inserted into the result slot.
            });
        }
    }

    public boolean canCraft() {
        return this.recipe.get().isPresent();
    }

    public BaseItemStackHandler getInventory() {
        return inventory;
    }
    
    public int getMana() {
        return this.manaStorage.getMana();
    }
    
    public void setMana(int mana) {
        this.manaStorage.setMana(mana);
    }
}
