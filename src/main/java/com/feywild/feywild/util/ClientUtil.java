package com.feywild.feywild.util;

import com.feywild.feywild.quest.old.MessageQuest;
import com.feywild.feywild.screens.LibrarianScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;

import java.util.LinkedList;
import java.util.List;

public class ClientUtil {

    static List<ItemStack> books = new LinkedList<>();

    public static void openQuestScreen(List<MessageQuest> quest, int id) {
//        Minecraft.getInstance().setScreen(new PixieScreen(new StringTextComponent("Fey Quest"), quest, id));
    }

    public static void resetBooks() {
        books.clear();
    }

    public static void openLibrarianScreen() {
        Minecraft.getInstance().setScreen(new LibrarianScreen(new StringTextComponent("Librarian Screen"), books));
    }

    public static void addBook(ItemStack stack) {
        if (!stack.isEmpty() && !stack.getDisplayName().getString().contains("Guide Book"))
            books.add(stack);
    }
}
