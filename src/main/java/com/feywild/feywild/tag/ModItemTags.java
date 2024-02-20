package com.feywild.feywild.tag;

import com.feywild.feywild.FeywildMod;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {

    public static final TagKey<Item> SCHEMATICS = ItemTags.create(FeywildMod.getInstance().resource("schematics"));
    public static final TagKey<Item> FEY_LOGS = ItemTags.create(FeywildMod.getInstance().resource("fey_logs"));
    public static final TagKey<Item> DEADLY_BOOKS = ItemTags.create(FeywildMod.getInstance().resource("deadly_books"));
    public static final TagKey<Item> ELEMENTAL_BOOKS = ItemTags.create(FeywildMod.getInstance().resource("elemental_books"));
    public static final TagKey<Item> SEASONAL_BOOKS = ItemTags.create(FeywildMod.getInstance().resource("seasonal_books"));
    public static final TagKey<Item> YGGDRASIL_BOOKS = ItemTags.create(FeywildMod.getInstance().resource("yggdrasil_books"));
    public static final TagKey<Item> PIXIE_WING_COMPONENTS = ItemTags.create(FeywildMod.getInstance().resource("pixie_wing_components"));
    public static final TagKey<Item> COOKIES = ItemTags.create(FeywildMod.getInstance().resource("cookies"));
}
