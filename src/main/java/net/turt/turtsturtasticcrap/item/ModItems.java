package net.turt.turtsturtasticcrap.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SwordItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.turt.turtsturtasticcrap.Turts_Turtastic_Crap;


public class ModItems {
    public static final Item FLAXSTRING = registerItem("flax_string", new Item((new FabricItemSettings())));
    public static final Item NETHERITENUGGET = registerItem("netherite_nugget", new Item(new FabricItemSettings().fireproof()));
    public static final Item DIAMONDNUGGET = registerItem("diamond_nugget", new Item(new FabricItemSettings()));
    public static final Item COPPERNUGGET = registerItem("copper_nugget", new Item(new FabricItemSettings()));

    public static final Item SHORTBOW = registerItem("shortbow", new ModBowItem(new FabricItemSettings().maxCount(1).maxDamage(307),10,0.5F,0.95));
    public static final Item RECURVEBOW = registerItem("recurvebow", new ModBowItem(new FabricItemSettings().maxCount(1).maxDamage(384),25,1.1F,0.95));
    public static final Item FLATBOW = registerItem("flatbow", new ModBowItem(new FabricItemSettings().maxCount(1).maxDamage(480), 40, 1.5F, 1.1));
    public static final Item LONGBOW = registerItem("longbow", new ModBowItem(new FabricItemSettings().maxCount(1).maxDamage(576), 70, 2.25F, 1.25));
    public static final Item PISTOLCROSSBOW = registerItem("pistol_crossbow", new ModCrossbowItem(new FabricItemSettings().maxCount(1).maxDamage(261), 15, 0.5F, 1.0));
    public static final Item HEAVYCROSSBOW = registerItem("heavy_crossbow", new ModCrossbowItem(new FabricItemSettings().maxCount(1).maxDamage(408), 50, 1.75F, 1.15));
    public static final Item ARBALEST = registerItem("arbalest", new ModCrossbowItem(new FabricItemSettings().maxCount(1).maxDamage(489), 80, 2.5F, 1.3));

    public static final Item COPPER_LONGSWORD = registerItem("copper_longsword",
            new SwordItem(ModToolMaterials.COPPER, 3, -2.4f, new FabricItemSettings()));
    public static final Item COPPER_TWINBLADE = registerItem("copper_twinblade",
            new SwordItem(ModToolMaterials.COPPER, 3, -2f, new FabricItemSettings()));
    public static final Item COPPER_RAPIER = registerItem("copper_rapier",
            new SwordItem(ModToolMaterials.COPPER, 2, -1.8f, new FabricItemSettings()));
    public static final Item COPPER_KATANA = registerItem("copper_katana",
            new SwordItem(ModToolMaterials.COPPER, 3, -2f, new FabricItemSettings()));
    public static final Item COPPER_SAI = registerItem("copper_sai",
            new SwordItem(ModToolMaterials.COPPER, 0, -1.5f, new FabricItemSettings()));
    public static final Item COPPER_SPEAR = registerItem("copper_spear",
            new SwordItem(ModToolMaterials.COPPER, 3, -2.7f, new FabricItemSettings()));
    public static final Item COPPER_GLAIVE = registerItem("copper_glaive",
            new SwordItem(ModToolMaterials.COPPER, 3, -2.6f, new FabricItemSettings()));
    public static final Item COPPER_WARGLAIVE = registerItem("copper_warglaive",
            new SwordItem(ModToolMaterials.COPPER, 0, -2.2f, new FabricItemSettings()));
    public static final Item COPPER_CUTLASS = registerItem("copper_cutlass",
            new SwordItem(ModToolMaterials.COPPER, 3, -2f, new FabricItemSettings()));
    public static final Item COPPER_CLAYMORE = registerItem("copper_claymore",
            new SwordItem(ModToolMaterials.COPPER, 5, -2.8f, new FabricItemSettings()));
    public static final Item COPPER_GREATAXE = registerItem("copper_greataxe",
            new SwordItem(ModToolMaterials.COPPER, 6, -3.1f, new FabricItemSettings()));
    public static final Item COPPER_GREATHAMMER = registerItem("copper_greathammer",
            new SwordItem(ModToolMaterials.COPPER, 7, -3.2f, new FabricItemSettings()));
    public static final Item COPPER_CHAKRAM = registerItem("copper_chakram",
            new SwordItem(ModToolMaterials.COPPER, 2, 0f, new FabricItemSettings()));
    public static final Item COPPER_SCYTHE = registerItem("copper_scythe",
            new SwordItem(ModToolMaterials.COPPER, 4, -2.7f, new FabricItemSettings()));
    public static final Item COPPER_HALBERD = registerItem("copper_halberd",
            new SwordItem(ModToolMaterials.COPPER, 6, -2.8f, new FabricItemSettings()));

