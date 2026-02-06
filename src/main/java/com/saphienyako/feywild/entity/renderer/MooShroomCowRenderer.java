package com.saphienyako.feywild.entity.renderer;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.MooShroomCowEntity;
import com.saphienyako.feywild.entity.model.MandragoraModel;
import com.saphienyako.feywild.entity.model.ModModelLayers;
import com.saphienyako.feywild.entity.renderer.layer.MooShroomCowLayer;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.MushroomCowMushroomLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.MushroomCow;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Locale;

public class MooShroomCowRenderer extends MobRenderer<MooShroomCowEntity, CowModel<MooShroomCowEntity>> {


    public MooShroomCowRenderer(EntityRendererProvider.Context context) {
        super(context, new CowModel<>(context.bakeLayer(ModModelLayers.MOO_SHROOM_LAYER)),0.7F);
        this.addLayer(new MooShroomCowLayer<>(this, context.getBlockRenderDispatcher()));
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(MooShroomCowEntity cow) {
        return ResourceLocation.fromNamespaceAndPath(Feywild.MOD_ID, "textures/entity/moo_shroom_cow/" + cow.getMooShroomVariant().name().toLowerCase(Locale.ROOT) + ".png");
    }
}
