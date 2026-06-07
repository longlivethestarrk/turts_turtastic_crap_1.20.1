package net.turt.turtsturtasticcrap;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.turt.turtsturtasticcrap.item.ModBowItem;
import net.turt.turtsturtasticcrap.item.ModCrossbow;
import net.turt.turtsturtasticcrap.item.ModItems;

public class Turts_Turtastic_Crap_Client implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        // Iterate through all items registered under your Mod ID
        for (Item item : Registries.ITEM) {
            if (item instanceof ModCrossbow) {

                // 1. Register "pulling" predicate
                ModelPredicateProviderRegistry.register(item, new Identifier("pulling"),
                        (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F);

                // 2. Register "pull" predicate
                ModelPredicateProviderRegistry.register(item, new Identifier("pull"),
                        (stack, world, entity, seed) -> {
                            if (entity == null) {
                                return 0.0F;
                            } else {
                                return entity.getActiveItem() != stack ? 0.0F : (float)(stack.getMaxUseTime() - entity.getItemUseTimeLeft()) / (float)CrossbowItem.getPullTime(stack);
                            }
                        });

                // 3. Register "charged" predicate
                ModelPredicateProviderRegistry.register(item, new Identifier("charged"),
                        (stack, world, entity, seed) -> CrossbowItem.isCharged(stack) ? 1.0F : 0.0F);

                // 4. Register "firework" predicate
                ModelPredicateProviderRegistry.register(item, new Identifier("firework"),
                        (stack, world, entity, seed) -> CrossbowItem.isCharged(stack) && CrossbowItem.hasProjectile(stack, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F);
            }
        }

        // Create an array of your custom bows to easily register them all at once
        Item[] customBows = new Item[]{ ModItems.FLATBOW, ModItems.LONGBOW };
        for (Item bowItem : customBows) {
            // Predicate 1: Is the bow pulling?
            ModelPredicateProviderRegistry.register(bowItem, new Identifier("pulling"),
                    (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F
            );

            // Predicate 2: How far is it pulled?
            ModelPredicateProviderRegistry.register(bowItem, new Identifier("pull"),
                    (stack, world, entity, seed) -> {
                        if (entity == null || entity.getActiveItem() != stack) {
                            return 0.0F;
                        }

                        if (stack.getItem() instanceof ModBowItem customBow) {
                            // FIX 1: Call getMaxUseTime(stack) directly on the ItemStack object
                            int maxUseTime = stack.getMaxUseTime();

                            // FIX 2: Correct method call on LivingEntity to get remaining ticks
                            int timeLeft = entity.getItemUseTimeLeft();

                            int ticksHeld = maxUseTime - timeLeft;

                            // FIX 3: Safely pull your dynamic configuration limits
                            return (float) ticksHeld / (float) customBow.getMaxDrawTicks();
                        }
                        return 0.0F;
                    }
            );
        }
    }
}
