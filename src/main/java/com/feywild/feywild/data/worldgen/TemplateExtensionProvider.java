package com.feywild.feywild.data.worldgen;

import net.minecraft.core.Holder;
import org.moddingx.libx.datagen.DatagenContext;
import org.moddingx.libx.datagen.provider.sandbox.TemplateExtensionProviderBase;
import org.moddingx.libx.sandbox.structure.PoolExtension;
import org.moddingx.libx.vanilla.TemplatePools;

public class TemplateExtensionProvider extends TemplateExtensionProviderBase {
    
    public final Holder<PoolExtension> plainsHouses = this.extension(TemplatePools.VILLAGE_PLAINS_HOUSES)
            .single(5, "village/plains/houses/fountain")
            .single(10, "village/plains/houses/pond")
            .build();
    
    public final Holder<PoolExtension> desertHouses = this.extension(TemplatePools.VILLAGE_DESERT_HOUSES)
            .single(10, "village/desert/houses/temple")
            .build();
    
    public final Holder<PoolExtension> savannaHouses = this.extension(TemplatePools.VILLAGE_SAVANNA_HOUSES)
            .single(5, "village/plains/houses/fountain")
            .single(10, "village/plains/houses/pond")
            .build();
    
    public final Holder<PoolExtension> snowyHouses = this.extension(TemplatePools.VILLAGE_SNOWY_HOUSES)
            .single(5, "village/snowy/houses/winter_temple")
            .single(10, "village/snowy/houses/winter_retreat")
            .build();
    
    public final Holder<PoolExtension> taigaHouses = this.extension(TemplatePools.VILLAGE_TAIGA_HOUSES)
            .single(5, "village/taiga/houses/autumn_temple")
            .single(10, "village/taiga/houses/autumn_pumpkin_patch")
            .build();
    
    public TemplateExtensionProvider(DatagenContext ctx) {
        super(ctx);
    }
}
