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

    @Unique
    private boolean soulSeekerChecked = false;
    @Unique
    private boolean isSoulSeekerProjectile = false;

    @Inject(method = "tick", at = @At("HEAD"))
    private void applyHomingLogic(CallbackInfo ci) {
        ProjectileEntity projectile = (ProjectileEntity) (Object) this;

        if (projectile.getWorld().isClient() || !(projectile instanceof PersistentProjectileEntity arrow)) {
            return;
        }

        if (arrow.isOnGround()) {
            return;
        }

        if (!soulSeekerChecked) {
            Entity owner = arrow.getOwner();
            if (owner instanceof LivingEntity shooter) {
                ItemStack weapon = shooter.getActiveItem();

                if (weapon.isEmpty() || !weapon.isOf(Items.CROSSBOW)) {
                    weapon = shooter.getMainHandStack();
                }

                if (EnchantmentHelper.getLevel(ModEnchantments.SOUL_SEEKER, weapon) > 0) {
                    isSoulSeekerProjectile = true;
                }
            }
            soulSeekerChecked = true;
        }

        if (isSoulSeekerProjectile) {
            // Define bounding box for homing
            Box searchBox = arrow.getBoundingBox().expand(15.0);
            List<LivingEntity> nearbyTargets = arrow.getWorld().getEntitiesByClass(
                    LivingEntity.class,
                    searchBox,
                    target -> target.isAlive() && target != arrow.getOwner() && !target.isSpectator()
            );

            LivingEntity closestTarget = null;
            double closestDistance = Double.MAX_VALUE;

            for (LivingEntity potentialTarget : nearbyTargets) {
                double distance = arrow.squaredDistanceTo(potentialTarget);
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestTarget = potentialTarget;
                }
            }

            if (closestTarget != null) {
                Vec3d targetPos = new Vec3d(closestTarget.getX(), closestTarget.getBodyY(0.5), closestTarget.getZ());
                Vec3d arrowPos = arrow.getPos();

                Vec3d targetDirection = targetPos.subtract(arrowPos).normalize();
                Vec3d currentVelocity = arrow.getVelocity();
                double currentSpeed = currentVelocity.length();

                // Homing strength for stronger tracking and tighter turns
                double homingStrength = 0.3;
                Vec3d newVelocity = currentVelocity.normalize().lerp(targetDirection, homingStrength).normalize().multiply(currentSpeed);

                arrow.setVelocity(newVelocity);
            }
        }
    }
}
