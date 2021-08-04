package com.feywild.feywild.network.old;

import com.feywild.feywild.quest.old.Quest;
import com.feywild.feywild.quest.old.QuestMap;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

public class QuestMessage {
    String quest;
    UUID player;
    //Read msg from buf
    public QuestMessage(PacketBuffer buf) {
        if(buf.readBoolean()){
            this.player = buf.readUUID();
        }else
            this.player = null;
        this.quest = buf.readUtf(32767);

    }

    //constructor
    public QuestMessage(String quest, UUID player) {
        this.quest = quest;
        this.player = player;
    }

    //Save msg to buf
    public void toBytes(PacketBuffer buf) {
        if(player != null){
            buf.writeBoolean(true);
            buf.writeUUID(player);
        }else
            buf.writeBoolean(false);
        buf.writeUtf(quest);

    }

    //handle package data
    //Update current quest order (we trust the client and that is bad)
    // TODO make it so we can verify the client's claims
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        World world;

        if(player != null) {
            // FIXME will crash on server, fix later
            world = Minecraft.getInstance().level;
            ctx.get().enqueueWork(() -> {
                PlayerEntity entity = world.getPlayerByUUID(player);
                assert entity != null;

                entity.getPersistentData().putString("FWQuest", quest);
            });
        }else{
            PlayerEntity entity = ctx.get().getSender();
            ctx.get().enqueueWork(() -> {
                assert entity != null;

                String data = quest.split("&")[1];
                if(quest.startsWith("remove&"))
                    entity.getPersistentData().putString("FWQuest",entity.getPersistentData().getString("FWQuest").replaceFirst(data, ""));
                if(quest.startsWith("append&")) {
                    if(data.contains("init"))
                    entity.getPersistentData().putString("FWQuest", entity.getPersistentData().getString("FWQuest").replaceFirst("/", "-" + data + "/"));
                    Quest quest1 = QuestMap.getQuest(quest.replace("append&",""));
                    if(Objects.requireNonNull(quest1).getData().contains("NULL")){
                        QuestMap.completeQuest(entity,quest1);
                    }
                }

            });
        }
        ctx.get().setPacketHandled(true);
    }
}
