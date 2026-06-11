package net.turt.turtsturtasticcrap.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;
import net.turt.turtsturtasticcrap.util.ModEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class PlayerGodOfThunderMixin {
    @Inject(method = "damage", at = @At("TAIL"))
    private void onLightningDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue() && source.getAttacker() instanceof LivingEntity attacker) {
            LivingEntity target = (LivingEntity) (Object) this;

            if (!target.getWorld().isClient()) {
                int lightningLevel = EnchantmentHelper.getLevel(ModEnchantments.GOD_OF_THUNDER, attacker.getMainHandStack());
                if (lightningLevel > 0) {
                    ServerWorld world = (ServerWorld) target.getWorld();

                    // Standard clear/rainy weather = 5% chance (0.05F)
                    // Active Thunderstorms = 50% chance (0.50F)
                    float strikeChance = world.isThundering() ? 0.50F : 0.05F;

                    if (attacker.getRandom().nextFloat() < strikeChance) {
                        LightningEntity lightningBolt = EntityType.LIGHTNING_BOLT.create(world);
                        if (lightningBolt != null) {
                            lightningBolt.refreshPositionAfterTeleport(target.getX(), target.getY(), target.getZ());
                            world.spawnEntity(lightningBolt);
                        }
                    }
                }
            }
        }
    }
}
