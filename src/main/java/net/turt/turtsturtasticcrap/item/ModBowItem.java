package net.turt.turtsturtasticcrap.item;

import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.turt.turtsturtasticcrap.Turts_Turtastic_Crap;

public class ModBowItem extends BowItem {
    private final int maxDrawTicks;
    private final float velocityMultiplier;
    private final double damageMultiplier;

    public ModBowItem(Settings settings, int maxDrawTicks, float velocityMultiplier, double damageMultiplier) {
        super(settings);
        this.maxDrawTicks = maxDrawTicks;
        this.damageMultiplier = damageMultiplier;
        this.velocityMultiplier = velocityMultiplier;
    }

    public int getMaxDrawTicks() {
        return this.maxDrawTicks;
    }

    public float getVelocityMultiplier() {
        return this.velocityMultiplier;
    }

    public double getDamageMultiplier() {
        return this.damageMultiplier;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    public static void registerModBow() {
        Turts_Turtastic_Crap.LOGGER.info("Registering Mod Items for " + Turts_Turtastic_Crap.MOD_ID);
    }
}
