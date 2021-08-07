package com.feywild.feywild.config;

public enum MythicCompat {
    
    OVERWORLD(true, false),
    ALFHEIM(false, true),
    BOTH(true, true);
    
    public final boolean overworld;
    public final boolean alfheim;

    MythicCompat(boolean overworld, boolean alfheim) {
        this.overworld = overworld;
        this.alfheim = alfheim;
    }
}
