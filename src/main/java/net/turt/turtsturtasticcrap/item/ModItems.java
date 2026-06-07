package net.turt.turtsturtasticcrap.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
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
