package com.feywild.feywild.data;

import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;

import javax.annotation.Nonnull;
import java.io.IOException;

public class QuestProvider implements IDataProvider {

    private final ModX mod;

    public QuestProvider(ModX mod) {
        this.mod = mod;
    }

    @Override
    public void run(@Nonnull DirectoryCache cache) throws IOException {
        
    }

    @Nonnull
    @Override
    public String getName() {
        return mod.modid + "quests";
    }
}
