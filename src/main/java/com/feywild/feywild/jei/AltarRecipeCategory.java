package com.feywild.feywild.jei;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.recipes.AltarRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class AltarRecipeCategory implements IRecipeCategory<AltarRecipe> {

    public final static RecipeType<AltarRecipe> TYPE = RecipeType.create(FeywildMod.getInstance().modid, "altar", AltarRecipe.class);
    public final static ResourceLocation TEXTURE = FeywildMod.getInstance().resource("textures/gui/fey_altar_jei.png");

    private final IDrawable background;
    private final IDrawable icon;

    public AltarRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 85, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.summerFeyAltar));
    }

    @Nonnull
    @Override
    public RecipeType<AltarRecipe> getRecipeType() {
        return TYPE;
    }

    @Nonnull
    @Override
    public Component getTitle() {
        return Component.translatable("Fey Altar");
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Nonnull
    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull AltarRecipe recipe, @Nonnull IFocusGroup foci) {
        double anglePerItem = (2 * Math.PI) / recipe.getIngredients().size();
        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            double xd = Math.round(29 * Math.sin(anglePerItem * i));
            double yd = -Math.round(29 * Math.cos(anglePerItem * i));
            int x = (int) Math.round(44 + xd - 8);
            int z = (int) Math.round(46 + yd - 8);
            builder.addSlot(RecipeIngredientRole.INPUT, x, z).addIngredients(recipe.getIngredients().get(i));
        }
        builder.addSlot(RecipeIngredientRole.OUTPUT, 36, 38).addItemStack(recipe.getResultItem());
    }
}
