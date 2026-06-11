package net.turt.turtsturtasticcrap.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.Property;
import net.minecraft.screen.ScreenHandlerType;
import net.turt.turtsturtasticcrap.util.ModEnchantments;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.Set;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {

    public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, net.minecraft.screen.ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
    }

    @Inject(method = "updateResult", at = @At("TAIL"))
    private void enforceStrictCustomEnchantmentRestrictions(CallbackInfo ci) {
        ItemStack inputItem = this.input.getStack(0);
        ItemStack outputItem = this.output.getStack(0);

        if (!outputItem.isEmpty() && !inputItem.isEmpty()) {
            String itemRegistryName = Registries.ITEM.getId(inputItem.getItem()).toString().toLowerCase();

            int thunderLevel = EnchantmentHelper.getLevel(ModEnchantments.GOD_OF_THUNDER, outputItem);
            if (thunderLevel > 0) {
                if (itemRegistryName.contains("trident") || itemRegistryName.contains("bow") || itemRegistryName.contains("crossbow") || itemRegistryName.contains("arbalest")) {
                    this.output.setStack(0, ItemStack.EMPTY);
                    this.sendContentUpdates();
                    return;
                }
            }

            int seaGodLevel = EnchantmentHelper.getLevel(ModEnchantments.GOD_OF_THE_SEA, outputItem);
            if (seaGodLevel > 0) {
                if (!inputItem.isOf(Items.TRIDENT)) {
                    this.output.setStack(0, ItemStack.EMPTY);
                    this.sendContentUpdates();
                }
            }
        }
    }
}