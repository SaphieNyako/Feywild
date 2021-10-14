package com.feywild.feywild.data;

import com.feywild.feywild.block.ModTrees;
import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.quest.*;
import com.feywild.feywild.quest.reward.ItemReward;
import com.feywild.feywild.quest.task.*;
import com.feywild.feywild.quest.util.FeyGift;
import com.feywild.feywild.quest.util.SpecialTaskAction;
import com.feywild.feywild.sound.ModSoundEvents;
import com.feywild.feywild.util.DatapackHelper;
import io.github.noeppi_noeppi.libx.crafting.IngredientStack;
import io.github.noeppi_noeppi.libx.mod.ModX;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.feywild.feywild.quest.Alignment.*;

public class QuestProvider implements IDataProvider {

    private final ModX mod;
    private final DataGenerator generator;
    private final Map<Alignment, Set<Quest>> quests = new HashMap<>();

    public QuestProvider(ModX mod, DataGenerator generator) {
        this.mod = mod;
        this.generator = generator;
    }

    public void setup() {
        this.root(SPRING)
                .icon(Items.DIAMOND)
                .reputation(25)
                .startSound(ModSoundEvents.summoningSpringPixie)
                .build();

        this.quest(SPRING, "levitate_sheep")
                .parent("root")
                .icon(Blocks.PINK_WOOL)
                .task(QuestTask.of(SpecialTask.INSTANCE, SpecialTaskAction.LEVITATE_SHEEP, 3))
                .build();

        this.quest(SPRING, "cake")
                .parent("levitate_sheep")
                .gift(Ingredient.of(Items.CAKE), 2)
                .reward(QuestReward.of(ItemReward.INSTANCE, new ItemStack(Items.SWEET_BERRIES, 6)))
                .build();

        this.quest(SPRING, "honey")
                .parent("cake")
                .gift(Ingredient.of(ModItems.honeycomb), 1)
                .reward(QuestReward.of(ItemReward.INSTANCE, new ItemStack(ModItems.honeycomb, 1)))
                .build();

        this.quest(SPRING, "sapling")
                .parent("honey")
                .icon(ModTrees.springTree.getSapling())
                .reward(QuestReward.of(ItemReward.INSTANCE, new ItemStack(ModTrees.springTree.getSapling(), 3)))
                .build();

        this.root(SUMMER)
                .icon(Items.DIAMOND)
                .reputation(25)
                .startSound(ModSoundEvents.summoningSummerPixie)
                .build();

        this.quest(SUMMER, "kill_golem")
                .parent("root")
                .icon(Items.IRON_NUGGET)
                .task(QuestTask.of(KillTask.INSTANCE, EntityType.IRON_GOLEM))
                .reward(QuestReward.of(ItemReward.INSTANCE, new ItemStack(Items.IRON_INGOT, 7)))
                .build();

        this.quest(SUMMER, "bee_nest")
                .reputation(20)
                .parent("kill_golem")
                .gift(Ingredient.of(Blocks.BEE_NEST))
                .reward(QuestReward.of(ItemReward.INSTANCE, new ItemStack(Items.HONEYCOMB)))
                .build();

        this.quest(SUMMER, "kill_pillager")
                .parent("bee_nest")
                .icon(Items.IRON_AXE)
                .task(QuestTask.of(KillTask.INSTANCE, EntityType.PILLAGER, 5))
                .reward(QuestReward.of(ItemReward.INSTANCE, new ItemStack(Items.GOLD_INGOT, 3)))
                .build();

        this.quest(SUMMER, "sapling")
                .parent("kill_pillager")
                .icon(ModTrees.summerTree.getSapling())
                .reward(QuestReward.of(ItemReward.INSTANCE, new ItemStack(ModTrees.summerTree.getSapling(), 3)))
                .build();

        this.root(AUTUMN)
                .icon(Items.DIAMOND)
                .reputation(25)
                .startSound(ModSoundEvents.summoningAutumnPixie)
                .build();

        this.quest(AUTUMN, "food_potatoes")
                .parent("root")
                .complete(null)
                .gift(Ingredient.of(Items.POTATO), 9)
                .build();

        this.quest(AUTUMN, "food_beetroots")
                .parent("root")
                .complete(null)
                .gift(Ingredient.of(Items.BEETROOT), 9)
                .build();

        this.quest(AUTUMN, "food_carrots")
                .parent("root")
                .complete(null)
                .gift(Ingredient.of(Items.CARROT), 9)
                .build();

        this.quest(AUTUMN, "food_complete")
                .parent("food_potatoes", "food_beetroots", "food_carrots")
                .icon(Items.BEETROOT_SOUP)
                .complete(null)
                .reward(QuestReward.of(ItemReward.INSTANCE, new ItemStack(Items.BEETROOT_SOUP, 1)))
                .build();

        this.quest(AUTUMN, "pumpkin")
                .parent("food_complete")
                .task(QuestTask.of(ItemTask.INSTANCE, new IngredientStack(Ingredient.of(Blocks.CARVED_PUMPKIN), 2)))
                .build();

        this.quest(AUTUMN, "dyes")
                .parent("pumpkin")
                .icon(Items.RED_DYE)
                .task(QuestTask.of(CraftTask.INSTANCE, Ingredient.of(Tags.Items.DYES), 9))
                .reward(QuestReward.of(ItemReward.INSTANCE, new ItemStack(ModTrees.autumnTree.getSapling(), 3)))
                .build();

        this.root(WINTER)
                .icon(Items.DIAMOND)
                .reputation(25)
                .startSound(ModSoundEvents.summoningWinterPixie)
                .build();

        this.quest(WINTER, "snowballs")
                .parent("root")
                .gift(Ingredient.of(Items.SNOWBALL), 16)
                .reward(QuestReward.of(ItemReward.INSTANCE, new ItemStack(Blocks.ICE, 4)))
                .build();

        this.quest(WINTER, "skull")
                .parent("snowballs")
                .task(QuestTask.of(ItemTask.INSTANCE, new IngredientStack(Ingredient.of(Items.ZOMBIE_HEAD), 1)))
                .reward(QuestReward.of(ItemReward.INSTANCE, new ItemStack(Items.WITHER_SKELETON_SKULL)))
                .build();

        this.quest(WINTER, "lantern")
                .parent("skull")
                .task(QuestTask.of(CraftTask.INSTANCE, Ingredient.of(Items.SOUL_LANTERN)))
                .reward(QuestReward.of(ItemReward.INSTANCE, new ItemStack(Items.BONE, 5)))
                .build();

        this.quest(WINTER, "sapling")
                .parent("lantern")
                .icon(ModTrees.winterTree.getSapling())
                .reward(QuestReward.of(ItemReward.INSTANCE, new ItemStack(ModTrees.winterTree.getSapling(), 3)))
                .build();
    }

