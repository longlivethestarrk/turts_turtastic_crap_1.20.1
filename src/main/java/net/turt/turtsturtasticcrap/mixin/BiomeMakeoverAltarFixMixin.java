package net.turt.turtsturtasticcrap.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "party.lemons.biomemakeover.item.Cursing")
public class BiomeMakeoverAltarFixMixin {

    @SuppressWarnings("UnresolvedMixinReference")
    @Redirect(
            method = "curseItemStack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/class_1887;method_8192(Lnet/minecraft/class_1799;)Z"
            ),
            remap = false
    )
    private static boolean safelyCheckCompatibility(Enchantment enchantment, ItemStack stack) {
        try {
            return enchantment.isAcceptableItem(stack);
        } catch (AbstractMethodError | Exception e) {
            System.out.println("[Turtastic Crap] Intercepted Altar checking error! Preventing server crash. You're Welcome.");
            return false;
        }
    }
}