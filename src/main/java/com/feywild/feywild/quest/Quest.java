package com.feywild.feywild.quest;

import com.google.gson.*;
import net.minecraft.loot.LootTypesManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.lang.reflect.Type;

public class Quest {
    private int id , link, lines, rep;
    private boolean skip;

    public Quest(int id, int link, int lines, int rep, boolean skip){
        this.id = id;
        this.link = link;
        this.lines = lines;
        this.rep = rep;
        this.skip = skip;
    }

    public int getId() {
        return id;
    }

    public int getLink() {
        return link;
    }

    public int getLines() {
        return lines;
    }

    public int getRep() {
        return rep;
    }

    public boolean canSkip() {
        return skip;
    }

   public static class Serializer {

        public Serializer(){
        }

        public Quest deserialize(JsonObject object){
            if(object.get("type").getAsString().equals("quest"))
            return new Quest(object.get("id").getAsInt(), object.get("link").getAsInt(), object.get("lines").getAsInt(), object.get("reputation").getAsInt(), object.get("canSkip").getAsBoolean());
            return null;
       }
    }
}
