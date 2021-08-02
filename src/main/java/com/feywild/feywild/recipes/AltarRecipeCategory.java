package com.feywild.feywild.recipes;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AltarRecipeCategory implements IRecipeCategory<AltarRecipe> {

    public final static ResourceLocation UID = new ResourceLocation(FeywildMod.getInstance().modid, "fey_altar");
    public IDrawable background;
    public IDrawable icon;
    IGuiHelper helper;

    public AltarRecipeCategory(IGuiHelper helper) {
        ResourceLocation location = new ResourceLocation(FeywildMod.getInstance().modid, "textures/gui/fey_altar_jei.png");
        this.helper = helper;
        background = helper.createDrawable(location, 0, 0, 85, 85);
        icon = helper.createDrawableIngredient(new ItemStack(ModBlocks.feyAltar));
    }

    @Nonnull
    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Nonnull
    @Override
    public Class<? extends AltarRecipe> getRecipeClass() {
        return AltarRecipe.class;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return "Fey Altar";
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Nonnull
    @Override
    public IDrawable getIcon() {
        return icon;
    }

    /*
    @Override
    public void draw(AltarRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        IDrawableAnimated arrow = this.cachedArrows.getUnchecked(40); // The crafting arrow ???
        arrow.draw(matrixStack, 38, 6); //location on screen
    } */

    @Override
    public void setIngredients(AltarRecipe altarRecipe, IIngredients iIngredients) {
        List<List<ItemStack>> itemStacks = new ArrayList<>();

        altarRecipe.getIngredients().forEach(ingredient -> itemStacks.add(Arrays.asList(ingredient.getItems())));
        iIngredients.setInputLists(VanillaTypes.ITEM, itemStacks);
        iIngredients.setOutput(VanillaTypes.ITEM, altarRecipe.getResultItem());
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout iRecipeLayout, @Nonnull AltarRecipe altarRecipe, IIngredients iIngredients) {

        for (int i = 0; i < iIngredients.getInputs(VanillaTypes.ITEM).size(); i++) {
            if (i == 0) {
                iRecipeLayout.getItemStacks().init(i, true, 0, 32);
                iRecipeLayout.getItemStacks().set(i, iIngredients.getInputs(VanillaTypes.ITEM).get(i));
            }
            if (i == 1) {
                iRecipeLayout.getItemStacks().init(i, true, 16, 64);
                iRecipeLayout.getItemStacks().set(i, iIngredients.getInputs(VanillaTypes.ITEM).get(i));
            }
            if (i == 2) {
                iRecipeLayout.getItemStacks().init(i, true, 32, 0);
                iRecipeLayout.getItemStacks().set(i, iIngredients.getInputs(VanillaTypes.ITEM).get(i));
            }
            if (i == 3) {
                iRecipeLayout.getItemStacks().init(i, true, 48, 64);
                iRecipeLayout.getItemStacks().set(i, iIngredients.getInputs(VanillaTypes.ITEM).get(i));
            }
            if (i == 4) {
                iRecipeLayout.getItemStacks().init(i, true, 64, 32);
                iRecipeLayout.getItemStacks().set(i, iIngredients.getInputs(VanillaTypes.ITEM).get(i));
            }
        }

        iRecipeLayout.getItemStacks().init(iIngredients.getInputs(VanillaTypes.ITEM).size(), true, 32, -32);
        iRecipeLayout.getItemStacks().set(iIngredients.getInputs(VanillaTypes.ITEM).size(), iIngredients.getOutputs(VanillaTypes.ITEM).get(0));

    }
}
