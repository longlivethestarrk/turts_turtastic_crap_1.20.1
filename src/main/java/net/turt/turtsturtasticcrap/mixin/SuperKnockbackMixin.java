package net.turt.turtsturtasticcrap.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class SuperKnockbackMixin {

    @Unique private static final float KNOCKBACK_CHANCE = 0.3f;
    @Unique private static final float KNOCKBACK_STRENGTH = 20.0f;
    @Unique private static final float LAUNCH_SPEED_BOOST = 2.5f;

    @Unique
    private static final java.util.Set<String> SUPER_KNOCKBACK_WEAPONS = java.util.Set.of(
            "farmersdelight:skillet"
    );

    @Inject(method = "attack", at = @At("HEAD"))
    private void injectPanEffect(Entity target, CallbackInfo ci) {
        PlayerEntity attacker = (PlayerEntity) (Object) this;

        if (!attacker.getWorld().isClient() && target instanceof LivingEntity livingTarget) {
            ItemStack weapon = attacker.getMainHandStack();
            String itemId = Registries.ITEM.getId(weapon.getItem()).toString();

            if (SUPER_KNOCKBACK_WEAPONS.contains(itemId)) {

                if (attacker.getAttackCooldownProgress(0.5F) >= 1.0F) {

                    if (attacker.getRandom().nextFloat() < KNOCKBACK_CHANCE) {

                        double xRatio = MathHelper.sin(attacker.getYaw() * ((float)Math.PI / 180F));
                        double zRatio = -MathHelper.cos(attacker.getYaw() * ((float)Math.PI / 180F));

                        livingTarget.takeKnockback(KNOCKBACK_STRENGTH * 0.5F, xRatio, zRatio);

                        livingTarget.setVelocity(
                                livingTarget.getVelocity().x * LAUNCH_SPEED_BOOST,
                                livingTarget.getVelocity().y * 1.2F,
                                livingTarget.getVelocity().z * LAUNCH_SPEED_BOOST
                        );

                        livingTarget.velocityModified = true;

                        attacker.getWorld().playSound(
                                null,
                                attacker.getX(), attacker.getY(), attacker.getZ(),
                                SoundEvents.BLOCK_ANVIL_PLACE,
                                SoundCategory.PLAYERS,
                                1.0F, 1.0F
                        );
                    }
                }
            }
        }
    }
}