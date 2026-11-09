package com.saphienyako.feywild.patchouli.processor.base;

import com.saphienyako.feywild.recipe.FeyAltarRecipe;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public abstract class FeywildComponentProcessor implements IComponentProcessor {

    @Nullable
    private FeyAltarRecipe recipe;

    @Nullable
    private HolderLookup.Provider registries;

    @Override
    public void setup(Level level, @NotNull IVariableProvider vars) {
        registries = level.registryAccess();

        if (!(level instanceof ServerLevel serverLevel)) {
            throw new IllegalStateException("Fey Altar recipes can only be loaded server-side.");
        }

        RecipeManager manager = serverLevel.recipeAccess();

        ResourceKey<Recipe<?>> recipeKey = ResourceKey.create(Registries.RECIPE, Objects.requireNonNull(Identifier.tryParse(getRecipeId())));

        recipe = manager.byKey(recipeKey)
                .map(RecipeHolder::value)
                .filter(FeyAltarRecipe.class::isInstance)
                .map(FeyAltarRecipe.class::cast)
                .orElseThrow(() -> new IllegalArgumentException("Fey Altar recipe not found: " + getRecipeId()));
    }

    @Nonnull
    @Override
    public IVariable process(@NotNull Level level, @NotNull String key) {
        if (recipe == null || registries == null) return IVariable.empty();

        return switch (key) {
            case "description" -> {ItemStack output = recipe.output();yield IVariable.from(output.getItemName(), registries);}
            case "output" -> {ItemStack output = recipe.output();yield IVariable.from(output, registries);}
            case "inputs" -> {
                List<IVariable> stacks = recipe.getIngredients().stream()
                        .map(ingredient -> IVariable.from(ingredient, registries))
                        .toList();
                yield IVariable.wrapList(stacks, registries);}
            default -> IVariable.empty();
        };
    }

    public abstract String getRecipeId();
}