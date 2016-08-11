package com.guard13007.periodic.core;

import com.guard13007.periodic.Periodic;
import com.guard13007.periodic.core.handler.PeriodicGuiHandler;

import cpw.mods.fml.common.network.NetworkRegistry;

public class Proxy {
    public boolean canSeeIO;
    public void preInit() {
        canSeeIO = false;
    }

    public void init() {
        NetworkRegistry.INSTANCE.registerGuiHandler(Periodic.instance, new PeriodicGuiHandler());
    }

    public void postInit() {

    }
}
