package net.turt.turtsturtasticcrap.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.turt.turtsturtasticcrap.util.IceAspectManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class PlayerIceAspectMixin {

    @Inject(method = "damage", at = @At("TAIL"))
    private void onIceDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        // Confirm the living entity actually sustained damage successfully
        if (cir.getReturnValue()) {
            LivingEntity target = (LivingEntity) (Object) this;

            // Server Guard: Ensure the application occurs strictly on the server logic loop
            if (!target.getWorld().isClient()) {
                if (source.getAttacker() instanceof LivingEntity attacker) {
                    // Triggers your UUID-optimized manager
                    IceAspectManager.onEntityHit(attacker, target);
                }
            }
        }
    }
}
