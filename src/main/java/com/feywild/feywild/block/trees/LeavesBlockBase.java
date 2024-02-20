package com.feywild.feywild.block.trees;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.LeavesBlock;
import org.moddingx.libx.mod.ModX;
import org.moddingx.libx.registration.Registerable;
import org.moddingx.libx.registration.RegistrationContext;

import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;

public class LeavesBlockBase extends LeavesBlock implements Registerable {

    protected final ModX mod;
    @Nullable private final Item item;
    
    public LeavesBlockBase(ModX mod, Properties properties) {
        super(properties);
        this.mod = mod;
        this.item = new BlockItem(this, new Item.Properties());
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    public void registerAdditional(RegistrationContext ctx, EntryCollector builder) {
        builder.register(Registries.ITEM, this.item);
    }
}
