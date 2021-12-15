package com.cullinan.cullinanclient;

import com.cullinan.cullinanclient.hack.HackList;
import net.fabricmc.api.ModInitializer;

public class CullinanClient implements ModInitializer {

    private static CullinanClient instance;
    private boolean enabled = true;

    @Override
    public void onInitialize() {
        instance = this;
        HackList.getInstance(); //initialize hacklist
    }

    public static CullinanClient getInstance() {
        return instance;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
