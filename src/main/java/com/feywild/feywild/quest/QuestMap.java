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
import java.util.function.Supplier;

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
                        Score rep1 = ModUtil.getOrCreatePlayerScore(playerEntity.getName().getString(), Scores.FW_Reputation.toString(), world,0);
                        score.addAndGet(rep1.getScore());
                    }
                break;
                case "AutumnAligned":
                    if(playerEntity.getTags().contains(Courts.AutumnAligned.toString())){
                        Score rep1 = ModUtil.getOrCreatePlayerScore(playerEntity.getName().getString(), Scores.FW_Reputation.toString(), world,0);
                        score.addAndGet(rep1.getScore());
                    }
                    break;
                case "WinterAligned":
                    if(playerEntity.getTags().contains(Courts.WinterAligned.toString())){
                        Score rep1 = ModUtil.getOrCreatePlayerScore(playerEntity.getName().getString(), Scores.FW_Reputation.toString(), world,0);
                        score.addAndGet(rep1.getScore());
                    }
                    break;
                case "SummerAligned":
                    if(playerEntity.getTags().contains(Courts.SummerAligned.toString())){
                        Score rep1 = ModUtil.getOrCreatePlayerScore(playerEntity.getName().getString(), Scores.FW_Reputation.toString(), world,0);
                        score.addAndGet(rep1.getScore());
                    }
                    break;
            }
        });
        return score.get();
    }



    public static void updateQuest( PlayerEntity entity){

        Score questId = ModUtil.getOrCreatePlayerScore(entity.getName().getString(), QuestMap.Scores.FW_Quest.toString(), entity.level,0);
        Score rep = ModUtil.getOrCreatePlayerScore(entity.getName().getString(), QuestMap.Scores.FW_Reputation.toString(), entity.level,0);

        //Add the court alignment once base quest are created
        switch (questId.getScore()){
            case 0:
                entity.addTag(Courts.SpringAligned.toString());
            break;
        }

        rep.add(getRepNumber(questId.getScore()));


        AtomicReference<Quest> questA = new AtomicReference<>();
        AtomicReference<Quest> questB = new AtomicReference<>();
        AtomicInteger link = new AtomicInteger(-1);
        quests.forEach(quest -> {
            if(quest.getId() == questId.getScore()){
                link.set(quest.getLink());
                questB.set(quest);
                quests.forEach(quest1 -> {
                    if(link.get() == quest1.getId()){
                        questA.set(quest1);
                    }
                });
            }
        });

        questId.setScore(link.get());

        storeQuestData(entity);

            if (!questB.get().canSkip())
                entity.displayClientMessage(new TranslationTextComponent("message.quest_completion_spring"), true);

            FeywildPacketHandler.sendToPlayer(new QuestMessage(entity.getUUID(), questId.getScore()), entity);
        }


    public static void storeQuestData(PlayerEntity entity) {
        AtomicReference<Quest> quest = new AtomicReference<>();
        Score score = ModUtil.getOrCreatePlayerScore(entity.getName().getString(), QuestMap.Scores.FW_Quest.toString(), entity.level,0);

        quests.forEach(quest1 -> {
                if(quest1.getId() == score.getScore()){
                    quest.set(quest1);
                }
            });

        if (!entity.level.isClientSide) {
            String[] tokens = quest.get().getData().toUpperCase().split(" ").clone();


            //REMOVE
            entity.getPersistentData().remove("FWT");
            entity.getPersistentData().remove("FWA");
            entity.getPersistentData().remove("FWU");
            entity.getPersistentData().remove("FWR");

            for (int i = 0; i < tokens.length; i++) {
                switch (tokens[i]) {
                    case "TARGET":
                        entity.getPersistentData().putString("FWT", tokens[i + 1]);
                        break;
                    case "ACTION":
                        entity.getPersistentData().putString("FWA", tokens[i + 1]);
                        break;
                    case "USING":
                        entity.getPersistentData().putString("FWU", tokens[i + 1]);
                        break;
                    case "TIMES":
                        entity.getPersistentData().putInt("FWR", Integer.parseInt(tokens[i + 1]));
                        break;
                }
            }
        }
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
