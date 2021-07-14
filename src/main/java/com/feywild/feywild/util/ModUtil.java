package com.feywild.feywild.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
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

import java.util.LinkedList;
import java.util.List;

public class ModUtil {
    public static List<LivingEntity> killOnExit = new LinkedList<>();

    public static List<ItemStack> librarianBooks = new LinkedList<>();

    public static boolean inventoryContainsItem(PlayerInventory inventory, Item item) {
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            Item inventoryItem = inventory.getItem(i).getItem();

            if (inventory.getItem(i).getItem() == item.asItem()) {
                return true;
            }
        }
        return false;
    }

    public static ScoreObjective getOrCreateScoreObjective(World world, String name){
        if(world.getScoreboard().getObjective(name) == null)
        return world.getScoreboard().addObjective(name, ScoreCriteria.DUMMY, new StringTextComponent(name), ScoreCriteria.RenderType.INTEGER);
        return world.getScoreboard().getObjective(name);
    }

    public static Score getOrCreatePlayerScore(String playerName, String objectiveName , World world, int defaultScore){
        Score score = world.getScoreboard().getOrCreatePlayerScore(playerName, getOrCreateScoreObjective(world, objectiveName));
        if(!(score.getScore() > 0 || score.getScore() < 0))
        score.setScore(defaultScore);
        return score;
    }

    public static List<String> getTokens (PlayerEntity playerEntity){
        AtomicReference<String> target = new AtomicReference<>("null"), item = new AtomicReference<>("empty"), action = new AtomicReference<>("null"), times = new AtomicReference<>("1");
            target.set(playerEntity.getPersistentData().getString("FWT"));
            target.set(target.get().replace(" ", "_"));

            action.set(playerEntity.getPersistentData().getString("FWA"));

            item.set(playerEntity.getPersistentData().getString("FWU"));



            if(target.get().startsWith("B#")) {
                BlockTags.getAllTags().getTagOrEmpty(new ResourceLocation(target.get().replaceFirst("B#","").toLowerCase())).getValues().forEach(block -> {
                    target.set(target.get() + "/" + block.getRegistryName());
                });
                target.set("#"+target.get());
            }


            if(target.get().startsWith("I#")) {
                ItemTags.getAllTags().getTagOrEmpty(new ResourceLocation(target.get().replaceFirst("I#","").toLowerCase())).getValues().forEach(block -> {
                    target.set(target.get() + "/" + block.getRegistryName());
                });
                target.set("#"+target.get());
            }


            if(item.get().startsWith("B#")) {
                BlockTags.getAllTags().getTagOrEmpty(new ResourceLocation(item.get().replaceFirst("B#","").toLowerCase())).getValues().forEach(block -> {
                    item.set(item.get() + "/" + block.getRegistryName());
                });
                item.set("#"+target.get());
            }


            if(item.get().startsWith("I#")) {
                ItemTags.getAllTags().getTagOrEmpty(new ResourceLocation(item.get().replaceFirst("I#","").toLowerCase())).getValues().forEach(block -> {
                    item.set(item.get() + "/" + block.getRegistryName());
                });
                item.set("#"+target.get());
            }

            times.set(String.valueOf(playerEntity.getPersistentData().getInt("FWR")));

        return Arrays.asList(target.get(),item.get(),action.get(),times.get());
    }


    public static List<String> getTagTokens(String string){
        List<String> items = new LinkedList<>();
        if(string.startsWith("#")) {
            Arrays.stream(string.replaceFirst("#", "").split("/").clone()).iterator().forEachRemaining(items::add);
        }else{
            items.add(string);
        }

        return items;
    }

}
