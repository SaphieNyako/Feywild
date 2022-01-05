package com.feywild.feywild.block;

import com.feywild.feywild.entity.Mandragora;
import com.feywild.feywild.entity.ModEntityTypes;
import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.quest.task.SpecialTask;
import com.feywild.feywild.quest.util.SpecialTaskAction;
import com.feywild.feywild.sound.ModSoundEvents;
import com.google.common.collect.ImmutableMap;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.Registerable;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.function.Consumer;

public class MandrakeCrop extends CropBlock implements Registerable {

    private static final VoxelShape[] SHAPES = new VoxelShape[]{
            Block.box(0, 0, 0, 16, 2, 16),
            Block.box(0, 0, 0, 16, 4, 16),
            Block.box(0, 0, 0, 16, 6, 16),
            Block.box(0, 0, 0, 16, 8, 16),
            Block.box(0, 0, 0, 16, 10, 16),
            Block.box(0, 0, 0, 16, 12, 16),
            Block.box(0, 0, 0, 16, 14, 16),
            Block.box(0, 0, 0, 16, 16, 16)
    };

    private final BlockItem seed;

    public MandrakeCrop(ModX mod) {
        super(BlockBehaviour.Properties.copy(Blocks.WHEAT));
        Item.Properties properties = mod.tab == null ? new Item.Properties() : new Item.Properties().tab(mod.tab);
        this.seed = new BlockItem(this, properties);
    }

    @Override
    public Map<String, Object> getNamedAdditionalRegisters(ResourceLocation id) {
        return ImmutableMap.of(
                "seed", this.seed
        );
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void registerClient(ResourceLocation id, Consumer<Runnable> defer) {
        defer.accept(() -> ItemBlockRenderTypes.setRenderLayer(this, RenderType.cutout()));
    }

    @Nonnull
    @Override
    protected ItemLike getBaseSeedId() {
        return this.seed;
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        return SHAPES[state.getValue(this.getAgeProperty())];
    }

    public BlockItem getSeed() {
        return this.seed;
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult hit) {
        if (player.getItemInHand(hand).getItem() == ModItems.magicalHoneyCookie && state.getValue(this.getAgeProperty()) == 7) {
            if (!level.isClientSide && QuestData.get((ServerPlayer) player).getAlignment() == Alignment.SPRING) {

                Mandragora entity = ModEntityTypes.mandragora.create(level);

                if (entity != null) {
                    entity.setPos(pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5);
                    entity.setSummonPos(pos);
                    level.addFreshEntity(entity);
                    entity.playSound(SoundEvents.FOX_EAT, 1, 1);
                    level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                    if (!player.isCreative()) player.getItemInHand(hand).shrink(1);
                    QuestData.get((ServerPlayer) player).checkComplete(SpecialTask.INSTANCE, SpecialTaskAction.SUMMON_MANDRAGORA);
                }
            } else if (!level.isClientSide && QuestData.get((ServerPlayer) player).getAlignment() != Alignment.SPRING) {
                player.sendMessage(new TranslatableComponent("message.feywild.summon_cookie_fail"), player.getUUID());
            }

            return InteractionResult.sidedSuccess(level.isClientSide);

        } else {
            level.playSound(player, pos, ModSoundEvents.mandrakeScream, SoundSource.BLOCKS, 1.0f, 0.8f);
            return super.use(state, level, pos, player, hand, hit);
        }
    }
}
