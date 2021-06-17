package com.feywild.feywild.quest;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.Score;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class QuestMap {

    /*
          QUEST MAP

          1 - > 2

          -1 - > -1


          1. Fey dust quest
          2. ???
     */

    static Map<Integer,Integer> questMap = new HashMap<>();
    static Map<Integer, Integer> questLines = new HashMap<>();

    public static void loadQuests(){
        loadLines();
        questMap.put(0,1); // Common begin quest - Fey dust quest
        questMap.put(1,2);

        questMap.put(-1, -1); // Repeatable Fey Dust quest !!!! NEED TO FIND A WAY TO BREAK OUT OF THE LOOP!
    }

    public static void loadLines(){
        questLines.put(0,0);
        questLines.put(1,9);
        questLines.put(2,1);
    }

    public static void updateQuest(Score score){
        score.setScore(questMap.get(score.getScore()));
    }

    public static int getLineNumber(Score score){
       return getLineNumber(score.getScore());
    }

    public static int getLineNumber(int quest){
        return questLines.get(quest);
    }

    public static Map<Integer, Integer> getQuestLines() {
        return questLines;
    }

    public static Map<Integer, Integer> getQuestMap() {
        return questMap;
    }
}
