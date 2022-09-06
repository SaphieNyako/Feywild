package com.feywild.feywild.quest.task;

import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.quest.util.AlignmentStack;
import com.feywild.feywild.quest.util.FeyGift;
import com.google.gson.JsonObject;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nullable;

public class FeyGiftTask implements TaskType<FeyGift, AlignmentStack> {

    public static final FeyGiftTask INSTANCE = new FeyGiftTask();

    private FeyGiftTask() {

    }

    @Override
    public Class<FeyGift> element() {
        return FeyGift.class;
    }

    @Override
    public Class<AlignmentStack> testType() {
        return AlignmentStack.class;
    }

    @Override
    public boolean checkCompleted(ServerPlayer player, FeyGift element, AlignmentStack match) {
        return element.alignment() == match.alignment() && element.ingredient().test(match.stack());
    }

    @Override
    public FeyGift fromJson(JsonObject json) {
        Alignment alignment = Alignment.byId(json.get("alignment").getAsString());
        Ingredient ingredient = Ingredient.fromJson(json.get("item"));
        return new FeyGift(alignment, ingredient);
    }

    @Override
    public JsonObject toJson(FeyGift element) {
        JsonObject json = new JsonObject();
        json.addProperty("alignment", element.alignment().id);
        json.add("item", element.ingredient().toJson());
        return json;
    }

    @Nullable
    @Override
    public Item icon(FeyGift element) {
        ItemStack[] matching = element.ingredient().getItems();
        if (matching.length == 1 && !matching[0].isEmpty()) {
            return matching[0].getItem();
        } else {
            return null;
        }
    }
}
