package com.feywild.feywild.item;

import com.feywild.feywild.entity.goals.pixie.Ability;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import org.moddingx.libx.mod.ModX;

public class RuneStone extends TooltipItem {

    public final Ability ability;
    public final Item replaceableItem;

    public RuneStone(ModX mod, Properties properties, Ability ability, Item replaceableItem, Component... itemTooltip) {
        super(mod, properties, itemTooltip);
        this.ability = ability;
        this.replaceableItem = replaceableItem;
    }
}
