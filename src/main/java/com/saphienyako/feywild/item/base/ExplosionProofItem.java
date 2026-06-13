package com.saphienyako.feywild.item.base;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ItemStack;

public class ExplosionProofItem extends ToolTipBaseItem {

    public ExplosionProofItem(Properties properties, MutableComponent tooltip) {
        super(properties, tooltip);
    }

    @Override
    public boolean canBeHurtBy(ItemStack stack, DamageSource source) {
        return false;
    }
}
