package com.feywild.feywild.quest;

import com.google.gson.*;

public class Quest {
    private int id , link, lines, rep;
    private boolean skip;
    private String data;

    public Quest(int id, int link, int lines, int rep, boolean skip, String data){
        this.id = id;
        this.link = link;
        this.lines = lines;
        this.rep = rep;
        this.skip = skip;
        this.data = data;
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
                    return new Quest(object.get("id").getAsInt(), object.get("link").getAsInt(), object.get("lines").getAsInt(), object.get("reputation").getAsInt(), object.get("canSkip").getAsBoolean(), object.get("extraData").getAsString());

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }
}
