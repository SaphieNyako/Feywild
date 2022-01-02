package com.feywild.feywild.patchouli.processor;

import com.feywild.feywild.patchouli.PatchouliUtils;
import com.feywild.feywild.recipes.AltarRecipe;
import com.feywild.feywild.recipes.ModRecipeTypes;
import net.minecraft.network.chat.TranslatableComponent;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FeyAltarProcessor implements IComponentProcessor {

    @Nullable
    private AltarRecipe recipe;

    @Override
    public void setup(@Nonnull IVariableProvider vars) {
        this.recipe = PatchouliUtils.getRecipe(AltarRecipe.class, ModRecipeTypes.ALTAR, vars.get("recipe").asString());
    }

    @Override
    public IVariable process(@Nonnull String key) {
        if (recipe == null) return null;
        return switch (key) {
            case "description" -> IVariable.from(new TranslatableComponent(this.recipe.getResultItem().getDescriptionId()));
            case "output" -> IVariable.from(this.recipe.getResultItem());
            case "inputs" -> IVariable.wrapList(this.recipe.getIngredients().stream().map(IVariable::from).toList());
            default -> null;
        };
    }
}
