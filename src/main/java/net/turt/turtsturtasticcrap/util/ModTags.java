package net.turt.turtsturtasticcrap.util;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.turt.turtsturtasticcrap.Turts_Turtastic_Crap;

public class ModTags {
    public static class Items {
        public static final TagKey<Item> DIAMOND_GEAR =
                createtag("diamond_gear");

        private static TagKey<Item> createtag(String name) {
            return TagKey.of(RegistryKeys.ITEM, new Identifier(Turts_Turtastic_Crap.MOD_ID, name));
        }
    }

}
