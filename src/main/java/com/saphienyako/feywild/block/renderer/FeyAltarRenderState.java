package com.saphienyako.feywild.block.renderer;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

import java.util.ArrayList;
import java.util.List;

public class FeyAltarRenderState extends BlockEntityRenderState {

    public final List<ItemStackRenderState> ingredients = new ArrayList<>();
    public final ItemStackRenderState centerpiece = new ItemStackRenderState();

    public float progress;
    public float animationTime;
}
