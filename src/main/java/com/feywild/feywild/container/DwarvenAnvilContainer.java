package com.feywild.feywild.container;

import com.feywild.feywild.block.entity.DwarvenAnvil;
import com.feywild.feywild.util.Util;
import io.github.noeppi_noeppi.libx.inventory.container.ContainerBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nullable;

public class DwarvenAnvilContainer extends ContainerBase<DwarvenAnvil> {

    public DwarvenAnvilContainer(@Nullable ContainerType<?> type, int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
        super(type, windowId, world, pos, playerInventory, player, 7, 8);
        this.addSlot(new SlotItemHandler(this.tile.getInventory().getUnrestricted(), 0, 30, 56)); //this will hold the feydust
        this.addSlot(new SlotItemHandler(this.tile.getInventory().getUnrestricted(), 1, 30, 8)); // this will hold the scheme
        this.addSlot(new SlotItemHandler(this.tile.getInventory().getUnrestricted(), 2, 82, 15)); // item 1
        this.addSlot(new SlotItemHandler(this.tile.getInventory().getUnrestricted(), 3, 55, 29)); // item 2
        this.addSlot(new SlotItemHandler(this.tile.getInventory().getUnrestricted(), 4, 112, 29)); // item 3
        this.addSlot(new SlotItemHandler(this.tile.getInventory().getUnrestricted(), 5, 70, 50)); // item 4
        this.addSlot(new SlotItemHandler(this.tile.getInventory().getUnrestricted(), 6, 96, 50)); // item 5
        this.addSlot(new SlotItemHandler(this.tile.getInventory().getUnrestricted(), 7, 149, 56)); // Output
        this.layoutPlayerInventorySlots(8, 84);
        this.addDataSlot(Util.trackHigherOrderBits(this.tile::getMana, this.tile::setMana));
        this.addDataSlot(Util.trackLowerOrderBits(this.tile::getMana, this.tile::setMana));
    }
}
