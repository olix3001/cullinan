/*
 * Copyright (c) 2022.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package com.cullinan.cullinanclient.commands;

import com.cullinan.cullinanclient.command.Command;
import com.cullinan.cullinanclient.command.CommandList;
import com.cullinan.cullinanclient.command.CommandResult;
import com.cullinan.cullinanclient.hack.Hack;
import com.cullinan.cullinanclient.hack.HackList;

public class ToggleCommand extends Command {
    public ToggleCommand() {
        super("toggle", "Toggles specified chat :uwu:");
    }

    @Override
    public CommandResult execute(String[] args) {
        if (args.length != 1) return new CommandResult(false, "This command takes exactly one argument");
        Hack hack = HackList.getInstance().getHackByName(args[0]);
        if (hack == null) return new CommandResult(false, "Specified hack do not exist");
        hack.setEnabled(!hack.isEnabled());
        return new CommandResult(true, "Hack " + hack.getName() + " toggled successfully");
    }
}
