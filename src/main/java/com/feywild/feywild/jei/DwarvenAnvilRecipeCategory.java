package com.feywild.feywild.jei;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.block.entity.DwarvenAnvil;
import com.feywild.feywild.recipes.DwarvenAnvilRecipe;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class DwarvenAnvilRecipeCategory implements IRecipeCategory<DwarvenAnvilRecipe> {

    public final static ResourceLocation UID = new ResourceLocation(FeywildMod.getInstance().modid, "dwarven_anvil");
    public final static ResourceLocation TEXTURE = new ResourceLocation(FeywildMod.getInstance().modid, "textures/gui/dwarven_anvil_jei.png");

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableStatic manaOverlay;

    public DwarvenAnvilRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 156, 69);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(ModBlocks.dwarvenAnvil));
        this.manaOverlay = helper.createDrawable(TEXTURE, 156, 0, 13, 66);
    }

    @Nonnull
    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Nonnull
    @Override
    public Class<? extends DwarvenAnvilRecipe> getRecipeClass() {
        return DwarvenAnvilRecipe.class;
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
    public void setIngredients(DwarvenAnvilRecipe recipe, IIngredients ii) {
        List<Ingredient> inputs = new ArrayList<>();
        inputs.add(recipe.getSchematics());
        inputs.addAll(recipe.getInputs());
        ii.setInputIngredients(inputs);
        ii.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout layout, @Nonnull DwarvenAnvilRecipe recipe, IIngredients ii) {
        layout.getItemStacks().init(0, true, 18, 1);
        if (ii.getInputs(VanillaTypes.ITEM).size() >= 2) layout.getItemStacks().init(1, true, 70, 8);
        if (ii.getInputs(VanillaTypes.ITEM).size() >= 3) layout.getItemStacks().init(2, true, 100, 22);
        if (ii.getInputs(VanillaTypes.ITEM).size() >= 4) layout.getItemStacks().init(3, true, 84, 43);
        if (ii.getInputs(VanillaTypes.ITEM).size() >= 5) layout.getItemStacks().init(4, true, 58, 43);
        if (ii.getInputs(VanillaTypes.ITEM).size() >= 6) layout.getItemStacks().init(5, true, 43, 22);
        layout.getItemStacks().init(ii.getInputs(VanillaTypes.ITEM).size(), false, 137, 49);
        layout.getItemStacks().set(ii);
    }

    @Override
    public void draw(@Nonnull DwarvenAnvilRecipe recipe, @Nonnull PoseStack poseStack, double mouseX, double mouseY) {
        int maskBottom = (int) Math.round((Mth.clamp(recipe.getMana(), 0, DwarvenAnvil.MAX_MANA) / (double) DwarvenAnvil.MAX_MANA) * this.manaOverlay.getHeight());
        this.manaOverlay.draw(poseStack, 1, 2, 0, maskBottom, 0, 0);
        Minecraft.getInstance().font.draw(poseStack, new TranslatableComponent("screen.feywild.mana_amount", recipe.getMana()), 100, 0, 0xFFFFFF);
    }
}