    @Override
    public void run(@Nonnull DirectoryCache cache) throws IOException {
        this.setup();
        for (Alignment alignment : this.quests.keySet()) {
            Set<ResourceLocation> ids = new HashSet<>();
            for (Quest quest : this.quests.get(alignment)) {
                if (ids.contains(quest.id)) {
                    throw new IllegalStateException("Duplicate quest id: " + quest.id);
                } else {
                    ids.add(quest.id);
                }
            }
            for (Quest quest : this.quests.get(alignment)) {
                for (ResourceLocation parent : quest.parents) {
                    if (!ids.contains(parent)) {
                        throw new IllegalStateException("Reference to unknown quest: " + parent + " (in " + quest.id + ")");
                    }
                }
                IDataProvider.save(DatapackHelper.GSON, cache, quest.toJson(), this.generator.getOutputFolder().resolve("data").resolve(quest.id.getNamespace()).resolve("feywild_quests").resolve(alignment.id).resolve(quest.id.getPath() + ".json"));
            }
        }
    }

    @Nonnull
    @Override
    public String getName() {
        return this.mod.modid + "quests";
    }

    public QuestBuilder root(Alignment alignment) {
        return this.quest(alignment, "root");
    }

    public QuestBuilder quest(Alignment alignment, String name) {
        return new QuestBuilder(alignment, name);
    }

