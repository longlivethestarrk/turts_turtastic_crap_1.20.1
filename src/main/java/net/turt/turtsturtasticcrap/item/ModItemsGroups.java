package net.turt.turtsturtasticcrap.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.turt.turtsturtasticcrap.Turts_Turtastic_Crap;

import static net.turt.turtsturtasticcrap.item.ModItems.*;

public class ModItemsGroups {
    public static final ItemGroup TURT_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(Turts_Turtastic_Crap.MOD_ID, "turt"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.diamond_nugget"))
                    .icon(() -> new ItemStack(ModItems.DIAMONDNUGGET)).entries((displayContext, entries) -> {
                        entries.add(FLAXSTRING);
                        entries.add(NETHERITENUGGET);
                        entries.add(DIAMONDNUGGET);
                        entries.add(COPPERNUGGET);

                        entries.add(SHORTBOW);
                        entries.add(RECURVEBOW);
                        entries.add(FLATBOW);
                        entries.add(LONGBOW);
                        entries.add(PISTOLCROSSBOW);
                        entries.add(HEAVYCROSSBOW);
                        entries.add(ARBALEST);

                        entries.add(COPPER_LONGSWORD);
                        entries.add(COPPER_TWINBLADE);
                        entries.add(COPPER_RAPIER);
                        entries.add(COPPER_KATANA);
                        entries.add(COPPER_SAI);
                        entries.add(COPPER_SPEAR);
                        entries.add(COPPER_GLAIVE);
                        entries.add(COPPER_WARGLAIVE);
                        entries.add(COPPER_CUTLASS);
                        entries.add(COPPER_CLAYMORE);
                        entries.add(COPPER_GREATAXE);
                        entries.add(COPPER_GREATHAMMER);
                        entries.add(COPPER_CHAKRAM);
                        entries.add(COPPER_SCYTHE);
                        entries.add(COPPER_HALBERD);

                        entries.add(COPPER_GREAT_KATANA);
                        entries.add(COPPER_GRANDSWORD);
                        entries.add(COPPER_BACKHAND_BLADE);
                        entries.add(COPPER_LANCE);
                        entries.add(COPPER_KHOPESH);
                        entries.add(COPPER_DAGGER);
                        entries.add(COPPER_PERNACH);
                        entries.add(COPPER_QUARTERSTAFF);
                        entries.add(COPPER_GREAT_SPEAR);
                        entries.add(COPPER_DEER_HORNS);
                    }).build());

    public static void registerItemGroups() {
        Turts_Turtastic_Crap.LOGGER.info("Registering Item Groups for " + Turts_Turtastic_Crap.MOD_ID);
    }
}
