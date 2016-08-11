package com.guard13007.periodic.client.events;

import com.guard13007.periodic.Periodic;
import com.guard13007.periodic.items.ModItems;

import net.minecraft.entity.player.EntityPlayer;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class TickHandlerClient {
    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;
        if (event.phase == TickEvent.Phase.END) {
            if (player.getCurrentEquippedItem() != null) {
                if (player.getCurrentEquippedItem().getItem() != null) {
                    Periodic.proxy.canSeeIO = player.getCurrentEquippedItem().getItem() == ModItems.mid;
                    if (player.getCurrentEquippedItem().getItem() == ModItems.mid) {

                    }
                }
            } else
                Periodic.proxy.canSeeIO = false;
        }
    }
}
