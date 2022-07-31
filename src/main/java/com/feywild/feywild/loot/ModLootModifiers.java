package com.feywild.feywild.loot;

import com.mojang.serialization.Codec;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import org.moddingx.libx.annotation.registration.RegisterClass;

@RegisterClass(registry = "GLOBAL_LOOT_MODIFIER_SERIALIZERS")
public class ModLootModifiers {
    
    public static final Codec<? extends IGlobalLootModifier> addition = AdditionLootModifier.CODEC;
}
