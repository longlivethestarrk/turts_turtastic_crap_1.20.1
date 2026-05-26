package net.turt.turtsturtasticcrap.util;

import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.turt.turtsturtasticcrap.item.ModItems;

public class ModModelPredicates {
    public static void registerModModelPredicates() {
        registerCustomBow(ModItems.FLATBOW);
        registerCustomBow(ModItems.LONGBOW);
        registerCustomCrossbow(ModItems.PISTOLCROSSBOW);
        registerCustomCrossbow(ModItems.HEAVYCROSSBOW);
        registerCustomCrossbow(ModItems.ARBALEST);
    }

    private static void registerCustomBow(Item item) {
        ModelPredicateProviderRegistry.register(item, new Identifier("pull"), (stack, world, entity, seed) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                return entity.getActiveItem() != stack ? 0.0F : (stack.getMaxUseTime() - entity.getItemUseTimeLeft()) / 20.0F;
            }
        });
        ModelPredicateProviderRegistry.register(
                item,
                new Identifier("pulling"),
                (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F
        );
    }
    private static void registerCustomCrossbow(Item item) {
        ModelPredicateProviderRegistry.register(item, new Identifier("pull"), (stack, world, entity, seed) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                return CrossbowItem.isCharged(stack) ? 0.0F : (float)(stack.getMaxUseTime() - entity.getItemUseTimeLeft()) / CrossbowItem.getPullTime(stack);
            }
        });
        ModelPredicateProviderRegistry.register(
                item,
                new Identifier("pulling"),
                (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack && !CrossbowItem.isCharged(stack) ? 1.0F : 0.0F
        );
        ModelPredicateProviderRegistry.register(item, new Identifier("charged"), (stack, world, entity, seed) -> CrossbowItem.isCharged(stack) ? 1.0F : 0.0F);
        ModelPredicateProviderRegistry.register(
                item,
                new Identifier("firework"),
                (stack, world, entity, seed) -> CrossbowItem.isCharged(stack) && CrossbowItem.hasProjectile(stack, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F
        );
    }
}
