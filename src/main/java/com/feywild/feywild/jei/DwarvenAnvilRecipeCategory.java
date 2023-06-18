package com.feywild.feywild.jei;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.block.entity.DwarvenAnvil;
import com.feywild.feywild.recipes.DwarvenAnvilRecipe;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class DwarvenAnvilRecipeCategory implements IRecipeCategory<DwarvenAnvilRecipe> {

    public final static RecipeType<DwarvenAnvilRecipe> TYPE = RecipeType.create(FeywildMod.getInstance().modid, "dwarven_anvil", DwarvenAnvilRecipe.class);
    public final static ResourceLocation TEXTURE = new ResourceLocation(FeywildMod.getInstance().modid, "textures/gui/dwarven_anvil_jei.png");

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableStatic manaOverlay;

    public DwarvenAnvilRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 156, 69);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.dwarvenAnvil));
        this.manaOverlay = helper.createDrawable(TEXTURE, 156, 0, 13, 66);
    }

    @Nonnull
    @Override
    public RecipeType<DwarvenAnvilRecipe> getRecipeType() {
        return TYPE;
    }

    @Nonnull
    @Override
    public Component getTitle() {
        return ModBlocks.dwarvenAnvil.getName();
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
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull DwarvenAnvilRecipe recipe, @Nonnull IFocusGroup foci) {
        if (recipe.getSchematics() != null) {
            builder.addSlot(RecipeIngredientRole.INPUT, 19, 2).addIngredients(recipe.getSchematics());
        }
        if (recipe.getInputs().size() >= 1) builder.addSlot(RecipeIngredientRole.INPUT, 71, 9).addIngredients(recipe.getInputs().get(0));
        if (recipe.getInputs().size() >= 2) builder.addSlot(RecipeIngredientRole.INPUT, 101, 23).addIngredients(recipe.getInputs().get(1));
        if (recipe.getInputs().size() >= 3) builder.addSlot(RecipeIngredientRole.INPUT, 85, 44).addIngredients(recipe.getInputs().get(2));
        if (recipe.getInputs().size() >= 4) builder.addSlot(RecipeIngredientRole.INPUT, 59, 44).addIngredients(recipe.getInputs().get(3));
        if (recipe.getInputs().size() >= 5) builder.addSlot(RecipeIngredientRole.INPUT, 44, 23).addIngredients(recipe.getInputs().get(4));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 138, 50).addItemStack(recipe.getResultItem());
    }

    @Override
    public void draw(@Nonnull DwarvenAnvilRecipe recipe, @Nonnull IRecipeSlotsView slots, @Nonnull GuiGraphics graphics, double mouseX, double mouseY) {
        int maskBottom = (int) Math.round((Mth.clamp(recipe.getMana(), 0, DwarvenAnvil.MAX_MANA) / (double) DwarvenAnvil.MAX_MANA) * this.manaOverlay.getHeight());
        this.manaOverlay.draw(graphics, 1, 2, 0, maskBottom, 0, 0);
        graphics.drawString(Minecraft.getInstance().font, Component.translatable("screen.feywild.mana_amount", recipe.getMana()), 100, 0, 0xFFFFFF, false);
    }
}
