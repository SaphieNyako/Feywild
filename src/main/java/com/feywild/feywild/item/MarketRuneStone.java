package com.feywild.feywild.item;

import com.feywild.feywild.world.dimension.ModDimensions;
import com.feywild.feywild.world.dimension.teleporters.MarketPlaceTeleporter;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.server.MinecraftServer;
import net.minecraft.test.StructureHelper;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.concurrent.atomic.AtomicInteger;

public class MarketRuneStone extends TooltipItem {

    public MarketRuneStone(ModX mod, Properties prop) {
        super(mod, prop, new TranslationTextComponent("message.feywild.market_scroll"));
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity entity, Hand hand) {

        if (!world.isClientSide) {

            MinecraftServer server = world.getServer();

            BlockPos pos = entity.blockPosition();

            if (server != null) {

                ServerWorld overWorld = server.getLevel(World.OVERWORLD);
                ServerWorld marketWorld = server.getLevel(ModDimensions.MARKET_PLACE_DIMENSION);

                if (world.dimension() == ModDimensions.MARKET_PLACE_DIMENSION) {
                    if (overWorld != null) {
                        AtomicInteger x = new AtomicInteger(0);
                        AtomicInteger y = new AtomicInteger(0);
                        AtomicInteger z = new AtomicInteger(0);
                        entity.getTags().forEach(tag -> {
                            switch ("" + tag.charAt(0)) {
                                case "x":
                                    x.set(Integer.parseInt(tag.replaceFirst("x", "")));
                                    break;

                                case "y":
                                    y.set(Integer.parseInt(tag.replaceFirst("y", "")));
                                    break;

                                case "z":
                                    z.set(Integer.parseInt(tag.replaceFirst("z", "")));
                                    break;

                            }
                        });

                        entity.setGameMode(GameType.SURVIVAL);

                        entity.getTags().removeIf(s -> s.startsWith("x") || s.startsWith("y") || s.startsWith("z"));
                        entity.changeDimension(overWorld, new MarketPlaceTeleporter(new BlockPos(x.get(), y.get(), z.get()), false));
                    }
                } else {
                    if (marketWorld != null && entity.level.getServer().overworld().getDayTime() < 13000) {
                        entity.getTags().removeIf(s -> s.startsWith("x") || s.startsWith("y") || s.startsWith("z"));
                        entity.addTag("x" + pos.getX());
                        entity.addTag("y" + pos.getY());
                        entity.addTag("z" + pos.getZ());
                        entity.changeDimension(marketWorld, new MarketPlaceTeleporter(new BlockPos(0, 0, 0), true));

                        if (!entity.level.getBlockState(new BlockPos(0, 0, 0)).equals(Blocks.GOLD_BLOCK.defaultBlockState())) {
                            StructureHelper.spawnStructure("feywild:market", new BlockPos(-10, 57, -10), Rotation.NONE, 1, (ServerWorld) entity.level, true).loadStructure((ServerWorld) entity.level);
                            entity.level.setBlock(new BlockPos(0, 0, 0), Blocks.GOLD_BLOCK.defaultBlockState(), 2);
                        }

                        entity.addEffect(new EffectInstance(Effects.BLINDNESS, 60, 60));
                        entity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 60, 60));
                        entity.setGameMode(GameType.ADVENTURE);

                    } else {
                        entity.sendMessage(new TranslationTextComponent("message.feywild.market_closed"), entity.getUUID());
                    }
                }
                return ActionResult.success(entity.getItemInHand(hand));
            }
        }

        return super.use(world, entity, hand);
    }
}
