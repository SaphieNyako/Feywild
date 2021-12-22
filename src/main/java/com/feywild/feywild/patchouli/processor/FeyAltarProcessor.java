package com.feywild.feywild.patchouli.processor;

import com.feywild.feywild.recipes.AltarRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

import javax.annotation.Nonnull;

public class FeyAltarProcessor implements IComponentProcessor {

    AltarRecipe recipe;
    //IRecipe = recipe

    @Override
    public void setup(IVariableProvider iVariableProvider) {

        RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();
        ResourceLocation id = new ResourceLocation(iVariableProvider.get("recipe").asString());
        this.recipe = (AltarRecipe) manager.byKey(id).orElse(null);

        //this.recipe = PatchouliUtils.getRecipe(ModRecipeTypes.ALTAR, id);
    }

    @Override
    public IVariable process(@Nonnull String key) {

        if (recipe == null) {
            return null;
        }
        if (key.equals("description")) {

            return IVariable.from(new TranslatableComponent(this.recipe.getResultItem().getDescriptionId()));

        }
        if (key.equals("output")) {
            return IVariable.from(this.recipe.getResultItem());
        }
        if (key.startsWith("item")) {

            int indexNumber = Integer.parseInt(key.substring(4)) - 1;

            Ingredient ingredient = this.recipe.getIngredients().get(indexNumber);
            ItemStack[] stacks = ingredient.getItems();
            ItemStack stack = stacks.length == 0 ? ItemStack.EMPTY : stacks[0];
            return IVariable.from(stack);
        }

        return null;
    }
}
