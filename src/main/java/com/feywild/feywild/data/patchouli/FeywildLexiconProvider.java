package com.feywild.feywild.data.patchouli;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.block.ModTrees;
import com.feywild.feywild.entity.ModEntities;
import com.feywild.feywild.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.moddingx.libx.annotation.data.Datagen;
import org.moddingx.libx.datagen.provider.patchouli.BookProperties;
import org.moddingx.libx.datagen.provider.patchouli.PatchouliProviderBase;
import org.moddingx.libx.datagen.provider.patchouli.page.Content;
import org.moddingx.libx.mod.ModX;

@Datagen
public class FeywildLexiconProvider extends PatchouliProviderBase {

    public FeywildLexiconProvider(ModX mod, DataGenerator generator, ExistingFileHelper fileHelper) {
        super(mod, generator, fileHelper, new BookProperties("feywild_lexicon", PackType.CLIENT_RESOURCES, true));
    }

    /* Missing title entry for patchouli books*/

    @Override
    protected void setup() {
        this.category("feywild")
                .name("Feywild")
                .description("Fey, a treatise on the wild and whimsical.")
                .icon(ModItems.feywildLexicon);

        this.entry("introduction")
                .name("Introduction")
                .icon(Items.WRITABLE_BOOK)
                .text("Did you ever find your shoes laced together after a good nights sleep?")
                .text("The sugar in your tea has suddenly turned to salt?")
                .text("Did you ever travel through the forest swearing you see something just in the corner of your eye or hearing the giggle of children's voices?")
                .text("Don't fret, you're not going mad!")
                .text("It's just the local fey showing you they care for you in their own very special way.")
                .text("Fey are pranksters, tricksters who enjoy teasing us big folk.")
                .text("Most of the time they are harmless, but just as with us, there might be a bad seed or two amongst them.")
                .text("It's hard to gain the trust of a fairy, as they see the world in a different way that we do.")
                .item(ModItems.feywildLexicon)
                .text("If you want to learn more about the Fey, you may want to visit a Library and speak to one of the librarians there.")
                .text("They also have guide books on magic, depending what magic you brought to the Feywild.")
                .text("If he is not present just ring the bell.")
                .image("Library", "textures/patchouli_images/library_book.png")
                .crafting("feywild_lexicon");

        this.entry("seasonal_trees")
                .name("Seasonal Trees")
                .icon(ModTrees.autumnTree.getSapling())
                .item(ModItems.feyDust, false)
                .text("The fey trees will also drop fey dust when you cut down their leaves.")
                .item(ModTrees.springTree.getSapling())
                .text("Flowers from the spring season will grow around the tree.")
                .add(this.altar("spring_tree_sapling_fey_altar"))
                .crafting("spring_tree_wood")
                .crafting("spring_tree_planks")
                .item(ModTrees.summerTree.getSapling())
                .text("Flowers from the summer season will grow around the tree and sometimes the tree may be a home for the Summer Court's bees.")
                .add(this.altar("summer_tree_sapling_fey_altar"))
                .crafting("summer_tree_wood")
                .crafting("summer_tree_planks")
                .item(ModTrees.autumnTree.getSapling())
                .text("The area around the tree will soon be filled with fallen leaves.")
                .text("To ward of evil spirits sometimes a carved pumpkin may appear.")
                .add(this.altar("autumn_tree_sapling_fey_altar"))
                .crafting("autumn_tree_wood")
                .crafting("autumn_tree_planks")
                .item(ModTrees.winterTree.getSapling())
                .text("The Winter tree will be covered in snow and coldness.")
                .add(this.altar("winter_tree_sapling_fey_altar"))
                .crafting("winter_tree_wood")
                .crafting("winter_tree_planks")
                .item(ModTrees.hexenTree.getSapling())
                .text("Some use Hexerei to corrupt fey magic. In places where the magic has been corrupted a hexen tree will grow.")
                .add(this.altar("hexen_tree_sapling_fey_altar"))
                .crafting("hexen_tree_wood")
                .crafting("hexen_tree_planks")
                .item(ModTrees.blossomTree.getSapling())
                .text("An area blessed by a arch fey will grow a special fey blossom tree.")
                .add(this.altar("blossom_tree_sapling_fey_altar"))
                .crafting("blossom_tree_wood")
                .crafting("blossom_tree_planks")
                .item(ModTrees.blossomTree.getCrackedLogBlock())
                .text("Sometimes the magic of the feywild will leaves crackes in the tree's structure. Using feydust on the cracked log will only scar the block more.");


        this.entry("fey_altar")
                .name("Summoning Fey")
                .icon(ModItems.summoningScroll)
                .text("To create the summoning scroll for a specific fey you will requires a Fey Altar, which can be crafted or can be found at places of worship in small villages.")
                .crafting("fey_altar")
                .caption("With the right ingredients and incantation written on a summoning scroll with magic ink, you can summon back a Fey.")
                .flip()
                .crafting("summoning_scroll")
                .crafting("fey_ink_bottle")
                .item(ModBlocks.mandrakeCrop.getSeed())
                .text("A Mandrake Seed can be found in the Fey trees.")
                .text("Once a normal tree is corrupted by the fey's magic some of its seeds will turn into a mandrake seed.");

        this.entry("handling_fey")
                .name("Handling Fey")
                .icon(ModItems.summoningScroll)
                .item(ModItems.summoningScroll)
                .text("You can summon a fey by using a fey summoning scroll on a block.")
                .text("Once used you get a empty summoning scroll back.")
                .text("By left clicking your fey when it's stationary you can put the fey back into the summoning scroll.")
                .text("Each Fey has it's own summoning scroll and the recipes for each fey can be found on their corresponding pages or by using JEI.")
                .text("Some fey follow you around the world.")
                .text("You can make a fey follow and stop following you by interacting with them while holding shift.");

        this.entry("quest")
                .name("Quest")
                .icon(ModItems.summoningScrollSpringPixie)
                .text("All the pixies have a quest-line.")
                .text("After summoning one you can access quests by right clicking the pixie with an empty hand.")
                .text("The Pixie first asks you to accept a contract and you will become a Ally of that Court.")
                .flip("alignment")
                .text("After accepting the contract with the Pixie you are aligned with that Court.")
                .text("Aligning yourself with a Court causes all Fey from other Courts to leave!");

        this.entry("feywild")
                .name("The Feywild Dimension")
                .icon(ModItems.teleportationOrb)
                .item(ModItems.teleportationOrb)
                .text("By completing questlines for your pixie you can obtain the Feywild teleportation Orb. The quest is repeatable should you loose the orb.")
                .item(ModItems.pixieOrb)
                .text("During the questline the pixie will ask you to capture a fey with an eye of ender.")
                .text("When returning the pixie orb to your pixie she will grant it special powers wich allow you to travel back and forth from the feywild.");


        this.category("fey_gems")
                .name("Fey Gems")
                .description("When a Fey passes into the next world, they leave behind a small amount of Fey Dust. This dust is sought after by craftsmen, as compressing it turns it into the rare and precious Fey Ore.")
                .icon(ModItems.greaterFeyGem);

        this.entry("fey_dust")
                .name("Fey Dust")
                .icon(ModItems.feyDust)
                .text("When a Fey passes into the next world, they leave behind a small amount of Fey Dust.")
                .text("Breaking the fey trees will also provide you with fey dust.")
                .text("Fey Dust is also obtained by heating up fey ore.")
                .smelting("smelting/fey_dust");

        this.entry("fey_ore")
                .name("Fey Ore")
                .icon(ModItems.lesserFeyGem)
                .item(ModBlocks.feyGemOre)
                .text("Fey gems are very wanted by some creatures and wizards because it still contains some of the Fey's original magic.")
                .text("Be careful when mining the Fey ore as it is fragile and putting too much pressure on the ore can lower the quality of the excavated Fey Gems.")
                .flip()
                .text("Fey gems can be cut to smaller pieces by using a stone cutter.")
                .text("The knowledge on how to increase the quality of a fey gem is said to be only known by the dwarves.")
                .item(ModBlocks.feyGemOreDeepSlate)
                .text("Fey ore can also be found in Deepslate veins.")
                .text("The Fey ore is better preserved in Deepslate and will have a slightly higher chance of dropping higher quality gems.");

        this.entry("elven_quartz")
                .name("Elven Quartz")
                .icon(ModItems.rawElvenQuartz)
                .item(ModBlocks.feyStarBlockBlue)
                .caption("In the Feywild Dimension you can find colorful geodes filled with star blocks.")
                .item(ModItems.rawSpringElvenQuartz)
                .caption("The star blocks will contain raw elven quartz. There are five different types of quartz. Normal and one representing each season.")
                .crafting("elven_quartz")
                .caption("Combining the raw quartz will create a elven quartz block. These blocks can be used to create all kinds of blocks using a stonecutter.")
                .crafting("elven_spring_quartz")
                .crafting("elven_summer_quartz")
                .crafting("elven_winter_quartz")
                .crafting("elven_autumn_quartz");


        this.category("spring_court")
                .name("Spring Court")
                .description("They represent the birth of a new year and the coming of new life and are the most innocent of the Court. They are however, notoriously obnoxious when playing pranks, as they do not know the dangers or consequences of their actions. Nor do they care about it.")
                .icon(ModItems.summoningScrollSpringPixie);

        this.entry("spring_court")
                .name("Spring Court")
                .icon(ModTrees.springTree.getSapling())
                .text("Bouncy, bubbly and completely void of the idea of personal space, the fey of the Spring Court treat everything as if they have never seen it before.")
                .text("They are like hyperactive children, always wondering what everything is, stuffing anything into their mouth that seems slightly edible and their only concern is not having either enough fun or food for the day.")
                .text("They represent the birth of a new year and the coming of new life and are the most innocent of the Courts.")
                .text("They are however, notoriously obnoxious when playing pranks, as they do not know the dangers or consequences of their actions.")
                .text("Nor do they care about it.")
                .image("Blossoming Wealds", "textures/patchouli_images/spring_book.png", "textures/patchouli_images/spring_worldtree_book.png")
                .item(ModBlocks.dandelion.getSeed())
                .text("In the Blossoming Wealds you can find giant dandelions.")
                .text("The giant dandelion has three variations; a bud, a flower and the flower's well known fluff.")
                .text("The flower will also drop dandelion seeds, which can be used to grow your own giant dandelions.");

        this.entry("spring_pixie")
                .name("Spring Pixie")
                .icon(ModItems.summoningScrollSpringPixie)
                .entity(ModEntities.springPixie)
                .flip()
                .text("Beware that when you summon a pixie, you are bound to their Court!")
                .text("This means other courts might not be willing to interact with you afterwards.")
                .text("Also note that not all pixies are willing familiars, and they might trick you!")
                .text("Try interacting with your pixie after you summoned one, if possible try to gain their trust.")
                .add(this.altar("summoning_scroll_spring_pixie_fey_altar"))
                .text("With this scroll you can summon a Spring Pixie.")
                .text("The presence of a Spring Pixie will make the animals fall in love with each other.")
                .text("The summoned pixie will follow you by shift-right clicking her, if you want her to stay at a certain position, shift-right click her again.");

        this.entry("mandragora")
                .name("Mandragora")
                .icon(ModItems.summoningScrollMandragora)
                .text("As if touched by a fey spirit, mandragoras are typically bright and cheerful optimists, ready to laugh, frolic and enjoy life and nature.")
                .text("They sprout from their less charming relatives, the mandrakes.")
                .entity(ModEntities.mandragora)
                .crafting("magical_honey_cookie")
                .caption("By feeding a mandrake their favorite food, a magical honey cookie, they will sprout into a Mandragora.")
                .caption("A Mandragora will take care of your garden, and her lovely singing voice might even grow crops.")
                .item(ModBlocks.mandrakeCrop.getSeed())
                .caption("You can use a Mandrake Seed to change the Type of the Mandragora.")
                .add(this.altar("summoning_scroll_mandragora_fey_altar"))
                .caption("Sometimes other mods may prevent you from summoning a Mandragora with a Honey Cookie, then try creating a Summoning Scroll.")
                .item(ModItems.magicalHoneyCookie)
                .caption("The magical Honey cookie can be found in the Golden Seelie Fields. There will be a structure called the Beekeep, where the summer Court protects their honey.");

        this.category("summer_court")
                .name("Summer Court")
                .description("The Summer Court is the primary reigning Court within the Feywild and they know it. The other Courts and inhabitants of the Feywild are there to serve them, and though polite, they demand recognition of their self-claimed royal status.")
                .icon(ModItems.summoningScrollSummerPixie);

        this.entry("summer_court")
                .name("Summer Court")
                .icon(ModTrees.summerTree.getSapling())
                .text("The Summer Court is the primary reigning Court within the Feywild and they know it.")
                .text("Haughty and demanding, they expect anyone who addresses them to treat them as the royals they are.")
                .text("The other Courts and inhabitants of the Feywild are there to serve them, and though polite, they demand recognition of their self-claimed royal status.")
                .text("The Summer Court defend the Feywild against intruders which seek to take a hold of the Faerie Court's riches and are more combat orientated then the other Courts.")
                .text("They are quick to answer any threat or opposition with their righteous judgment.")
                .image("Golden Seelie Fields", "textures/patchouli_images/summer_book.png", "textures/patchouli_images/summer_worldtree_book.png")
                .caption("The Summer World tree is difficult to find, but it is the home of Queen Titania.")
                .caption("Note: Only the pixies are implemented, other court members will be added in later updates.")
                .caption("The World Tree also has a Fey altar for you to use.")
                .item(ModBlocks.sunflower.getSeed())
                .text("In the Golden Seelie Fields you can find giant sunflowers.")
                .text("The giant sunflowers will face the sun's position.")
                .text("The flower will also drop sunflower seeds, which can be used to grow your own giant sunflowers.");

        this.entry("summer_pixie")
                .name("Summer Pixie")
                .icon(ModItems.summoningScrollSummerPixie)
                .entity(ModEntities.summerPixie)
                .caption("The Summer Court is the primary reigning Court within the Feywild and they know it.")
                .flip()
                .text("Beware that when you summon a pixie, you are bound to their Court!")
                .text("This means other courts might not be willing to interact with you afterwards.")
                .text("Also note that not all pixies are willing familiars, and they might trick you!")
                .text("Try interacting with your pixie after you summoned one, if possible try to gain their trust.")
                .add(this.altar("summoning_scroll_summer_pixie_fey_altar"))
                .text("With this scroll you can summon a Summer Pixie.")
                .text("As a proud member of the Summer Court the Summer Pixie will smite down her foes.")
                .text("The summoned pixie will follow you by shift-right clicking her, if you want her to stay at a certain position, shift-right click her again.");

        this.entry("bee_knight")
                .name("Bee Knight")
                .icon(ModItems.honeycomb)
                .entity(ModEntities.beeKnight)
                .caption("The Bee Knights patrol the Golden Seelie Fields, looking for anyone who tries to steal their sacred honey.")
                .image("Bee Keep", "textures/patchouli_images/bee_keep_book.png")
                .caption("Inspired by a building of Kelpie The Fox: $(#b0b)$(l:https://www.youtube.com/watch?v=Cs-nHCvrOpg)YouTube$(/l)$()")
                .item(ModItems.honeycomb)
                .text("The best honey of the Summer Court is kept at the Bee Keep.")
                .text("The Bee Keep is protected by several Bee Knights.")
                .text("The Sacred Honey Comb is kept in a glass display.")
                .text("The display can be broken by left clicking it rapidly so it will break, and the golden treasure is yours.")
                .add(this.altar("summoning_scroll_bee_knight_fey_altar"))
                .text("With this scroll you can summon a Bee Knight.")
                .text("The Bee Knight will protect the area from other creatures interacting with it.");

        this.category("autumn_court")
                .name("Autumn Court")
                .description("The Autumn Court is the most mature of the four Fae Courts, their age and wisdom has given them a appreciation of fine art, music and poetry. They know the upcoming period will be a harsh one, and feel the responsibility of preparing for the end of a cycle.")
                .icon(ModItems.summoningScrollAutumnPixie);

        this.entry("autumn_court")
                .name("Autumn Court")
                .icon(ModTrees.autumnTree.getSapling())
                .text("The Autumn Court is the most mature of the four Fae Courts, their age and wisdom has given them a appreciation of fine art, music and poetry.")
                .text("They rather spend a night listening to song, dancing and flirting then going to combat or mindlessly indulging themselves.")
                .text("They know the upcoming period will be a harsh one, and feel the responsibility of preparing for the end of a cycle.")
                .text("Perhaps the most kind of the Courts, they are still capable of trickery and mischief, but they are more hush-hush about it.")
                .text("The Autumn Court will protect the Feywild from any evil spirits and will harvest souls who have gone astray.")
                .text("They tend to disguise themselves as past years fallen.")
                .text("To honour them but also to give the lost soul one more good time before passing on to the next stage.")
                .image("The Eternal Fall", "textures/patchouli_images/autumn_book.png", "textures/patchouli_images/autumn_worldtree_book.png")
                .text("The Autumn World tree is difficult to find, but it is the home of the Ashen Lady.")
                .text("Note: Only the pixies are implemented, other court members will be added in later updates.")
                .text("The World Tree also has a Fey altar for you to use.")
                .item(ModBlocks.treeMushroom)
                .text("In the Eternal Falls you can find giant Mushrooms.")
                .text("You can grow your own giant mushrooms by placing a red or brown mushroom on podzol, and grow it with bone meal.")
                .text("The tree mushrooms can be carefully harvested with a shear.");

        this.entry("autumn_pixie")
                .name("Autumn Pixie")
                .icon(ModItems.summoningScrollAutumnPixie)
                .entity(ModEntities.autumnPixie)
                .caption("The Autumn Court is the most mature of the four Fey Courts, their age and wisdom has given them a appreciation of fine art, music and poetry.")
                .flip()
                .text("Beware that when you summon a pixie, you are bound to their Court!")
                .text("This means other courts might not be willing to interact with you afterwards.")
                .text("Also note that not all pixies are willing familiars, and they might trick you!")
                .text("Try interacting with your pixie after you summoned one, if possible try to gain their trust.")
                .add(this.altar("summoning_scroll_autumn_pixie"))
                .text("With this scroll you can summon a Autumn Pixie.")
                .text("Autumn Pixies will try to ward off any evil spirits by protecting you with a wind walk spell.")
                .text("The summoned pixie will follow you by shift-right clicking her, if you want her to stay at a certain location, shift-right click her again.");

        this.entry("shroomling")
                .name("Shroomling")
                .icon(ModItems.summoningScrollShroomling)
                .item(ModItems.summoningScrollShroomling)
                .text("With this scroll you can summon a Shroomling.")
                .text("The Shroomling has some allergies for spores and will sneeze at random times.")
                .entity(ModEntities.shroomling)
                .add(this.altar("summoning_scroll_shroomling_fey_altar"))
                .crafting("fey_mushroom")
                .text("If the area the shroomling is in, is not well lit up, it will spawn a fey mushroom to light up the area. $(p) You can easily harvest the fey mushroom and replace it.")
                .text("Right clicking with fey dust or bonemeal will light up the mushroom.")
                .text("Don't use to much or it will loose it magic.")
                .entity(EntityType.MOOSHROOM)
                .caption("If the Shroomling sneezes near a cow it might infect it with the 'shrooms', turning the cow into a Mooshroom.")
                .caption("Did you know you can shear a Mooshroom, it removes the mushrooms and turn it back into a normal cow again?")
                .caption("You can hold a bowl near a Mushroom and milk it for some fresh Mushroom Stew.");

        this.category("winter_court")
                .name("Winter Court")
                .description("When leaves have fallen and the sky has turned to gray and the night has closed in on the day. That's when the Winter Court reigns supreme in the Feywild. Eventually death will come for us all and their purpose is to make sure the Feywild sees another turn of the year.")
                .icon(ModItems.summoningScrollWinterPixie);

        this.entry("winter_court")
                .name("Winter Court")
                .icon(ModTrees.winterTree.getSapling())
                .text("When leaves have fallen and the sky has turned to gray and the night has closed in on the day.")
                .text("That's when the Winter Court reigns supreme in the Feywild.")
                .text("These fey are not concerned with the living and their power struggles.")
                .text("Eventually death will come for us all and their purpose is to make sure the Feywild sees another turn of the year.")
                .text("They may seem cold and sullen at a first glance, but they will warm up to anyone who is willing to help the Feywild to its harshest most deadly period.")
                .text("Winter fey tend to be softspoken, slightly morbid and never choose their words without thinking about them.")
                .text("They seem to be a bit on the awkward side when it comes to social dealings, and some prefer the company of the dead.")
                .image("Frozen Retreat", "textures/patchouli_images/winter_book.png", "textures/patchouli_images/winter_worldtree_book.png")
                .text("The Winter World tree is difficult to find, but it is the home of Queen Mab.")
                .text("Note: Only the pixies are implemented, other court members will be added in later updates.")
                .text("The World Tree also has a Fey altar for you to use.")
                .item(ModBlocks.crocus.getSeed())
                .text("In the Frozen Retreat you can find giant crocuses.")
                .text("The giant crocus will open it's flower during the day and will close at night.")
                .text("The flower will also drop crocus seeds, which can be used to grow your own giant crocus.");

        this.entry("winter_pixie")
                .name("Winter Pixie")
                .icon(ModItems.summoningScrollWinterPixie)
                .entity(ModEntities.winterPixie)
                .caption("They may seem cold and sullen at a first glance, but they will warm up to anyone who is willing to help the Feywild to its harshest most deadly period.")
                .flip()
                .text("Beware that when you summon a pixie, you are bound to their Court!")
                .text("This means other courts might not be willing to interact with you afterwards.")
                .text("Also note that not all pixies are willing familiars, and they might trick you!")
                .text("Try interacting with your pixie after you summoned one, if possible try to gain their trust.")
                .add(this.altar("summoning_scroll_winter_pixie_fey_altar"))
                .text("With this scroll you can summon a Winter Pixie.")
                .text("The Winter Pixie will occasionally summon back a soul into a snowman's body.")
                .text("The summoned pixie will follow you by shift-right clicking her, if you want her to stay at a certain location, shift-right click her again.");

        this.entry("reaper_scythe")
                .name("Winter's Reaper Scythe")
                .icon(ModItems.soulShard)
                .add(this.altar("reaper_scythe_fey_altar"))
                .text("The winter reaper scythe grants special abilities to a player aligned to the Winter Court.")
                .item(ModItems.soulShard)
                .caption("With the reaper scythe you can collect soul shards from the undead.")
                .item(Blocks.ZOMBIE_HEAD)
                .caption("With the reaper scythe you can sometimes take the head of a undead monster.");

        this.category("dwarves")
                .name("Dwarves")
                .description("Brought into life by the spilling of Ymirs lifeblood, the dwarves have a strong binding with the earth, fire and the mountains. This makes them excellent and skilled metalworkers and craftsmen, and they take great pride in this.")
                .icon(ModBlocks.dwarvenAnvil);

        this.entry("dwarves")
                .name("Dwarves")
                .icon(Items.DIAMOND_PICKAXE)
                .text("Brought into life by the spilling of Ymirs lifeblood, the dwarves have a strong binding with the earth, fire and the mountains.")
                .text("This makes them excellent and skilled metalworkers and craftsmen, and they take great pride in this.")
                .entity(ModEntities.dwarfBlacksmith)
                .item(ModItems.summoningScrollDwarfBlacksmith, false)
                .text("Most dwarves can be found underground mining and some have build a home, somewhere in the Feywild.")
                .text("If you trade alot with them, they may offer you a Summoning Contract.");

        this.entry("dwarven_anvil")
                .name("Dwarven Anvil")
                .icon(ModBlocks.dwarvenAnvil)
                .crafting("dwarven_anvil")
                .caption("Dwarves don't easily make friends with outsiders, but if you are one of the few who has earned the gratitude of a dwarf, you have a friend for life.")
                .item(ModItems.summoningScrollDwarfBlacksmith)
                .text("A summoning contract can be obtained by trading with dwarves (up to level 3).")
                .text("By shift-right clicking the dwarven anvil with the summoning scroll, you will bind a dwarf to that anvil.")
                .text("The summoned dwarf will craft for you when you place the correct recipe in the anvil.")
                .text("So make sure you have a dwarf bound to the anvil.")
                .flip()
                .text("You can replenish the mana of the anvil, by placing fey dust as fuel in the buttom-left slot of the anvil.")
                .text("Most special recipes require mana.")
                .flip()
                .text("The summoned dwarf will have different trades then those found in caves or at the blacksmith.")
                .text("They will require a daily meal in exchange for ore.")
                .text("If you provide the dwarf with enough meals he will offer you his knowledge in the form of schematics.")
                .item(ModItems.schematicsGemTransmutation, false)
                .text("Most recipes for the anvil will require schematics.")
                .text("You can place the schematics in the top-left slot of the anvil.")
                .flip()
                .text("So to craft with the dwarven anvil make sure you have enough mana, a dwarf should be bound to the anvil, and you require the correct schematics.")
                .text("Once all items are placed the dwarf will start crafting for you!");

        this.entry("gem_transmutation")
                .name("Gem Transmutation")
                .icon(ModItems.brilliantFeyGem)
                .item(ModItems.schematicsGemTransmutation)
                .text("With the knowledge of gem transmutation your dwarf can craft you high quality fey gems.")
                .text("All you have to do is provide the correct ingredients.")
                .add(this.anvil("greater_fey_gem_dwarven_anvil"))
                .add(this.anvil("shiny_fey_gem_dwarven_anvil"))
                .add(this.anvil("brilliant_fey_gem_dwarven_anvil"));

        this.entry("dwarf_market")
                .name("Dwarven Market")
                .icon(ModItems.marketRuneStone)
                .item(ModItems.inactiveMarketRuneStone)
                .text("Runestones are small slabs of stone with magical properties.")
                .text("They can hold different kinds of magical spells.")
                .text("The most common are the Traveling stones, used to travel to other locations.")
                .item(ModItems.marketRuneStone)
                .text("You can ask a dwarf you are friends with to activate an inactive rune stone.")
                .text("When activated it can be used to teleport to a secret location only known by the dwarves and those they trust.")
                .image("Dwarven Market", "textures/patchouli_images/market_book.png")
                .caption("At the dwarven market you can trade for all kinds of items.")
                .entity(ModEntities.dwarfBaker).caption("This dwarf trades for all kinds of culinary items.")
                .entity(ModEntities.dwarfShepherd).caption("This dwarf trades for all kinds of farm animal items.")
                .entity(ModEntities.dwarfToolsmith).caption("This dwarf trades for all kinds of armour, weapons and tools.")
                .entity(ModEntities.dwarfArtificer).caption("This dwarf trades for all kinds of artifacts and trinkets.")
                .entity(ModEntities.dwarfMiner).caption("This dwarf trades for all kinds of stones and ore.")
                .entity(ModEntities.dwarfDragonHunter).caption("This dwarf trades for all kinds of monster drops.");
    }

    private Content altar(String id) {
        return this.altar(this.mod.resource(id));
    }

    private Content altar(ResourceLocation id) {
        return new AltarPage(id);
    }

    private Content anvil(String id) {
        return this.anvil(this.mod.resource(id));
    }

    private Content anvil(ResourceLocation id) {
        return new AnvilPage(id);
    }
}
