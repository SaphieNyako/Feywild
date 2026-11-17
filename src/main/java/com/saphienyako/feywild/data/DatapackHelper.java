package com.saphienyako.feywild.data;

import com.google.gson.*;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.fml.ModList;

import javax.annotation.Nullable;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class DatapackHelper {

    public static final Gson GSON = new GsonBuilder()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .create();


    public static List<ItemStackTemplate> loadStackList(ResourceManager manager, String folder, String filename, HolderLookup.Provider registryAccess) {
        List<ItemStackTemplate> result = new ArrayList<>();
        String targetPath = folder + "/" + filename + ".json";

        Map<Identifier, Resource> resources = manager.listResources(folder, loc -> loc.getPath().equals(targetPath));

        for (Map.Entry<Identifier, Resource> entry : resources.entrySet()) {
            Identifier id = entry.getKey();
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

                    ItemStackTemplate template = itemStackTemplateFromJson(json, registryAccess);

                    if (template != null) {
                        int weight = json.has("rarity") ? json.get("rarity").getAsInt() : 1;

                        weight = Mth.clamp(weight, 1, 10);

                        for (int i = 0; i < weight; i++) {
                            result.add(template);
                        }
                    }
                }

            } catch (Exception e) {
                throw new RuntimeException("Failed to load datapack resource: " + id, e);
            }
        }

        return result;
    }


    @Nullable
    private static ItemStackTemplate itemStackTemplateFromJson(JsonObject json, HolderLookup.Provider registryAccess) {
        if (!json.has("item")) return null;

        Identifier itemId = Identifier.tryParse(json.get("item").getAsString());
        if (itemId == null) return null;

        Holder<Item> itemHolder = registryAccess
                .lookupOrThrow(Registries.ITEM)
                .get(ResourceKey.create(Registries.ITEM, itemId))
                .orElse(null);

        if (itemHolder == null) {
            return null;
        }

        int count = json.has("count") ? json.get("count").getAsInt() : 1;

        if (count <= 0 || itemHolder.value() == Items.AIR) {
            return null;
        }

        DataComponentPatch.Builder components = DataComponentPatch.builder();

        // Enchantments
        if (json.has("enchantments")) {
            JsonObject enchantmentsJson = json.getAsJsonObject("enchantments");

            ItemEnchantments.Mutable enchantments = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);

            enchantmentsJson.entrySet().forEach(entry -> {
                Identifier enchantmentId = Identifier.tryParse(entry.getKey());
                if (enchantmentId == null) return;

                int level = entry.getValue().getAsInt();

                Holder<Enchantment> holder = registryAccess
                        .lookupOrThrow(Registries.ENCHANTMENT)
                        .getOrThrow(ResourceKey.create(Registries.ENCHANTMENT, enchantmentId));

                enchantments.set(holder, level);
            });

            components.set(DataComponents.ENCHANTMENTS, enchantments.toImmutable());
        }

        // Potion and suspicious stew effects
        if (json.has("effects")) {
            List<MobEffectInstance> effects = readEffects(json.getAsJsonArray("effects"), registryAccess);

            Item item = itemHolder.value();

            if (item == Items.POTION
                    || item == Items.SPLASH_POTION
                    || item == Items.LINGERING_POTION
                    || item == Items.TIPPED_ARROW) {

                components.set(DataComponents.POTION_CONTENTS, new PotionContents(Optional.empty(), Optional.empty(), effects, Optional.empty()));
            }

            if (item == Items.SUSPICIOUS_STEW) {
                components.set(DataComponents.SUSPICIOUS_STEW_EFFECTS, new SuspiciousStewEffects(toStewEntries(effects)));
            }
        }

        // Custom name
        if (json.has("name")) {
            components.set(DataComponents.CUSTOM_NAME, Component.literal(json.get("name").getAsString()));
        }

        // Item lore
        if (json.has("description")) {
            List<Component> loreList = new ArrayList<>();
            loreList.add(Component.literal(json.get("description").getAsString()));

            components.set(DataComponents.LORE, new ItemLore(loreList));
        }

        return new ItemStackTemplate(itemHolder, count, components.build());
    }


    private static List<MobEffectInstance> readEffects(JsonArray array, HolderLookup.Provider registryAccess) {
        List<MobEffectInstance> effects = new ArrayList<>();

        for (JsonElement element : array) {
            if (!element.isJsonObject()) continue;

            JsonObject obj = element.getAsJsonObject();

            Identifier id = Identifier.tryParse(obj.get("effect").getAsString());
            if (id == null) continue;

            Holder<MobEffect> effect = registryAccess
                    .lookupOrThrow(Registries.MOB_EFFECT)
                    .getOrThrow(ResourceKey.create(Registries.MOB_EFFECT, id));

            int duration = obj.has("duration") ? obj.get("duration").getAsInt() : 200;
            int amplifier = obj.has("amplifier") ? obj.get("amplifier").getAsInt() : 0;

            effects.add(new MobEffectInstance(effect, duration, amplifier));
        }

        return effects;
    }


    private static List<SuspiciousStewEffects.Entry> toStewEntries(List<MobEffectInstance> effects) {
        return effects.stream()
                .map(e -> new SuspiciousStewEffects.Entry(e.getEffect(), e.getDuration()))
                .toList();
    }


    public static List<ItemStackTemplate> getAllEnchantedBooks(HolderLookup.Provider registryAccess) {
        List<ItemStackTemplate> books = new ArrayList<>();

        Holder<Item> bookHolder = registryAccess
                .lookupOrThrow(Registries.ITEM)
                .getOrThrow(ResourceKey.create(Registries.ITEM, Identifier.withDefaultNamespace("enchanted_book")));

        registryAccess.lookupOrThrow(Registries.ENCHANTMENT)
                .listElements()
                .forEach(holder -> {
                    Enchantment enchant = holder.value();

                    for (int level = enchant.getMinLevel(); level <= enchant.getMaxLevel(); level++) {

                        ItemEnchantments.Mutable enchantments = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);

                        enchantments.set(holder, level);
                        DataComponentPatch components = DataComponentPatch.builder()
                                .set(DataComponents.STORED_ENCHANTMENTS, enchantments.toImmutable())
                                .build();

                        books.add(new ItemStackTemplate(bookHolder, 1, components));
                    }
                });

        return books;
    }
}