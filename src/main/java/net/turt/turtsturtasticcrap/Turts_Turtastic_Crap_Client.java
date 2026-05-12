package net.turt.turtsturtasticcrap;

import net.fabricmc.api.ClientModInitializer;
import net.turt.turtsturtasticcrap.util.ModModelPredicates;

public class Turts_Turtastic_Crap_Client implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModModelPredicates.registerModModelPredicates();
    }
}
