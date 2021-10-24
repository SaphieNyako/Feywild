package com.feywild.feywild.container;

import com.feywild.feywild.block.entity.DwarvenAnvil;
import com.feywild.feywild.util.Util;
import io.github.noeppi_noeppi.libx.menu.BlockEntityMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nullable;

public class DwarvenAnvilContainerMenu extends BlockEntityMenu<DwarvenAnvil> {

    public DwarvenAnvilContainerMenu(@Nullable MenuType<?> type, int windowId, Level level, BlockPos pos, Inventory playerContainer, Player player) {
        super(type, windowId, level, pos, playerContainer, player, 7, 8);
        this.addSlot(new SlotItemHandler(this.blockEntity.getInventory().getUnrestricted(), 0, 30, 56)); //this will hold the feydust
        this.addSlot(new SlotItemHandler(this.blockEntity.getInventory().getUnrestricted(), 1, 30, 8)); // this will hold the scheme
        this.addSlot(new SlotItemHandler(this.blockEntity.getInventory().getUnrestricted(), 2, 82, 15)); // item 1
        this.addSlot(new SlotItemHandler(this.blockEntity.getInventory().getUnrestricted(), 3, 55, 29)); // item 2
        this.addSlot(new SlotItemHandler(this.blockEntity.getInventory().getUnrestricted(), 4, 112, 29)); // item 3
        this.addSlot(new SlotItemHandler(this.blockEntity.getInventory().getUnrestricted(), 5, 70, 50)); // item 4
        this.addSlot(new SlotItemHandler(this.blockEntity.getInventory().getUnrestricted(), 6, 96, 50)); // item 5
        this.addSlot(new SlotItemHandler(this.blockEntity.getInventory().getUnrestricted(), 7, 149, 56)); // Output
        this.layoutPlayerInventorySlots(8, 84);
        this.addDataSlot(Util.trackHigherOrderBits(this.blockEntity::getMana, this.blockEntity::setMana));
        this.addDataSlot(Util.trackLowerOrderBits(this.blockEntity::getMana, this.blockEntity::setMana));
    }
}
