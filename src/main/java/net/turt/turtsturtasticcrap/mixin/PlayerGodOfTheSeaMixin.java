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

        // Run this logic check entirely on the server thread side
        if (!player.getWorld().isClient()) {
            // Check the main hand item stack
            int mainHandLevel = EnchantmentHelper.getLevel(ModEnchantments.GOD_OF_THE_SEA, player.getMainHandStack());
            // Check the off hand item stack
            int offHandLevel = EnchantmentHelper.getLevel(ModEnchantments.GOD_OF_THE_SEA, player.getOffHandStack());

            // If either hand holds a trident carrying the Gills enchantment
            if (mainHandLevel > 0 || offHandLevel > 0) {
                // Instantly refill their oxygen bubble meter to maximum capacity every frame
                player.setAir(player.getMaxAir());
            }
        }
    }
}
