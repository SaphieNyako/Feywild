package com.saphienyako.feywild.block.entity;

import com.saphienyako.feywild.block.NewFeyAltarBlock;
import com.saphienyako.feywild.entity.Alignment;
import com.saphienyako.feywild.network.AltarParticleMessage;
import com.saphienyako.feywild.recipe.FeyAltarRecipe;
import com.saphienyako.feywild.recipe.FeyAltarRecipeInput;
import com.saphienyako.feywild.recipe.ModRecipes;
import com.saphienyako.feywild.screen.FeyAltarMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FeyAltarBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(6){
        @Override
        public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            if (slot == OUTPUT_SLOT) {

                return stack;
            }
            return super.insertItem(slot, stack, simulate);
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!Objects.requireNonNull(level).isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 2);
            }
        }
    };
    private static final int INPUT_SLOT_00 = 0;
    private static final int INPUT_SLOT_01 = 1;
    private static final int INPUT_SLOT_02 = 2;
    private static final int INPUT_SLOT_03 = 3;
    private static final int INPUT_SLOT_04 = 4;
    private static final int OUTPUT_SLOT = 5;

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 40;


    public FeyAltarBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.FEY_ALTAR_BLOCK_ENTITY.get(), pos, blockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> FeyAltarBlockEntity.this.progress;
                    case 1 -> FeyAltarBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> FeyAltarBlockEntity.this.progress = pValue;
                    case 1 -> FeyAltarBlockEntity.this.maxProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    public IItemHandler getItemHandler() {
        return this.itemHandler;
    }

    public ItemStackHandler getInventory() {
        return itemHandler;
    }

    public int getProgress() {return progress;}

    public int getMaxProgress() {return maxProgress;}

    public void drops() {
        //Drop Items
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(Objects.requireNonNull(this.level), this.worldPosition, inventory);
    }




    @Override
    protected void saveAdditional(@NotNull CompoundTag nbt, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(nbt, registries);
        nbt.put("inventory", itemHandler.serializeNBT(registries));
        nbt.putInt("progress", this.progress);
        super.saveAdditional(nbt, registries);
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag nbt, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(nbt, registries);
        itemHandler.deserializeNBT(registries, nbt.getCompound("inventory"));
        this.progress = nbt.getInt("progress");
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.feywild.fey_altar");
    }


    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
       return new FeyAltarMenu(id,inventory,this,this.data);

    }

    @SuppressWarnings("unused")
    public void tick(Level level, BlockPos pos, BlockState state) {
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
            addParticles((ServerLevel) level);
        }
    }

    private void addParticles(ServerLevel level) {
        if (this.progress > 0) {
            if (this.progress >= (maxProgress - 1)) {
                //Particles after item has been crafted
                    PacketDistributor.sendToPlayersTrackingChunk(level, Objects.requireNonNull(this.level).getChunkAt(this.worldPosition).getPos(),
                            new AltarParticleMessage(
                                    AltarParticleMessage.Particles.ALTAR_01,
                                    this.worldPosition,
                                    progress,
                                    maxProgress
                            )
                    );
            } else {
                List<ItemStack> stacks = new ArrayList<>();
                for (int slot = 0; slot < itemHandler.getSlots(); slot++) {
                    ItemStack stack = itemHandler.getStackInSlot(slot);
                    if (!stack.isEmpty()) stacks.add(stack);
                }
                if (!stacks.isEmpty()) {
                    //Particles moving up while being crafted
                    PacketDistributor.sendToPlayersTrackingChunk(level, Objects.requireNonNull(this.level).getChunkAt(this.worldPosition).getPos(),
                            new AltarParticleMessage(
                                    AltarParticleMessage.Particles.ALTAR_02,
                                    this.worldPosition,
                                    progress,
                                    maxProgress
                            )
                    );
                }
            }
        } else {
            //Particles on Model
            PacketDistributor.sendToPlayersTrackingChunk(level, Objects.requireNonNull(this.level).getChunkAt(this.worldPosition).getPos(),
                    new AltarParticleMessage(
                            AltarParticleMessage.Particles.ALTAR_03,
                            this.worldPosition,
                            progress,
                            maxProgress
                    )
            );
        }
    }

    private void resetProgress() {
        progress = 0;
    }

    private boolean hasRecipe() {
        Optional<RecipeHolder<FeyAltarRecipe>> recipe = getCurrentRecipe();

        if(recipe.isEmpty()) {
            return false;
        }
        ItemStack result = recipe.get().value().getResultItem(Objects.requireNonNull(getLevel()).registryAccess());

        return canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private Optional<RecipeHolder<FeyAltarRecipe>> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());

        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }

        assert this.level != null;

        FeyAltarRecipeInput input = new FeyAltarRecipeInput(inventory);

        return this.level.getRecipeManager().getRecipeFor(
                ModRecipes.FEY_ALTAR_TYPE.get(),
                input,
                this.level
        );
    }

    @SuppressWarnings("ConstantConditions")
    private void craftItem() {
        Optional<RecipeHolder<FeyAltarRecipe>> recipe = getCurrentRecipe();

        recipe.ifPresent(recipeResult -> {
            ItemStack result = recipeResult.value().getResultItem(null);

            // Extract input items
            this.itemHandler.extractItem(INPUT_SLOT_00, 1, false);
            this.itemHandler.extractItem(INPUT_SLOT_01, 1, false);
            this.itemHandler.extractItem(INPUT_SLOT_02, 1, false);
            this.itemHandler.extractItem(INPUT_SLOT_03, 1, false);
            this.itemHandler.extractItem(INPUT_SLOT_04, 1, false);

            // Add result to output slot
            ItemStack currentOutput = this.itemHandler.getStackInSlot(OUTPUT_SLOT);
            this.itemHandler.setStackInSlot(
                    OUTPUT_SLOT,
                    new ItemStack(result.getItem(), currentOutput.getCount() + result.getCount())
            );
        });
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(item);
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
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }


    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider registries) {
        return saveWithoutMetadata(registries);
    }


    public Alignment getAlignment() {
        return getBlockState().getBlock() instanceof NewFeyAltarBlock altar
                ? altar.getAlignment()
                : Alignment.SPRING;
    }
}

