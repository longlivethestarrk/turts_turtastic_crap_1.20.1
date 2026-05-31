package net.turt.turtsturtasticcrap.item;

import net.minecraft.item.CrossbowItem;
import net.turt.turtsturtasticcrap.Turts_Turtastic_Crap;

public class ModCrossbow extends CrossbowItem {
    public ModCrossbow(Settings settings) {
        super(settings);
    }

    public static void registerModCrossbow() {
        Turts_Turtastic_Crap.LOGGER.info("Registering Mod Items for " + Turts_Turtastic_Crap.MOD_ID);
    }
}
