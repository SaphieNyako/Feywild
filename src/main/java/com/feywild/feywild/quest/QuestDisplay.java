package com.feywild.feywild.quest;

import com.google.gson.JsonObject;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class QuestDisplay {

    public final ITextComponent title;
    public final ITextComponent description;
    @Nullable
    public final SoundEvent sound;

    public QuestDisplay(ITextComponent title, ITextComponent description, @Nullable SoundEvent sound) {
        this.title = title;
        this.description = description;
        this.sound = sound;
    }
    
    public static QuestDisplay fromJson(JsonObject json) {
        ITextComponent title = ITextComponent.Serializer.fromJson(json.get("title"));
        ITextComponent description = ITextComponent.Serializer.fromJson(json.get("description"));
        SoundEvent sound = json.has("sound") ? ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(json.get("start_sound").getAsString())) : null;
        return new QuestDisplay(title, description, sound);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.add("title", ITextComponent.Serializer.toJsonTree(this.title));
        json.add("description", ITextComponent.Serializer.toJsonTree(this.description));
        if (this.sound != null && this.sound.getRegistryName() != null) {
            json.addProperty("sound", this.sound.getRegistryName().toString());
        }
        return json;
    }
}
