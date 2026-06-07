package net.turt.turtsturtasticcrap.mixin;

import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.turt.turtsturtasticcrap.item.ModCrossbowItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {

    @Redirect(
            method = "renderFirstPersonItem",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z")
    )
    private boolean redirectIsOf(ItemStack stack, Item item) {
        // If Minecraft is checking if the held item is a vanilla crossbow...
        if (item == Items.CROSSBOW) {
            // ...return true if it is a vanilla crossbow OR if it belongs to your custom class
            return stack.isOf(Items.CROSSBOW) || stack.getItem() instanceof ModCrossbowItem;
        }

        // Otherwise, fall back to default behavior
        return stack.isOf(item);
    }
}