package net.turt.turtsturtasticcrap.item;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.turt.turtsturtasticcrap.Turts_Turtastic_Crap;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class ModCrossbowItem extends CrossbowItem {
    private final int maxDrawTicks;
    private final float velocityMultiplier;
    private final double damageMultiplier;
    private boolean playedMidClick = false;

    public ModCrossbowItem(Settings settings, int maxDrawTicks, float velocityMultiplier, double damageMultiplier) {
        super(settings);
        this.maxDrawTicks = maxDrawTicks;
        this.velocityMultiplier = velocityMultiplier;
        this.damageMultiplier = damageMultiplier;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        // If the crossbow is charged, handle custom server-side shooting mechanics
        if (isCharged(itemStack)) {
            shootCustomProjectiles(world, user, hand, itemStack);
            setCharged(itemStack, false);
            return TypedActionResult.consume(itemStack);
        }

        // Otherwise, fall back to vanilla charging mechanics
        return super.use(world, user, hand);
    }

    /**
     * CUSTOM REPLICATION OF VANILLA SHOOTING
     * Unpacks stored projectiles from NBT and spawns them with custom velocity and damage modifiers.
     */
    private void shootCustomProjectiles(World world, LivingEntity shooter, Hand hand, ItemStack crossbow) {
        // Read charged ammo from NBT list
        NbtCompound nbtCompound = crossbow.getOrCreateNbt();
        if (!nbtCompound.contains("ChargedProjectiles", 9)) return;
        NbtList nbtList = nbtCompound.getList("ChargedProjectiles", 10);

        List<ItemStack> projectiles = new ArrayList<>();
        for (int i = 0; i < nbtList.size(); ++i) {
            projectiles.add(ItemStack.fromNbt(nbtList.getCompound(i)));
        }

        // Multishot enchantment calculations
        float[] soundPitches = projectiles.size() == 3 ? new float[]{1.0F, 1.22F, 0.8F} : new float[]{1.0F};

        for (int i = 0; i < projectiles.size(); ++i) {
            ItemStack ammoStack = projectiles.get(i);
            if (ammoStack.isEmpty()) continue;

            if (!world.isClient) {
                ProjectileEntity projectileEntity;

                // Handle standard arrows or firework ammo variants
                if (ammoStack.isOf(Items.FIREWORK_ROCKET)) {
                    projectileEntity = new FireworkRocketEntity(world, ammoStack, shooter, shooter.getX(), shooter.getEyeY() - 0.15000000596046448D, shooter.getZ(), true);
                } else {
                    ArrowItem arrowItem = (ArrowItem)(ammoStack.getItem() instanceof ArrowItem ? ammoStack.getItem() : Items.ARROW);
                    PersistentProjectileEntity arrow = arrowItem.createArrow(world, ammoStack, shooter);

                    // Apply vanilla enchantments
                    if (shooter instanceof PlayerEntity) {
                        arrow.setShotFromCrossbow(true); // <--- FIXED FOR YARN 1.20.1
                    }
                    int piercingLevel = EnchantmentHelper.getLevel(Enchantments.PIERCING, crossbow);
                    if (piercingLevel > 0) {
                        arrow.setPierceLevel((byte)piercingLevel);
                    }

                    // Apply your custom damage multiplier directly to the server side entity
                    arrow.setDamage(arrow.getDamage() * this.damageMultiplier);
                    projectileEntity = arrow;
                }

                // Handle Multishot spread angles (0 degrees for center, -10 and +10 for side arrows)
                float angleDegrees = i == 1 ? -10.0F : (i == 2 ? 10.0F : 0.0F);

                // Calculate custom velocity vector (Vanilla crossbow base is 3.15F)
                float baseCrossbowVelocity = 3.15F;
                float finalVelocity = baseCrossbowVelocity * this.velocityMultiplier;

                // Core velocity rotation application
                Vec3d lookDir = shooter.getOppositeRotationVector(1.0F);
                Quaternionf quaternionf = (new Quaternionf()).setAngleAxis((double)(angleDegrees * 0.017453292F), lookDir.x, lookDir.y, lookDir.z);
                Vec3d vec3d2 = shooter.getRotationVec(1.0F);
                Vector3f vector3f = vec3d2.toVector3f().rotate(quaternionf);

                // Set the modified velocity explicitly on the server entity configuration
                projectileEntity.setVelocity((double)vector3f.x(), (double)vector3f.y(), (double)vector3f.z(), finalVelocity, 1.0F);

                // Spawn projectile safely on the server loop thread
                world.spawnEntity(projectileEntity);
            }

            // Sound execution replication across both client/server boundary channels
            float pitch = soundPitches[i];
            world.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.ITEM_CROSSBOW_SHOOT, SoundCategory.PLAYERS, 1.0F, pitch);
        }

        // Tool damage and stat triggers
        crossbow.damage(1, shooter, (e) -> e.sendToolBreakStatus(hand));
        if (shooter instanceof PlayerEntity player) {
            player.incrementStat(Stats.USED.getOrCreateStat(this));
        }

        // Wipe NBT project storage list clean
        nbtCompound.remove("ChargedProjectiles");
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (!world.isClient) {
            int ticksHeld = this.getMaxUseTime(stack) - remainingUseTicks;
            int quickChargeLevel = EnchantmentHelper.getLevel(Enchantments.QUICK_CHARGE, stack);
            int targetTicks = this.maxDrawTicks - (quickChargeLevel * 5);
            if (targetTicks < 1) targetTicks = 1;
            if (ticksHeld == 1) {
                playedMidClick = false;
            }
            if (ticksHeld >= targetTicks / 2 && !playedMidClick) {
                world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_CROSSBOW_LOADING_MIDDLE, SoundCategory.PLAYERS, 0.5F, 1.0F);
                playedMidClick = true;
            }
        }
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        int ticksHeld = this.getMaxUseTime(stack) - remainingUseTicks;
        int quickChargeLevel = EnchantmentHelper.getLevel(Enchantments.QUICK_CHARGE, stack);
        int targetTicks = this.maxDrawTicks - (quickChargeLevel * 5);
        if (targetTicks < 1) targetTicks = 1;
        if (ticksHeld >= targetTicks) {
            boolean loadSuccess = loadProjectilesPublicly(user, stack);
            if (loadSuccess) {
                world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_CROSSBOW_LOADING_END, SoundCategory.PLAYERS, 1.0F, 1.0F / (world.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F);
                setCharged(stack, true);
            }
        }
    }

    private boolean loadProjectilesPublicly(LivingEntity user, ItemStack crossbow) {
        boolean isCreative = user instanceof PlayerEntity player && player.getAbilities().creativeMode;
        ItemStack ammoStack = user.getProjectileType(crossbow);
        if (ammoStack.isEmpty() && isCreative) {
            ammoStack = new ItemStack(Items.ARROW);
        } else if (ammoStack.isEmpty()) {
            return false;
        }
        int multishotLevel = EnchantmentHelper.getLevel(Enchantments.MULTISHOT, crossbow);
        int projectilesToLoad = multishotLevel > 0 ? 3 : 1;
        NbtList chargedProjectilesList = new NbtList();
        ItemStack ammoToLoad = ammoStack.copy();
        ammoToLoad.setCount(1);
        for (int i = 0; i < projectilesToLoad; i++) {
            NbtCompound ammoNbt = new NbtCompound();
            ammoToLoad.writeNbt(ammoNbt);
            chargedProjectilesList.add(ammoNbt);
        }
        NbtCompound weaponNbt = crossbow.getOrCreateNbt();
        weaponNbt.put("ChargedProjectiles", chargedProjectilesList);
        if (!isCreative) {
            ammoStack.decrement(1);
            if (ammoStack.isEmpty() && user instanceof PlayerEntity player) {
                player.getInventory().removeOne(ammoStack);
            }
        }
        return true;
    }

    public int getMaxDrawTicks() { return this.maxDrawTicks; }
    public float getVelocityMultiplier() { return this.velocityMultiplier; }
    public double getDamageMultiplier() { return this.damageMultiplier; }

    @Override
    public int getMaxUseTime(ItemStack stack) { return 72000; }

    public static void registerModCrossbow() {
        Turts_Turtastic_Crap.LOGGER.info("Registering Mod Items for " + Turts_Turtastic_Crap.MOD_ID);
    }
}