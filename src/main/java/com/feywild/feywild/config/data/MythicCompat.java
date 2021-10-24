package com.feywild.feywild.config.data;

public enum MythicCompat {
    
    OVERWORLD(true, false, false),
    ALFHEIM(false, true, false),
    BOTH(true, true, false),
    ALFHEIM_LOCKED(false, true, true);
    
    public final boolean overworld;
    public final boolean alfheim;
    public final boolean locked;

    MythicCompat(boolean overworld, boolean alfheim, boolean locked) {
        this.overworld = overworld;
        this.alfheim = alfheim;
        this.locked = locked;
    }
}
