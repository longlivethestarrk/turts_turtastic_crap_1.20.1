package net.turt.turtsturtasticcrap;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.turt.turtsturtasticcrap.item.ModBowItem;
import net.turt.turtsturtasticcrap.item.ModItems;

public class Turts_Turtastic_Crap_Client implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
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
