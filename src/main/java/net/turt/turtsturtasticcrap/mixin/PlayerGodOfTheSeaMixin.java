package net.turt.turtsturtasticcrap.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.turt.turtsturtasticcrap.util.ModEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerGodOfTheSeaMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    private void applyGillsEffect(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        // 1. Core Server Guard: Ensure logic processes strictly on the server thread loop
        if (!player.getWorld().isClient()) {

            // Check the main hand and off hand item stacks for the custom enchantment
            int mainHandLevel = EnchantmentHelper.getLevel(ModEnchantments.GOD_OF_THE_SEA, player.getMainHandStack());
            int offHandLevel = EnchantmentHelper.getLevel(ModEnchantments.GOD_OF_THE_SEA, player.getOffHandStack());

            if (mainHandLevel > 0 || offHandLevel > 0) {

                // FIX: Only modify the player's air status if it has actually decreased.
                // This prevents the server from spamming data sync packets to the client every single frame.
                if (player.getAir() < player.getMaxAir()) {
                    player.setAir(player.getMaxAir());
                }
            }
        }
    }
}
