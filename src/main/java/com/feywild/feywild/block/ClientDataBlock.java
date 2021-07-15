package com.feywild.feywild.block;

import net.minecraft.block.Block;

// TODO remove magic numbers
// Might be better to have an approach using NBT
public class ClientDataBlock extends Block {
    private int data;

    public ClientDataBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);

        data = 0;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
