package com.feywild.feywild.network;

import net.minecraft.network.FriendlyByteBuf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class PacketUtil {
    
    public static <T> void writeList(List<T> list, FriendlyByteBuf buffer, BiConsumer<FriendlyByteBuf, T> writeFunc) {
        buffer.writeVarInt(list.size());
        for (T t : list) {
            writeFunc.accept(buffer, t);
        }
    }
    
    public static <T> List<T> readList(FriendlyByteBuf buffer, Function<FriendlyByteBuf, T> readFunc) {
        int size = buffer.readVarInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(readFunc.apply(buffer));
        }
        return Collections.unmodifiableList(list);
    }
}
