package net.turt.turtsturtasticcrap.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(Enchantment.class)
public class EnchantmentCompatibilityMixin {

    // Global list of all conflicting Aspect enchantments
    private static final Set<String> MUTUALLY_EXCLUSIVE_ASPECTS = Set.of(
            "minecraft:fire_aspect",            // Vanilla Fire Aspect
            "minecraft:soul_fire_aspect",      // Soul Fire Aspect
            "supplementaries:lumisene_aspect",       // Lumisene Aspect
            "turts_turtastic_crap:poison_aspect",         // Poison Aspect
            "turts_turtastic_crap:ice_aspect"              // Your Ice Aspect
    );

    @Inject(method = "canAccept", at = @At("HEAD"), cancellable = true)
    private void interceptCompatibility(Enchantment other, CallbackInfoReturnable<Boolean> cir) {
        if (other == null) return;

        Enchantment self = (Enchantment) (Object) this;

        Identifier selfId = Registries.ENCHANTMENT.getId(self);
        Identifier otherId = Registries.ENCHANTMENT.getId(other);

        if (selfId != null && otherId != null) {
            String selfString = selfId.toString();
            String otherString = otherId.toString();

            // If BOTH the evaluated enchantment and target enchantment are in the set, deny pairing
            if (MUTUALLY_EXCLUSIVE_ASPECTS.contains(selfString) && MUTUALLY_EXCLUSIVE_ASPECTS.contains(otherString)) {
                cir.setReturnValue(false); // Overrides and returns false globally
            }
        }
    }
}
