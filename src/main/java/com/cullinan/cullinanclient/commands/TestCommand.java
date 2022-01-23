/*
 * Copyright (c) 2022.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package com.cullinan.cullinanclient.commands;

import com.cullinan.cullinanclient.command.Command;
import com.cullinan.cullinanclient.command.CommandResult;

public class TestCommand extends Command {

    public TestCommand() {
        super("test", "This is a test command");
    }

    @Override
    public CommandResult execute(String[] args) {
        return new CommandResult(true, "This is a test command");
    }
}
