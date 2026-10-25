package net.turt.turtsturtasticcrap.item;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import net.turt.turtsturtasticcrap.Turts_Turtastic_Crap;

public class ModBowItem extends BowItem {
    private final int maxDrawTicks;
    private final float velocityMultiplier;
    private final double damageMultiplier;

    public ModBowItem(Settings settings, int maxDrawTicks, float velocityMultiplier, double damageMultiplier) {
        super(settings);
        this.maxDrawTicks = maxDrawTicks;
        this.velocityMultiplier = velocityMultiplier;
        this.damageMultiplier = damageMultiplier;
    }

    // FIX: Changed from onPlayerStoppedUsing to onStoppedUsing for Fabric 1.20.1 Yarn mappings
    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof PlayerEntity playerEntity)) return;

        boolean hasAmmo = playerEntity.getAbilities().creativeMode || EnchantmentHelper.getLevel(Enchantments.INFINITY, stack) > 0;
        ItemStack ammoStack = playerEntity.getProjectileType(stack);

        if (!ammoStack.isEmpty() || hasAmmo) {
            if (ammoStack.isEmpty()) {
                ammoStack = new ItemStack(Items.ARROW);
            }

            int useTicks = this.getMaxUseTime(stack) - remainingUseTicks;
            float pullProgress = getCustomPullProgress(useTicks);

            if (!((double)pullProgress < 0.1)) {
                boolean isCreativeOrInfinity = playerEntity.getAbilities().creativeMode || (ammoStack.isOf(Items.ARROW) && EnchantmentHelper.getLevel(Enchantments.INFINITY, stack) > 0);

                if (!world.isClient) {
                    ArrowItem arrowItem = (ArrowItem)(ammoStack.getItem() instanceof ArrowItem ? ammoStack.getItem() : Items.ARROW);
                    PersistentProjectileEntity projectileEntity = arrowItem.createArrow(world, ammoStack, playerEntity);

                    // Apply your custom velocity multiplier
                    float finalVelocity = pullProgress * 3.0F * this.velocityMultiplier;
                    projectileEntity.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, finalVelocity, 1.0F);

                    // Apply your custom damage multiplier
                    projectileEntity.setDamage(projectileEntity.getDamage() * this.damageMultiplier);

                    if (pullProgress == 1.0F) {
                        projectileEntity.setCritical(true);
                    }
                    int powerLevel = EnchantmentHelper.getLevel(Enchantments.POWER, stack);
                    if (powerLevel > 0) {
                        projectileEntity.setDamage(projectileEntity.getDamage() + (double)powerLevel * 0.5 + 0.5);
                    }
                    int punchLevel = EnchantmentHelper.getLevel(Enchantments.PUNCH, stack);
                    if (punchLevel > 0) {
                        projectileEntity.setPunch(punchLevel);
                    }
                    if (EnchantmentHelper.getLevel(Enchantments.FLAME, stack) > 0) {
                        projectileEntity.setOnFireFor(100);
                    }

                    stack.damage(1, playerEntity, (p) -> p.sendToolBreakStatus(user.getActiveHand()));

                    if (isCreativeOrInfinity || playerEntity.getAbilities().creativeMode && (ammoStack.isOf(Items.SPECTRAL_ARROW) || ammoStack.isOf(Items.TIPPED_ARROW))) {
                        projectileEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
                    }

                    world.spawnEntity(projectileEntity);
                }

                world.playSound((PlayerEntity)null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + pullProgress * 0.5F);

                if (!isCreativeOrInfinity && !playerEntity.getAbilities().creativeMode) {
                    ammoStack.decrement(1);
                    if (ammoStack.isEmpty()) {
                        playerEntity.getInventory().removeOne(ammoStack);
                    }
                }

                playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
            }
        }
    }

    public float getCustomPullProgress(int useTicks) {
        float f = (float)useTicks / (float)this.maxDrawTicks;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }
        return f;
    }

    public int getMaxDrawTicks() { return this.maxDrawTicks; }
    public float getVelocityMultiplier() { return this.velocityMultiplier; }
    public double getDamageMultiplier() { return this.damageMultiplier; }

    @Override
    public int getMaxUseTime(ItemStack stack) { return 72000; }

    public static void registerModBow() {
        Turts_Turtastic_Crap.LOGGER.info("Registering Mod Items for " + Turts_Turtastic_Crap.MOD_ID);
    }
}
