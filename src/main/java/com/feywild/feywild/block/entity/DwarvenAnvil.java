package com.feywild.feywild.block.entity;

import com.feywild.feywild.block.entity.mana.CapabilityMana;
import com.feywild.feywild.block.entity.mana.IManaStorage;
import com.feywild.feywild.block.entity.mana.ManaStorage;
import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.recipes.IDwarvenAnvilRecipe;
import com.feywild.feywild.recipes.ModRecipeTypes;
import com.feywild.feywild.tag.ModItemTags;
import com.feywild.feywild.util.StreamUtil;
import io.github.noeppi_noeppi.libx.base.tile.BlockEntityBase;
import io.github.noeppi_noeppi.libx.base.tile.TickableBlock;
import io.github.noeppi_noeppi.libx.capability.ItemCapabilities;
import io.github.noeppi_noeppi.libx.crafting.recipe.RecipeHelper;
import io.github.noeppi_noeppi.libx.inventory.BaseItemStackHandler;
import io.github.noeppi_noeppi.libx.inventory.IAdvancedItemHandlerModifiable;
import io.github.noeppi_noeppi.libx.util.LazyValue;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DwarvenAnvil extends BlockEntityBase implements TickableBlock {

    public static final int MAX_MANA = 1000;
    public static final int FEY_DUST_MANA_COST = 50;

    private final BaseItemStackHandler inventory = BaseItemStackHandler.builder(8)
            .contentsChanged(() -> {
                this.setChanged();
                this.updateRecipe();
            })
            .validator(stack -> stack.getItem() == ModItems.feyDust, 0)
            .validator(stack -> ModItemTags.SCHEMATICS.contains(stack.getItem()), 1)
            .validator(stack -> this.level == null || RecipeHelper.isItemValidInput(this.level.getRecipeManager(), ModRecipeTypes.DWARVEN_ANVIL, stack), 2, 3, 4, 5, 6)
            .validator(stack -> false, 7)
            .build();
    private final ManaStorage manaStorage = new ManaStorage(MAX_MANA, () -> {
        this.setChanged();
        this.updateRecipe();
    });

    // Top for main inputs, side for fey dust and bottom to extract result.
    private final LazyOptional<IAdvancedItemHandlerModifiable> itemHandlerGeneric = ItemCapabilities.create(() -> this.inventory);
    private final LazyOptional<IAdvancedItemHandlerModifiable> itemHandlerTop = ItemCapabilities.create(() -> this.inventory, slot -> false, (slot, stack) -> slot >= 2 && slot < 7);
    private final LazyOptional<IAdvancedItemHandlerModifiable> itemHandlerSide = ItemCapabilities.create(() -> this.inventory, slot -> false, (slot, stack) -> slot < 2);
    private final LazyOptional<IAdvancedItemHandlerModifiable> itemHandlerBottom = ItemCapabilities.create(() -> this.inventory, slot -> slot == 7, (slot, stack) -> false);
    private final LazyOptional<IManaStorage> manaHandler = LazyOptional.of(() -> this.manaStorage);

    // As `load` is often called without a level, we need to store that a recipe update
    // is required, so it can be updated next tick.
    private boolean needsUpdate = false;
    // Whenever the inventory or mana changes, this is reset to a new lazy value holding
    // the current result item. It's lazy, so the result item is not computed on every
    // inventory or mana change but also not computed multiple times without a change.
    // Hold a pair of the recipe that is used and the result value.
    private LazyValue<Optional<Pair<ItemStack, IDwarvenAnvilRecipe>>> recipe = new LazyValue<>(Optional::empty);

    public DwarvenAnvil(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        this.itemHandlerTop.invalidate();
        this.itemHandlerSide.invalidate();
        this.itemHandlerBottom.invalidate();
        this.manaHandler.invalidate();
    }

    @Override
    public void tick() {
        if (this.level != null && this.level instanceof ServerLevel) {
            if (this.needsUpdate) {
                this.updateRecipe();
                this.needsUpdate = false;
            }
            if (((ServerLevel) this.level).getServer().getTickCount() % 20 == 0 && this.manaStorage.getMana() + FEY_DUST_MANA_COST <= this.manaStorage.getMaxMana()) {
                // Simulate extraction first
                ItemStack extracted = this.inventory.extractItem(0, 1, true);
                if (!extracted.isEmpty() && extracted.getItem() == ModItems.feyDust) {
                    this.inventory.extractItem(0, 1, false);
                    this.manaStorage.receiveMana(FEY_DUST_MANA_COST, false);
                    this.setChanged();
                }
            }
        }
    }

    @Nonnull
    @Override
    public CompoundTag save(CompoundTag nbt) {
        nbt.put("inventory", this.inventory.serializeNBT());
        nbt.put("mana", this.manaStorage.serializeNBT());
        return super.save(nbt);
    }

    @Override
    public void load(@Nonnull CompoundTag nbt) {
        super.load(nbt);
        this.inventory.deserializeNBT(nbt.getCompound("inventory"));
        this.manaStorage.deserializeNBT(nbt.getCompound("mana"));
        this.needsUpdate = true;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side == null) return this.itemHandlerGeneric.cast();
            return switch (side) {
                case UP -> this.itemHandlerTop.cast();
                case DOWN -> this.itemHandlerBottom.cast();
                default -> this.itemHandlerSide.cast();
            };
        } else if (capability == CapabilityMana.MANA) {
            return this.manaHandler.cast();
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
        if (this.level == null || this.level.isClientSide) {
            this.recipe = new LazyValue<>(Optional::empty);
        } else {
            this.recipe = new LazyValue<>(() -> {
                ItemStack schematics = this.inventory.getStackInSlot(1);
                List<ItemStack> inputs = IntStream.range(2, 7).mapToObj(this.inventory::getStackInSlot).filter(stack -> !stack.isEmpty()).collect(Collectors.toList());
                return this.level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.DWARVEN_ANVIL).stream()
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
        return this.inventory;
    }
    
    public int getMana() {
        return this.manaStorage.getMana();
    }
    
    public void setMana(int mana) {
        this.manaStorage.setMana(mana);
    }
}
