/*
 * Copyright (c) 2021.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package com.cullinan.cullinanclient.mixin;

import com.cullinan.cullinanclient.event.CullinanEvents;
import com.cullinan.cullinanclient.events.GUIRenderListener;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin extends DrawableHelper {
    @Inject(
            at = @At(value = "HEAD", target = "Lcom/mojang/blaze3d/systems/RenderSystem;enableBlend()V"),
            method = "render")
    private void onRender(MatrixStack matrixStack, float partialTicks, CallbackInfo ci) {
        GUIRenderListener.GUIRenderEvent event = new GUIRenderListener.GUIRenderEvent(matrixStack, partialTicks);
        CullinanEvents.fire(event);
    }
}
