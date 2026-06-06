package net.turt.turtsturtasticcrap.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.turt.turtsturtasticcrap.item.ModBowItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BowItem.class)
public class ModBowItemMixin {

    @Redirect(
            method = "onStoppedUsing",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/BowItem;getPullProgress(I)F")
    )
    private float redirectPullProgress(int useTicks, ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (stack.getItem() instanceof ModBowItem customBow) {
            int ticksHeld = customBow.getMaxUseTime(stack) - remainingUseTicks;

            // Dynamically grab this specific bow's draw duration threshold
            float progress = (float) ticksHeld / (float) customBow.getMaxDrawTicks();
            progress = (progress * progress + progress * 2.0F) / 3.0F;
            return progress > 1.0F ? 1.0F : progress;
        }
        return BowItem.getPullProgress(useTicks);
    }

    @Inject(method = "onStoppedUsing", at = @At("TAIL"))
    private void overrideArrowPhysicsAtTail(ItemStack stack, World world, LivingEntity user, int remainingUseTicks, CallbackInfo ci) {
        if (stack.getItem() instanceof ModBowItem customBow && !world.isClient) {

            int ticksHeld = customBow.getMaxUseTime(stack) - remainingUseTicks;
            float progress = (float) ticksHeld / (float) customBow.getMaxDrawTicks();
            progress = (progress * progress + progress * 2.0F) / 3.0F;
            if (progress > 1.0F) progress = 1.0F;

            java.util.List<PersistentProjectileEntity> arrows = world.getEntitiesByClass(
                    PersistentProjectileEntity.class,
                    user.getBoundingBox().expand(2.0),
                    projectile -> projectile.getOwner() == user
            );

            if (!arrows.isEmpty()) {
                PersistentProjectileEntity arrow = arrows.get(arrows.size() - 1);

                // Dynamically read the bow's custom multipliers
                float speedMultiplier = customBow.getVelocityMultiplier();
                double damageMultiplier = customBow.getDamageMultiplier();

                // Apply velocity scaling based on this specific bow's profile
                Vec3d currentVelocity = arrow.getVelocity();
                arrow.setVelocity(currentVelocity.multiply(speedMultiplier * progress));

                if (progress >= 1.0F) {
                    arrow.setCritical(true);
                    arrow.setDamage(arrow.getDamage() * damageMultiplier);
                }

                arrow.velocityModified = true;
            }
        }
    }
}
