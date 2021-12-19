/*
 * Copyright (c) 2021.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package com.cullinan.cullinanclient.events;

import com.cullinan.cullinanclient.event.Event;
import com.cullinan.cullinanclient.event.Listener;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;

public interface RenderListener extends Listener {

    public void onRender(MatrixStack matrixStack, float partialTicks);

    public static class RenderEvent extends Event<RenderListener> {
        private final MatrixStack matrixStack;
        private final float partialTicks;

        public RenderEvent(MatrixStack matrixStack, float partialTicks) {
            this.matrixStack = matrixStack;
            this.partialTicks = partialTicks;
        }

        @Override
        public void fire(ArrayList<RenderListener> listeners) {
            for(RenderListener listener : listeners)
                listener.onRender(matrixStack, partialTicks);
        }

        @Override
        public Class<RenderListener> getListenerType() {
            return RenderListener.class;
        }
    }
}
