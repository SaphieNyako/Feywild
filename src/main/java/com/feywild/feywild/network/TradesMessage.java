package com.feywild.feywild.network;

import com.feywild.feywild.trade.recipe.TradeRecipe;
import com.google.common.collect.ImmutableList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.network.NetworkEvent;
import org.moddingx.libx.network.PacketHandler;
import org.moddingx.libx.network.PacketSerializer;

import java.util.List;
import java.util.function.Supplier;

public record TradesMessage(List<TradeRecipe> recipes) {
    
    public static class Serializer implements PacketSerializer<TradesMessage> {

        @Override
        public Class<TradesMessage> messageClass() {
            return TradesMessage.class;
        }

        @Override
        public void encode(TradesMessage msg, FriendlyByteBuf buffer) {
            buffer.writeVarInt(msg.recipes().size());
            msg.recipes().forEach(recipe -> {
                buffer.writeResourceLocation(recipe.id);
                buffer.writeVarInt(recipe.level);
                buffer.writeVarInt(recipe.trades.size());
                recipe.trades.forEach(trade -> {
                    PacketUtil.writeList(trade.input, buffer, FriendlyByteBuf::writeItem);
                    PacketUtil.writeList(trade.additional, buffer, FriendlyByteBuf::writeItem);
                    PacketUtil.writeList(trade.output, buffer, FriendlyByteBuf::writeItem);
                });
            });
        }

        @Override
        public TradesMessage decode(FriendlyByteBuf buffer) {
            ImmutableList.Builder<TradeRecipe> recipes = ImmutableList.builder();
            int size = buffer.readVarInt();
            for (int i = 0; i < size; i++) {
                ResourceLocation id = buffer.readResourceLocation();
                int level = buffer.readVarInt();
                ImmutableList.Builder<TradeRecipe.Entry> trades = ImmutableList.builder();
                int tradeSize = buffer.readVarInt();
                for (int j = 0; j < tradeSize; j++) {
                    trades.add(new TradeRecipe.Entry(
                            PacketUtil.readList(buffer, FriendlyByteBuf::readItem),
                            PacketUtil.readList(buffer, FriendlyByteBuf::readItem),
                            PacketUtil.readList(buffer, FriendlyByteBuf::readItem)
                    ));
                }
                recipes.add(new TradeRecipe(id, level, trades.build()));
            }
            return new TradesMessage(recipes.build());
        }
    }
    
    public static class Handler implements PacketHandler<TradesMessage> {

        @Override
        public Target target() {
            return Target.NETWORK_THREAD;
        }

        @Override
        public boolean handle(TradesMessage msg, Supplier<NetworkEvent.Context> ctx) {
            if (ModList.get().isLoaded("jei")) {
                ctx.get().enqueueWork(() -> {
                    // UPDATE_TODO
//                    FeywildJeiReloader.clientTrades = msg.recipes;
//                    if (!(ModList.get().isLoaded("jeresources"))) { //TODO 1.19, see JEA
//                        FeywildJeiReloader.reload();
//                    }
                });
            }
            return true;
        }
    }
}
