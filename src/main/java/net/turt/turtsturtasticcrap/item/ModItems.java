package net.turt.turtsturtasticcrap.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.turt.turtsturtasticcrap.Turts_Turtastic_Crap;


public class ModItems {
    public static final Item NETHERITENUGGET = registerItem("netherite_nugget", new Item(new FabricItemSettings().fireproof()));
    public static final Item DIAMONDNUGGET = registerItem("diamond_nugget", new Item(new FabricItemSettings()));
    public static final Item COPPERNUGGET = registerItem("copper_nugget", new Item(new FabricItemSettings()));

    public static final Item FLATBOW = registerItem("flatbow", new  BowItem(new Item.Settings().maxDamage(400)));
    public static final Item LONGBOW = registerItem("longbow", new BowItem(new Item.Settings().maxDamage(500)));
    public static final Item PISTOLCROSSBOW = registerItem("pistol_crossbow", new CrossbowItem(new Item.Settings().maxDamage(500)));
    public static final Item ARBALEST = registerItem("arbalest", new CrossbowItem(new Item.Settings().maxDamage(500)));

    private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries) {
        entries.add(NETHERITENUGGET);
        entries.add(DIAMONDNUGGET);
        entries.add(COPPERNUGGET);
    }

    private static void addItemsToCombatItemGroup(FabricItemGroupEntries entries) {
        entries.add(FLATBOW);
        entries.add(LONGBOW);
        entries.add(PISTOLCROSSBOW);
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
