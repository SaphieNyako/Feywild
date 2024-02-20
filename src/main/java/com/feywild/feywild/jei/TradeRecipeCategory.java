package com.feywild.feywild.jei;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.trade.recipe.TradeRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class TradeRecipeCategory implements IRecipeCategory<TradeRecipe> {

    public final static RecipeType<TradeRecipe> TYPE = RecipeType.create(FeywildMod.getInstance().modid, "trades", TradeRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable slot;
    private final IDrawable rightArrow;

    public TradeRecipeCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(178, 192);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModItems.marketRuneStone));
        this.slot = helper.getSlotDrawable();
        this.rightArrow = helper.createDrawable(new ResourceLocation("minecraft", "textures/gui/container/furnace.png"), 176, 14, 24, 16);
    }

    @Nonnull
    @Override
    public RecipeType<TradeRecipe> getRecipeType() {
        return TYPE;
    }

    @Nonnull
    @Override
    public Component getTitle() {
        return Component.translatable("jei.trades.title");
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

    @Override
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull TradeRecipe recipe, @Nonnull IFocusGroup foci) {
        for (int i = 0; i < recipe.trades.size(); i++) {
            TradeRecipe.Entry trade = recipe.trades.get(i);
            int xOff = recipe.trades.size() <= 8 ? 43 : (i / 8) * 93 + 1;
            int yOff = 27 + (20 * (i % 8));
            builder.addSlot(RecipeIngredientRole.INPUT, xOff + 1, yOff).addItemStacks(trade.input);
            if (!trade.additional.isEmpty()) builder.addSlot(RecipeIngredientRole.INPUT, xOff + 21, yOff).addItemStacks(trade.additional);
            builder.addSlot(RecipeIngredientRole.OUTPUT, xOff + 67, yOff).addItemStacks(trade.output);
        }
    }

    @Override
    public void draw(@Nonnull TradeRecipe recipe, @Nonnull IRecipeSlotsView slots, @Nonnull GuiGraphics graphics, double mouseX, double mouseY) {
        Font font = Minecraft.getInstance().font;
        int textX = 89 - (font.width(recipe.id.getPath()) / 2);
        graphics.drawString(font, getText(recipe.id.getPath()), textX, 3, 0xFFFFFF, true);
        Component levelText = Component.translatable("jei.trades.level", recipe.level);
        graphics.drawString(font, levelText, textX, 14, 0xFFFFFF, true);
        for (int i = 0; i < recipe.trades.size(); i++) {
            int xOff = recipe.trades.size() <= 8 ? 42 : (i / 8) * 93;
            int yOff = 26 + (20 * (i % 8));
            slot.draw(graphics, xOff + 1, yOff);
            slot.draw(graphics, xOff + 21, yOff);
            slot.draw(graphics, xOff + 67, yOff);
            rightArrow.draw(graphics, xOff + 41, yOff + 1);
        }
    }

    public String getText(String text) {
        return text.replace("_", " ").replace("/trades", " ");
    }
}
