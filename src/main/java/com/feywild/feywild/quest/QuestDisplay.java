package com.feywild.feywild.quest;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class QuestDisplay {

    public final Component title;
    public final Component description;
    @Nullable
    public final SoundEvent sound;

    public QuestDisplay(Component title, Component description, @Nullable SoundEvent sound) {
        this.title = title;
        this.description = description;
        this.sound = sound;
    }

    public static QuestDisplay fromJson(JsonObject json) {
        Component title = Component.Serializer.fromJson(json.get("title"));
        Component description = Component.Serializer.fromJson(json.get("description"));
        SoundEvent sound = json.has("sound") ? ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(json.get("sound").getAsString())) : null;
        return new QuestDisplay(title, description, sound);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.add("title", Component.Serializer.toJsonTree(this.title));
        json.add("description", Component.Serializer.toJsonTree(this.description));
        if (this.sound != null && this.sound.getRegistryName() != null) {
            json.addProperty("sound", this.sound.getRegistryName().toString());
        }
        return json;
    }

    public static QuestDisplay fromNetwork(FriendlyByteBuf buffer) {
        Component title = buffer.readComponent();
        Component description = buffer.readComponent();
        SoundEvent sound = buffer.readBoolean() ? buffer.readRegistryId() : null;
        return new QuestDisplay(title, description, sound);
    }

    public void toNetwork(FriendlyByteBuf buffer) {
        buffer.writeComponent(this.title);
        buffer.writeComponent(this.description);
        buffer.writeBoolean(this.sound != null);
        if (this.sound != null) buffer.writeRegistryId(this.sound);
    }
}
