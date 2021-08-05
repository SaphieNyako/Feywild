package com.feywild.feywild.util;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreCriteria;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class ModUtil {
    
    public static List<ItemStack> librarianBooks;

    public static void setLibrarianBooks(List<ItemStack> books) {
        librarianBooks = books;
    }

    public static List<ItemStack> getLibrarianBooks() {
        return librarianBooks;
    }

    public static boolean inventoryContainsItem(PlayerInventory inventory, Item item) {
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            Item inventoryItem = inventory.getItem(i).getItem();

            if (inventory.getItem(i).getItem() == item.asItem()) {
                return true;
            }
        }
        return false;
    }

    public static ScoreObjective getOrCreateScoreObjective(World world, String name) {
        if (world.getScoreboard().getObjective(name) == null)
            return world.getScoreboard().addObjective(name, ScoreCriteria.DUMMY, new StringTextComponent(name), ScoreCriteria.RenderType.INTEGER);
        return world.getScoreboard().getObjective(name);
    }

    public static Score getOrCreatePlayerScore(String playerName, String objectiveName, World world, int defaultScore) {
        Score score = world.getScoreboard().getOrCreatePlayerScore(playerName, getOrCreateScoreObjective(world, objectiveName));
        if (!(score.getScore() > 0 || score.getScore() < 0))
            score.setScore(defaultScore);
        return score;
    }
}
