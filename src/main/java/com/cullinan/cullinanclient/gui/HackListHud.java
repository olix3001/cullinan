/*
 * Copyright (c) 2021.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package com.cullinan.cullinanclient.gui;

import com.cullinan.cullinanclient.CullinanClient;
import com.cullinan.cullinanclient.client.CullinanClientClient;
import com.cullinan.cullinanclient.event.CullinanEvents;
import com.cullinan.cullinanclient.events.TickListener;
import com.cullinan.cullinanclient.hack.Hack;
import com.cullinan.cullinanclient.hack.HackList;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.Iterator;

public class HackListHud implements TickListener {

    private final ArrayList<HackEntry> activeHacks = new ArrayList<>();
    private int posY;

    public HackListHud() {
        CullinanEvents.getInstance().add(TickListener.class, this);
    }

    public void render(MatrixStack matrixStack, float partialTicks) {
        drawHackList(matrixStack, partialTicks);
    }

    private void drawHackList(MatrixStack matrixStack, float partialTicks) {
        posY = 4;
        for (HackEntry e : activeHacks)
                drawWithOffset(matrixStack, e, partialTicks);
    }

    private void drawWithOffset(MatrixStack matrixStack, HackEntry e, float partialTicks) {
        TextRenderer tr = CullinanClient.MINECRAFT.textRenderer;
        String s = e.hack.getName();

        float offset = e.offset * partialTicks + e.pOffset * (1-partialTicks);

        int screenW = CullinanClient.MINECRAFT.getWindow().getScaledWidth();
        int stringW = tr.getWidth(s);

        float posX = screenW - stringW - 2 + 5*offset;
        int alpha = (int)(255*(1-offset / 4)) << 24;

        tr.draw(matrixStack, s, posX, posY, e.hack.getColor() | alpha);

        posY += tr.fontHeight+1;
    }

    public void updateState() {
        for (Hack h : HackList.getInstance().getAllHacks())
            updateState(h);
    }

    public void updateState(Hack hack) {
        int offset = 4;
        HackEntry entry = new HackEntry(hack, offset);

        if (hack.isEnabled()) {
            if (activeHacks.contains(entry))
                    return;

            activeHacks.add(entry);
        }
    }

    @Override
    public void onTick() {
        for (Iterator<HackEntry> i = activeHacks.iterator(); i.hasNext();) {
            HackEntry e = i.next();
            boolean enabled = e.hack.isEnabled();
            e.pOffset = e.offset;

            if (enabled && e.offset > 0)
                --e.offset;
            else if (!enabled && e.offset < 4)
                ++e.offset;
            else if (!enabled)
                i.remove();
        }
    }

    private static final class HackEntry {
        private final Hack hack;
        private int offset;
        private int pOffset;

        public HackEntry(Hack hack, int offset) {
            this.hack = hack;
            this.offset = offset;
            this.pOffset = offset;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof HackEntry other)) return false;

            return hack == other.hack;
        }
    }
}
