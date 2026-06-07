package net.turt.turtsturtasticcrap;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.turt.turtsturtasticcrap.item.ModBowItem;
import net.turt.turtsturtasticcrap.item.ModCrossbow;
import net.turt.turtsturtasticcrap.item.ModItems;

public class Turts_Turtastic_Crap_Client implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        Item[] customCrossbows = new Item[]{ ModItems.PISTOLCROSSBOW, ModItems.HEAVYCROSSBOW, ModItems.ARBALEST };
        for (Item crossbowItem : customCrossbows) {

            ModelPredicateProviderRegistry.register(crossbowItem, new Identifier("pull"), (stack, world, entity, seed) -> {
                if (entity == null || entity.getActiveItem() != stack) {
                    return 0.0F;
                }
                if (CrossbowItem.isCharged(stack)) {
                    return 0.0F;
                }
                if (stack.getItem() instanceof ModCrossbow customCrossbow) {
                    // 1. Tracks current progress relative to the 72000-tick baseline
                    int ticksHeld = stack.getMaxUseTime() - entity.getItemUseTimeLeft();

                    // 2. NEW: Calculate the Quick Charge level reduction dynamically
                    int quickChargeLevel = net.minecraft.enchantment.EnchantmentHelper.getLevel(
                            net.minecraft.enchantment.Enchantments.QUICK_CHARGE, stack
                    );
                    int targetTicks = customCrossbow.getMaxDrawTicks() - (quickChargeLevel * 5);
                    if (targetTicks < 1) targetTicks = 1;

                    // 3. Divide by the modified target ticks so the visual animation matches the logic
                    return (float) ticksHeld / (float) targetTicks;
                }
                return 0.0F;
            });

            ModelPredicateProviderRegistry.register(crossbowItem, new Identifier("pulling"),
                    (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack && !CrossbowItem.isCharged(stack) ? 1.0F : 0.0F
            );

            ModelPredicateProviderRegistry.register(crossbowItem, new Identifier("charged"),
                    (stack, world, entity, seed) -> CrossbowItem.isCharged(stack) ? 1.0F : 0.0F
            );

            ModelPredicateProviderRegistry.register(crossbowItem, new Identifier("firework"),
                    (stack, world, entity, seed) -> CrossbowItem.isCharged(stack) && CrossbowItem.hasProjectile(stack, net.minecraft.item.Items.FIREWORK_ROCKET) ? 1.0F : 0.0F
            );
        }

        Item[] customBows = new Item[]{ ModItems.SHORTBOW, ModItems.RECURVEBOW, ModItems.FLATBOW, ModItems.LONGBOW };
        for (Item bowItem : customBows) {

            ModelPredicateProviderRegistry.register(bowItem, new Identifier("pulling"),
                    (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F
            );

            ModelPredicateProviderRegistry.register(bowItem, new Identifier("pull"),
                    (stack, world, entity, seed) -> {
                        if (entity == null || entity.getActiveItem() != stack) {
                            return 0.0F;
                        }

                        if (stack.getItem() instanceof ModBowItem customBow) {
                            int maxUseTime = stack.getMaxUseTime();

                            int timeLeft = entity.getItemUseTimeLeft();

                            int ticksHeld = maxUseTime - timeLeft;

                            return (float) ticksHeld / (float) customBow.getMaxDrawTicks();
                        }
                        return 0.0F;
                    }
            );
        }
    }
}
