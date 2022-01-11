package com.feywild.feywild.events.loot_modifiers;

import com.feywild.feywild.config.MiscConfig;
import com.feywild.feywild.item.ModItems;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class MineshaftChestLootModifier extends LootModifier {

    protected MineshaftChestLootModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);

    }

    @NotNull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {

        if (context.getRandom().nextFloat() < MiscConfig.rune_stone_weight) {
            generatedLoot.add(new ItemStack(ModItems.inactiveMarketRuneStone, 1));
        }
        if (context.getRandom().nextFloat() < 0.60) {
            generatedLoot.add(new ItemStack(ModItems.lesserFeyGem, 3));
        }
        if (context.getRandom().nextFloat() < 0.30) {
            generatedLoot.add(new ItemStack(ModItems.greaterFeyGem, 2));
        }
        if (context.getRandom().nextFloat() < 0.15) {
            generatedLoot.add(new ItemStack(ModItems.schematicsGemTransmutation, 1));
            generatedLoot.add(new ItemStack(ModItems.shinyFeyGem, 1));
        }
        if (context.getRandom().nextFloat() < 0.08) {
            generatedLoot.add(new ItemStack(ModItems.brilliantFeyGem, 1));
        }
        if (context.getRandom().nextFloat() < 0.04) {
            generatedLoot.add(new ItemStack(ModItems.feywildMusicDisc, 1));
        }

        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<MineshaftChestLootModifier> {

        @Override
        public MineshaftChestLootModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] ailootcondition) {

            return new MineshaftChestLootModifier(ailootcondition);

        }

        @Override
        public JsonObject write(MineshaftChestLootModifier instance) {

            JsonObject json = makeConditions(instance.conditions);
            json.addProperty("addition", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(ItemStack.EMPTY.getItem())).toString());
            return json;
        }
    }
}
