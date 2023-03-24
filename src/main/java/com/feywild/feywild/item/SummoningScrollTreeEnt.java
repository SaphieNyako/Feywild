package com.feywild.feywild.item;

import com.feywild.feywild.FeywildMod;
import com.feywild.feywild.block.ModTrees;
import com.feywild.feywild.entity.ModEntities;
import com.feywild.feywild.entity.base.TreeEntBase;
import com.feywild.feywild.network.ParticleMessage;
import com.feywild.feywild.quest.Alignment;
import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.util.TooltipHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;
import org.moddingx.libx.mod.ModX;
import org.moddingx.libx.registration.Registerable;
import org.moddingx.libx.registration.SetupContext;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class SummoningScrollTreeEnt<T extends TreeEntBase> extends SummoningScroll<T> implements Registerable {

    Alignment alignment;

    public SummoningScrollTreeEnt(ModX mod, EntityType<T> type, @Nullable SoundEvent soundEvent, Properties properties, @Nullable Alignment alignment) {
        super(mod, type, soundEvent, properties);
        this.alignment = alignment;
    }

    @Override
    public void registerCommon(SetupContext ctx) {
        EmptyScroll.registerCapture(type, this);
    }

    @Override
    protected boolean canSummon(Level level, Player player, BlockPos pos, @Nullable CompoundTag storedTag, T entity) {
        if (player instanceof ServerPlayer serverPlayer) {
            Alignment alignmentPlayer = QuestData.get(serverPlayer).getAlignment();
            if (getAlignment() == null) {
                return true;
            } else {
                if (alignmentPlayer != alignment) {
                    player.sendSystemMessage(Component.translatable("message.feywild.summon_fail"));
                    return false;
                } else {
                    return true;
                }
            }
        }
        player.sendSystemMessage(Component.translatable("message.feywild.summon_fail"));
        return false;
    }

    protected boolean canSummonOnBlock(Level level, Player player, BlockPos pos, @Nullable CompoundTag storedTag, T entity, Block block) {

        if (this.type == ModEntities.hexenTreeEnt && block == ModTrees.hexenTree.getSapling()) {
            return this.canSummon(level, player, pos, storedTag, entity);
        } else if (this.type == ModEntities.blossomTreeEnt && block == ModTrees.blossomTree.getSapling()) {
            return this.canSummon(level, player, pos, storedTag, entity);
        } else if (this.type == ModEntities.springTreeEnt && block == ModTrees.springTree.getSapling()) {
            return this.canSummon(level, player, pos, storedTag, entity);
        } else if (this.type == ModEntities.summerTreeEnt && block == ModTrees.summerTree.getSapling()) {
            return this.canSummon(level, player, pos, storedTag, entity);
        } else if (this.type == ModEntities.winterTreeEnt && block == ModTrees.winterTree.getSapling()) {
            return this.canSummon(level, player, pos, storedTag, entity);
        } else {
            player.sendSystemMessage(Component.translatable("message.feywild.summon_fail"));
            return false;
        }
    }

    @Override
    protected boolean canCapture(Level level, Player player, T entity) {
        return player.getUUID().equals(entity.getOwner());
    }

    @Nonnull
    @Override
    public InteractionResult useOn(@Nonnull UseOnContext context) {
        CompoundTag storedTag = null;
        Block clickedBlock = context.getLevel().getBlockState(context.getClickedPos()).getBlock();
        if (context.getItemInHand().hasTag() && context.getItemInHand().getOrCreateTag().contains("StoredEntityData", Tag.TAG_COMPOUND)) {
            storedTag = context.getItemInHand().getOrCreateTag().getCompound("StoredEntityData");
        }
        if (context.getPlayer() != null) {
            if (!context.getLevel().isClientSide) {
                T entity = this.type.create(context.getLevel());
                if (entity != null && this.canSummonOnBlock(context.getLevel(), context.getPlayer(), context.getClickedPos().immutable(), storedTag, entity, clickedBlock)) {
                    if (storedTag != null) entity.load(storedTag);
                    if (context.getItemInHand().hasCustomHoverName()) {
                        entity.setCustomName(context.getItemInHand().getHoverName());
                    }
                    BlockPos offsetPos = context.getClickedPos().relative(context.getClickedFace());
                    entity.setPos(offsetPos.getX() + 0.5, offsetPos.getY(), offsetPos.getZ() + 0.5);
                    this.prepareEntity(context.getLevel(), context.getPlayer(), context.getClickedPos().immutable(), entity);

                    context.getLevel().addFreshEntity(entity);
                    FeywildMod.getNetwork().sendParticles(context.getLevel(), ParticleMessage.Type.DANDELION_FLUFF, context.getClickedPos().getX(), context.getClickedPos().getY() + 1, context.getClickedPos().getZ());
                    addLeavesParticles(context);
                    context.getLevel().setBlock(context.getClickedPos(), Blocks.AIR.defaultBlockState(), 3);
                    if (this.soundEvent != null) entity.playSound(this.soundEvent, 1, 1);

                    if (!context.getPlayer().isCreative()) {
                        context.getItemInHand().shrink(1);
                    }
                }
            }
            return InteractionResult.sidedSuccess(context.getLevel().isClientSide);
        }
        return InteractionResult.PASS;
    }

    private void addLeavesParticles(@Nonnull UseOnContext context) {
        if (getAlignment() == null) {
            if (this.type == ModEntities.hexenTreeEnt) {
                FeywildMod.getNetwork().sendParticles(context.getLevel(), ParticleMessage.Type.LEAVES_HEXEN, context.getClickedPos().getX(), context.getClickedPos().getY() + 2, context.getClickedPos().getZ());
            }
            if (this.type == ModEntities.blossomTreeEnt) {
                FeywildMod.getNetwork().sendParticles(context.getLevel(), ParticleMessage.Type.LEAVES_BLOSSOM, context.getClickedPos().getX(), context.getClickedPos().getY() + 2, context.getClickedPos().getZ());
            }
        } else {
            switch (Objects.requireNonNull(getAlignment())) {
                case SPRING -> FeywildMod.getNetwork().sendParticles(context.getLevel(), ParticleMessage.Type.LEAVES_SPRING, context.getClickedPos().getX(), context.getClickedPos().getY() + 2, context.getClickedPos().getZ());
                case SUMMER -> FeywildMod.getNetwork().sendParticles(context.getLevel(), ParticleMessage.Type.LEAVES_SUMMER, context.getClickedPos().getX(), context.getClickedPos().getY() + 2, context.getClickedPos().getZ());
                case WINTER -> FeywildMod.getNetwork().sendParticles(context.getLevel(), ParticleMessage.Type.LEAVES_WINTER, context.getClickedPos().getX(), context.getClickedPos().getY() + 2, context.getClickedPos().getZ());
                default -> FeywildMod.getNetwork().sendParticles(context.getLevel(), ParticleMessage.Type.LEAVES_AUTUMN, context.getClickedPos().getX(), context.getClickedPos().getY() + 2, context.getClickedPos().getZ());
            }
        }
    }

    @Nullable
    public Alignment getAlignment() {
        return alignment;
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, Level level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        TooltipHelper.addTooltip(tooltip, level, Component.translatable("message.feywild." + Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(this.type)).getPath()));
        super.appendHoverText(stack, level, tooltip, flag);
    }
}
