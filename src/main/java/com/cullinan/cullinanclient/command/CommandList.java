/*
 * Copyright (c) 2022.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package com.cullinan.cullinanclient.command;

import com.cullinan.cullinanclient.commands.SettingsCommand;
import com.cullinan.cullinanclient.commands.TestCommand;
import com.cullinan.cullinanclient.commands.ToggleCommand;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.TreeMap;

public class CommandList {

    public TestCommand testCommand = new TestCommand();
    public ToggleCommand toggleCommand = new ToggleCommand();
    public SettingsCommand settingsCommand = new SettingsCommand();


    private final TreeMap<String, Command> commands =
            new TreeMap<>(String::compareToIgnoreCase);
    private static CommandList instance;

    public static CommandList getInstance() {
        if(instance == null) instance = new CommandList();
        return instance;
    }

    private CommandList() {
        try
        {
            for(Field field : CommandList.class.getDeclaredFields())
            {
                if(!field.getName().endsWith("Command"))
                    continue;

                Command command = (Command)field.get(this);
                commands.put(command.getName(), command);
            }

        }catch(Exception e)
        {
            e.printStackTrace();

            String message = "Initializing Cullinan commands";
            CrashReport report = CrashReport.create(e, message);
            throw new CrashException(report);
        }
    }

    public Command getCommandByName(String name) {
        return commands.get(name);
    }

    public Collection<Command> getAllCommands()
    {
        return Collections.unmodifiableCollection(commands.values());
    }
}
