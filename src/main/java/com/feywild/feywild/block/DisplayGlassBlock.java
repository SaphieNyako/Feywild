package com.feywild.feywild.block;

import com.feywild.feywild.block.entity.DisplayGlass;
import com.feywild.feywild.block.render.DisplayGlassRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.moddingx.libx.base.tile.BlockBE;
import org.moddingx.libx.mod.ModX;
import org.moddingx.libx.registration.SetupContext;

import javax.annotation.Nonnull;

public class DisplayGlassBlock extends BlockBE<DisplayGlass> {

    public static final BooleanProperty CAN_GENERATE = BooleanProperty.create("can_generate");
    public static final IntegerProperty BREAKAGE = IntegerProperty.create("breakage", 0, 4);

    public DisplayGlassBlock(ModX mod) {
        super(mod, DisplayGlass.class, BlockBehaviour.Properties.of(Material.GLASS).strength(9999999f).noOcclusion().noLootTable());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(CAN_GENERATE, false)
                .setValue(BREAKAGE, 0)
        );
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void registerClient(SetupContext ctx) {
        ctx.enqueue(() -> BlockEntityRenderers.register(this.getBlockEntityType(), c -> new DisplayGlassRenderer()));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CAN_GENERATE).add(BREAKAGE);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void attack(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull Player player) {
        super.attack(state, level, pos, player);
        if (!level.isClientSide) {
            getBlockEntity(level, pos).hitGlass();
        }
    }

    @Override
    protected boolean shouldDropInventory(Level level, BlockPos pos, BlockState state) {
        return false;
    }
}
