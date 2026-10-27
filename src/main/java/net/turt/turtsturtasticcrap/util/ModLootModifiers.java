package net.turt.turtsturtasticcrap.util;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetDamageLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.turt.turtsturtasticcrap.item.ModItems;

public class ModLootModifiers {

    public static void registerLoot() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {

            if (source.isBuiltin() && id.getPath().contains("chests") && !id.getPath().contains("spectrum")) {

                if (id.getPath().contains("village")) {
                    return;
                }

                LootPool.Builder pool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(1.0F))
                        .apply(SetDamageLootFunction.builder(UniformLootNumberProvider.create(0.1F, 0.75F)))
                        .with(ItemEntry.builder(net.minecraft.registry.Registries.ITEM.get(new net.minecraft.util.Identifier("minecraft", "copper_sword"))))
                        .with(ItemEntry.builder(ModItems.COPPER_LONGSWORD))
                        .with(ItemEntry.builder(ModItems.COPPER_TWINBLADE))
                        .with(ItemEntry.builder(ModItems.COPPER_RAPIER))
                        .with(ItemEntry.builder(ModItems.COPPER_KATANA))
                        .with(ItemEntry.builder(ModItems.COPPER_SAI))
                        .with(ItemEntry.builder(ModItems.COPPER_SPEAR))
                        .with(ItemEntry.builder(ModItems.COPPER_GLAIVE))
                        .with(ItemEntry.builder(ModItems.COPPER_WARGLAIVE))
                        .with(ItemEntry.builder(ModItems.COPPER_CUTLASS))
                        .with(ItemEntry.builder(ModItems.COPPER_CLAYMORE))
                        .with(ItemEntry.builder(ModItems.COPPER_GREATAXE))
                        .with(ItemEntry.builder(ModItems.COPPER_GREATHAMMER))
                        .with(ItemEntry.builder(ModItems.COPPER_CHAKRAM))
                        .with(ItemEntry.builder(ModItems.COPPER_SCYTHE))
                        .with(ItemEntry.builder(ModItems.COPPER_HALBERD))
                        .with(ItemEntry.builder(ModItems.COPPER_GREAT_KATANA))
                        .with(ItemEntry.builder(ModItems.COPPER_GRANDSWORD))
                        .with(ItemEntry.builder(ModItems.COPPER_BACKHAND_BLADE))
                        .with(ItemEntry.builder(ModItems.COPPER_LANCE))
                        .with(ItemEntry.builder(ModItems.COPPER_KHOPESH))
                        .with(ItemEntry.builder(ModItems.COPPER_DAGGER))
                        .with(ItemEntry.builder(ModItems.COPPER_PERNACH))
                        .with(ItemEntry.builder(ModItems.COPPER_QUARTERSTAFF))
                        .with(ItemEntry.builder(ModItems.COPPER_GREAT_SPEAR))
                        .with(ItemEntry.builder(ModItems.COPPER_DEER_HORNS));

                tableBuilder.pool(pool);
            }
        });
    }
}
