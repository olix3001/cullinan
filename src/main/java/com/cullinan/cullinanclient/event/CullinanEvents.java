/*
 * Copyright (c) 2021.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package com.cullinan.cullinanclient.event;

import com.cullinan.cullinanclient.CullinanClient;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CullinanEvents {
    private HashMap<Class<? extends Listener>, ArrayList<? extends Listener>> listenerMap = new HashMap<>();
    private final CullinanClient cullinan;

    public static <L extends Listener, E extends Event<L>> void fire(E event) {
        if (instance == null) getInstance();
        instance.fireImpl(event);
    }
    public <L extends Listener, E extends Event<L>> void fireImpl(E event) {
        if (!cullinan.isEnabled()) return;

        try {
            Class<L> type = event.getListenerType();
            @SuppressWarnings("unchecked")
            ArrayList<L> listeners = (ArrayList<L>) listenerMap.get(type);

            if (listeners == null || listeners.isEmpty()) return;

            event.fire(listeners);
        } catch (Throwable e) {
            e.printStackTrace();

            CrashReport report = CrashReport.create(e, "Firing Cullinan event");
            CrashReportSection section = report.addElement("Affected event");
            section.add("Event class", () -> event.getClass().getName());

            throw new CrashException(report);
        }
    }

    public <L extends Listener> void add(Class<L> type, L listener) {
        try {
            @SuppressWarnings("unchecked")
            ArrayList<L> listeners = (ArrayList<L>) listenerMap.get(type);

            if (listeners == null) {
                listeners = new ArrayList<>(List.of(listener));
                listenerMap.put(type, listeners);
                return;
            }
            listeners.add(listener);
        } catch (Throwable e) {
            e.printStackTrace();

            CrashReport report =
                    CrashReport.create(e, "Adding Cullinan event listener");
            CrashReportSection section = report.addElement("Affected listener");
            section.add("Listener type", type::getName);
            section.add("Listener class", () -> listener.getClass().getName());

            throw new CrashException(report);
        }
    }

    public <L extends Listener> void remove(Class<L> type, L listener) {
        try {
            @SuppressWarnings("unchecked")
            ArrayList<L> listeners = (ArrayList<L>) listenerMap.get(type);

            if (listeners != null) listeners.remove(listener);
        } catch (Throwable e) {
            e.printStackTrace();

            CrashReport report =
                    CrashReport.create(e, "Removing Cullinan event listener");
            CrashReportSection section = report.addElement("Affected listener");
            section.add("Listener type", type::getName);
            section.add("Listener class", () -> listener.getClass().getName());

            throw new CrashException(report);
        }
    }

    private static CullinanEvents instance;

    private CullinanEvents() {
        this.cullinan = CullinanClient.getInstance();
    }

    public static CullinanEvents getInstance() {
        if (instance == null) instance = new CullinanEvents();
        return instance;
    }
}
