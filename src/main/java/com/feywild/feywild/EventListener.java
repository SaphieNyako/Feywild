package com.feywild.feywild;

import com.feywild.feywild.config.ClientConfig;
import com.feywild.feywild.config.MiscConfig;
import com.feywild.feywild.config.data.ScrollSelectType;
import com.feywild.feywild.entity.BeeKnight;
import com.feywild.feywild.entity.base.Pixie;
import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.item.ReaperScythe;
import com.feywild.feywild.network.OpeningScreenMessage;
import com.feywild.feywild.network.TradesMessage;
import com.feywild.feywild.quest.player.QuestData;
import com.feywild.feywild.quest.task.*;
import com.feywild.feywild.screens.DisplayQuestScreen;
import com.feywild.feywild.screens.FeywildTitleScreen;
import com.feywild.feywild.screens.SelectQuestScreen;
import com.feywild.feywild.trade.TradeManager;
import com.feywild.feywild.world.FeywildDimensions;
import com.feywild.feywild.world.market.MarketData;
import com.feywild.feywild.world.market.MarketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.AnimalTameEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.SaplingGrowTreeEvent;
import net.minecraftforge.event.level.SleepFinishedTimeEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.PacketDistributor;
import org.moddingx.libx.event.ConfigLoadedEvent;

import java.util.List;
import java.util.Random;

public class EventListener {
    
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void showGui(RenderGuiOverlayEvent.Pre event) {
        if (Minecraft.getInstance().screen instanceof DisplayQuestScreen || Minecraft.getInstance().screen instanceof SelectQuestScreen) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void craftItem(PlayerEvent.ItemCraftedEvent event) {
        if (event.getEntity() instanceof ServerPlayer) {
            QuestData.get((ServerPlayer) event.getEntity()).checkComplete(CraftTask.INSTANCE, event.getCrafting());
        }
    }

    @SubscribeEvent
    public void playerKill(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player) {
            QuestData quests = QuestData.get(player);
            quests.checkComplete(KillTask.INSTANCE, event.getEntity());
            if (event.getEntity() instanceof Monster || event.getEntity() instanceof Villager || event.getEntity() instanceof Raider) {
                InteractionHand hand = player.getUsedItemHand();
                ItemStack stack = player.getItemInHand(hand);
                if (stack.getItem() instanceof ReaperScythe) {
                    spawnRandomItem(event.getEntity(), 2, ModItems.soulShard);
                    if (event.getEntity() instanceof Zombie) {
                        spawnRandomItem(event.getEntity(), 8, Items.ZOMBIE_HEAD);
                    }
                    if (event.getEntity() instanceof Skeleton) {
                        spawnRandomItem(event.getEntity(), 10, Items.SKELETON_SKULL);
                    }
                    if (event.getEntity() instanceof WitherSkeleton) {
                        spawnRandomItem(event.getEntity(), 20, Items.WITHER_SKELETON_SKULL);
                    }
                    if (event.getEntity() instanceof Creeper) {
                        spawnRandomItem(event.getEntity(), 15, Items.CREEPER_HEAD);
                    }
                }
            }
        }
    }

    public void spawnRandomItem(Entity entity, int randomNextInt, ItemLike item) {
        Random random = new Random();
        if (random.nextInt(randomNextInt) <= 0) {
            entity.spawnAtLocation(new ItemStack(item));
        }
    }

