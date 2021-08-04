package com.feywild.feywild.quest.old;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.events.QuestCompletionEvent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class QuestMap {

    static List<Quest> quests = new LinkedList<>();

    public static int getCourtScore(String court, World world) {
        AtomicInteger score = new AtomicInteger();
        world.players().forEach(playerEntity -> {
            switch (court) {
                case "SpringAligned":
                    if (playerEntity.getTags().contains(Courts.SpringAligned.toString())) {
                        int rep = playerEntity.getPersistentData().getInt("FWRep");
                        score.addAndGet(rep);
                    }
                    break;
                case "AutumnAligned":
                    if (playerEntity.getTags().contains(Courts.AutumnAligned.toString())) {
                        int rep = playerEntity.getPersistentData().getInt("FWRep");
                        score.addAndGet(rep);
                    }
                    break;
                case "WinterAligned":
                    if (playerEntity.getTags().contains(Courts.WinterAligned.toString())) {
                        int rep = playerEntity.getPersistentData().getInt("FWRep");
                        score.addAndGet(rep);
                    }
                    break;
                case "SummerAligned":
                    if (playerEntity.getTags().contains(Courts.SummerAligned.toString())) {
                        int rep = playerEntity.getPersistentData().getInt("FWRep");
                        score.addAndGet(rep);
                    }
                    break;
            }
        });
        return score.get();
    }

    public static boolean hasActiveQuest(@Nonnull String questProgressData, @Nonnull Quest quest) {
        return Arrays.stream(questProgressData.split("/")[0].split("-")).anyMatch(s -> s.contains(quest.getId().toString()));
    }

    public static String getDataBasedOnAction(@Nonnull String questProgressData, @Nonnull String action) {
        StringBuilder data = new StringBuilder();
        String[] dataArr = questProgressData.split("-");
        for (Quest quest : quests) {
            Arrays.stream(dataArr).iterator().forEachRemaining(s -> {
                if (s.equalsIgnoreCase(quest.getId().toString()) && quest.getData().contains("ACTION " + action.toLowerCase())) {
                    data.append(quest.getData()).append("&");
                }
            });
        }
        return data.toString();
    }

    public static List<Quest> getQuests() {
        return quests;
    }

    public static boolean isQuestRequired(@Nonnull Quest quest) {
        return quests.stream().anyMatch(q -> q.getRequiredQuests().contains(quest.getId()));
    }

    public static void clearQuests() {
        quests.clear();
    }

    public static Quest getQuest(@Nonnull String id) {
        for (Quest quest : quests) {
            if (quest.getId().toString().equalsIgnoreCase(id)) {
                return quest;
            }
        }
        return null;
    }

    public static void completeQuest(@Nonnull PlayerEntity entity, @Nonnull Quest quest) {

        int rep = entity.getPersistentData().getInt("FWRep");
        String questProgressData = entity.getPersistentData().getString("FWQuest");

        if (hasActiveQuest(questProgressData, quest) && !entity.level.isClientSide) {

            QuestCompletionEvent event = new QuestCompletionEvent(entity, quest, rep);
            MinecraftForge.EVENT_BUS.post(event);

            if (!event.isCanceled()) {
                // Set court alignments
                if (quest.getId().equals(new ResourceLocation(FeywildMod.getInstance().modid, "spring_init"))) {
                    entity.addTag(Courts.SpringAligned.toString());
                } else if (quest.getId().equals(new ResourceLocation(FeywildMod.getInstance().modid, "summer_init"))) {
                    entity.addTag(Courts.SummerAligned.toString());
                } else if (quest.getId().equals(new ResourceLocation(FeywildMod.getInstance().modid, "autumn_init"))) {
                    entity.addTag(Courts.AutumnAligned.toString());
                } else if (quest.getId().equals(new ResourceLocation(FeywildMod.getInstance().modid, "winter_init"))) {
                    entity.addTag(Courts.WinterAligned.toString());
                }

                //Reward Completion
                entity.getPersistentData().putInt("FWRep", rep + quest.getRep());
                entity.addItem(quest.getStack().copy());

                // update questProgress
                questProgressData = questProgressData.replaceFirst(quest.getId().toString(), "");
                questProgressData = questProgressData + "-" + quest.getId().toString();

                String[] backQuests = questProgressData.split("/")[1].split("-");

                // Update quest data - unused quests

                assert backQuests.length > 0;
                for (String s : backQuests) {
                    boolean remove = true;
                    for (Quest quest2 : quests) {
                        if (quest2.getRequiredQuests().contains(new ResourceLocation(s)) && !questProgressData.contains(quest2.getId().toString())) {
                            remove = false;
                            break;
                        }
                    }
                    if (remove) {
                        questProgressData = questProgressData.replaceFirst(s, "");
                    }
                }

                for (Quest quest1 : quests) {

                    boolean add = true;
                    for (ResourceLocation res : quest1.getRequiredQuests()) {
                        if (!questProgressData.split("/")[1].contains(res.toString())) {
                            add = false;
                            break;
                        }
                    }
                    if (add && !questProgressData.contains(quest1.getId().toString())) {
                        questProgressData = quest1.getId().toString().concat("-").concat(questProgressData);
                    }
                }

                // Do some clean up
                /* Never use - / in the json files */
                questProgressData = questProgressData.replaceFirst("--", "-");
                questProgressData = questProgressData.replaceFirst("/-", "/");
                questProgressData = questProgressData.replaceFirst("-/", "/");

                //Add repeatable quests
                if (quest.isRepeatable())
                    questProgressData = questProgressData.replaceFirst("/", "-" + quest.getId().toString() + "/");

                questProgressData = questProgressData.startsWith("-") ? questProgressData.replaceFirst("-", "") : questProgressData;

                entity.getPersistentData().putString("FWQuest", questProgressData);

                entity.displayClientMessage(new TranslationTextComponent("message.quest_completion_spring"), true);
                // TODO networking
//                FeywildNetwork.sendToPlayer(new QuestMessage(questProgressData, entity.getUUID()), entity);
            }
        }

    }

    // get quest tokens
    public static HashMap<String, String> getQuestData(Quest quest) {
        HashMap<String, String> ret = new HashMap<>();
        String[] tokens = quest.getData().toUpperCase().split(" ").clone();

        for (int i = 0; i < tokens.length; i++) {
            switch (tokens[i]) {
                case "TARGET":
                    ret.put("TARGET", tokens[i + 1]);
                    break;
                case "ACTION":
                    ret.put("ACTION", tokens[i + 1]);
                    break;
                case "USING":
                    ret.put("USING", tokens[i + 1]);
                    break;
                case "TIMES":
                    ret.put("TIMES", tokens[i + 1]);
                    break;
            }
        }
        return ret;
    }

    public enum Courts {
        SpringAligned,
        AutumnAligned,
        WinterAligned,
        SummerAligned
    }

}
