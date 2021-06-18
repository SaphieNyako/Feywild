package com.feywild.feywild.quest;

import com.feywild.feywild.util.ModUtil;
import net.minecraft.scoreboard.Score;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class QuestMap {


    static Map<Integer, List<Integer>> questMap = new HashMap<>();

    public static void loadQuests(){
        questMap.put(0, Arrays.asList(1,1,25,1)); // Welcome message
        questMap.put(1, Arrays.asList(2,9,7,0));// Common begin quest - Fey dust quest
        questMap.put(2,Arrays.asList(3,1,0,1)); // Congrats message
        questMap.put(3, Arrays.asList(4,1,10,0)); // Cake quest
        questMap.put(4, Arrays.asList(5,0,0,1));
    }

    // USABLE SCORES
    public enum Scores{
        FW_FeyDustUse,
        FW_Quest,
        FW_Reputation
    }

    public enum Courts{
        SpringAligned,
        AutumnAligned,
        WinterAligned,
        SummerAligned
    }


    public static int getCourtScore(String court, World world){
        AtomicInteger score = new AtomicInteger();
        world.players().forEach(playerEntity -> {
            switch (court){
                case "SpringAligned":
                    if(playerEntity.getTags().contains(Courts.SpringAligned.toString())){
                        Score rep1 = ModUtil.getOrCreatePlayerScore(playerEntity.getName().getString(), Scores.FW_Reputation.toString(), world);
                        score.addAndGet(rep1.getScore());
                    }
                break;
                case "AutumnAligned":
                    if(playerEntity.getTags().contains(Courts.AutumnAligned.toString())){
                        Score rep1 = ModUtil.getOrCreatePlayerScore(playerEntity.getName().getString(), Scores.FW_Reputation.toString(), world);
                        score.addAndGet(rep1.getScore());
                    }
                    break;
                case "WinterAligned":
                    if(playerEntity.getTags().contains(Courts.WinterAligned.toString())){
                        Score rep1 = ModUtil.getOrCreatePlayerScore(playerEntity.getName().getString(), Scores.FW_Reputation.toString(), world);
                        score.addAndGet(rep1.getScore());
                    }
                    break;
                case "SummerAligned":
                    if(playerEntity.getTags().contains(Courts.SummerAligned.toString())){
                        Score rep1 = ModUtil.getOrCreatePlayerScore(playerEntity.getName().getString(), Scores.FW_Reputation.toString(), world);
                        score.addAndGet(rep1.getScore());
                    }
                    break;
            }
        });
        return score.get();
    }



    public static void updateQuest(Score score, Score rep){
        rep.setScore(rep.getScore() + getRepNumber(score.getScore()));
        score.setScore(questMap.get(score.getScore()).get(0));
    }

    public static int getLineNumber(int quest){
        return questMap.get(quest).get(1);
    }


    public static int getRepNumber(int quest) {return questMap.get(quest).get(2);}

    public static boolean getCanSkip(int quest){
        return questMap.get(quest).get(3) == 1;
    }

    public static Map<Integer, List<Integer>> getQuestMap() {
        return questMap;
    }
}
