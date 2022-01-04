package com.feywild.feywild.network;

import com.feywild.feywild.trade.recipe.TradeRecipe;
import com.google.common.collect.ImmutableList;
import io.github.noeppi_noeppi.libx.network.PacketSerializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class TradesSerializer implements PacketSerializer<TradesSerializer.Message> {

    @Override
    public Class<Message> messageClass() {
        return Message.class;
    }

    @Override
    public void encode(Message msg, FriendlyByteBuf buffer) {
        buffer.writeVarInt(msg.recipes.size());
        msg.recipes.forEach(recipe -> {
            buffer.writeResourceLocation(recipe.id);
            buffer.writeVarInt(recipe.level);
            buffer.writeVarInt(recipe.trades.size());
            recipe.trades.forEach(trade -> {
                buffer.writeVarInt(trade.input.size());
                trade.input.forEach(buffer::writeItem);
                buffer.writeVarInt(trade.additional.size());
                trade.additional.forEach(buffer::writeItem);
                buffer.writeVarInt(trade.output.size());
                trade.output.forEach(buffer::writeItem);
            });
        });
    }

    @Override
    public Message decode(FriendlyByteBuf buffer) {
        ImmutableList.Builder<TradeRecipe> recipes = ImmutableList.builder();
        int size = buffer.readVarInt();
        for (int i = 0; i < size; i++) {
            ResourceLocation id = buffer.readResourceLocation();
            int level = buffer.readVarInt();
            ImmutableList.Builder<TradeRecipe.Entry> trades = ImmutableList.builder();
            int tradeSize = buffer.readVarInt();
            for (int j = 0; j < tradeSize; j++) {
                trades.add(new TradeRecipe.Entry(
                        readStackList(buffer),
                        readStackList(buffer),
                        readStackList(buffer)
                ));
            }
            recipes.add(new TradeRecipe(id, level, trades.build()));
        }
        return new Message(recipes.build());
    }

    private List<ItemStack> readStackList(FriendlyByteBuf buffer) {
        ImmutableList.Builder<ItemStack> stacks = ImmutableList.builder();
        int size = buffer.readVarInt();
        for (int i = 0; i < size; i++) {
            stacks.add(buffer.readItem());
        }
        return stacks.build();
    }

    public static class Message {

        public final List<TradeRecipe> recipes;

        public Message(List<TradeRecipe> recipes) {
            this.recipes = recipes;
        }
    }
}
