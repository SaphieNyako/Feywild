package com.feywild.feywild.quest;

import com.feywild.feywild.util.ModUtil;
import net.minecraft.scoreboard.Score;
import net.minecraft.world.World;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class QuestMap {


    static List<Quest> quests = new LinkedList<>();

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

        AtomicInteger i = new AtomicInteger();
        quests.forEach(quest -> {
            if(quest.getId() == score.getScore()){
                i.set(quest.getLink());
            }
        });

        score.setScore(i.get());
    }

    public static int getLineNumber(int id){

        AtomicInteger i = new AtomicInteger();
        quests.forEach(quest -> {
            if(quest.getId() == id){
                i.set(quest.getLines());
            }
        });

        return i.get();
    }


    public static int getRepNumber(int id) {
        AtomicInteger i = new AtomicInteger();
        quests.forEach(quest -> {
            if(quest.getId() == id){
                i.set(quest.getRep());
            }
        });

        return i.get();
    }

    public static boolean getCanSkip(int id){

        AtomicBoolean i = new AtomicBoolean();
        quests.forEach(quest -> {
            if(quest.getId() == id){
                i.set(quest.canSkip());
            }
        });

        return i.get();
    }

}
