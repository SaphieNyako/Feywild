package com.feywild.feywild.util;

import net.minecraft.util.IntReferenceHolder;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Util {

    public static IntReferenceHolder trackHigherOrderBits(Supplier<Integer> getter, Consumer<Integer> setter) {
        return new IntReferenceHolder() {

            @Override
            public int get() {
                return (getter.get() >>> 16) & 0xFFFF;
            }

            @Override
            public void set(int value) {
                setter.accept((getter.get() & 0xFFFF) | (value << 16));
            }
        };
    }
    
    public static IntReferenceHolder trackLowerOrderBits(Supplier<Integer> getter, Consumer<Integer> setter) {
        return new IntReferenceHolder() {
            
            @Override
            public int get() {
                return getter.get() & 0xFFFF;
            }

            @Override
            public void set(int value) {
                setter.accept((getter.get() & 0xFFFF0000) | (value & 0xFFFF));
            }
        };
    }
}
