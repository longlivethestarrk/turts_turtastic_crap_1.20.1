package net.turt.turtsturtasticcrap.util;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

import java.util.HashSet;
import java.util.Set;

public class IceAspectManager {

    public static final Set<LivingEntity> FROZEN_ENTITIES = new HashSet<>();

    public static void onEntityHit(LivingEntity attacker, LivingEntity target) {
        int level = EnchantmentHelper.getLevel(ModEnchantments.ICE_ASPECT, attacker.getMainHandStack());

        if (level > 0) {
            boolean isImmuneViaTag = target.getType().getRegistryEntry().isIn(ModTags.Entities.ICE_ASPECT_DAMAGE_IMMUNE);
            boolean isColdVanillaMob = (target.getType() == EntityType.STRAY ||
                    target.getType() == EntityType.SNOW_GOLEM ||
                    target.getType() == EntityType.POLAR_BEAR);
            boolean isImmuneEntity = isImmuneViaTag || isColdVanillaMob;

            int matchingArmorPieces = 0;
            for (ItemStack armorItem : target.getArmorItems()) {
                if (!armorItem.isEmpty() && armorItem.isIn(ItemTags.FREEZE_IMMUNE_WEARABLES)) {
                    matchingArmorPieces++;
                }
            }
            boolean isWearingFullLeather = (matchingArmorPieces == 4);

            boolean isLeatherArmoredHorse = false;
            if (target instanceof HorseEntity horse) {
                ItemStack horseArmor = horse.getArmorType();
                if (!horseArmor.isEmpty() && horseArmor.isOf(Items.LEATHER_HORSE_ARMOR)) {
                    isLeatherArmoredHorse = true;
                }
            }

            if (isImmuneEntity || isWearingFullLeather || isLeatherArmoredHorse) {
                return;
            }

            int duration = level * 60;
            int amplifier = level - 1;

            target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, duration, amplifier, false, false));
            target.setFrozenTicks(140);

            FROZEN_ENTITIES.add(target);
            target.getWorld().playSound(null, target.getBlockPos(), SoundEvents.BLOCK_POWDER_SNOW_STEP, SoundCategory.PLAYERS, 1.0F, 1.0F);
        }
    }

    public static void tick(ServerWorld world) {
        FROZEN_ENTITIES.removeIf(target -> {
            if (!target.isAlive() || !target.hasStatusEffect(StatusEffects.SLOWNESS)) {
                return true;
            }

            int remainingTicks = target.getStatusEffect(StatusEffects.SLOWNESS).getDuration();
            int level = target.getStatusEffect(StatusEffects.SLOWNESS).getAmplifier() + 1;
            boolean shouldDamage = false;

            if (level == 1) {
                if (remainingTicks == 40 || remainingTicks == 20 || remainingTicks == 1) {
                    shouldDamage = true;
                }
            }
            else if (level == 2) {
                if (remainingTicks == 100 || remainingTicks == 80 || remainingTicks == 60 ||
                        remainingTicks == 40 || remainingTicks == 20 || remainingTicks == 1) {
                    shouldDamage = true;
                }
            }
            //Hard check mob immunity
            if (shouldDamage) {
                boolean isImmuneViaTag = target.getType().getRegistryEntry().isIn(ModTags.Entities.ICE_ASPECT_DAMAGE_IMMUNE);
                boolean isColdVanillaMob = (target.getType() == EntityType.STRAY ||
                        target.getType() == EntityType.SNOW_GOLEM ||
                        target.getType() == EntityType.POLAR_BEAR);
                boolean isImmuneEntity = isImmuneViaTag || isColdVanillaMob;

                if (!isImmuneEntity) {
                    //Standard check armor slots for players/humanoids immunity
                    int matchingArmorPieces = 0;
                    for (ItemStack armorItem : target.getArmorItems()) {
                        if (!armorItem.isEmpty() && armorItem.isIn(ItemTags.FREEZE_IMMUNE_WEARABLES)) {
                            matchingArmorPieces++;
                        }
                    }

                    float baseDamage = 1.0F;
                    float damageReduction = matchingArmorPieces * 0.25F;
                    float finalDamage = baseDamage - damageReduction;

                    // Vulnerability Check
                    if (target.getType().getRegistryEntry().isIn(ModTags.Entities.ICE_ASPECT_DAMAGE_VULNERABLE)) {
                        finalDamage *= 2.0F;
                    }

                    if (target instanceof HorseEntity horse) {
                        ItemStack horseArmor = horse.getArmorType();
                        if (!horseArmor.isEmpty() && horseArmor.isOf(Items.LEATHER_HORSE_ARMOR)) {
                            finalDamage = 0.0F;
                        }
                    }

                    if (finalDamage > 0.0F) {
                        target.damage(world.getDamageSources().freeze(), finalDamage);
                        target.hurtTime = 10;
                        target.maxHurtTime = 10;

                        int particleCount = Math.round(finalDamage * 15);
                        world.spawnParticles(
                                ParticleTypes.SNOWFLAKE,
                                target.getX(),
                                target.getBodyY(0.5),
                                target.getZ(),
                                particleCount,
                                0.3, 0.5, 0.3,
                                0.02
                        );
                    }
                }
            }

            target.setFrozenTicks(140);
            return false;
        });
    }
}