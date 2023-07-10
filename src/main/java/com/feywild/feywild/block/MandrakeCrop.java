package com.feywild.feywild.block;

import com.feywild.feywild.config.MiscConfig;
import com.feywild.feywild.entity.Mandragora;
import com.feywild.feywild.entity.ModEntities;
import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.quest.task.SpecialTask;
import com.feywild.feywild.quest.util.SpecialTaskAction;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
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
import net.minecraftforge.registries.ForgeRegistries;
import org.moddingx.libx.registration.Registerable;
import org.moddingx.libx.registration.RegistrationContext;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;

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

    public MandrakeCrop() {
        super(BlockBehaviour.Properties.copy(Blocks.WHEAT));
        this.seed = new BlockItem(this, new Item.Properties());
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    public void registerAdditional(RegistrationContext ctx, EntryCollector builder) {
        builder.registerNamed(Registries.ITEM, "seed", this.seed);
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    public void initTracking(RegistrationContext ctx, TrackingCollector builder) throws ReflectiveOperationException {
        builder.trackNamed(ForgeRegistries.ITEMS, "seed", MandrakeCrop.class.getDeclaredField("seed"));
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

    protected void addMandragora(@Nonnull Level level, BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand) {
        Mandragora entity = ModEntities.mandragora.create(level);

        if (entity != null) {
            entity.setPos(pos.getX() + 0.5, pos.getY() + 0.1, pos.getZ() + 0.5);
            entity.setSummonPos(pos);
            level.addFreshEntity(entity);
            entity.playSound(SoundEvents.FOX_EAT, 1, 1);
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            if (!player.isCreative()) player.getItemInHand(hand).shrink(1);
            QuestData.get((ServerPlayer) player).checkComplete(SpecialTask.INSTANCE, SpecialTaskAction.SUMMON_MANDRAGORA);
        }
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult hit) {
        if (player.getItemInHand(hand).getItem() == ModItems.magicalHoneyCookie && state.getValue(this.getAgeProperty()) == 7) {
            if (!level.isClientSide) {
                if (QuestData.get((ServerPlayer) player).checkReputation(Alignment.SPRING, 0)) {
                    addMandragora(level, pos, player, hand);
                } else {
                    if (MiscConfig.summon_all_fey) {
                        addMandragora(level, pos, player, hand);
                    } else {
                        player.sendSystemMessage(Component.translatable("message.feywild.summon_cookie_fail"));
                    }
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            level.playSound(player, pos, ModSoundEvents.mandrakeScream.getSoundEvent(), SoundSource.BLOCKS, 0.6f, 0.8f);
            return super.use(state, level, pos, player, hand, hit);
        }
    }
}
