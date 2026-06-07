package net.turt.turtsturtasticcrap.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.turt.turtsturtasticcrap.item.ModCrossbow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CrossbowItem.class)
public class ProjectileEntityMixin {

    /**
     * TARGETS THE STATIC CROSSBOW SHOOT METHOD AT THE TAIL END
     * Since vanilla passes the 'ItemStack crossbow' reference directly into this method,
     * we can reliably extract your custom velocity and damage configurations regardless
     * of whether the player's active item has already been cleared.
     */
    @Inject(
            method = "shoot",
            at = @At("TAIL")
    )
    private static void modifyCrossbowProjectileVelocity(World world, LivingEntity shooter, Hand hand, ItemStack crossbow, ItemStack projectile, float soundPitch, boolean creative, float speed, float divergence, float simulated, CallbackInfo ci) {
        // Ensure this logic applies only to your custom weapon profile on the server side
        if (crossbow.getItem() instanceof ModCrossbow customCrossbow && !world.isClient) {

            // Search a tiny boundary around the shooter to capture the newly spawned arrow entity instance
            java.util.List<PersistentProjectileEntity> arrows = world.getEntitiesByClass(
                    PersistentProjectileEntity.class,
                    shooter.getBoundingBox().expand(2.0),
                    entity -> entity.getOwner() == shooter
            );

            if (!arrows.isEmpty()) {
                // Snatch the projectile that was just fired from the list
                PersistentProjectileEntity arrow = arrows.get(arrows.size() - 1);

                // Dynamically extract the multipliers from this specific item type's configuration registry
                float velocityMultiplier = customCrossbow.getVelocityMultiplier();
                double damageMultiplier = customCrossbow.getDamageMultiplier();

                // Multiply the arrow's physical velocity vector cleanly
                Vec3d currentVelocity = arrow.getVelocity();
                arrow.setVelocity(currentVelocity.multiply(velocityMultiplier));

                // Scale up baseline weapon damage matching your unique preferences
                arrow.setDamage(arrow.getDamage() * damageMultiplier);

                // Force sync changes across server/client boundaries so the arrow travels smoothly on screen
                arrow.velocityModified = true;
            }
        }
    }
}
