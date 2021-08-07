package com.feywild.feywild.tag;

import com.feywild.feywild.FeywildMod;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class ModItemTags {

    public static final ITag.INamedTag<Item> SCHEMATICS = ItemTags.bind(new ResourceLocation(FeywildMod.getInstance().modid, "schematics").toString());
    public static final ITag.INamedTag<Item> FEY_LOGS = ItemTags.bind(new ResourceLocation(FeywildMod.getInstance().modid, "fey_logs").toString());
}
