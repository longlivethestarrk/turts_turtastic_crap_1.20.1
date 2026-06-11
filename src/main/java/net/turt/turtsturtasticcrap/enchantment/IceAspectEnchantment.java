package net.turt.turtsturtasticcrap.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.turt.turtsturtasticcrap.util.ModEnchantments;
import net.turt.turtsturtasticcrap.util.ModTags;

public class IceAspectEnchantment extends Enchantment {
    public IceAspectEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public int getMinPower(int level) {
        return 10 + 20 * (level - 1);
    }

    @Override
    public int getMaxLevel() {return 2;}

    @Override
    public boolean canAccept(Enchantment other) {
        if (other != null) {
            var registryEntry = Registries.ENCHANTMENT.getEntry(other);

            if (registryEntry.isIn(ModTags.Items.ICE_ASPECT_INCOMPATIBLE)) {
                return false;
            }
        }
        return super.canAccept(other) && other != Enchantments.FIRE_ASPECT && other != ModEnchantments.POISON_ASPECT;
    }
}
