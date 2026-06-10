package net.turt.turtsturtasticcrap.mixin;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.turt.turtsturtasticcrap.util.IceAspectManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(MinecraftServer.class)
public class ServerTickMixin {

    @Inject(method = "tickWorlds", at = @At("TAIL"))
    private void onTickWorlds(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        MinecraftServer server = (MinecraftServer) (Object) this;
        ServerWorld overworld = server.getOverworld();
        if (overworld != null) {
            IceAspectManager.tick(overworld);
        }
    }
}
