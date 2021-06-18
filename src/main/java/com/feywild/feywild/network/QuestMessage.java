package com.feywild.feywild.network;

import com.feywild.feywild.block.entity.FeyAltarBlockEntity;
import com.feywild.feywild.quest.QuestMap;
import com.feywild.feywild.setup.ClientProxy;
import com.feywild.feywild.util.ModUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class QuestMessage {

    UUID uuid;
    int quest;

    //Read msg from buf
    public QuestMessage(PacketBuffer buf) {
        this.quest = buf.readInt();
        this.uuid = buf.readUUID();
    }

    //constructor
    public QuestMessage(UUID uuid, int quest) {
        this.uuid = uuid;
        this.quest = quest;
    }

    //Save msg to buf
    public void toBytes(PacketBuffer buf) {
        buf.writeInt(quest);
        buf.writeUUID(uuid);
    }

    //handle package data
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        World world = new ClientProxy().getClientWorld();
        PlayerEntity entity = world.getPlayerByUUID(uuid);
        ctx.get().enqueueWork(() -> {
            Score scores = ModUtil.getOrCreatePlayerScore(entity.getName().getString(),QuestMap.Scores.FW_Quest.toString(),world);
            if(scores.getScore() != quest){
                Score reputation = ModUtil.getOrCreatePlayerScore(entity.getName().getString(), QuestMap.Scores.FW_Reputation.toString(), world);
                reputation.setScore(QuestMap.getRepNumber(quest));
            }

            scores.setScore(quest);
        });
        ctx.get().setPacketHandled(true);
    }
}
