package com.saphienyako.feywild.patchouli.processor.base;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public abstract class FeywildProcessor implements IComponentProcessor {

    @Nullable
    private Recipe recipe;

    @Override
    public void setup(Level level, IVariableProvider vars) {
        RecipeManager manager = level.getRecipeManager();
        recipe = manager.byKey(Objects.requireNonNull(ResourceLocation.tryParse(getRecipeId()))).orElseThrow(IllegalArgumentException::new);
    }


    @Nonnull
    @Override
    public IVariable process(Level level, String key) {
        if (recipe == null) return IVariable.empty();
        return switch (key) {
            case "description" -> IVariable.from(Component.translatable(this.recipe.getResultItem(level.registryAccess()).getDescriptionId()));
            case "output" -> IVariable.from(this.recipe.getResultItem(level.registryAccess()));
            case "inputs" -> IVariable.wrapList(this.recipe.getIngredients().stream().map(IVariable::from).toList());
            default -> IVariable.empty();
        };
    }

    public abstract  String getRecipeId();

}
