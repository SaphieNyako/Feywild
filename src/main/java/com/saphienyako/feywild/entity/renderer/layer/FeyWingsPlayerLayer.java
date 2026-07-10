package com.saphienyako.feywild.entity.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.saphienyako.feywild.Feywild;
import com.saphienyako.feywild.effect.ModEffects;
import com.saphienyako.feywild.entity.FeyWingsEntity;
import com.saphienyako.feywild.entity.ModEntities;
import com.saphienyako.feywild.item.PixieWingTiaraItem;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class FeyWingsPlayerLayer<T extends Player, M extends PlayerModel<T>> extends RenderLayer<T, M> {

    private static final ResourceLocation SPRING =
            new ResourceLocation(Feywild.MOD_ID, "textures/entity/wings/spring.png");

    private static final ResourceLocation SUMMER =
            new ResourceLocation(Feywild.MOD_ID, "textures/entity/wings/summer.png");

    private static final ResourceLocation AUTUMN =
            new ResourceLocation(Feywild.MOD_ID, "textures/entity/wings/autumn.png");

    private static final ResourceLocation WINTER =
            new ResourceLocation(Feywild.MOD_ID, "textures/entity/wings/winter.png");

    private static final ResourceLocation SHADOW =
            new ResourceLocation(Feywild.MOD_ID, "textures/entity/wings/shadow.png");

    private static final ResourceLocation LIGHT =
            new ResourceLocation(Feywild.MOD_ID, "textures/entity/wings/light.png");

    private final EntityRenderDispatcher dispatcher;
    private LivingEntity entity;
    public FeyWingsPlayerLayer(RenderLayerParent<T, M> parent, EntityRenderDispatcher dispatcher) {
        super(parent);
        this.dispatcher = dispatcher;
    }

    private LivingEntity getTestEntity(ClientLevel level) {
        if (entity == null) {
            entity = ModEntities.FEY_WINGS.get().create(level);
        }
        return entity;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T player, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {

        if (!player.hasEffect(ModEffects.FEY_FLYING.get())) return;

        if (!(player.level() instanceof ClientLevel level)) return;

        LivingEntity entity = getTestEntity(level);

        ItemStack stack = player.getOffhandItem();

        if (stack.getItem() instanceof PixieWingTiaraItem wingItem && entity instanceof FeyWingsEntity wingsEntity) {

            wingsEntity.setTexture(wingItem.getWingTexture(stack));
        }

        poseStack.pushPose();

        //Attach to chest/back
        this.getParentModel().body.translateAndRotate(poseStack);

        //Position
        poseStack.translate(0.0D, 1.1D, 0.1D); //left/right, up/down, back/forward
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F)); //sideways flip
        poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F)); //clockwise flip

        //Scaling
        poseStack.scale(1.0F, 1.0F, 1.0F);

        entity.tickCount = player.tickCount;
        dispatcher.render(
                entity,
                0.0,
                0.0,
                0.0,
                0.0F,
                partialTick,
                poseStack,
                buffer,
                packedLight
        );

        poseStack.popPose();
    }
}
