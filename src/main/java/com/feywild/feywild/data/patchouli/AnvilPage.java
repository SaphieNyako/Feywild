package com.feywild.feywild.data.patchouli;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import org.moddingx.libx.datagen.provider.patchouli.page.Content;
import org.moddingx.libx.datagen.provider.patchouli.page.PageBuilder;

public class AnvilPage implements Content {

    private final ResourceLocation recipe;

    public AnvilPage(ResourceLocation recipe) {
        this.recipe = recipe;
    }

    @Override
    public void pages(PageBuilder builder) {
        JsonObject json = new JsonObject();
        json.addProperty("type", "feywild:dwarven_anvil");
        json.addProperty("recipe", this.recipe.toString());
        builder.addPage(json);
    }
}
