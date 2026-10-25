package net.turt.turtsturtasticcrap.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.turt.turtsturtasticcrap.util.ModEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class PlayerPoisonAspectMixin {

    @Inject(method = "damage", at = @At("TAIL"))
    private void onPoisonDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) {
            LivingEntity target = (LivingEntity) (Object) this;

            if (!target.getWorld().isClient()) {
                if (source.getAttacker() instanceof LivingEntity attacker) {
                    int poisonLevel = EnchantmentHelper.getLevel(ModEnchantments.POISON_ASPECT, attacker.getMainHandStack());
                    if (poisonLevel > 0) {
                        int duration = poisonLevel * 110;
                        int amplifier = 0;

                        target.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, duration, amplifier));
                    }
                }
            }
        }
    }
}
