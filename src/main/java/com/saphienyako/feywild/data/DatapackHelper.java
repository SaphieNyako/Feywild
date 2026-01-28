package com.saphienyako.feywild.data;

import com.google.gson.*;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.*;
import net.neoforged.fml.ModList;

import java.io.Reader;
import java.util.*;

public class DatapackHelper {

    public static final Gson GSON = new GsonBuilder()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .create();


    public static List<ItemStack> loadStackList(ResourceManager manager, String folder, String filename, RegistryAccess registryAccess) {
        List<ItemStack> result = new ArrayList<>();
        String targetPath = folder + "/" + filename + ".json";

        Map<ResourceLocation, Resource> resources = manager.listResources(folder, loc -> loc.getPath().equals(targetPath));

        for (Map.Entry<ResourceLocation, Resource> entry : resources.entrySet()) {
            ResourceLocation id = entry.getKey();
            Resource resource = entry.getValue();

            try (Reader reader = resource.openAsReader()) {
                JsonElement root = GsonHelper.fromJson(GSON, reader, JsonElement.class);
                if (!root.isJsonArray()) continue;

                for (JsonElement element : root.getAsJsonArray()) {
                    if (!element.isJsonObject()) continue;

                    JsonObject json = element.getAsJsonObject();
                    if (json.has("mod")) {
                        String modid = json.get("mod").getAsString();
                        if (!ModList.get().isLoaded(modid)) continue;
                    }

                    ItemStack stack = itemStackFromJson(json, registryAccess);
                    if (!stack.isEmpty()) {
                        int weight = json.has("rarity") ? json.get("rarity").getAsInt() : 1;
                        weight = Mth.clamp(weight, 1, 10);
                        for (int i = 0; i < weight; i++) {
                            result.add(stack.copy());
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to load datapack resource: " + id, e);
            }
        }

        return result;
    }

    private static ItemStack itemStackFromJson(JsonObject json, RegistryAccess registryAccess) {
        if (!json.has("item")) return ItemStack.EMPTY;

        ResourceLocation itemId = ResourceLocation.tryParse(json.get("item").getAsString());
        if (itemId == null) return ItemStack.EMPTY;

        Item item = BuiltInRegistries.ITEM.get(itemId);
        if (item == Items.AIR) return ItemStack.EMPTY;

        int count = json.has("count") ? json.get("count").getAsInt() : 1;
        ItemStack stack = new ItemStack(item, count);

        if (json.has("enchantments")) {
            JsonObject ench = json.getAsJsonObject("enchantments");

            EnchantmentHelper.updateEnchantments(stack, mutable -> {
                ench.entrySet().forEach(entry -> {
                    ResourceLocation enchId = ResourceLocation.tryParse(entry.getKey());
                    if (enchId == null) return;

                    int level = entry.getValue().getAsInt();

                    Holder<Enchantment> holder = registryAccess
                            .lookupOrThrow(Registries.ENCHANTMENT)
                            .getOrThrow(ResourceKey.create(Registries.ENCHANTMENT, enchId));

                    mutable.set(holder, level);
                });
            });
        }

        return stack;
    }

    public static List<ItemStack> getAllEnchantedBooks(RegistryAccess registryAccess) {
        List<ItemStack> books = new ArrayList<>();

        registryAccess
                .registryOrThrow(Registries.ENCHANTMENT)
                .holders()
                .forEach(holder -> {
                    Enchantment enchant = holder.value();

                    for (int level = enchant.getMinLevel(); level <= enchant.getMaxLevel(); level++) {
                        books.add(
                                EnchantedBookItem.createForEnchantment(
                                        new EnchantmentInstance(holder, level)
                                )
                        );
                    }
                });

        return books;
    }
}