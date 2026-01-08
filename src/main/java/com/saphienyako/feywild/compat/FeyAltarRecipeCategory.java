package com.saphienyako.feywild.compat;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.block.ModBlocks;
import com.saphienyako.feywild.recipe.FeyAltarRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;


import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FeyAltarRecipeCategory implements IRecipeCategory<FeyAltarRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(Feywild.MOD_ID, "fey_altar");
    public static final ResourceLocation TEXTURE = new ResourceLocation(Feywild.MOD_ID,
            "textures/gui/fey_altar_gui.png");

    public static final IRecipeType<FeyAltarRecipe> FEY_ALTAR_TYPE =
            new IRecipeType<FeyAltarRecipe>() {};

    private final IDrawable background;
    private final IDrawable icon;

    public FeyAltarRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 5, 3,155 , 80);
        this.icon = helper.createDrawableIngredient(new ItemStack(ModBlocks.FEY_ALTAR.get()));
    }

    @Nonnull
    @Override
    public ITextComponent getTitleAsTextComponent() {
        return new TranslationTextComponent("block.feywild.fey_altar");
    }

    @Override
    public @Nonnull IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @Nonnull IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setIngredients(FeyAltarRecipe recipe, IIngredients iIngredients) {

        List<List<ItemStack>> inputs = recipe.getIngredients().stream()
                .map(ingredient -> Arrays.asList(ingredient.getItems()))
                .collect(Collectors.toList());

        // Set all inputs
        iIngredients.setInputLists(VanillaTypes.ITEM, inputs);

        // Set the output
        iIngredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());;
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout,@Nonnull FeyAltarRecipe recipe,@Nonnull IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(0, true, 75, 2);
        guiItemStacks.init(1, true, 44, 23);
        guiItemStacks.init(2, true, 105, 23);
        guiItemStacks.init(3, true, 56, 58);
        guiItemStacks.init(4, true, 94, 58);
        int outputIndex = recipe.getIngredients().size();
        guiItemStacks.set(outputIndex, recipe.getResultItem());
    }

    @SuppressWarnings("removal")
    @Override
    public @Nonnull ResourceLocation getUid() {
        return UID;
    }

    @Nonnull
    @Override
    public Class<? extends FeyAltarRecipe> getRecipeClass() {
        return FeyAltarRecipe.class;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return new TranslationTextComponent("block.feywild.fey_altar").toString();
    }
}
