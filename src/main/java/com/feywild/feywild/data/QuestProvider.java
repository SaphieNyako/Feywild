package com.feywild.feywild.data;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.block.ModTrees;
import com.feywild.feywild.item.ModItems;
import com.feywild.feywild.quest.*;
import com.feywild.feywild.quest.reward.ItemReward;
import com.feywild.feywild.quest.task.*;
import com.feywild.feywild.quest.util.FeyGift;
import com.feywild.feywild.quest.util.SpecialTaskAction;
import com.feywild.feywild.sound.ModSoundEvents;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import org.moddingx.libx.annotation.data.Datagen;
import org.moddingx.libx.crafting.IngredientStack;
import org.moddingx.libx.mod.ModX;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.feywild.feywild.quest.Alignment.*;

@Datagen
public class QuestProvider implements DataProvider {

    private final ModX mod;
    private final DataGenerator generator;
    private final Map<Alignment, Set<Quest>> quests = new HashMap<>();

    public QuestProvider(ModX mod, DataGenerator generator) {
        this.mod = mod;
        this.generator = generator;
    }

    public void setup() {
        this.root(SPRING)
                .icon(ModItems.summoningScrollSpringPixie)
                .reputation(25)
                .startSound(ModSoundEvents.summoningSpringPixie)
                .build();

        this.quest(SPRING, "quest_01")
                .parent("root")
                .icon(Blocks.CHEST)
                .task(QuestTask.of(CraftTask.INSTANCE, Ingredient.of(Items.CHEST), 1))
                .build();

        this.quest(SPRING, "quest_02")
                .parent("quest_01")
                .icon(Blocks.WHITE_WOOL)
                .task(QuestTask.of(ItemStackTask.INSTANCE, new IngredientStack(Ingredient.of(Blocks.WHITE_WOOL), 3)))
                .build();
        //TODO detect animal task, if a animal is near the pixie you complete the task.
        // quest_02 show animals from the overworld

        this.quest(SPRING, "quest_03")
                .parent("quest_02")
                .icon(Blocks.PINK_WOOL)
                .task(QuestTask.of(SpecialTask.INSTANCE, SpecialTaskAction.LEVITATE_SHEEP, 3))
                .build();
        //TODO reward: Raindbow Grass or Sheep  Plush

        this.quest(SPRING, "quest_04")
                .parent("quest_03")
                .icon(Items.CAKE)
                .gift(Ingredient.of(Items.CAKE), 2)
                .reward(QuestReward.of(ItemReward.INSTANCE, new ItemStack(ModTrees.springTree.getSapling(), 3)))
                .build();

        this.quest(SPRING, "quest_05")
                .parent("quest_04")
                .icon(ModTrees.springTree.getSapling())
                .task(QuestTask.of(SpecialTask.INSTANCE, SpecialTaskAction.GROW_SPRING_TREE, 3))
                .build();

        this.quest(SPRING, "quest_06")
                .parent("quest_05")
                .icon(Items.BOOK)
                .task(QuestTask.of(StructureTask.INSTANCE, ResourceLocation.of("feywild:library", ':')))
                .build();

        this.quest(SPRING, "quest_07")
                .parent("quest_06")
                .icon(ModBlocks.libraryBell)
                .task(QuestTask.of(SpecialTask.INSTANCE, SpecialTaskAction.ANNOY_LIBRARIAN))
                .build();

        this.quest(SPRING, "quest_08")
                .parent("quest_06")
                .icon(ModItems.feywildLexicon)
                .task(QuestTask.of(ItemStackTask.INSTANCE, new IngredientStack(Ingredient.of(ModItems.feywildLexicon), 1)))
                .build();
        //TODO reward Mysthical world, patchouli book

        /* SPRING PET ANIMAL QUESTS */

        this.quest(SPRING, "quest_09")
                .parent("quest_08")
                .icon(Items.PINK_CARPET)
                .task(QuestTask.ofEntry(AnimalPetTask.INSTANCE, EntityType.LLAMA))
                .build();

        this.quest(SPRING, "quest_10")
                .parent("quest_09")
                .icon(Items.POTATO)
                .task(QuestTask.ofEntry(AnimalPetTask.INSTANCE, EntityType.VILLAGER))
                .build();

        this.quest(SPRING, "quest_11")
                .parent("quest_10")
                .icon(Items.SCUTE)
                .task(QuestTask.ofEntry(AnimalPetTask.INSTANCE, EntityType.TURTLE))
                .build();

        this.quest(SPRING, "quest_12")
                .parent("quest_11")
                .icon(Items.PEARLESCENT_FROGLIGHT)
                .task(QuestTask.ofEntry(AnimalPetTask.INSTANCE, EntityType.FROG))
                .build();

        this.quest(SPRING, "quest_13")
                .parent("quest_12")
                .icon(Items.SWEET_BERRIES)
                .task(QuestTask.ofEntry(AnimalPetTask.INSTANCE, EntityType.FOX))
                .build();

        //TODO quest 14, reward Friendship Bracelet

        /* INTO THE FEYWILD SPRING QUEST */

        this.quest(SPRING, "quest_14")
                .parent("quest_08")
                .icon(Items.DANDELION)
                .task(QuestTask.of(BiomeTask.INSTANCE, ResourceLocation.of("feywild:blossoming_wealds", ':')))
                .build();

        this.quest(SPRING, "quest_15")
                .parent("quest_14")
                .icon(Items.DANDELION)
                .task(QuestTask.of(SpecialTask.INSTANCE, SpecialTaskAction.DANDELION))
                .build();

        this.quest(SPRING, "quest_16")
                .parent("quest_15")
                .icon(ModItems.honeycomb)
                .gift(Ingredient.of(ModItems.honeycomb), 1)
                .task(QuestTask.of(BiomeTask.INSTANCE, ResourceLocation.of("feywild:golden_seelie_fields", ':')))
                .build();

        this.quest(SPRING, "quest_17")
                .parent("quest_16")
                .icon(Items.RED_MUSHROOM)
                .task(QuestTask.of(ItemStackTask.INSTANCE, new IngredientStack(Ingredient.of(Items.COOKED_BEEF), 1)))
                .task(QuestTask.of(ItemStackTask.INSTANCE, new IngredientStack(Ingredient.of(Items.BREAD), 1)))
                .task(QuestTask.of(BiomeTask.INSTANCE, ResourceLocation.of("feywild:eternal_fall", ':')))
                .build();

        this.quest(SPRING, "quest_18")
                .parent("quest_17")
                .icon(Items.CARVED_PUMPKIN)
                .task(QuestTask.of(ItemStackTask.INSTANCE, new IngredientStack(Ingredient.of(Items.CARVED_PUMPKIN), 1)))
                .task(QuestTask.of(ItemStackTask.INSTANCE, new IngredientStack(Ingredient.of(Items.SNOW_BLOCK), 2)))
                .task(QuestTask.of(BiomeTask.INSTANCE, ResourceLocation.of("feywild:frozen_retreat", ':')))
                .build();

        this.quest(SPRING, "quest_19")
                .parent("quest_18")
                .icon(Items.CARVED_PUMPKIN)
                .task(QuestTask.of(ItemStackTask.INSTANCE, new IngredientStack(Ingredient.of(Items.ELYTRA), 1)))
                .build();


        this.quest(SPRING, "quest_20")
                .parent("quest_08")
                .repeatable()
                .icon(ModItems.pixieOrb)
                .gift(Ingredient.of(ModItems.pixieOrb), 1)
                .reward(QuestReward.of(ItemReward.INSTANCE, new ItemStack(ModItems.teleportationOrb)))
                .build();

        /* SUMMER */


        this.root(SUMMER)
                .icon(Items.DIAMOND)
                .reputation(25)
                .startSound(ModSoundEvents.summoningSummerPixie)
                .build();

        this.quest(SUMMER, "quest_01")
                .parent("root")
                .icon(Items.GOLD_NUGGET)
                .task(QuestTask.of(ItemStackTask.INSTANCE, new IngredientStack(Ingredient.of(Items.GOLDEN_CHESTPLATE), 1)))
                .task(QuestTask.of(ItemStackTask.INSTANCE, new IngredientStack(Ingredient.of(Items.GOLDEN_HELMET), 1)))
                .build();

        this.quest(SUMMER, "quest_02")
                .icon(Items.IRON_INGOT)
                .reputation(20)
                .parent("quest_01")
                .task(QuestTask.ofEntry(KillTask.INSTANCE, EntityType.IRON_GOLEM, 1))
                .build();

        this.quest(SUMMER, "quest_03")
                .reputation(20)
                .parent("quest_02")
                .gift(Ingredient.of(Blocks.BEE_NEST))
                .reward(QuestReward.of(ItemReward.INSTANCE, new ItemStack(Items.HONEYCOMB)))
                .build();

        //TODO add item for stringer

        this.quest(SUMMER, "quest_04")
                .parent("quest_03")
                .icon(Items.IRON_AXE)
                .task(QuestTask.ofEntry(KillTask.INSTANCE, EntityType.PILLAGER, 8))
                .reward(QuestReward.of(ItemReward.INSTANCE, new ItemStack(ModItems.summoningScrollBeeKnight, 1)))
                .build();

        this.quest(SUMMER, "quest_05")
                .parent("quest_04")
                .icon(ModTrees.summerTree.getSapling())
                .task(QuestTask.of(SpecialTask.INSTANCE, SpecialTaskAction.GROW_SUMMER_TREE, 3))

                .build();

        this.quest(SUMMER, "quest_06")
                .parent("quest_05")
                .icon(Items.BOOKSHELF)
                .task(QuestTask.of(StructureTask.INSTANCE, ResourceLocation.of("feywild:library", ':')))
                .build();

        this.quest(SUMMER, "quest_07")
                .parent("quest_06")
                .icon(Items.BOOK)
                .task(QuestTask.of(ItemStackTask.INSTANCE, new IngredientStack(Ingredient.of(Items.BOOK), 1)))
                .build();

        //TODO add book about the shadow court.

        this.quest(SUMMER, "quest_08")
                .parent("quest_07")
                .icon(Items.ENDER_EYE)
                .task(QuestTask.of(ItemStackTask.INSTANCE, new IngredientStack(Ingredient.of(Items.ENDER_EYE), 12)))
                .build();

        this.quest(SUMMER, "quest_09")
                .parent("quest_08")
                .icon(Items.DRAGON_HEAD)
                .task(QuestTask.ofEntry(KillTask.INSTANCE, EntityType.ENDER_DRAGON, 1))
                .build();

        this.quest(SUMMER, "quest_10")
                .parent("quest_09")
                .icon(Items.DRAGON_HEAD)
                .task(QuestTask.of(ItemStackTask.INSTANCE, new IngredientStack(Ingredient.of(Items.ELYTRA), 1)))
                .build();

        this.quest(SUMMER, "quest_11")
                .parent("quest_05")
                .repeatable()
                .icon(ModItems.pixieOrb)
                .gift(Ingredient.of(ModItems.pixieOrb), 1)
                .reward(QuestReward.of(ItemReward.INSTANCE, new ItemStack(ModItems.teleportationOrb)))
                .build();

        /* AUTUMN */

        this.root(AUTUMN)
                .icon(ModItems.summoningScrollAutumnPixie)
                .reputation(25)
                .startSound(ModSoundEvents.summoningAutumnPixie)
                .build();

        this.quest(AUTUMN, "quest_01")
                .parent("root")
                .icon(Items.ARROW)
                .gift(Ingredient.of(Items.IRON_SWORD), 9)
                .build();

        this.quest(AUTUMN, "quest_02")
                .parent("quest_01")
                .icon(Items.SMOKER)
                .task(QuestTask.of(CraftTask.INSTANCE, Ingredient.of(Items.SMOKER), 1))
                .task(QuestTask.of(CraftTask.INSTANCE, Ingredient.of(Items.CAULDRON), 1))
                .build();

        this.quest(AUTUMN, "quest_03")
                .parent("quest_02")
                .icon(Items.BONE)
                .task(QuestTask.ofEntry(AnimalTameTask.INSTANCE, EntityType.WOLF))
                .build();

        this.quest(AUTUMN, "quest_04")
                .parent("quest_03")
                .icon(Items.NAME_TAG)
                .task(QuestTask.of(ItemStackTask.INSTANCE, new IngredientStack(Ingredient.of(Items.COOKED_BEEF), 2)))
                .task(QuestTask.of(ItemStackTask.INSTANCE, new IngredientStack(Ingredient.of(Items.NAME_TAG), 1)))
                .build();

        this.quest(AUTUMN, "quest_05")
                .parent("quest_04")
                .icon(ModTrees.autumnTree.getSapling())
                .task(QuestTask.of(SpecialTask.INSTANCE, SpecialTaskAction.GROW_AUTUMN_TREE, 3))
                .build();

        this.quest(AUTUMN, "quest_06")
                .parent("quest_05")
                .icon(Items.WRITABLE_BOOK)
                .task(QuestTask.of(ItemStackTask.INSTANCE, new IngredientStack(Ingredient.of(Items.WRITABLE_BOOK), 1)))
                .build();

        this.quest(AUTUMN, "quest_07")
                .parent("quest_06")
                .icon(Items.POPPY)
                .task(QuestTask.of(ItemStackTask.INSTANCE, new IngredientStack(Ingredient.of(ItemTags.FLOWERS), 9)))
                .task(QuestTask.of(BiomeTask.INSTANCE, ResourceLocation.of("feywild:blossoming_wealds", ':')))
                .build();

        this.quest(AUTUMN, "quest_08")
                .parent("quest_07")
                .icon(Items.RED_DYE)
                .task(QuestTask.of(ItemStackTask.INSTANCE, new IngredientStack(Ingredient.of(Tags.Items.DYES), 9)))
                .task(QuestTask.of(BiomeTask.INSTANCE, ResourceLocation.of("feywild:eternal_fall", ':')))
                .build();

        this.quest(AUTUMN, "quest_09")
                .parent("quest_08")
                .icon(Items.COOKIE)
                .task(QuestTask.of(ItemStackTask.INSTANCE, new IngredientStack(Ingredient.of(Items.COOKIE), 8)))
                .task(QuestTask.of(BiomeTask.INSTANCE, ResourceLocation.of("feywild:golden_seelie_fields", ':')))
                .build();

        this.quest(AUTUMN, "quest_10")
                .parent("quest_09")
                .icon(Items.BONE)
                .task(QuestTask.of(BiomeTask.INSTANCE, ResourceLocation.of("feywild:frozen_retreat", ':')))
                .build();

        this.quest(AUTUMN, "quest_11")
                .parent("quest_10")
                .icon(Items.ELYTRA)
                .task(QuestTask.of(ItemStackTask.INSTANCE, new IngredientStack(Ingredient.of(Items.ELYTRA), 1)))
                .build();

        this.quest(AUTUMN, "quest_12")
                .parent("quest_05")
                .repeatable()
                .icon(ModItems.pixieOrb)
                .gift(Ingredient.of(ModItems.pixieOrb), 1)
                .reward(QuestReward.of(ItemReward.INSTANCE, new ItemStack(ModItems.teleportationOrb)))
                .build();

        /* WINTER */

        this.root(WINTER)
                .icon(Items.DIAMOND)
                .reputation(25)
                .startSound(ModSoundEvents.summoningWinterPixie)
                .build();

        this.quest(WINTER, "quest_01")
                .parent("root")
                .icon(Items.ROTTEN_FLESH)
                .gift(Ingredient.of(Items.ROTTEN_FLESH), 8)
                .build();

        this.quest(WINTER, "quest_02")
                .parent("quest_01")
                .icon(ModItems.soulShard)
                .task(QuestTask.of(ItemStackTask.INSTANCE, new IngredientStack(Ingredient.of(ModItems.reaperScythe), 1)))
                .build();

        this.quest(WINTER, "quest_03")
                .parent("quest_02")
                .task(QuestTask.of(ItemStackTask.INSTANCE, new IngredientStack(Ingredient.of(Items.ZOMBIE_HEAD), 1)))
                .reward(QuestReward.of(ItemReward.INSTANCE, new ItemStack(Items.WITHER_SKELETON_SKULL)))
                .build();

        this.quest(WINTER, "quest_04")
                .parent("quest_03")
                .task(QuestTask.of(ItemStackTask.INSTANCE, new IngredientStack(Ingredient.of(ModItems.soulShard), 10)))
                .reward(QuestReward.of(ItemReward.INSTANCE, new ItemStack(Items.TOTEM_OF_UNDYING)))
                .build();

        this.quest(WINTER, "quest_05")
                .parent("quest_04")
                .icon(ModTrees.winterTree.getSapling())
                .task(QuestTask.of(SpecialTask.INSTANCE, SpecialTaskAction.GROW_WINTER_TREE, 3))
                .build();

        this.quest(WINTER, "quest_06")
                .parent("quest_05")
                .icon(Items.BOOKSHELF)
                .task(QuestTask.of(StructureTask.INSTANCE, ResourceLocation.of("feywild:library", ':')))
                .build();

        this.quest(WINTER, "quest_07")
                .parent("quest_06")
                .icon(Items.BOOK)
                .task(QuestTask.of(ItemStackTask.INSTANCE, new IngredientStack(Ingredient.of(Items.BOOK), 1)))
                .build();

        //TODO add book about the shadow court.

        this.quest(WINTER, "quest_08")
                .parent("quest_07")
                .icon(Items.ENDER_EYE)
                .task(QuestTask.of(ItemStackTask.INSTANCE, new IngredientStack(Ingredient.of(Items.ENDER_EYE), 12)))
                .build();

        this.quest(WINTER, "quest_09")
                .parent("quest_08")
                .icon(Items.DRAGON_HEAD)
                .task(QuestTask.ofEntry(KillTask.INSTANCE, EntityType.ENDER_DRAGON, 1))
                .build();

        this.quest(WINTER, "quest_10")
                .parent("quest_09")
                .icon(Items.DRAGON_HEAD)
                .task(QuestTask.of(ItemStackTask.INSTANCE, new IngredientStack(Ingredient.of(Items.ELYTRA), 1)))
                .build();

        this.quest(WINTER, "quest_11")
                .parent("quest_05")
                .repeatable()
                .icon(ModItems.pixieOrb)
                .gift(Ingredient.of(ModItems.pixieOrb), 1)
                .reward(QuestReward.of(ItemReward.INSTANCE, new ItemStack(ModItems.teleportationOrb)))
                .build();


        //TODO as reward player will receive WINGS, add wings for each faction
    }

