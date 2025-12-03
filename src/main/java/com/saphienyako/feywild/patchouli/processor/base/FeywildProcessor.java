package com.saphienyako.feywild.patchouli.processor.base;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
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
    public void setup(@NotNull IVariableProvider variables) {
        RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();
        recipe = manager.byKey(Objects.requireNonNull(ResourceLocation.tryParse(getRecipeId()))).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public @NotNull IVariable process(@NotNull String key) {
        if (recipe == null) return IVariable.empty();
        return switch (key) {
            case "description" -> IVariable.from(Component.translatable(this.recipe.getResultItem().getDescriptionId()));
            case "output" -> IVariable.from(this.recipe.getResultItem());
            case "inputs" -> IVariable.wrapList(this.recipe.getIngredients().stream().map(IVariable::from).toList());
            default -> IVariable.empty();
        };
    }

    public abstract  String getRecipeId();

}
