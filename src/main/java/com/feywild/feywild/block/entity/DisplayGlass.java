package com.feywild.feywild.block.entity;

import com.feywild.feywild.block.DisplayGlassBlock;
import com.feywild.feywild.config.MiscConfig;
import com.feywild.feywild.item.ModItems;
import org.moddingx.libx.base.tile.BlockEntityBase;
import org.moddingx.libx.base.tile.TickingBlock;
import org.moddingx.libx.capability.ItemCapabilities;
import org.moddingx.libx.inventory.BaseItemStackHandler;
import org.moddingx.libx.inventory.IAdvancedItemHandlerModifiable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DisplayGlass extends BlockEntityBase implements TickingBlock {

    private final BaseItemStackHandler inventory;
    private final LazyOptional<IAdvancedItemHandlerModifiable> itemHandler;

    private int generationCoolDown = 0;
    private int hitCounter = 0;

    public DisplayGlass(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.inventory = BaseItemStackHandler.builder(1)
                .contentsChanged(() -> {
                    this.setChanged();
                    this.setDispatchable();
                })
                .build();
        this.itemHandler = ItemCapabilities.create(() -> this.inventory);
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
                this.inventory.getUnrestricted().insertItem(new ItemStack(ModItems.honeycomb), false);
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
            level.playSound(null, worldPosition, SoundEvents.GLASS_HIT, SoundSource.BLOCKS, 1, 1);
            if (hitCounter > 3) {
                if (getBlockState().getValue(DisplayGlassBlock.BREAKAGE) < 4) {
                    level.setBlock(worldPosition, getBlockState().setValue(DisplayGlassBlock.BREAKAGE, getBlockState().getValue(DisplayGlassBlock.BREAKAGE) + 1), 3);
                    level.playSound(null, worldPosition, SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 1, 1);
                    hitCounter = 0;
                }
                if (getBlockState().getValue(DisplayGlassBlock.BREAKAGE) == 4) {
                    generationCoolDown = MiscConfig.magical_honey_timer;
                    for (int i = 0; i < inventory.getSlots(); i++) {
                        ItemStack stack = inventory.getStackInSlot(i);
                        if (!stack.isEmpty()) {
                            ItemEntity entity = new ItemEntity(level, worldPosition.getX() + 0.5, worldPosition.getY() + 0.3, worldPosition.getZ() + 0.5, stack.copy());
                            level.addFreshEntity(entity);
                            inventory.setStackInSlot(i, ItemStack.EMPTY);
                        }
                    }
                }
            }
            this.setChanged();
        }
    }

    @Override
    public void saveAdditional(@Nonnull CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.put("Inventory", this.inventory.serializeNBT());
        nbt.putInt("GenerationCoolDown", this.generationCoolDown);
        nbt.putInt("Hits", this.hitCounter);
    }

    @Override
    public void load(@Nonnull CompoundTag nbt) {
        super.load(nbt);
        this.inventory.deserializeNBT(nbt.getCompound("Inventory"));
        this.generationCoolDown = nbt.getInt("GenerationCoolDown");
        this.hitCounter = nbt.getInt("Hits");
    }

    @Nonnull
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = super.getUpdateTag();
        if (this.level != null && !this.level.isClientSide) {
            nbt.put("Inventory", this.inventory.serializeNBT());
        }
        return nbt;
    }

    @Override
    public void handleUpdateTag(CompoundTag nbt) {
        super.handleUpdateTag(nbt);
        if (this.level != null && this.level.isClientSide) {
            this.inventory.deserializeNBT(nbt.getCompound("Inventory"));
        }
    }

    public BaseItemStackHandler getInventory() {
        return this.inventory;
    }
}
