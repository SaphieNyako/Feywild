package com.feywild.feywild.quest;

import com.google.gson.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipe;

public class Quest {
    private int id , link, lines, rep;
    private boolean skip;
    private String data,sound;
    private ItemStack stack;

    public Quest(int id, int link, int lines, int rep, boolean skip, String data, String sound, ItemStack stack){
        this.id = id;
        this.link = link;
        this.lines = lines;
        this.rep = rep;
        this.skip = skip;
        this.data = data;
        this.sound = sound;
        this.stack = stack;
    }

    public String getSound() {
        return sound;
    }

    public ItemStack getStack() {
        return stack;
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

    public String getData() {
        return data;
    }

    public static class Serializer {

        public Serializer(){
        }

        public Quest deserialize(JsonObject object){

            try {

                if(object.get("type").getAsString().equals("quest"))
                    return new Quest(object.get("id").getAsInt(), object.get("link").getAsInt(), object.get("lines").getAsInt(), object.get("reputation").getAsInt(), object.get("canSkip").getAsBoolean(), object.get("extraData").getAsString(),object.get("sound").getAsString(),ShapedRecipe.itemFromJson(object.getAsJsonObject("reward")));

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }
}
