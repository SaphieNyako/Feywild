package com.feywild.feywild.patchouli.component;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.crafting.Ingredient;
import vazkii.patchouli.api.IComponentRenderContext;
import vazkii.patchouli.api.ICustomComponent;
import vazkii.patchouli.api.IVariable;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.UnaryOperator;

public class CircularIngredientsComponent implements ICustomComponent {

    public IVariable items;

    private transient int x;
    private transient int y;
    private transient List<Ingredient> ingredients;

    @Override
    public void build(int componentX, int componentY, int pageNum) {
        this.x = componentX;
        this.y = componentY;
    }

    @Override
    public void onVariablesAvailable(UnaryOperator<IVariable> lookup) {
        IVariable ingredients = lookup.apply(items);
        if (ingredients.unwrap().isJsonNull()) {
            this.ingredients = List.of();
        } else {
            this.ingredients = ingredients.asStream().map(var -> var.as(Ingredient.class)).toList();
        }
    }

    @Override
    public void render(@Nonnull GuiGraphics graphics, @Nonnull IComponentRenderContext context, float partialTick, int mouseX, int mouseY) {
        if (this.ingredients == null) return;
        float degreePerInput = 360f / (float) this.ingredients.size();
        float angle = 0;
        for (Ingredient ingredient : ingredients) {
            this.renderIngredientAtAngle(graphics, context, angle, ingredient, mouseX, mouseY);
            angle += degreePerInput;
        }
    }

    private void renderIngredientAtAngle(GuiGraphics graphics, IComponentRenderContext context, float angle, Ingredient ingredient, int mouseX, int mouseY) {
        if (!ingredient.isEmpty()) {
            angle -= 90f;
            int radius = 32;
            double xPos = (this.x + Math.cos(angle * Math.PI / 180d) * radius) + radius;
            double yPos = (this.y + Math.sin(angle * Math.PI / 180d) * radius) + radius;
            context.renderIngredient(graphics, (int) xPos, (int) yPos, mouseX, mouseY, ingredient);
        }
    }
}