    public static final Item COPPER_GREAT_KATANA = registerItem("copper_great_katana",
            new SwordItem(ModToolMaterials.COPPER, 4, -2.6f, new FabricItemSettings()));
    public static final Item COPPER_GRANDSWORD = registerItem("copper_grandsword",
            new SwordItem(ModToolMaterials.COPPER, 9, -3.4f, new FabricItemSettings()));
    public static final Item COPPER_BACKHAND_BLADE = registerItem("copper_backhand_blade",
            new SwordItem(ModToolMaterials.COPPER, 1, -1.7f, new FabricItemSettings()));
    public static final Item COPPER_LANCE = registerItem("copper_lance",
            new SwordItem(ModToolMaterials.COPPER, 3, -3f, new FabricItemSettings()));
    public static final Item COPPER_KHOPESH = registerItem("copper_khopesh",
            new SwordItem(ModToolMaterials.COPPER, 2, -2.1f, new FabricItemSettings()));
    public static final Item COPPER_DAGGER = registerItem("copper_dagger",
            new SwordItem(ModToolMaterials.COPPER, 1, -1.8f, new FabricItemSettings()));
    public static final Item COPPER_PERNACH = registerItem("copper_pernach",
            new SwordItem(ModToolMaterials.COPPER, 4, -2.7f, new FabricItemSettings()));
    public static final Item COPPER_QUARTERSTAFF = registerItem("copper_quarterstaff",
            new SwordItem(ModToolMaterials.COPPER, 1, -2.f, new FabricItemSettings()));
    public static final Item COPPER_GREAT_SPEAR = registerItem("copper_great_spear",
            new SwordItem(ModToolMaterials.COPPER, 6, -3f, new FabricItemSettings()));
    public static final Item COPPER_DEER_HORNS = registerItem("copper_deer_horns",
            new SwordItem(ModToolMaterials.COPPER, 2, -1.9f, new FabricItemSettings()));


    private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries) {
        entries.add(FLAXSTRING);
        entries.add(NETHERITENUGGET);
        entries.add(DIAMONDNUGGET);
        entries.add(COPPERNUGGET);
    }

    private static void addItemsToCombatItemGroup(FabricItemGroupEntries entries) {
        entries.add(SHORTBOW);
        entries.add(RECURVEBOW);
        entries.add(FLATBOW);
        entries.add(LONGBOW);
        entries.add(PISTOLCROSSBOW);
        entries.add(HEAVYCROSSBOW);
        entries.add(ARBALEST);

        entries.add(COPPER_LONGSWORD);
        entries.add(COPPER_TWINBLADE);
        entries.add(COPPER_RAPIER);
        entries.add(COPPER_KATANA);
        entries.add(COPPER_SAI);
        entries.add(COPPER_SPEAR);
        entries.add(COPPER_GLAIVE);
        entries.add(COPPER_WARGLAIVE);
        entries.add(COPPER_CUTLASS);
        entries.add(COPPER_CLAYMORE);
        entries.add(COPPER_GREATAXE);
        entries.add(COPPER_GREATHAMMER);
        entries.add(COPPER_CHAKRAM);
        entries.add(COPPER_SCYTHE);
        entries.add(COPPER_HALBERD);

        entries.add(COPPER_GREAT_KATANA);
        entries.add(COPPER_GRANDSWORD);
        entries.add(COPPER_BACKHAND_BLADE);
        entries.add(COPPER_LANCE);
        entries.add(COPPER_KHOPESH);
        entries.add(COPPER_DAGGER);
        entries.add(COPPER_PERNACH);
        entries.add(COPPER_QUARTERSTAFF);
        entries.add(COPPER_GREAT_SPEAR);
        entries.add(COPPER_DEER_HORNS);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Turts_Turtastic_Crap.MOD_ID, name), item);
    }

    public static void registerModItems() {
        Turts_Turtastic_Crap.LOGGER.info("Registering Mod Items for " + Turts_Turtastic_Crap.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(ModItems::addItemsToCombatItemGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientItemGroup);
    }
}
