package com.feywild.feywild.util;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.io.File;

public class Config {


    public static ForgeConfigSpec SERVER_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;

    //CONFIG VARIABLES
    public static ForgeConfigSpec.IntValue FEY_DUST_DURATION;

    static {

        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

        setFeyDustDuration(SERVER_BUILDER, CLIENT_BUILDER);

        SERVER_CONFIG = SERVER_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();


    }

    //CONFIG METHOD
    private static void setFeyDustDuration(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER){

        FEY_DUST_DURATION = CLIENT_BUILDER.comment("How long fey dust effect lasts")
                .defineInRange("fey_dust_duration", 15, 0, 180);
    }

    public static void loadConfigFile(ForgeConfigSpec config, String path){

        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path))
        .sync().autosave().writingMode(WritingMode.REPLACE).build();

        file.load();
        config.setConfig(file);

    }

}
