package net.turt.turtsturtasticcrap.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.turt.turtsturtasticcrap.enchantment.*;

public class ModEnchantments {

    //for registering enchantments
    public static final Enchantment GOD_OF_THE_SEA = register(
            "god_of_the_sea",
            new GodOfTheSeaEnchantment()
    );

    public static final Enchantment GOD_OF_THUNDER = register(
            "god_of_thunder",
            new GodOfThunderEnchantment()
    );

    public static final Enchantment ICE_ASPECT = register(
            "ice_aspect",
            new IceAspectEnchantment()
    );

    public static final Enchantment POISON_ASPECT = register(
            "poison_aspect",
            new PoisonAspectEnchantment()
    );


    public static final Enchantment SOUL_SEEKER = register(
            "soul_seeker",
            new SoulSeekerEnchantment()
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
