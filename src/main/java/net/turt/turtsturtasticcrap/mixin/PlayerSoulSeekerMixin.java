package net.turt.turtsturtasticcrap.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.turt.turtsturtasticcrap.util.ModEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ProjectileEntity.class)
public abstract class PlayerSoulSeekerMixin {
    @Unique private boolean soulSeekerChecked = false;
    @Unique private boolean isSoulSeekerProjectile = false;
    @Unique private int scanCooldown = 0; // Performance throttle tracker
    @Unique private LivingEntity activeLockedTarget = null; // Caches target to save performance

    @Inject(method = "tick", at = @At("HEAD"))
    private void applyHomingLogic(CallbackInfo ci) {
        ProjectileEntity projectile = (ProjectileEntity) (Object) this;

        // Server-only guard and filter for arrows
        if (projectile.getWorld().isClient() || !(projectile instanceof PersistentProjectileEntity arrow)) {
            return;
        }

        if (arrow.isOnGround()) {
            return;
        }

        // 1. First-Tick Initialization Cache Fix
        if (!soulSeekerChecked) {
            Entity owner = arrow.getOwner();
            if (owner instanceof LivingEntity shooter) {
                // Look for the weapon across BOTH hands since players can fire from main or off-hand
                ItemStack mainHand = shooter.getMainHandStack();
                ItemStack offHand = shooter.getOffHandStack();

                boolean hasEnchant = EnchantmentHelper.getLevel(ModEnchantments.SOUL_SEEKER, mainHand) > 0 ||
                        EnchantmentHelper.getLevel(ModEnchantments.SOUL_SEEKER, offHand) > 0;

                if (hasEnchant) {
                    isSoulSeekerProjectile = true;
                }
            }
            soulSeekerChecked = true;
        }

        // 2. Optimized Homing Logic Loop
        if (isSoulSeekerProjectile) {
            scanCooldown--;

            // Performance Throttle: Only scan for entities if we don't have a valid target, or every 5 ticks
            if (scanCooldown <= 0 || activeLockedTarget == null || !activeLockedTarget.isAlive() || activeLockedTarget.isSpectator()) {
                scanCooldown = 5; // Reset throttle cooldown (scans 4 times per second)

                Box searchBox = arrow.getBoundingBox().expand(15.0);
                List<LivingEntity> nearbyTargets = arrow.getWorld().getEntitiesByClass(
                        LivingEntity.class, searchBox, target -> target.isAlive() && target != arrow.getOwner() && !target.isSpectator()
                );

                LivingEntity closestTarget = null;
                double closestDistance = Double.MAX_VALUE;

                for (LivingEntity potentialTarget : nearbyTargets) {
                    // Performance optimization: Using squared distance avoids slow square root calculations
                    double distance = arrow.squaredDistanceTo(potentialTarget);
                    if (distance < closestDistance) {
                        closestDistance = distance;
                        closestTarget = potentialTarget;
                    }
                }
                activeLockedTarget = closestTarget; // Lock on to the target
            }

            // 3. Smooth Vector Adjustments (Runs every tick for visual fluidity)
            if (activeLockedTarget != null) {
                Vec3d targetPos = new Vec3d(activeLockedTarget.getX(), activeLockedTarget.getBodyY(0.5), activeLockedTarget.getZ());
                Vec3d arrowPos = arrow.getPos();
                Vec3d targetDirection = targetPos.subtract(arrowPos).normalize();

                Vec3d currentVelocity = arrow.getVelocity();
                double currentSpeed = currentVelocity.length();

                // 0.25f - 0.35f provides standard tracking strength; adjustments update smoothly on the server thread
                double homingStrength = 0.3;
                Vec3d newVelocity = currentVelocity.normalize().lerp(targetDirection, homingStrength).normalize().multiply(currentSpeed);

                arrow.setVelocity(newVelocity);
            }
        }
    }
}