    @Override
    public void run(@Nonnull CachedOutput cache) throws IOException {
        this.setup();
        for (Alignment alignment : this.quests.keySet()) {
            Set<ResourceLocation> ids = new HashSet<>();
            for (Quest quest : this.quests.get(alignment)) {
                if (ids.contains(quest.id)) {
                    throw new IllegalStateException("Duplicate quest entityId: " + quest.id);
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
                DataProvider.saveStable(cache, quest.toJson(), this.generator.getOutputFolder().resolve("data").resolve(quest.id.getNamespace()).resolve("feywild_quests").resolve(alignment.id).resolve(quest.id.getPath() + ".json"));
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
                    Component.translatable("quest." + QuestProvider.this.mod.modid + "." + alignment.id + "." + name + ".start.title"),
                    Component.translatable("quest." + QuestProvider.this.mod.modid + "." + alignment.id + "." + name + ".start.description"),
                    null
            );
            this.complete = new QuestDisplay(
                    Component.translatable("quest." + QuestProvider.this.mod.modid + "." + alignment.id + "." + name + ".complete.title"),
                    Component.translatable("quest." + QuestProvider.this.mod.modid + "." + alignment.id + "." + name + ".complete.description"),
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

        public QuestBuilder icon(ItemLike icon) {
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
