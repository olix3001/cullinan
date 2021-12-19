/*
 * Copyright (c) 2021.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package com.cullinan.cullinanclient.gui;

import com.cullinan.cullinanclient.events.GUIRenderListener;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.opengl.GL11;

public class InGameHud implements GUIRenderListener {

    public final HackListHud hackListHud = new HackListHud();

    @Override
    public void onRenderGUI(MatrixStack matrixStack, float partialTicks) {
        boolean blend = GL11.glGetBoolean(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_CULL_FACE);

        hackListHud.render(matrixStack, partialTicks);

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        if (blend)
            GL11.glEnable(GL11.GL_BLEND);
        else GL11.glDisable(GL11.GL_BLEND);
    }
}
