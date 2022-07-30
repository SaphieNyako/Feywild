package com.feywild.feywild.events;

import com.feywild.feywild.events.loot_modifiers.MineshaftChestLootModifier;
import org.moddingx.libx.annotation.registration.RegisterClass;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;

@RegisterClass
public class LootModifiers {

    public static final GlobalLootModifierSerializer<?> mineshaftChestAdditions = new MineshaftChestLootModifier.Serializer();

}
