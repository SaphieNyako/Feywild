package com.feywild.feywild.data;

import com.feywild.feywild.FeyRegistries;
import com.feywild.feywild.block.trees.BaseTree;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryManager;
import org.moddingx.libx.datagen.DatagenContext;
import org.moddingx.libx.datagen.provider.texture.TextureProviderBase;

public class TextureProvider extends TextureProviderBase {
    
    public TextureProvider(DatagenContext ctx) {
        super(ctx);
    }

    @Override
    public void setup() {
        //noinspection UnstableApiUsage
        IForgeRegistry<BaseTree> registry = RegistryManager.ACTIVE.getRegistry(FeyRegistries.TREES);
        for (BaseTree tree : registry.getValues()) {
            this.sign(tree.getPlankBlock().getMaterialProperties().woodType(), tree.getLogBlock(), tree.getPlankBlock());
            this.hangingSign(tree.getPlankBlock().getMaterialProperties().woodType(), tree.getStrippedLogBlock());
        }
    }
}
