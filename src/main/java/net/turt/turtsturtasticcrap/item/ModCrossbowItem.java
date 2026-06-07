package net.turt.turtsturtasticcrap.item;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import net.turt.turtsturtasticcrap.Turts_Turtastic_Crap;

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

    public int getMaxDrawTicks() { return this.maxDrawTicks; }
    public float getVelocityMultiplier() { return this.velocityMultiplier; }
    public double getDamageMultiplier() { return this.damageMultiplier; }

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
                world.playSound(null, user.getX(), user.getY(), user.getZ(),
                        SoundEvents.ITEM_CROSSBOW_LOADING_MIDDLE, SoundCategory.PLAYERS, 0.5F, 1.0F);
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
            // Replicate vanilla's loadProjectiles functionality using public operations
            boolean loadSuccess = loadProjectilesPublicly(user, stack);

            if (loadSuccess) {
                world.playSound(null, user.getX(), user.getY(), user.getZ(),
                        SoundEvents.ITEM_CROSSBOW_LOADING_END, SoundCategory.PLAYERS, 1.0F, 1.0F / (world.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F);

                stack.getOrCreateNbt().putBoolean("Charged", true);
            }
        }
    }

    /**
     * PUBLIC REPLICATION OF VANILLA PROIECTILE LOADING
     * Searches player inventories, deducts the correct ammunition count,
     * and compiles the internal NBT inventory storage compound cleanly.
     */
    private boolean loadProjectilesPublicly(LivingEntity user, ItemStack crossbow) {
        // Creative mode players get infinite ammunition properties automatically
        boolean isCreative = user instanceof PlayerEntity player && player.getAbilities().creativeMode;

        // Use public player inventory functions to seek valid ammunition matching the weapon profile
        ItemStack ammoStack = user.getProjectileType(crossbow);

        // If the player has no ammo and isn't creative, loading fails
        if (ammoStack.isEmpty() && isCreative) {
            ammoStack = new ItemStack(Items.ARROW); // Default fallback ammo stack for creative
        } else if (ammoStack.isEmpty()) {
            return false;
        }

        // Multishot enchantment loads 3 arrows instead of 1
        int multishotLevel = EnchantmentHelper.getLevel(Enchantments.MULTISHOT, crossbow);
        int projectilesToLoad = multishotLevel > 0 ? 3 : 1;

        // Copy ammunition details into crossbow's NBT storage tag configuration arrays
        NbtList chargedProjectilesList = new NbtList();

        // Process stack replication constraints
        ItemStack ammoToLoad = ammoStack.copy();
        ammoToLoad.setCount(1); // Individual arrow data models

        for (int i = 0; i < projectilesToLoad; i++) {
            NbtCompound ammoNbt = new NbtCompound();
            ammoToLoad.writeNbt(ammoNbt);
            chargedProjectilesList.add(ammoNbt);
        }

        // Write directly to the weapon compound data map structure
        NbtCompound weaponNbt = crossbow.getOrCreateNbt();
        weaponNbt.put("ChargedProjectiles", chargedProjectilesList);

        // Deduct ammunition natively if player is in survival mod context profiles
        if (!isCreative) {
            ammoStack.decrement(1);
            if (ammoStack.isEmpty() && user instanceof PlayerEntity player) {
                player.getInventory().removeOne(ammoStack);
            }
        }

        return true;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    public static void registerModCrossbow() {
        Turts_Turtastic_Crap.LOGGER.info("Registering Mod Items for " + Turts_Turtastic_Crap.MOD_ID);
    }
}
