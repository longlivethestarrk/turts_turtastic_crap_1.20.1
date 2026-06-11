package net.turt.turtsturtasticcrap.mixin;

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
        if (cir.getReturnValue() && source.getAttacker() instanceof LivingEntity attacker) {
            LivingEntity target = (LivingEntity) (Object) this;

            if (!target.getWorld().isClient()) {
                IceAspectManager.onEntityHit(attacker, target);
            }
        }
    }
}
