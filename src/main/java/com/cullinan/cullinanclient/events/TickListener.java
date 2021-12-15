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

import java.util.ArrayList;

public interface TickListener extends Listener {

    public void onTick();

    public static class TickEvent extends Event<TickListener> {
        public static final TickEvent INSTANCE = new TickEvent();

        @Override
        public void fire(ArrayList<TickListener> listeners) {
            for(TickListener listener : listeners)
                listener.onTick();
        }

        @Override
        public Class<TickListener> getListenerType() {
            return TickListener.class;
        }
    }
}
