package com.guard13007.periodic.client;

import com.guard13007.periodic.client.events.TickHandlerClient;
import com.guard13007.periodic.client.render.RenderIO;
import com.guard13007.periodic.core.ModVars;
import com.guard13007.periodic.core.Proxy;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;

public class ClientProxy extends Proxy {
    @Override
    public void preInit() {
        super.preInit();
    }

    @Override
    public void init() {
        super.init();
        ModVars.ioRenderID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new RenderIO());

        FMLCommonHandler.instance().bus().register(new TickHandlerClient());
    }

    @Override
    public void postInit() {
        super.postInit();
    }
}
