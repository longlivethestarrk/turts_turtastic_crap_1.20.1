package net.turt.turtsturtasticcrap;

import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.turt.turtsturtasticcrap.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Turts_Turtastic_Crap implements ModInitializer {
	public static final String MOD_ID = "turts_turtastic_crap";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModItems.registerModItems();

	}
}