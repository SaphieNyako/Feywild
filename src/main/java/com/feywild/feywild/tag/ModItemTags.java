package com.feywild.feywild.tag;

import com.feywild.feywild.FeywildMod;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class ModItemTags {

    public static final ITag.INamedTag<Item> SCHEMATICS = ItemTags.bind(new ResourceLocation(FeywildMod.getInstance().modid, "schematics").toString());
    public static final ITag.INamedTag<Item> FEY_LOGS = ItemTags.bind(new ResourceLocation(FeywildMod.getInstance().modid, "fey_logs").toString());
    public static final ITag.INamedTag<Item> DEADLY_BOOKS = ItemTags.bind(new ResourceLocation(FeywildMod.getInstance().modid, "deadly_books").toString());
    public static final ITag.INamedTag<Item> ELEMENTAL_BOOKS = ItemTags.bind(new ResourceLocation(FeywildMod.getInstance().modid, "elemental_books").toString());
    public static final ITag.INamedTag<Item> SEASONAL_BOOKS = ItemTags.bind(new ResourceLocation(FeywildMod.getInstance().modid, "seasonal_books").toString());
    public static final ITag.INamedTag<Item> YGGDRASIL_BOOKS = ItemTags.bind(new ResourceLocation(FeywildMod.getInstance().modid, "yggdrasil_books").toString());
}
