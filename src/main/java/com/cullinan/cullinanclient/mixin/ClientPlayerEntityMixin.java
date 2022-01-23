/*
 * Copyright (c) 2021.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package com.cullinan.cullinanclient.mixin;

import com.cullinan.cullinanclient.CullinanClient;
import com.cullinan.cullinanclient.event.CullinanEvents;
import com.cullinan.cullinanclient.events.ChatMessageListener;
import com.cullinan.cullinanclient.events.TickListener;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {

    @Shadow @Final public ClientPlayNetworkHandler networkHandler;

    public ClientPlayerEntityMixin(CullinanClient cullinan, ClientWorld clientWorld,
                                   GameProfile gameProfile)
    {
        super(clientWorld, gameProfile);
    }

    @Inject(at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;tick()V",
            ordinal = 0), method = "tick()V")
    private void onTick(CallbackInfo ci)
    {
        CullinanEvents.fire(TickListener.TickEvent.INSTANCE);
    }

    @Inject(at = @At("HEAD"), method = "sendChatMessage", cancellable = true)
    private void onSendChatMessage(String message, CallbackInfo ci) {
        ChatMessageListener.ChatMessageEvent event = new ChatMessageListener.ChatMessageEvent(message);
        CullinanEvents.fire(event);

        if (event.isCancelled()) {
            ci.cancel();
            return;
        }

        if (!event.isModified())
            return;

        ChatMessageC2SPacket packet = new ChatMessageC2SPacket(event.getMessage());
        networkHandler.sendPacket(packet);
        ci.cancel();
    }
}
