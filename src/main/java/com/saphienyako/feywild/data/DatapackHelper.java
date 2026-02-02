package com.saphienyako.feywild.data;

import com.google.gson.*;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.fml.ModList;


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
            JsonObject enchantments = json.getAsJsonObject("enchantments");

            enchantments.entrySet().forEach(entry -> {
                ResourceLocation enchantmentId = ResourceLocation.tryParse(entry.getKey());
                if (enchantmentId == null) return;

                Enchantment enchantment =
                        BuiltInRegistries.ENCHANTMENT.get(enchantmentId);
                if (enchantment == null) return;

                int level = entry.getValue().getAsInt();
                stack.enchant(enchantment, level);
            });
        }

        if (json.has("effects")) {
            List<MobEffectInstance> effects =
                    readEffects(json.getAsJsonArray("effects"), registryAccess);

            if (stack.is(Items.POTION)
                    || stack.is(Items.SPLASH_POTION)
                    || stack.is(Items.LINGERING_POTION)
                    || stack.is(Items.TIPPED_ARROW)) {

                if (!effects.isEmpty()) {
                    PotionUtils.setCustomEffects(stack, effects);
                }
            }

            if (stack.is(Items.SUSPICIOUS_STEW)) {
                for (MobEffectInstance effect : effects) {
                    SuspiciousStewItem.saveMobEffect(
                            stack,
                            effect.getEffect(),
                            effect.getDuration()
                    );
                }
            }
        }

        if (json.has("name")) {
            stack.setHoverName(Component.literal(json.get("name").getAsString()));
        }

        if (json.has("description")) {
            ListTag loreTag = new ListTag();

            loreTag.add(
                    StringTag.valueOf(
                            Component.Serializer.toJson(
                                    Component.literal(json.get("description").getAsString())
                            )
                    )
            );

            CompoundTag display = stack.getOrCreateTagElement("display");
            display.put("Lore", loreTag);
        }

        return stack;
    }

    private static List<MobEffectInstance> readEffects(JsonArray array, RegistryAccess registryAccess) {
        List<MobEffectInstance> effects = new ArrayList<>();

        for (JsonElement element : array) {
            if (!element.isJsonObject()) continue;

            JsonObject obj = element.getAsJsonObject();
            ResourceLocation id = ResourceLocation.tryParse(obj.get("effect").getAsString());
            if (id == null) continue;

            Holder<MobEffect> effect = registryAccess
                    .lookupOrThrow(Registries.MOB_EFFECT)
                    .getOrThrow(ResourceKey.create(Registries.MOB_EFFECT, id));

            int duration = obj.has("duration") ? obj.get("duration").getAsInt() : 200;
            int amplifier = obj.has("amplifier") ? obj.get("amplifier").getAsInt() : 0;

            effects.add(new MobEffectInstance(effect.value(), duration, amplifier));
        }

        return effects;
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
                                        new EnchantmentInstance(holder.value(), level)
                                )
                        );
                    }
                });

        return books;
    }
}