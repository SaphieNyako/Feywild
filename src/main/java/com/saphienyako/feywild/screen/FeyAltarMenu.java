package com.saphienyako.feywild.screen;

import com.saphienyako.feywild.block.ModBlocks;
import com.saphienyako.feywild.block.entity.FeyAltarBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class FeyAltarMenu extends AbstractContainerMenu {

    // Quick-move implementation based on diesieben07's SevenCommons.
    // https://github.com/diesieben07/SevenCommons
    private static final int DATA_COUNT = 5;
    private static final int ALTAR_SLOT_COUNT = 6;
    private static final int PLAYER_INVENTORY_ROWS = 3;
    private static final int PLAYER_INVENTORY_COLUMNS = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_ROWS * PLAYER_INVENTORY_COLUMNS;
    private static final int HOT_BAR_SLOT_COUNT = 9;

    private static final int ALTAR_SLOT_START = PLAYER_INVENTORY_SLOT_COUNT;
    private static final int ALTAR_SLOT_END = ALTAR_SLOT_START + ALTAR_SLOT_COUNT;

    public final FeyAltarBlockEntity blockEntity;

    private final ContainerData data;

    public static FeyAltarMenu create(int containerId, Inventory inventory, RegistryFriendlyByteBuf extraData) {
        BlockPos pos = extraData.readBlockPos();
        BlockEntity blockEntity = inventory.player.level().getBlockEntity(pos);

        return new FeyAltarMenu(containerId, inventory, (FeyAltarBlockEntity) blockEntity, new SimpleContainerData(DATA_COUNT));
    }

    public FeyAltarMenu(int containerId, Inventory inventory, FeyAltarBlockEntity blockEntity, ContainerData data) {
        super(ModMenuTypes.FEY_ALTAR_MENU.get(), containerId);

        this.blockEntity = blockEntity;
        this.data = data;

        this.addPlayerInventory(inventory);
        this.addPlayerHotBar(inventory);
        this.addAltarSlots();

        this.addDataSlots(data);
    }

    private void addAltarSlots() {
        IItemHandler handler = this.blockEntity.getItemHandler();

        this.addSlot(new SlotItemHandler(handler, 0, 95, 18));
        this.addSlot(new SlotItemHandler(handler, 1, 64, 39));
        this.addSlot(new SlotItemHandler(handler, 2, 125, 39));
        this.addSlot(new SlotItemHandler(handler, 3, 76, 74));
        this.addSlot(new SlotItemHandler(handler, 4, 114, 74));
        this.addSlot(new SlotItemHandler(handler, 5, 158, 67));
    }

    private void addPlayerInventory(Inventory inventory) {
        for (int row = 0; row < PLAYER_INVENTORY_ROWS; row++) {
            for (int column = 0; column < PLAYER_INVENTORY_COLUMNS; column++) {
                this.addSlot(new Slot(inventory, column + row * PLAYER_INVENTORY_COLUMNS + 9,
                        23 + column * 18, 97 + row * 18));
            }
        }
    }

    private void addPlayerHotBar(Inventory inventory) {
        for (int column = 0; column < HOT_BAR_SLOT_COUNT; column++) {
            this.addSlot(new Slot(inventory, column, 23 + column * 18, 155));
        }
    }

    public boolean isCrafting() {
        return this.data.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        int progressArrowSize = 14;

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        if (index < 0 || index >= this.slots.size()) {
            return ItemStack.EMPTY;
        }

        Slot sourceSlot = this.slots.get(index);

        if (!sourceSlot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack originalStack = sourceStack.copy();

        if (index < ALTAR_SLOT_START) {
            if (!this.moveItemStackTo(sourceStack, ALTAR_SLOT_START, ALTAR_SLOT_END, false)) {
                return ItemStack.EMPTY;
            }
        } else if (index < ALTAR_SLOT_END) {
            if (!this.moveItemStackTo(sourceStack, 0, ALTAR_SLOT_START, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            return ItemStack.EMPTY;
        }

        if (sourceStack.isEmpty()) {
            sourceSlot.setByPlayer(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }

        sourceSlot.onTake(player, sourceStack);
        return originalStack;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        Block block = this.blockEntity.getBlockState().getBlock();

        return block == ModBlocks.FEY_ALTAR.get()
                || block == ModBlocks.FEY_ALTAR_QUEEN_MAB.get()
                || block == ModBlocks.FEY_ALTAR_QUEEN_TITANIA.get()
                || block == ModBlocks.FEY_ALTAR_ASHEN_LORD.get()
                || block == ModBlocks.FEY_ALTAR_OBERON.get();
    }
}