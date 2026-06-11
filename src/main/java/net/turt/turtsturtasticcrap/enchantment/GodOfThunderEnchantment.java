package net.turt.turtsturtasticcrap.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.SwordItem;

public class GodOfThunderEnchantment extends Enchantment {
    public GodOfThunderEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public int getMinPower(int level) {return 10;}

    @Override
    public boolean isTreasure() {return true;}

    @Override
    public int getMaxLevel() {return 1;}

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        boolean isMeleeOrTool = stack.getItem() instanceof SwordItem
                || stack.getItem() instanceof AxeItem
                || stack.getItem() instanceof MiningToolItem;

        boolean isRangedOrTrident = stack.isOf(net.minecraft.item.Items.TRIDENT)
                || stack.isOf(net.minecraft.item.Items.BOW)
                || stack.isOf(net.minecraft.item.Items.CROSSBOW);

        return isMeleeOrTool && !isRangedOrTrident;
    }
}
