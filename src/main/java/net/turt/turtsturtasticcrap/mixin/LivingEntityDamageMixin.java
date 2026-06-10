package net.turt.turtsturtasticcrap.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.turt.turtsturtasticcrap.util.IceAspectManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityDamageMixin {
    @Inject(method = "damage", at = @At("TAIL"))
    private void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        // Ensure the hit registered, was an actual attack, and happened on the server
        if (cir.getReturnValue() && source.getAttacker() instanceof LivingEntity attacker) {
            LivingEntity target = (LivingEntity) (Object) this;

            if (!target.getWorld().isClient()) {
                // Pass both entities to the manager
                IceAspectManager.onEntityHit(attacker, target);
            }
        }
    }
}
