package com.feywild.feywild.util;

import com.feywild.feywild.quest.old.QuestMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreCriteria;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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

    public static String[][] getTokens(PlayerEntity playerEntity, String event) {
        AtomicReference<String> target = new AtomicReference<>("null"), item = new AtomicReference<>("empty");

        String[] data = playerEntity.getPersistentData().getString("FWQuest").split("/");

        if(data.length > 0) {
            String questData = data[0];
            questData = QuestMap.getDataBasedOnAction(questData, event);

            String[] array = questData.split("&");
            String[] innerArr;

            String[][] ret = new String[array.length][4];

            for (int i = 0; i < array.length; i++) {
                innerArr = array[i].split(" ");
                for (int j = 0; j < innerArr.length; j++) {
                    if (innerArr[j].equalsIgnoreCase("target")) {
                        target.set(innerArr[j + 1]);
                        target.set(target.get().replace(" ", "_"));
                        if (target.get().startsWith("B#")) {
                            BlockTags.getAllTags().getTagOrEmpty(new ResourceLocation(target.get().replaceFirst("B#", "").toLowerCase())).getValues().forEach(block -> target.set(target.get() + "/" + block.getRegistryName()));
                            target.set("#" + target.get());
                        } else if (target.get().startsWith("I#")) {
                            ItemTags.getAllTags().getTagOrEmpty(new ResourceLocation(target.get().replaceFirst("I#", "").toLowerCase())).getValues().forEach(block -> target.set(target.get() + "/" + block.getRegistryName()));
                            target.set("#" + target.get());
                        }
                        ret[i][0] = target.get();

                    } else if (innerArr[j].equalsIgnoreCase("using")) {
                        item.set(innerArr[j + 1]);
                        if (item.get().startsWith("B#")) {
                            BlockTags.getAllTags().getTagOrEmpty(new ResourceLocation(item.get().replaceFirst("B#", "").toLowerCase())).getValues().forEach(block -> item.set(item.get() + "/" + block.getRegistryName()));
                            item.set("#" + target.get());
                        } else if (item.get().startsWith("I#")) {
                            ItemTags.getAllTags().getTagOrEmpty(new ResourceLocation(item.get().replaceFirst("I#", "").toLowerCase())).getValues().forEach(block -> item.set(item.get() + "/" + block.getRegistryName()));
                            item.set("#" + target.get());
                        }

                        ret[i][1] = item.get();
                    } else if (innerArr[j].equalsIgnoreCase("times")) {
                        ret[i][2] = innerArr[j + 1];
                    } else if (innerArr[j].equalsIgnoreCase("id")) {
                        ret[i][3] = innerArr[j + 1];
                    }
                }
            }
            return ret;
        }
        return new String[1][4];

    }


    public static List<String> getTagTokens( String string) {
        List<String> items = new LinkedList<>();
        if (string != null && string.startsWith("#")) {
            Arrays.stream(string.replaceFirst("#", "").split("/").clone()).iterator().forEachRemaining(items::add);
        } else {
            items.add(string);
        }

        return items;
    }

}
