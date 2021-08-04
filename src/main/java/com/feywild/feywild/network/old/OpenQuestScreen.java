package com.feywild.feywild.network.old;

import com.feywild.feywild.quest.MessageQuest;
import com.feywild.feywild.util.ClientUtil;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class OpenQuestScreen {

    //buf has a 256 byte cap
    List<MessageQuest> quest = new LinkedList<>();
    int id;
    //Read msg from buf
    public OpenQuestScreen(PacketBuffer buf) {
        int count = buf.readInt();
        for (int i = 0; i < count; i++) {
            if (count == 1) {
                quest.add(new MessageQuest(buf.readResourceLocation(), buf.readUtf(32767), null, null, buf.readBoolean()));
            } else {
                quest.add(new MessageQuest(buf.readResourceLocation(),null,buf.readUtf(32767), buf.readItem(), false));
            }
        }
        if(count != 1){
            id = buf.readInt();
        }
    }

    //constructor
    public OpenQuestScreen(List<MessageQuest> quest, int id) {
        this.quest = quest;
        this.id = id;
    }

    //Save msg to buf
    public void toBytes(PacketBuffer buf) {
        buf.writeInt(quest.size());
            quest.forEach(quest1 -> {
                buf.writeResourceLocation(quest1.getId());
                if(quest.size() == 1) {
                    buf.writeUtf(quest1.getText());
                    buf.writeBoolean(quest1.canSkip());
                }else {
                    buf.writeUtf(quest1.getName());
                    buf.writeItem(quest1.getIcon());
                }
            });
            if(quest.size() != 1){
                buf.writeInt(id);
            }
    }

    //handle package data
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork( () -> {
            try{
                if(ctx.get().getDirection().getReceptionSide().isClient()) {
                    ClientUtil.openQuestScreen(quest, id);
                    ctx.get().setPacketHandled(true);
                }
            }catch (Exception e) {
                ctx.get().setPacketHandled(false);
                e.printStackTrace();
            }
        });

        ctx.get().setPacketHandled(true);
    }
}
