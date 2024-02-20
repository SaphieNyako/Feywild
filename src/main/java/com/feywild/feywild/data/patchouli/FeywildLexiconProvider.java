package com.feywild.feywild.data.patchouli;

import com.feywild.feywild.block.ModBlocks;
import com.feywild.feywild.block.ModTrees;
import com.feywild.feywild.entity.ModEntities;
import com.feywild.feywild.item.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import org.moddingx.libx.datagen.DatagenContext;
import org.moddingx.libx.datagen.provider.patchouli.BookProperties;
import org.moddingx.libx.datagen.provider.patchouli.PatchouliProviderBase;
import org.moddingx.libx.datagen.provider.patchouli.page.Content;

public class FeywildLexiconProvider extends PatchouliProviderBase {

    public FeywildLexiconProvider(DatagenContext ctx) {
        super(ctx, new BookProperties("feywild_lexicon", true));
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
                .text("Did you ever find your shoes laced together after a good night's sleep?")
                .text("Or the sugar in your tea suddenly turn to salt?")
                .text("Did you ever travel through the forest swearing you saw something in the corner of your eye or hearing the giggle of a child's voice?")
                .text("Don't fret, you're not going mad! It's just the local Fey showing you that they care for you in their own very special way.")
                .flip()
                .text("The Fey are pranksters- tricksters who enjoy teasing us big folk. Most of the time they are harmless, but just as with us, there may be a bad seed or two amongst them.")
                .text("It's hard to gain the trust of the Fey, as they see the world in a different way that we do.")
                .item(ModItems.feywildLexicon)
                .text("If you want to learn more about the Fey, you may want to visit a Library and speak to one of the Lore Masters there. (If one isn't present, just ring the bell.)")
                .text("They also have guide books on all sorts of other subjects, depending what other schools of magic are present in the region.")
                .image("Library", "textures/patchouli_images/library_book.png")
                .crafting("feywild_lexicon");

        this.entry("seasonal_trees")
                .name("Seasonal Trees")
                .icon(ModTrees.autumnTree.getSapling())
                .item(ModTrees.springTree.getSapling())
                .text("Flowers from the spring season will grow around the Spring Tree.")
                .add(this.altar("spring_tree_sapling_fey_altar"))
                .crafting("spring_tree_wood")
                .crafting("spring_tree_planks")
                .item(ModTrees.summerTree.getSapling())
                .text("Flowers from the summer season will grow around the Summer Tree, and sometimes the Summer Court's bees will take residence in its branches.")
                .add(this.altar("summer_tree_sapling_fey_altar"))
                .crafting("summer_tree_wood")
                .crafting("summer_tree_planks")
                .item(ModTrees.autumnTree.getSapling())
                .text("The area around the Autumn Tree will soon be filled with fallen leaves. Sometimes a carved pumpkin will also appear to help ward off evil spirits.")
                .add(this.altar("autumn_tree_sapling_fey_altar"))
                .crafting("autumn_tree_wood")
                .crafting("autumn_tree_planks")
                .item(ModTrees.winterTree.getSapling())
                .text("The Winter Tree blankets the ground beneath it in snow and frost.")
                .add(this.altar("winter_tree_sapling_fey_altar"))
                .crafting("winter_tree_wood")
                .crafting("winter_tree_planks")
                .item(ModTrees.hexenTree.getSapling())
                .text("Some use witchcraft to corrupt Fey magic. Hexen Trees often grow in places where magic has been tainted in this way.")
                .crafting("hexen_tree_wood")
                .crafting("hexen_tree_planks")
                .item(ModTrees.blossomTree.getSapling())
                .text("An area blessed by a Archfey will often grow a special Blossom Tree.")
                .crafting("blossom_tree_wood")
                .crafting("blossom_tree_planks")
                .item(ModItems.feyDust, false)
                .text("The Fey Trees will also often drop Fey Dust from their leaves- as well as the occasional Mandrake Seed.")
                .item(ModTrees.blossomTree.getCrackedLogBlock())
                .text("Sometimes the Feywild's magic will leave cracks in the tree's bark. Using Fey Dust on the cracked log will only scar the block more.");


        this.entry("fey_altar")
                .name("Summoning Fey")
                .icon(ModBlocks.summerFeyAltar.asItem())
                .text("In order to summon the Fey or conjure the various artifacts associated with them, you will need a Fey Altar- which can be crafted or found in shrines present around villages.")
                .crafting("summer_fey_altar")
                .crafting("winter_fey_altar")
                .crafting("autumn_fey_altar")
                .caption("With the right combination of ingredients to establish a sympathetic link to your desired Fey, and the incantation written on a summoning scroll with magic ink, you can summon Fey out of the Feywild and into your realm.")
                .flip()
                .crafting("summoning_scroll")
                .crafting("fey_ink_bottle")
                .item(ModBlocks.mandrakeCrop.getSeed())
                .text("When a tree has been transmuted by Fey magic, some of its leaves will yield Mandrake Seeds when broken. You'll need the mandrakes grown from them for the ink used on summoning scrolls!");

        this.entry("handeling_fey")
                .name("Handling Fey")
                .icon(ModItems.summoningScroll)
                .item(ModItems.summoningScrollSpringPixie)
                .text("You can summon a fey by $(italic)using their Summoning Scroll on a block$(); you'll get the now-empty scroll back.")
                .text("Each kind of Fey has their own summoning scroll, and the recipes for each can be found on their corresponding pages [or by using JEI/REI].")
                .flip()
                .item(ModItems.summoningScroll)
                .text("By $(italic)hitting your Fey with an empty summoning scroll$(), you can withdraw them back into the summoning scroll.")
                .flip()
                .text("Some Fey are willing to follow you around the world. You can make a fey either $(italic)follow$() or $(italic)stay$() by interacting with them while sneaking.")
                .flip()
                .text("You can open your pixie's quest window by $(italic)interacting with them while empty-handed$().")
                .flip()
                .text("You can give an requested item to the Fey for their quest by $(italic)interacting with them while holding the required item$().");


        this.entry("quest")
                .name("Quests")
                .icon(ModItems.summoningScrollSpringPixie)
                .text("Each of the pixies have a questline that you can follow. To access your quests, interact with a Pixie you have summoned while empty-handed.")
                .text("The Pixie will start by asking for your name- after which you'll be aligned with their court.")
                .flip("Alignment")
                .text("[Editor's Note: Be aware that depending on your config settings, aligning with a court may cause Fey of other courts to leave! This is disabled by default.]");

        this.entry("feywild")
                .name("The Feywild Dimension")
                .icon(ModItems.teleportationOrb)
                .item(ModItems.teleportationOrb)
                .text("Once you have earned the trust of a Pixie you have summoned, she may offer to create a special artifact for you which will allow you to cross the veil of dreams and enter the Feywild.")
                .item(ModItems.pixieOrb)
                .text("These mystical pearls are created by sealing a Pixie inside an Eye of Ender; one of their primary uses is that a befrinded Pixie requires one in order to create your gateway into and out of the Feywild!")
                .text("You can offer additional Pixie Orbs to your Pixie friend if you need any replacement Teleportation Orbs.");

        this.category("fey_gems")
                .name("Fey Gems")
                .description("When a Fey passes on to the next world, they leave behind a pinch of Fey Dust. This dust is valued by craftsmen as it can be condensed into the rare and precious Fey Ore.")
                .icon(ModItems.greaterFeyGem);

        this.entry("fey_dust")
                .name("Fey Dust")
                .icon(ModItems.feyDust)
                .text("When a Fey passes into the next world, they leave behind a small amount of Fey Dust. This magical powder pervades all plants, creatures, and items native to the Feyild, and can be obtained in more peaceful ways by offering cookies to untamed Pixies.")
                .text("Alternatively, you can get Fey Dust from breaking the leaves of Fey trees.")
                .smelting("smelting/fey_dust")
                .caption("You can break a Fey Gem back down into dust by smelting it.");

        this.entry("fey_ore")
                .name("Fey Ore")
                .icon(ModItems.lesserFeyGem)
                .item(ModBlocks.feyGemOre)
                .text("Fey gems are sought after by some wizards and creatures because it is a refined form of the Fey's original magic.")
                .item(Items.STONECUTTER)
                .text("Fey gems can be cut to smaller pieces by using a Stonecutter.")
                .text("It's theoretically possible to improve the quality of a such a gem, but that's a secret that only the Dwarves know!")
                .flip()
                .text("Fey Ore can only be found in the Feywild Dimension. Be careful when mining this ore; it is fragile, and putting too much pressure on it can lower the quality of the excavated gems.")
                .item(ModBlocks.feyGemOreDeepSlate)
                .text("Fey ore can also be found in Deepslate veins. The magic is better preserved within deepslate, and this ore is somewhat more likely to yield high-quality gems.");

        this.entry("elven_quartz")
                .name("Elven Quartz")
                .icon(ModItems.rawElvenQuartz)
                .item(ModBlocks.feyStarBlockBlue)
                .caption("In the Feywild, you can find colorful geodes lined with Star Blocks.")
                .item(ModItems.rawSpringElvenQuartz)
                .caption("Underneath the starry shell is a geode full of Elven Quartz. There are five different varieties of it: one to represent each season, and a fifth that isn't attuned to any court.")
                .crafting("elven_quartz")
                .caption("Combining the raw quartz in a crafting table will create a block of Elven Quartz; these can be used to create all kinds of decorative blocks with a Stonecutter.")
                .crafting("elven_spring_quartz")
                .crafting("elven_summer_quartz")
                .crafting("elven_winter_quartz")
                .crafting("elven_autumn_quartz");

        this.category("spring_court")
                .name("Spring Court")
                .description("The Spring Court represents the birth of a new year and the coming of new life, and they are the most innocent of the Courts. However, they are notoriously obnoxious when playing pranks- as they seldom consider the consequences of their actions.")
                .icon(ModItems.summoningScrollSpringPixie);

        this.entry("spring_court")
                .name("Spring Court")
                .icon(ModTrees.springTree.getSapling())
                .text("Bouncy, bubbly, and completely devoid of the idea of personal space, the Fey of the Spring Court treat everything as if they have never seen it before.")
                        .text("They are like hyperactive children- always wondering what everything is, stuffing anything into their mouth that seems remotely edible, and only particularly concerned about not having enough fun or food for the day.")
                        .text("They represent the birth of a new year and the coming of new life, and are the most innocent of the Courts. However, they are notoriously obnoxious when playing pranks- as they do not know of the dangers or consequences of their actions.")
                        .text("...Nor do they care about that, for that matter!")
                        .image("Blossoming Wealds", "textures/patchouli_images/spring_book.png")
                        .item(ModBlocks.dandelion.getSeed())
                        .text("In the Blossoming Wealds, you can find Giant Dandelions in three variations: budding, flowering, and the flower's well known fluff- ready to scatter its seeds to the wind.")
                        .text("The flower will also drop dandelion seeds, which can be planted to grow your own giant dandelions.");

        this.entry("spring_pixie")
                .name("Spring Pixie")
                .icon(ModItems.summoningScrollSpringPixie)
                .entity(ModEntities.springPixie)
                .flip()
                .text("Commonly seen frolicking in the Blossoming Wealds without a care in the world, the Spring Pixie is often called the cutest and sweetest of the Fey- and she's more than happy to explore the world and make new friends. Just don't forget that sweetness and kindness are two different things!")
                .flip()
                .text("Remember that when you accept a Pixie's terms, you will be bound to their Court and the others will take notice of that!")
                .text("Also note that not all Pixies are willing familiars, and they may trick you! Try interacting with the pixie you summoned, and see if you can gain their trust.")
                .add(this.altar("summoning_scroll_spring_pixie_fey_altar"))
                .text("A Spring Pixie summoned by this scroll will cast a spell that causes flowers to grow where you tread- as well as to regenerate a bit of your health.")
                .text("As a reminder, you can ask the summoned pixie to follow you, or wait in a specific spot, by interacting with her while sneaking.")
                .item(Items.COOKIE)
                .text("Pixies love Cookies, as most Fey do! You can give a hungry Fey cookies to restore some of their health.")
                .item(ModItems.runeOfSpring)
                .text("You can change your Pixie's innate powers with Runestones. The Dwarves will know more about how these work.");

        this.entry("mandragora")
                .name("Mandragora")
                .icon(ModItems.summoningScrollMandragora)
                .text("Touched by a Fey spirit, the bright and optimistic Mandragoras are always ready to laugh, frolic, and enjoy life and nature. They sprout from their less-charming relatives: the Mandrakes.")
                .entity(ModEntities.mandragora)
                .crafting("magical_honey_cookie")
                .caption("By feeding a mandrake their favorite food- a Magical Honey Cookie- they will sprout into a Mandragora. They will take care of your garden, and her lovely singing voice may even help crops grow.")
                .item(ModItems.honeycomb)
                .caption("The Magical Honeycomb you need for these cookies can be found in the Feywild's Golden Seelie Fields. They're typically found in the Beekeeps, and guarded by Bee Knights!")
                .add(this.altar("summoning_scroll_mandragora_fey_altar"))
                .caption("[Editor's Note: If for whatever reason another mod prevents you from using Honey Cookies on your crops, a summoning scroll is available as an alternative.]")
                .item(ModBlocks.mandrakeCrop.getSeed())
                .caption("You can use Mandrake Seeds to change what type of Mandragora she currently looks like.");


        this.entry("spring_tree_ent")
                .name("Spring Tree Ent")
                .icon(ModItems.summoningScrollSpringTreeEnt)
                .entity(ModEntities.springTreeEnt)
                .flip()
                .text("The Tree Ents are Fey who, over time, came to resemble the trees they protected. They will attack anyone they deem unworthy of being in their territory- possibly including you, unless summoned by a player!")
                .add(this.altar("summoning_scroll_spring_tree_ent_fey_altar"))
                .item(ModTrees.springTree.getLogBlock().asItem())
                .text("You can help restore an Ent's body by giving them logs of the kind of tree they're made from; you can also give them a cookie if you really want to spoil them!")
                .item(Items.BONE_MEAL)
                .text("When given bone meal, the Spring Tree Ent will grant you some of their saplings- as well as flowers and their magical blessing to the particularly Lucky.");

        this.category("summer_court")
                .name("Summer Court")
                .description("The primary reigning Court within the Feywild, the Summer Court serve as the main defenders of the realm. Though polite, they demand recognition of their self-proclaimed sovereignty from the other inhabitants of the Feywild in turn.")
                .icon(ModItems.summoningScrollSummerPixie);

        this.entry("summer_court")
                .name("Summer Court")
                .icon(ModTrees.summerTree.getSapling())
                .text("The Summer Court is the primary reigning Court within the Feywild, and they know it! Haughty and demanding, they expect everyone who addresses them to treat them as the royals they are.")
                .text("The other Courts and inhabitants of the Feywild are there to serve them, and though polite, they demand recognition of their self-proclaimed royal status.")
                .flip()
                .text("The Summer Court defends the Feywild from intruders who seek to take a hold of the Faerie Court's riches; they are more combat-focused than the other Courts as a result- quick to answer any threat or opposition with their righteous judgment.")
                .image("Golden Seelie Fields", "textures/patchouli_images/summer_book.png")
                .item(ModBlocks.sunflower.getSeed())
                .text("In the Golden Seelie Fields you can find Giant Sunflowers, which will turn to face the sun's position in the sky.")
                .text("They can also drop sunflower seeds, which can be used to grow your own giant sunflowers.");

        this.entry("summer_pixie")
                .name("Summer Pixie")
                .icon(ModItems.summoningScrollSummerPixie)
                .entity(ModEntities.summerPixie)
                .flip()
                .text("In the Summer Court, even the smallest Pixie is endowed with the status and authority of their Court- and they'll make sure you remember that!")
                .text("If you manage to earn the favor of such a Fey, however, then you may gain a potent ally who will help guide you through thick and thin.")
                .flip()
                .text("Remember that when you accept a Pixie's terms, you will be bound to their Court and the others will take notice of that!")
                .text("Also note that not all Pixies are willing familiars, and they may trick you! Try interacting with the pixie you summoned, and see if you can gain their trust.")
                .add(this.altar("summoning_scroll_summer_pixie_fey_altar"))
                .text("A Summer Pixie summoned by this scroll will cast a spell that grants you Fire Resistance, and an aura that will burn any hostile mob who dares approach you.")
                .text("As a reminder, you can ask the summoned pixie to follow you, or wait in a specific spot, by interacting with her while sneaking.")
                .item(Items.COOKIE)
                .text("Pixies love Cookies, as most Fey do! You can give a hungry Fey cookies to restore some of their health.")
                .item(ModItems.runeOfSummer)
                .text("You can change your Pixie's innate powers with Runestones. The Dwarves will know more about how these work.");

        this.entry("bee_knight")
                .name("Bee Knight")
                .icon(ModItems.summoningScrollBeeKnight)
                .entity(ModEntities.beeKnight)
                .flip()
                .text("The Bee Knights patrol the Golden Seelie Fields, keeping an eye out for anyone who may try to steal their sacred honey.")
                .item(ModItems.honeycomb)
                .text("The best of the Summer Court's honey is stored in the Bee Keep and kept under constant watch by several Bee Knights.")
                .text("The glass display the Sacred Honeycomb is kept in is protected by magic. You can break into it by punching it repeatedly until the hole is big enough to extract the golden treasure within.")
                .add(this.altar("summoning_scroll_bee_knight_fey_altar"))
                .text("A Bee Knight summoned by this scroll will protect the nearby area from other creatures trying to interact with it.");

        this.entry("summer_tree_ent")
                .name("Summer Tree Ent")
                .icon(ModItems.summoningScrollSummerTreeEnt)
                .entity(ModEntities.summerTreeEnt)
                .flip()
                .text("The Tree Ents are Fey who, over time, came to resemble the trees they protected. They will attack anyone they deem unworthy of being in their territory- possibly including you, unless summoned by a player!")
                .add(this.altar("summoning_scroll_summer_tree_ent_fey_altar"))
                .item(ModTrees.summerTree.getLogBlock().asItem())
                .text("You can help restore an Ent's body by giving them logs of the kind of tree they're made from; you can also give them a cookie if you really want to spoil them!")
                .item(Items.BONE_MEAL)
                .text("When given bone meal the Summer Tree Ent will grant you some of their saplings, as well as the Strength to protect the realm of the Fey.")
                .item(ModItems.magicalHoneyCookie)
                .text("The Summer Tree Ent has attracted bees into nesting in their branches; perhaps giving them a Magical Honey Cookie will lure some of these bees out into the open.");

        this.category("autumn_court")
                .name("Autumn Court")
                .description("The most mature of the four Fae Courts, the Autumn Court bears the responsibility of preparing for the end of a yearly cycle. They have an appreciation for the arts, and are perhaps the kindest of the courts.")
                .icon(ModItems.summoningScrollAutumnPixie);

        this.entry("autumn_court")
                .name("Autumn Court")
                .icon(ModTrees.autumnTree.getSapling())
                .text("The Autumn Court is the most mature of the four Fae Courts, and their age and wisdom has given them a appreciation of fine art, music and poetry.")
                .text("They would rather spend a night listening to songs, dancing, and flirting than going to combat or mindlessly indulging themselves. They know the upcoming period will be a harsh one, and feel the responsibility of preparing for the end of a cycle.")
                .flip()
                .text("Perhaps the kindest of the Courts, the Autumn Fey are still fully capable of trickery and mischief; they are just more hush-hush about it.")
                .text("The Autumn Court will protect the Feywild from any evil spirits and harvest souls who have gone astray. To honor the lost souls, and to give them one more good time before they pass on to the next stage.")
                .image("The Eternal Fall", "textures/patchouli_images/autumn_book.png")
                .item(ModBlocks.treeMushroom)
                .text("In the Eternal Falls you can find Giant Mushrooms.")
                .text("You can grow your own giant mushrooms by bone-mealing red or brown mushrooms planted on podzol, and then carefully harvest them with shears.");

        this.entry("autumn_pixie")
                .name("Autumn Pixie")
                .icon(ModItems.summoningScrollAutumnPixie)
                .entity(ModEntities.autumnPixie)
                .flip()
                .text("A caretaker through and through, the Autumn Pixie is happy to look after anyone who comes to her. Though not particularly inclined to venture out into the larger world, she knows much about the mysteries of the Feywild and will do her best to support those she cares about.")
                .flip()
                .text("Remember that when you accept a Pixie's terms, you will be bound to their Court and the others will take notice of that!")
                .text("Also note that not all Pixies are willing familiars, and they may trick you! Try interacting with the pixie you summoned, and see if you can gain their trust.")
                .add(this.altar("summoning_scroll_autumn_pixie_fey_altar"))
                .text("An Autumn Pixie summoned by this scroll will wrap you in a magical wind that eases your movement and pushes away evil spirits who try to approach you.")
                .text("As a reminder, you can ask the summoned pixie to follow you, or wait in a specific spot, by interacting with her while sneaking.")
                .item(Items.COOKIE)
                .text("Pixies love Cookies, as most Fey do! You can give a hungry Fey cookies to restore some of their health.")
                .item(ModItems.runeOfAutumn)
                .text("You can change your Pixie's innate powers with Runestones. The Dwarves will know more about how these work.");

        this.entry("shroomling")
                .name("Shroomling")
                .icon(ModItems.summoningScrollShroomling)
                .item(ModItems.summoningScrollShroomling)
                .text("Though not the most talkative, the Shroomlings are always eager to please. They have a considerable spore allergy, though, and will sneeze at random.")
                .entity(ModEntities.shroomling)
                .add(this.altar("summoning_scroll_shroomling_fey_altar"))
                .crafting("fey_mushroom")
                .text("If the Shroomling wanders into an area that is not well lit, it will plant Fey Mushrooms to help light up the area; these can be harvested and placed elsewhere if you wish.")
                .text("Using Fey Dust or bone meal on a fey mushroom will make it glow brighter. Just don't use too much or it will turn dim instead!")
                .entity(EntityType.MOOSHROOM)
                .caption("A Shroomling sneezing near a cow might infect them with the 'shrooms- turning that cow into a Mooshroom. They can be milked with a bowl for some fresh Mushroom Stew, or sheared to revert them back into a normal cow.");

        this.category("winter_court")
                .name("Winter Court")
                .description("Sovereign over the Feywild's harshest season, the Winter Court are to make sure the Feywild sees another turn of the year. Soft-spoken and often a bit morbid, the Winter Fey will often warm up to those willing to help the Feywild through its deadliest period.")
                .icon(ModItems.summoningScrollWinterPixie);

        this.entry("winter_court")
                .name("Winter Court")
                .icon(ModTrees.winterTree.getSapling())
                .text("When the leaves have fallen, the sky has turned to gray, and the night closed in on the day- that's when the Winter Court reigns supreme in the Feywild.")
                .text("These Fey are apathetic to the living and their power struggles; eventually death will come for us all, and their purpose is to make sure the Feywild sees another turn of the year.")
                .flip()
                .text("Winter Fey tend to be soft spoken, slightly morbid, and never choose their words without thinking about them. They seem to be a bit on the awkward side in social dealings, and some prefer the company of the dead.")
                .image("Frozen Retreat", "textures/patchouli_images/winter_book.png")
                .item(ModBlocks.crocus.getSeed())
                .text("The Giant Crocuses of the Frozen Retreat will open their flowers during the day and close them at night.")
                .text("The flower will also drop crocus seeds, which can be used to grow your own giant crocus.");

        this.entry("winter_pixie")
                .name("Winter Pixie")
                .icon(ModItems.summoningScrollWinterPixie)
                .entity(ModEntities.winterPixie)
                .flip()
                .text("As with the rest of her Court, the Winter Pixie is soft-spoken, fairly withdrawn, and often given to... morbid interests.")
                .text("Cold and sullen at a first glance, they will often warm up to those willing to help the Feywild through its harshest and deadliest season.")
                .flip()
                .text("Remember that when you accept a Pixie's terms, you will be bound to their Court and the others will take notice of that!")
                .text("Also note that not all Pixies are willing familiars, and they may trick you! Try interacting with the pixie you summoned, and see if you can gain their trust.")
                .add(this.altar("summoning_scroll_winter_pixie_fey_altar"))
                .text("A Winter Pixie summoned by this scoll will grant you the power to walk on water by freezing it beneath your feet.")
                .text("As a reminder, you can ask the summoned pixie to follow you, or wait in a specific spot, by interacting with her while sneaking.")
                .item(Items.COOKIE)
                .text("Pixies love Cookies, as most Fey do! You can give a hungry Fey cookies to restore some of their health.")
                .item(ModItems.runeOfWinter)
                .text("You can change your Pixie's innate powers with Runestones. The Dwarves will know more about how these work.");

        this.entry("allay")
                .name("Allay")
                .icon(ModItems.summoningScrollAllay)
                .entity(EntityType.ALLAY)
                .flip()
                .text("The Allay are spirits of lost Fey. They're commonly instructed to look for lost items and return them to their owner; among other things, the Winter Court's Reapers like to employ them to retrieve souls shards for them. However, the Allay are not truly loyal to the Winter Court and will aid whomever they can. ")
                .add(this.altar("summoning_scroll_allay_fey_altar"))
                .item(Items.COOKIE)
                .text("Unlike other Fey, Allay do not react to cookies or other treats in any special way. [Editor's Note: I was told we're supposed to blame 'vanilla ice cream' or something like that.]");

        this.entry("winter_tree_ent")
                .name("Winter Tree Ent")
                .icon(ModItems.summoningScrollWinterTreeEnt)
                .entity(ModEntities.winterTreeEnt)
                .flip()
                .text("The Tree Ents are Fey who, over time, came to resemble the trees they protected. They will attack anyone they deem unworthy of being in their territory- possibly including you, unless summoned by a player!")
                .text("Ents of this persuasion are not hostile towards the undead; like the rest of the Winter Court, they are accustomed to life's many endings.")
                .add(this.altar("summoning_scroll_winter_tree_ent_fey_altar"))
                .item(ModTrees.winterTree.getLogBlock().asItem())
                .text("You can help restore an Ent's body by giving them logs of the kind of tree they're made from; you can also give them a cookie if you really want to spoil them.")
                .item(Items.BONE_MEAL)
                .text("When given bone meal, the Winter Tree Ent will grant you some of their saplings- as well as the power to Resist those who seek to extinguish your life prematurely.");

        this.entry("reaper_scythe")
                .name("Winter's Reaper Scythe")
                .icon(ModItems.soulShard)
                .add(this.altar("reaper_scythe_fey_altar"))
                .text("Bone-chilling scythes such as this one are commonly-used weapons by the soul reapers of the Winter Court; as such, it grants its full power to those attuned to their court.")
                .item(ModItems.soulShard)
                .caption("By striking down undead creatures using the Reaper Scythe, you may extract pieces of their reanimated souls- condensed into crystalline shards like these. ")
                .item(Blocks.ZOMBIE_HEAD)
                .caption("As an added bonus, slaying certain mobs with the scythe yields a moderate chance to behead them! Such a grisly trophy might earn you the approval of the most morbid of Fey.")
                .add(this.altar("soul_gem_fey_altar"))
                .text("By combining these crystalized soul fragments into a Soul Gem, you may return the poor soul within to the world of the living. This process isn't perfect, however, and if the soul inside is malformed or incomplete, then they might wind up corrupted as a result!")
                .item(ModItems.lesserFeyGem)
                .caption("Your scythe can be repaired with Lesser Fey Gems.");

        this.category("neutral_courts")
                .name("Neutral Courts")
                .description("Not all Fey belong to the Seasonal Courts, and some even hail from other, more obscure Courts! There are even rumours of Fey who have aligned with the stars.")
                .icon(ModItems.summoningScrollHexenTreeEnt);

        this.entry("pixie_wings")
                .name("Pixie Wings")
                .icon(ModItems.feyWingsSpring)
                .text("In stories past, Pixies didn't originally have wings; that changed after the fall of Light and Darkness, when their domains were split apart into the Courts we know of today. Wishing to discover their origins, the Fey yearning for the stars bestowed their kin with power of flight.$(br)You can recieve the Pixie Wing Tiara required to craft your own set of wings by earning your place within one of the Fey Courts.")
                .add(this.altar("fey_wings_spring_fey_altar"))
                .add(this.altar("fey_wings_summer_fey_altar"))
                .add(this.altar("fey_wings_autumn_fey_altar"))
                .add(this.altar("fey_wings_winter_fey_altar"))
                .add(this.altar("fey_wings_light_fey_altar"))
                .add(this.altar("fey_wings_shadow_fey_altar"))
                .item(ModItems.feyDust)
                .caption("You can repair your wings by using Fey Dust.");

        this.entry("hexen_tree_ent")
                .name("Hexen Tree Ent")
                .icon(ModItems.summoningScrollHexenTreeEnt)
                .entity(ModEntities.hexenTreeEnt)
                .flip()
                .text("The Tree Ents are Fey who, over time, came to resemble the trees they protected. They will attack anyone they deem unworthy of being in their territory- possibly including you, unless summoned by a player!")
                .add(this.altar("summoning_scroll_hexen_tree_ent_fey_altar"))
                .item(ModTrees.hexenTree.getLogBlock().asItem())
                .text("You can help restore an Ent's body by giving them logs of the kind of tree they're made from; you can also give them a cookie if you really want to spoil them.")
                .item(Items.BONE_MEAL)
                .text("When given bone meal, the Hexen Tree Ent will grant you some of their saplings.")
                .item(ModItems.feyDust)
                .text("Twisted by dark magic, Hexen ents can consume fey dust to provide the witches who summon them with all sorts of magical boons. Just be warned that due to the volatile nature of their magic, the 'blessings' these ents have to offer might not turn out the way you'd like them to!");

        this.entry("blossom_tree_ent")
                .name("Blossom Tree Ent")
                .icon(ModItems.summoningScrollBlossomTreeEnt)
                .entity(ModEntities.blossomTreeEnt)
                .flip()
                .text("The Tree Ents are Fey who, over time, came to resemble the trees they protected. They will attack anyone they deem unworthy of being in their territory- possibly including you, unless summoned by a player!")
                .add(this.altar("summoning_scroll_blossom_tree_ent_fey_altar"))
                .item(ModTrees.blossomTree.getLogBlock().asItem())
                .text("You can help restore an Ent's body by giving them logs of the kind of tree they're made from; you can also give them a cookie if you really want to spoil them.")
                .item(Items.BONE_MEAL)
                .text("When given bone meal, the Blossom Tree Ent will grant you some of their saplings. They may also grace you with apples- potentially magical ones at that!");

        this.category("dwarves")
                .name("Dwarves")
                .description("The secretive Dwarves are highly attuned with fire, the earth, and the mountains. They are excellent metalworkers and highly-skilled craftsmen, and they take great pride in this.")
                .icon(ModBlocks.dwarvenAnvil);

        this.entry("dwarves")
                .name("Dwarves")
                .icon(Items.DIAMOND_PICKAXE)
                .text("Brought into life by the spilling of Ymir's life-blood, the Dwarves are highly attuned with fire, the earth, and the mountains.")
                .text("This makes them excellent metalworkers and highly-skilled craftsmen, and they take great pride in this.")
                .entity(ModEntities.dwarfBlacksmith)
                .item(ModItems.summoningScrollDwarfBlacksmith, false)
                .text("Most dwarves can be found underground in their Dwarven Forges, and a few have built homes for themselves somewhere in the Feywild.")
                .text("If you trade a lot of items with them, they may offer you a Summoning Contract.");

        this.entry("dwarven_anvil")
                .name("Dwarven Anvil")
                .icon(ModBlocks.dwarvenAnvil)
                .crafting("dwarven_anvil")
                .caption("Dwarves don't easily make friends with outsiders, but if you are one of the few who have earned the gratitude of a dwarf, you have a friend for life.")
                .text("If you have a Dwarven Anvil and the resources it requires (read on), a Dwarven friend can use it to craft all sorts of magical items for you.")
                .item(ModItems.summoningScrollDwarfBlacksmith)
                .text("A Summoning Contract can be obtained by trading with dwarves [up to level 3].")
                .text("By sneak-using the summoning contract on a Dwarven Anvil, you will summon a dwarf bound to that anvil. You may then provide the anvil with the ingredients for your crafting recipe, which the summoned dwarf will use to craft your desired item!")
                .flip()
                .item(ModItems.feyDust)
                .caption("Many recipes require additional arcane power in the form of mana. You can replenish your anvil's mana reserves by inserting fuel in the form of Fey Dust into the bottom-left slot of the anvil.")
                .flip()
                .text("A summoned dwarf will have different trades than those found out in the world. They will request a daily meal in exchange for ore.")
                .text("If you provide the dwarf enough provisions, he may offer you his knowledge in the form of Schematics.")
                .item(ModItems.schematicsGemTransmutation, false)
                .text("Written in the Dwarves' native tongue, these various books contain the detailed procedures and instructions required to craft items with the Dwarven Anvil. They are not consumed during crafting, but a dwarf needs to have the prerequisite set of schematics on hand in order to craft items. They can be placed in the top-left slot of the anvil.")
                .flip()
                .text("Once the Dwarven Anvil has been supplied with the correct schematics, ingredients, and sufficient mana, the dwarf will begin crafting for you!");

        this.entry("gem_transmutation")
                .name("Gem Transmutation")
                .icon(ModItems.brilliantFeyGem)
                .item(ModItems.schematicsGemTransmutation)
                .text("This set of schematics contains the elusive secret to manufacturing high-quality Fey Gems! All you need to do is provide your friendly neighborhood Dwarf sufficient mana and five fey gems of matching quality.")
                .add(this.anvil("greater_fey_gem_dwarven_anvil"))
                .add(this.anvil("shiny_fey_gem_dwarven_anvil"))
                .add(this.anvil("brilliant_fey_gem_dwarven_anvil"));

        this.entry("elven_quartz")
                .name("Elven Quartz")
                .icon(ModItems.rawElvenQuartz)
                .item(ModItems.schematicsElvenQuartz)
                .text("With this knowledge, an allied Dwarf can craft all kinds of Elven Quartz, as well as the various Star Blocks.")
                .add(this.anvil("raw_elven_quartz_dwarven_anvil"))
                .add(this.anvil("raw_autumn_elven_quartz_dwarven_anvil"))
                .add(this.anvil("raw_spring_elven_quartz_dwarven_anvil"))
                .add(this.anvil("raw_summer_elven_quartz_dwarven_anvil"))
                .add(this.anvil("raw_winter_elven_quartz_dwarven_anvil"))
                .add(this.anvil("fey_star_block_blue_dwarven_anvil"))
                .add(this.anvil("fey_star_block_green_dwarven_anvil"))
                .add(this.anvil("fey_star_block_light_blue_dwarven_anvil"))
                .add(this.anvil("fey_star_block_orange_dwarven_anvil"))
                .add(this.anvil("fey_star_block_pink_dwarven_anvil"))
                .add(this.anvil("fey_star_block_purple_dwarven_anvil"))
                .add(this.anvil("fey_star_block_yellow_dwarven_anvil"));


        this.entry("runestones")
                .name("Runestones")
                .icon(ModItems.runeOfAutumn)
                .item(ModItems.schematicsRunestones)
                .text("Besides the Traveling Stones, there are a variety of different runestones with unique magical powers. This set of blueprints allows a Dwarf you've befriended to craft a few of them, as well as to recharge runestones that have been depleted of their energy.")
                .add(this.anvil("market_rune_stone_dwarven_anvil"))
                .add(this.anvil("rune_of_spring_dwarven_anvil"))
                .text("This runestone allows your pixie to cast the 'Flower Walk' spell on their owner.")
                .add(this.anvil("rune_of_summer_dwarven_anvil"))
                .text("This runestone allows your pixie to cast the 'Fire Walk' spell on their owner.")
                .add(this.anvil("rune_of_winter_dwarven_anvil"))
                .text("This runestone allows your pixie to cast the 'Frost Walk' spell on their owner.")
                .add(this.anvil("rune_of_autumn_dwarven_anvil"))
                .text("This runestone allows your pixie to cast the 'Wind Walk' spell on their owner.");

        this.entry("dwarf_market")
                .name("The Dwarven Market")
                .icon(ModItems.marketRuneStone)
                .item(ModItems.inactiveMarketRuneStone)
                .text("Runestones are small stone slates with magical properties. They can hold many kinds of magical powers; the most common are the Traveling Stones, used to travel to other locations.")
                .item(ModItems.marketRuneStone)
                .text("If you have earned the trust of a dwarf, you may ask them to reactivate such a runestone, and grant you access to a secret location known only to the dwarves and those they trust.")
                .image("Dwarven Market", "textures/patchouli_images/market_book.png")
                .caption("At the Dwarven Market, you can trade for all kinds of commodities:")
                .entity(ModEntities.dwarfBaker).caption("This dwarf has a variety of culinary items for sale.")
                .entity(ModEntities.dwarfShepherd).caption("This dwarf has some animal products up for sale.")
                .entity(ModEntities.dwarfToolsmith).caption("This dwarf can supply you with various weapons, armor, and tools.")
                .entity(ModEntities.dwarfArtificer).caption("This dwarf is an avid creator of artifacts, trinkets, and other gizmos- and eager to sell them, too.")
                .entity(ModEntities.dwarfMiner).caption("This dwarf sells some of the ore and stone they've dug up, and well as surplus mining supplies.")
                .entity(ModEntities.dwarfDragonHunter).caption("This dwarf is selling the drops of monsters they've hunted.");

        this.entry("magical_brazier")
                .name("Magical Brazier")
                .icon(ModBlocks.magicalBrazier.asItem())
                .crafting("magical_brazier")
                .caption("For now, the true nature of the Magical Brazier remains a mystery to us. What we know thus far is that the Dwarves invented them.")
                .item(ModItems.feyDust)
                .caption("By using Fey Dust on a brazier, you can set it alight; it can be extinguished with a shovel.");
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
