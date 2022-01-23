/*
 * Copyright (c) 2022.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package com.cullinan.cullinanclient.events;

import com.cullinan.cullinanclient.event.CancellableEvent;
import com.cullinan.cullinanclient.event.Listener;

import java.util.ArrayList;
import java.util.Objects;

public interface ChatMessageListener extends Listener {
    public void onSentMessage(ChatMessageEvent event);

    public static class ChatMessageEvent extends CancellableEvent<ChatMessageListener> {
        private final String originalMessage;
        private String message;

        public ChatMessageEvent(String message) {
            Objects.requireNonNull(this.message = message);
            originalMessage = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getOriginalMessage() {
            return originalMessage;
        }

        public boolean isModified() {
            return !originalMessage.equals(message);
        }

        @Override
        public void fire(ArrayList<ChatMessageListener> listeners) {
            for (ChatMessageListener listener : listeners) {
                listener.onSentMessage(this);

                if (isCancelled()) break;
            }
        }

        @Override
        public Class<ChatMessageListener> getListenerType() {
            return ChatMessageListener.class;
        }
    }
}
