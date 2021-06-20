package com.feywild.feywild.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreCriteria;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ModUtil {

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

    public static Score getOrCreatePlayerScore(String playerName, String objectiveName , World world){
        Score score = world.getScoreboard().getOrCreatePlayerScore(playerName, getOrCreateScoreObjective(world, objectiveName));
        if(!(score.getScore() > 0 || score.getScore() < 0))
        score.setScore(0);
        return score;
    }

    public static List<String> getTokens (PlayerEntity playerEntity){
        AtomicReference<String> target = new AtomicReference<>("null"), item = new AtomicReference<>("empty"), action = new AtomicReference<>("null"), times = new AtomicReference<>("1"), from = new AtomicReference<>("generic"), fromU = new AtomicReference<>("generic"), fromT = new AtomicReference<>("generic");
        playerEntity.getTags().forEach(s -> {
            if(s.startsWith("FWT")){
                target.set(s.substring(3));
                target.set(target.get().replace(" ", "_"));
            }else if(s.startsWith("FWA")){
                action.set(s.substring(3));
            }else if(s.startsWith("FWU")){
                item.set(s.substring(3));
            }else if(s.startsWith("FWR")){
                times.set(s.substring(3));
            }if(s.startsWith("FWFF")){
                from.set(s.substring(4));
                item.set(from.get().concat(":" + item.get()));
                target.set(from.get().concat(":" + target.get()));
            }if(s.startsWith("FWFT")){
                fromT.set(s.substring(4));
                target.set(fromT.get().concat(":" + target.get()));
            }if(s.startsWith("FWFU")){
                fromU.set(s.substring(4));
                item.set(fromU.get().concat(":" + item.get()));
            }
        });

        return Arrays.asList(target.get(),item.get(),action.get(),times.get());
    }
}
