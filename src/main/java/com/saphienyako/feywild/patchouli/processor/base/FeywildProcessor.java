package com.saphienyako.feywild.patchouli.processor.base;

import net.minecraft.client.Minecraft;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class FeywildProcessor implements IComponentProcessor {

    @Nullable
    private IRecipe<?>  recipe;


    @Override
    public void setup(@Nonnull IVariableProvider iVariableProvider) {
        RecipeManager manager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        recipe = manager.byKey(Objects.requireNonNull(ResourceLocation.tryParse(getRecipeId()))).orElseThrow(IllegalArgumentException::new);
    }


    @Nonnull
    @Override
    public IVariable process(@Nonnull String key) {
        switch (key) {
            case "description":
                return IVariable.from(new TranslationTextComponent(this.recipe.getResultItem().getDescriptionId()));
            case "output":
                return IVariable.from(this.recipe.getResultItem());
            case "inputs":
                return IVariable.wrapList(this.recipe.getIngredients().stream()
                        .map(IVariable::from)
                        .collect(Collectors.toList()));
            default:
                return IVariable.empty();
        }
    }

    public abstract  String getRecipeId();

}
