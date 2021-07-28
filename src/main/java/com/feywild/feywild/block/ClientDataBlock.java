package com.feywild.feywild.block;

import net.minecraft.block.Block;

// TODO Might be better to have an approach using NBT in ClientDataBlock
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
