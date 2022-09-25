package com.feywild.feywild.quest.reward;

import com.google.gson.JsonObject;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;

public class CommandReward implements RewardType<String> {

    public static final CommandReward INSTANCE = new CommandReward();

    private CommandReward() {

    }

    @Override
    public Class<String> element() {
        return String.class;
    }

    @Override
    public void grantReward(ServerPlayer player, String element) {
        if (player.getServer() == null) return;
        Commands commands = player.getServer().getCommands();
        // Find the player that completed the quest with @s
        CommandSourceStack source = player.getServer().createCommandSourceStack().withEntity(player);
        commands.performPrefixedCommand(source, element);
    }

    @Override
    public String fromJson(JsonObject json) {
        return json.get("command").getAsString();
    }

    @Override
    public JsonObject toJson(String element) {
        JsonObject json = new JsonObject();
        json.addProperty("command", element);
        return json;
    }
}
