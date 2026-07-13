package com.saphienyako.feywild.item.base;

import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.damagesource.DamageSource;

public class ExplosionProofItem extends ToolTipBaseItem {

    public ExplosionProofItem(Properties properties, MutableComponent tooltip) {
        super(properties.fireResistant(), tooltip);
    }

    @Override
    public boolean canBeHurtBy(DamageSource pDamageSource) {
        return false;
    }
}
