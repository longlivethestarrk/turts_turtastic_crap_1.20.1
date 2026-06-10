package net.turt.turtsturtasticcrap.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.turt.turtsturtasticcrap.enchantment.IceAspectEnchantment;

public class ModEnchantments {

    //for registering enchantments
    public static final Enchantment ICE_ASPECT = register(
            "ice_aspect",
            new IceAspectEnchantment()
    );

    private static Enchantment register(String name, Enchantment enchantment) {
        return Registry.register(
                Registries.ENCHANTMENT,
                new Identifier("turts_turtastic_crap", name),
                enchantment
        );
    }

    //for registering death messages
    public static final RegistryKey<DamageType> ICE_FREEZE_TYPE = RegistryKey.of(
            RegistryKeys.DAMAGE_TYPE,
            new Identifier("turts_turtastic_crap", "ice_freeze")
    );

    public static void registerModEnchantments() {
    }
}
