package com.feywild.feywild.quest;

import com.feywild.feywild.FeywildMod;
import com.google.gson.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import java.util.LinkedList;
import java.util.List;

public class Quest {
    private ResourceLocation id;
    private List<ResourceLocation> requiredQuests = new LinkedList<>();
    private int rep;
    private String data,sound, name, text;
    private ItemStack stack, icon;
    private boolean canSkip, repeatable;

    public Quest(ResourceLocation id, String requiredQuests, int rep, String data, String sound, ItemStack stack, String name, String text, ItemStack icon, boolean repeatable){
        this.id = id;
        for (String s : requiredQuests.split(",")) {
            this.requiredQuests.add(new ResourceLocation(s));
        }
        this.canSkip = data.contains("NULL");
        this.name = name;
        this.text = text;
        this.rep = rep;
        this.data = data;
        this.sound = sound;
        this.stack = stack;
        this.icon = icon;
        this.repeatable = repeatable;
    }


    public boolean isRepeatable() {
        return repeatable;
    }

    public String getSound() {
        return sound;
    }

    public ItemStack getStack() {
        return stack;
    }


    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public ResourceLocation getId() {
        return id;
    }

    public List<ResourceLocation> getRequiredQuests() {
        return requiredQuests;
    }

    public int getRep() {
        return rep;
    }


    public String getData() {
        return data;
    }

    public boolean canSkip() {
        return canSkip;
    }

    public static class Serializer {

        public Serializer(){
        }

        public Quest deserialize(JsonObject object){

            try {
                if(!object.get("sound").getAsString().equalsIgnoreCase("NULL") && Registry.SOUND_EVENT.get(new ResourceLocation(object.get("sound").getAsString())) == null){
                    FeywildMod.LOGGER.fatal("Sound " + object.get("sound").getAsString() + " does not exist.");
                }else
                if(object.get("type").getAsString().equals("quest"))
                    return new Quest(ResourceLocation.tryParse(object.get("id").getAsString()), object.get("requirements").getAsString(), object.get("reputation").getAsInt(), object.get("extraData").getAsString() + " ID " + object.get("id").getAsString(),object.get("sound").getAsString(),ShapedRecipe.itemFromJson(object.getAsJsonObject("reward")), object.get("name").getAsString(), object.get("text").getAsString(),ShapedRecipe.itemFromJson(object.getAsJsonObject("icon")),object.get("repeatable").getAsBoolean());
            }catch (Exception e){
               e.printStackTrace();
            }
            return null;
        }
    }
}
