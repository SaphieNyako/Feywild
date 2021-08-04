package com.feywild.feywild.quest.task;

import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.quest.util.AlignmentStack;
import com.google.gson.JsonObject;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.crafting.Ingredient;

public class FeyGiftTask implements TaskType<FeyGiftTask.Gift, AlignmentStack> {

    public static final FeyGiftTask INSTANCE = new FeyGiftTask();

    private FeyGiftTask() {

    }

    @Override
    public Class<Gift> element() {
        return Gift.class;
    }

    @Override
    public Class<AlignmentStack> testType() {
        return AlignmentStack.class;
    }

    @Override
    public boolean checkCompleted(ServerPlayerEntity player, Gift element, AlignmentStack match) {
        return element.alignment == match.alignment && element.ingredient.test(match.getStack());
    }

    @Override
    public Gift fromJson(JsonObject json) {
        return null;
    }

    @Override
    public JsonObject toJson(Gift element) {
        return null;
    }

    public static class Gift {
        
        public final Alignment alignment;
        public final Ingredient ingredient;

        private Gift(Alignment alignment, Ingredient ingredient) {
            this.alignment = alignment;
            this.ingredient = ingredient;
        }
    }
}
