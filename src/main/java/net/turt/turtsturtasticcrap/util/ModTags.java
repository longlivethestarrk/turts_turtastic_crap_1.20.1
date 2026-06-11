package net.turt.turtsturtasticcrap.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
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

        public static final TagKey<Enchantment> ICE_ASPECT_INCOMPATIBLE = TagKey.of(
                RegistryKeys.ENCHANTMENT,
                new Identifier("turts_turtastic_crap", "ice_aspect_incompatible")
        );

        public static final TagKey<Enchantment> POISON_ASPECT_INCOMPATIBLE = TagKey.of(
                RegistryKeys.ENCHANTMENT,
                new Identifier("turts_turtastic_crap", "poison_aspect_incompatible")
        );
    }

    public static class Entities {
        public static final TagKey<EntityType<?>> ICE_ASPECT_DAMAGE_IMMUNE =
                TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier("turts_turtastic_crap", "ice_aspect_damage_immune")
        );
        public static final TagKey<EntityType<?>> ICE_ASPECT_DAMAGE_VULNERABLE =
                TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier("turts_turtastic_crap", "ice_aspect_damage_vulnerable")
                );
    }
}