    public class QuestBuilder {

        private final Alignment alignment;
        private final ResourceLocation id;
        private final Set<String> parents;
        private final List<QuestTask> tasks;
        private final List<QuestReward> rewards;
        private boolean repeatable = false;
        private int reputation;
        private Item icon;
        private QuestDisplay start;
        @Nullable
        private QuestDisplay complete;

        public QuestBuilder(Alignment alignment, String name) {
            this.alignment = alignment;
            this.id = new ResourceLocation(QuestProvider.this.mod.modid, name);
            this.parents = new HashSet<>();
            this.reputation = 5;
            this.icon = null;
            this.start = new QuestDisplay(
                    new TranslationTextComponent("quest." + QuestProvider.this.mod.modid + "." + alignment.id + "." + name + ".start.title"),
                    new TranslationTextComponent("quest." + QuestProvider.this.mod.modid + "." + alignment.id + "." + name + ".start.description"),
                    null
            );
            this.complete = new QuestDisplay(
                    new TranslationTextComponent("quest." + QuestProvider.this.mod.modid + "." + alignment.id + "." + name + ".complete.title"),
                    new TranslationTextComponent("quest." + QuestProvider.this.mod.modid + "." + alignment.id + "." + name + ".complete.description"),
                    null
            );
            this.tasks = new ArrayList<>();
            this.rewards = new ArrayList<>();
        }

        public QuestBuilder parent(String... ids) {
            this.parents.addAll(Arrays.asList(ids));
            return this;
        }

        public QuestBuilder repeatable() {
            this.repeatable = true;
            return this;
        }

        public QuestBuilder reputation(int reputation) {
            this.reputation = reputation;
            return this;
        }

        public QuestBuilder icon(IItemProvider icon) {
            this.icon = icon.asItem();
            return this;
        }

        public QuestBuilder start(QuestDisplay display) {
            this.start = display;
            return this;
        }

        public QuestBuilder complete(@Nullable QuestDisplay display) {
            this.complete = display;
            return this;
        }

        public QuestBuilder startSound(@Nullable SoundEvent sound) {
            this.start = new QuestDisplay(this.start.title, this.start.description, sound);
            return this;
        }

        public QuestBuilder completeSound(@Nullable SoundEvent sound) {
            if (this.complete == null) throw new IllegalStateException("Can't set sound on null completion.");
            this.complete = new QuestDisplay(this.complete.title, this.complete.description, sound);
            return this;
        }

        public QuestBuilder task(QuestTask... tasks) {
            this.tasks.addAll(Arrays.asList(tasks));
            return this;
        }

        // Shorthand for fey gift task with current alignment
        public QuestBuilder gift(Ingredient ingredient) {
            return this.gift(ingredient, 1);
        }

        public QuestBuilder gift(Ingredient ingredient, int times) {
            this.tasks.add(QuestTask.of(FeyGiftTask.INSTANCE, new FeyGift(this.alignment, ingredient), times));
            return this;
        }

        public QuestBuilder reward(QuestReward... rewards) {
            this.rewards.addAll(Arrays.asList(rewards));
            return this;
        }

        public void build() {
            Item icon = this.icon;
            if (this.icon == null && this.tasks.size() == 1) {
                icon = this.tasks.get(0).icon();
            }
            if (icon == null) {
                throw new IllegalStateException("Can't build quest without icon: " + this.id);
            }
            Set<ResourceLocation> parents = this.parents.stream().map(str -> new ResourceLocation(QuestProvider.this.mod.modid, str)).collect(Collectors.toSet());
            Quest quest = new Quest(this.id, parents, this.repeatable, this.reputation, icon, this.start, this.tasks.isEmpty() ? null : this.complete, this.tasks, this.rewards);
            QuestProvider.this.quests.computeIfAbsent(this.alignment, k -> new HashSet<>()).add(quest);
        }
    }
}
