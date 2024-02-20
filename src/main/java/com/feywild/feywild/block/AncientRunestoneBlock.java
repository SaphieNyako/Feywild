package com.feywild.feywild.block;

import com.feywild.feywild.block.entity.AncientRunestone;
import com.feywild.feywild.block.render.AncientRunestoneRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import org.moddingx.libx.base.tile.BlockBE;
import org.moddingx.libx.mod.ModX;
import org.moddingx.libx.registration.SetupContext;

import javax.annotation.Nonnull;

public class AncientRunestoneBlock extends BlockBE<AncientRunestone> {

    public static final ResourceLocation NIDAVELLIR_RUNE = new ResourceLocation("mythicbotany", "nidavellir_rune");

    public AncientRunestoneBlock(ModX mod) {
        super(mod, AncientRunestone.class, BlockBehaviour.Properties.copy(Blocks.STONE).strength(-1.0F, 3600000).noLootTable());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void registerClient(SetupContext ctx) {
        ctx.enqueue(() -> BlockEntityRenderers.register(this.getBlockEntityType(), c -> new AncientRunestoneRenderer()));
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);
        if (!stack.isEmpty() && NIDAVELLIR_RUNE.equals(ForgeRegistries.ITEMS.getKey(stack.getItem()))) {
            if (getBlockEntity(level, pos).start() && !player.isCreative()) {
                stack.shrink(1);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.use(state, level, pos, player, hand, hit);
    }
}
