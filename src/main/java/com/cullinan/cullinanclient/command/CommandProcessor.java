/*
 * Copyright (c) 2022.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package com.cullinan.cullinanclient.command;

import com.cullinan.cullinanclient.CullinanClient;
import com.cullinan.cullinanclient.events.ChatMessageListener;
import net.minecraft.network.MessageType;
import net.minecraft.text.LiteralText;

import java.util.Arrays;

public class CommandProcessor implements ChatMessageListener {

    public boolean process(String command) {
        command = command.trim();
        String[] parts = command.split(" ");
        if (parts.length < 1) return false;
        if (!parts[0].startsWith(".")) return false;

        executeCommand(parts[0].substring(1), Arrays.copyOfRange(parts, 1, parts.length));
        return true;
    }

    private void executeCommand(String command, String[] args) {
        Command cmd = CommandList.getInstance().getCommandByName(command);
        if (cmd == null) return;
        CommandResult result = cmd.execute(args);

        if (!result.isSuccess()) {
            CullinanClient.MINECRAFT.inGameHud.addChatMessage(
                    MessageType.SYSTEM,
                    new LiteralText("\\u001b[31mError: " + result.getMessage()),
                    CullinanClient.MINECRAFT.player.getUuid()
            );
            return;
        }

        CullinanClient.MINECRAFT.inGameHud.addChatMessage(
                MessageType.SYSTEM,
                new LiteralText(result.getMessage()),
                CullinanClient.MINECRAFT.player.getUuid()
        );
    }

    @Override
    public void onSentMessage(ChatMessageEvent event) {
        if(process(event.getMessage())) event.setCancelled(true);
    }
}
