package net.turt.turtsturtasticcrap;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.entity.damage.DamageTracker;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.turt.turtsturtasticcrap.item.ModBowItem;
import net.turt.turtsturtasticcrap.item.ModCrossbowItem;
import net.turt.turtsturtasticcrap.item.ModItems;
import net.turt.turtsturtasticcrap.util.ModEnchantments;
import net.turt.turtsturtasticcrap.util.ModTrades;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Turts_Turtastic_Crap implements ModInitializer {
	public static final String MOD_ID = "turts_turtastic_crap";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModItems.registerModItems();
		ModBowItem.registerModBow();
		ModCrossbowItem.registerModCrossbow();
		ModTrades.registerModTrades();
		ModEnchantments.registerModEnchantments();

		FuelRegistry.INSTANCE.add(ModItems.SHORTBOW, 300);
		FuelRegistry.INSTANCE.add(ModItems.RECURVEBOW, 300);
		FuelRegistry.INSTANCE.add(ModItems.FLATBOW, 300);
		FuelRegistry.INSTANCE.add(ModItems.LONGBOW, 300);
		FuelRegistry.INSTANCE.add(ModItems.PISTOLCROSSBOW, 300);
		FuelRegistry.INSTANCE.add(ModItems.HEAVYCROSSBOW, 300);
		FuelRegistry.INSTANCE.add(ModItems.ARBALEST, 300);

	}
}