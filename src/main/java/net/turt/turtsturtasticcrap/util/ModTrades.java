package net.turt.turtsturtasticcrap.util;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;
import net.turt.turtsturtasticcrap.item.ModItems;

public class ModTrades {
    public static void registerModTrades() {
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FLETCHER, 2,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 2),
                            new ItemStack(ModItems.SHORTBOW, 1),
                            12,
                            5,
                            0.05f
                    ));
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 3),
                            new ItemStack(ModItems.RECURVEBOW, 1),
                            12,
                            5,
                            0.05f
                    ));
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 3),
                            new ItemStack(ModItems.FLATBOW, 1),
                            12,
                            5,
                            0.05f
                    ));
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 3),
                            new ItemStack(ModItems.LONGBOW, 1),
                            12,
                            5,
                            0.05f
                    ));
                });
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FLETCHER, 3,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.FLAXSTRING, 7),
                            new ItemStack(Items.EMERALD, 1),
                            16,
                            20,
                            0.05f
                    ));
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 3),
                            new ItemStack(ModItems.PISTOLCROSSBOW, 1),
                            12,
                            10,
                            0.05f
                    ));
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 4),
                            new ItemStack(ModItems.HEAVYCROSSBOW, 1),
                            12,
                            10,
                            0.05f
                    ));
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 4),
                            new ItemStack(ModItems.ARBALEST, 1),
                            12,
                            10,
                            0.05f
                    ));
                });
    }
}
