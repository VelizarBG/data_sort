package com.velizarbg.execute_output.mixins;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerCommandSource.class)
public class LogCommandOutputMixin {

    @Inject(method = "sendFeedback", at = @At("HEAD"))
    private void injected(Text message, boolean broadcastToOps, CallbackInfo ci) {
        System.out.println(message.getString());
    }
}
