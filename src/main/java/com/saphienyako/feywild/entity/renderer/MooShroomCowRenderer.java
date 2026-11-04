package com.saphienyako.feywild.entity.renderer;

import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.entity.MooShroomCowEntity;
import com.saphienyako.feywild.entity.model.ModModelLayers;
import com.saphienyako.feywild.entity.renderer.layer.MooShroomCowLayer;
import com.saphienyako.feywild.entity.state.MooShroomCowRenderState;
import net.minecraft.client.model.animal.cow.CowModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.animal.cow.MushroomCow;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class MooShroomCowRenderer extends MobRenderer<@NotNull MooShroomCowEntity, @NotNull MooShroomCowRenderState, @NotNull CowModel> {

    public MooShroomCowRenderer(EntityRendererProvider.Context context) {
        super(context, new CowModel(context.bakeLayer(ModModelLayers.MOO_SHROOM_LAYER)), 0.7F);
        this.addLayer(new MooShroomCowLayer(this));
    }

    @Override
    public MooShroomCowRenderState createRenderState() {
        return new MooShroomCowRenderState();
    }

    @Override
    public void extractRenderState(MooShroomCowEntity entity, MooShroomCowRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);

        state.variant = MushroomCow.Variant.valueOf(entity.getMooShroomVariant().name());
        state.mushroomBlockState = entity.getMooShroomVariant().getShroomBlock().defaultBlockState();
    }

    @Override
    public @NotNull Identifier getTextureLocation(MooShroomCowRenderState state) {
        return Identifier.fromNamespaceAndPath(Feywild.MOD_ID, "textures/entity/moo_shroom_cow/" + state.variant.name().toLowerCase(Locale.ROOT) + ".png");
    }
}