    @SubscribeEvent
    public void tameAnimal(AnimalTameEvent event) {
        Player player = event.getTamer();
        if (player instanceof ServerPlayer) {
            QuestData.get((ServerPlayer) player).checkComplete(AnimalTameTask.INSTANCE, event.getAnimal());
        }
    }

    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event) {
        // Only check one / second
        if (event.player.tickCount % 20 == 0 && !event.player.level().isClientSide && event.player instanceof ServerPlayer player) {
            QuestData quests = QuestData.get(player);
            player.getInventory().items.forEach(stack -> quests.checkComplete(ItemStackTask.INSTANCE, stack));
            //Quest Check for Biome
            player.level().getBiome(player.blockPosition()).is(biome -> quests.checkComplete(BiomeTask.INSTANCE, biome.location()));
            //Quest Check for Structure
            if (player.serverLevel().structureManager().hasAnyStructureAt(player.blockPosition())) {
                RegistryAccess access = player.level().registryAccess();
                Registry<Structure> structureRegistry = access.registryOrThrow(Registries.STRUCTURE);
                player.serverLevel().structureManager().getAllStructuresAt(player.blockPosition()).forEach((structure, set) -> {
                    ResourceLocation structureId = structureRegistry.getKey(structure);
                    if (structureId != null) {
                        quests.checkComplete(StructureTask.INSTANCE, structureId);
                    }
                });
            }
        }
    }

    @SubscribeEvent
    public void entityInteract(PlayerInteractEvent.EntityInteract event) {
        if (!event.getLevel().isClientSide && event.getEntity() instanceof ServerPlayer player) {
            QuestData.get(player).checkComplete(AnimalPetTask.INSTANCE, event.getTarget());
        }
    }

    @SubscribeEvent
    public void playerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.getEntity().level().isClientSide) {
            if (event.getEntity() instanceof ServerPlayer) {
                FeywildMod.getNetwork().channel.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.getEntity()), new TradesMessage(TradeManager.buildRecipes()));
            }
            if (!FeyPlayerData.get(event.getEntity()).getBoolean("feywild_got_lexicon") && MiscConfig.initial_lexicon) {
                event.getEntity().getInventory().add(new ItemStack(ModItems.feywildLexicon));
                FeyPlayerData.get(event.getEntity()).putBoolean("feywild_got_lexicon", true);
            }
            if (!FeyPlayerData.get(event.getEntity()).getBoolean("feywild_got_scroll") && MiscConfig.initial_scroll == ScrollSelectType.LOGIN) {
                FeywildMod.getNetwork().channel.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.getEntity()), new OpeningScreenMessage());
                //TODO change OpeningScreen text and add story sound
            }
        }
    }

    @SubscribeEvent
    public void playerClone(PlayerEvent.Clone event) {
        FeyPlayerData.copy(event.getOriginal(), event.getEntity());
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void openGui(ScreenEvent.Opening event) {
        if (ClientConfig.replace_menu && event.getScreen() instanceof TitleScreen && !(event.getScreen() instanceof FeywildTitleScreen)) {
            event.setNewScreen(new FeywildTitleScreen());
        }
    }

    @SubscribeEvent
    public void loadConfig(ConfigLoadedEvent event) {
        if (event.getConfigClass() == MiscConfig.class) {
            ModItems.feyDust.updateFood();
        }
    }

    @SubscribeEvent
    public void blockInteract(PlayerInteractEvent.RightClickBlock event) {
        BeeKnight.anger(event.getLevel(), event.getEntity(), event.getPos());
    }

    @SubscribeEvent
    public void blockInteract(PlayerInteractEvent.LeftClickBlock event) {
        BeeKnight.anger(event.getLevel(), event.getEntity(), event.getPos());
    }

    @SubscribeEvent
    public void tickServer(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            MarketHandler.update(event.getServer());
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void sleepInFeywild(SleepFinishedTimeEvent event) {
        if (event.getLevel() instanceof ServerLevel level && !level.isClientSide) {
            if (FeywildDimensions.FEYWILD.equals(level.dimension())) {
                level.getServer().overworld().setDayTime(event.getNewTime());
            }
        }
    }

    @SubscribeEvent
    public void afterReload(OnDatapackSyncEvent event) {
        if (event.getPlayer() == null) {
            FeywildMod.getNetwork().channel.send(PacketDistributor.ALL.noArg(), new TradesMessage(TradeManager.buildRecipes()));
        } else {
            FeywildMod.getNetwork().channel.send(PacketDistributor.PLAYER.with(event::getPlayer), new TradesMessage(TradeManager.buildRecipes()));
        }
    }

    @SubscribeEvent
    public void entityJoin(EntityJoinLevelEvent event) {
        if (event.getLevel() instanceof ServerLevel serverLevel) {
            MarketData data = MarketData.get(serverLevel);
            if (data != null && !data.isAllowedEntity(event.getEntity())) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void treeGrow(SaplingGrowTreeEvent event) {
        BlockPos pos = event.getPos();
        BlockState state = event.getLevel().getBlockState(pos);

        List<Pixie> pixies = event.getLevel().getEntitiesOfClass(Pixie.class, new AABB(pos).inflate(20));
        for (Pixie pixie : pixies) {
            if (pixie.distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) > 20 * 20) continue;
            if (pixie.getOwningPlayer() instanceof ServerPlayer serverPlayer) {
                QuestData.get(serverPlayer).checkComplete(GrowTreeTask.INSTANCE, state);
            }
        }
    }
}
