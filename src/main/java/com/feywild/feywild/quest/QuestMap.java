package com.feywild.feywild.quest;

import com.feywild.feywild.network.FeywildPacketHandler;
import com.feywild.feywild.network.QuestMessage;
import com.feywild.feywild.util.ModUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.Score;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class QuestMap {


    static List<Quest> quests = new LinkedList<>();

    // USABLE SCORES
    public enum Scores{
        FW_Interact,
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



    public static void updateQuest( PlayerEntity entity){

        Score score = ModUtil.getOrCreatePlayerScore(entity.getName().getString(), QuestMap.Scores.FW_Quest.toString(), entity.level);
        Score rep = ModUtil.getOrCreatePlayerScore(entity.getName().getString(), QuestMap.Scores.FW_Reputation.toString(), entity.level);

        //Add the court alignment once base quest are created
        switch (score.getScore()){
            case 0:
                entity.addTag(Courts.SpringAligned.toString());
            break;
        }

        rep.add(getRepNumber(score.getScore()));


        AtomicReference<Quest> questA = new AtomicReference<>();
        AtomicReference<Quest> questB = new AtomicReference<>();
        AtomicInteger link = new AtomicInteger(-1);
        quests.forEach(quest -> {
            if(quest.getId() == score.getScore()){
                link.set(quest.getLink());
                questB.set(quest);
                quests.forEach(quest1 -> {
                    if(link.get() == quest1.getId()){
                        questA.set(quest1);
                    }
                });
            }
        });

        score.setScore(link.get());

            String[] tokens = questA.get().getData().toUpperCase().split(" ").clone();

            Set<String> tags = new HashSet<>();
            for (int i = 0; i < entity.getTags().size(); i++) {
                if (!(entity.getTags().toArray()[i].toString().startsWith("FWT") || entity.getTags().toArray()[i].toString().startsWith("FWA") || entity.getTags().toArray()[i].toString().startsWith("FWU")|| entity.getTags().toArray()[i].toString().startsWith("FWR"))|| entity.getTags().toArray()[i].toString().startsWith("FWFF")|| entity.getTags().toArray()[i].toString().startsWith("FWFU")|| entity.getTags().toArray()[i].toString().startsWith("FWFT"))  {
                        tags.add(entity.getTags().toArray()[i].toString());
                }
            }

            /* MIGHT CREATE SOME CONFLICT WHEN IT COMES TO TAGS*/
            entity.getTags().clear();
            entity.getTags().addAll(tags);


            for (int i = 0; i < tokens.length; i++) {
                switch (tokens[i]) {
                    case "TARGET":
                        entity.addTag("FWT" + tokens[i + 1]);
                        break;
                    case "ACTION":
                        entity.addTag("FWA" + tokens[i + 1]);
                        break;
                    case "USING":
                        entity.addTag("FWU" + tokens[i + 1]);
                        break;
                    case "TIMES":
                        entity.addTag("FWR" + tokens[i + 1]);
                        break;
                    case "T_FROM":
                        entity.addTag("FWFT" + tokens[i + 1]);
                        break;
                    case "U_FROM":
                        entity.addTag("FWFU" + tokens[i + 1]);
                        break;
                    case "FROM":
                        entity.addTag("FWFF" + tokens[i + 1]);
                        break;
                }
            }
            if(!questB.get().canSkip())
                entity.displayClientMessage(new TranslationTextComponent("message.quest_completion_spring"),true);

        FeywildPacketHandler.sendToPlayer(new QuestMessage(entity.getUUID(),score.getScore()),entity);

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
