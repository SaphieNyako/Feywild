package com.feywild.feywild.tag;

import com.feywild.feywild.FeywildMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;

public class ModItemTags {

    public static final Tag.Named<Item> SCHEMATICS = ItemTags.bind(new ResourceLocation(FeywildMod.getInstance().modid, "schematics").toString());
    public static final Tag.Named<Item> FEY_LOGS = ItemTags.bind(new ResourceLocation(FeywildMod.getInstance().modid, "fey_logs").toString());
    public static final Tag.Named<Item> DEADLY_BOOKS = ItemTags.bind(new ResourceLocation(FeywildMod.getInstance().modid, "deadly_books").toString());
    public static final Tag.Named<Item> ELEMENTAL_BOOKS = ItemTags.bind(new ResourceLocation(FeywildMod.getInstance().modid, "elemental_books").toString());
    public static final Tag.Named<Item> SEASONAL_BOOKS = ItemTags.bind(new ResourceLocation(FeywildMod.getInstance().modid, "seasonal_books").toString());
    public static final Tag.Named<Item> YGGDRASIL_BOOKS = ItemTags.bind(new ResourceLocation(FeywildMod.getInstance().modid, "yggdrasil_books").toString());
}
